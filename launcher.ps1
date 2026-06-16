
param()
$ErrorActionPreference = 'Stop'

$ROOT     = "d:\Microservice\Microservice"
$LOGDIR   = "$ROOT\logs"
$KAFKA    = "C:\kafka"
$FRONTEND = "$ROOT\frontend\godavari-app"

if (-not (Test-Path $LOGDIR)) { New-Item -ItemType Directory -Path $LOGDIR | Out-Null }

# --------------------------------------------------------------------------
# Helper functions
# --------------------------------------------------------------------------
function Write-Header {
    Clear-Host
    Write-Host ""
    Write-Host "  ================================================================" -ForegroundColor Cyan
    Write-Host "    Godavari Building Materials Nashik" -ForegroundColor Yellow
    Write-Host "    Application Launcher v4.0  (PowerShell)" -ForegroundColor Yellow
    Write-Host "  ================================================================" -ForegroundColor Cyan
    Write-Host ""
}

function Write-OK   { param($msg) Write-Host "   [  OK  ] $msg" -ForegroundColor Green    }
function Write-Skip { param($msg) Write-Host "   [ SKIP ] $msg" -ForegroundColor DarkGray }
function Write-Warn { param($msg) Write-Host "   [ WARN ] $msg" -ForegroundColor Yellow   }
function Write-Info { param($msg) Write-Host "   [  ..  ] $msg" -ForegroundColor Cyan     }

function Test-Port {
    param($port)
    $r = netstat -ano 2>$null | Select-String ":$port\s.*LISTENING"
    return ($null -ne $r)
}

function Wait-Port {
    param($port, $maxSec, $label)
    Write-Host "   Waiting for $label (port $port): " -NoNewline -ForegroundColor Cyan
    for ($i = 1; $i -le $maxSec; $i++) {
        if (Test-Port $port) {
            Write-Host " UP in ${i}s" -ForegroundColor Green
            return
        }
        Write-Host "." -NoNewline -ForegroundColor DarkGray
        Start-Sleep -Seconds 1
    }
    Write-Host " TIMEOUT - continuing anyway" -ForegroundColor Yellow
}

function Stop-Port {
    param($port)
    $lines = netstat -ano 2>$null | Select-String ":$port\s.*LISTENING"
    foreach ($line in $lines) {
        $procId = ($line -split '\s+')[-1].Trim()
        if ($procId -match '^\d+$') {
            Stop-Process -Id ([int]$procId) -Force -ErrorAction SilentlyContinue
        }
    }
}

function Show-Progress {
    param($seconds)
    $steps = 10
    $each  = [math]::Floor($seconds / $steps)
    Write-Host "   [" -NoNewline -ForegroundColor Cyan
    for ($i = 1; $i -le $steps; $i++) {
        Start-Sleep -Seconds $each
        Write-Host "##" -NoNewline -ForegroundColor Green
    }
    Write-Host "]" -ForegroundColor Cyan
}

function Run-Background {
    param($dir, $cmd, $log)
    Start-Process -FilePath "cmd" `
        -ArgumentList "/c cd /d `"$dir`" && $cmd > `"$LOGDIR\$log`" 2>&1" `
        -WindowStyle Hidden
}

function Show-Status {
    $services = @(
        @{ Port=8888; Name="Config Server   " },
        @{ Port=8761; Name="Eureka Server   " },
        @{ Port=8081; Name="User Service    " },
        @{ Port=8082; Name="Product Service " },
        @{ Port=8083; Name="Order Service   " },
        @{ Port=8084; Name="Inventory Svc   " },
        @{ Port=8085; Name="Quotation Svc   " },
        @{ Port=8086; Name="Billing Service " },
        @{ Port=8087; Name="API Gateway     " },
        @{ Port=8089; Name="Notification Svc" },
        @{ Port=4200; Name="Angular App     " },
        @{ Port=9090; Name="Log Server      " },
        @{ Port=9091; Name="WhatsApp Server " },
        @{ Port=9092; Name="Kafka Broker    " }
    )
    foreach ($s in $services) {
        if (Test-Port $s.Port) {
            Write-Host "   [  UP  ]  $($s.Name)  --  http://localhost:$($s.Port)" -ForegroundColor Green
        } else {
            Write-Host "   [ DOWN ]  $($s.Name)  --  port $($s.Port) not ready" -ForegroundColor DarkGray
        }
    }
}

# --------------------------------------------------------------------------
# Start Kafka (background, no window)
# --------------------------------------------------------------------------
function Start-Kafka {
    if (-not (Test-Path $KAFKA)) { Write-Skip "Kafka not found at $KAFKA"; return }

    Write-Info "Cleaning up existing Kafka/Zookeeper..."
    Stop-Port 9092; Stop-Port 2181
    Start-Sleep -Seconds 2
    @("C:\tmp\zookeeper","C:\tmp\kafka-logs","$KAFKA\kafka-logs","$KAFKA\zookeeper-data") | ForEach-Object {
        if (Test-Path $_) { Remove-Item $_ -Recurse -Force -ErrorAction SilentlyContinue }
    }

    Write-Info "Starting Zookeeper..."
    Run-Background $KAFKA "bin\windows\zookeeper-server-start.bat config\zookeeper.properties" "zookeeper.log"
    Start-Sleep -Seconds 12

    Write-Info "Starting Kafka Broker..."
    Run-Background $KAFKA "bin\windows\kafka-server-start.bat config\server.properties" "kafka.log"
    Start-Sleep -Seconds 10

    Write-OK "Kafka started."
}

# --------------------------------------------------------------------------
# Start all backend microservices (staged)
# --------------------------------------------------------------------------
function Start-Backend {

    # Redis
    Write-Host ""
    Write-Info "Redis (port 6379)..."
    if (Get-Command redis-server -ErrorAction SilentlyContinue) {
        Start-Process -FilePath "redis-server" `
            -RedirectStandardOutput "$LOGDIR\redis.log" `
            -RedirectStandardError  "$LOGDIR\redis.log" `
            -WindowStyle Hidden
        Write-OK "Redis started."
    } else {
        Write-Skip "Redis not found."
    }

    # Stage 1 - Config Server
    Write-Host ""
    Write-Host "   [Stage 1/3] Config Server (port 8888)" -ForegroundColor Cyan
    Run-Background "$ROOT\config-server" "mvn spring-boot:run" "config-server.log"
    Wait-Port 8888 90 "Config Server"

    # Stage 2 - Eureka
    Write-Host ""
    Write-Host "   [Stage 2/3] Eureka Server (port 8761)" -ForegroundColor Cyan
    Run-Background "$ROOT\eureka-server" "mvn spring-boot:run" "eureka-server.log"
    Wait-Port 8761 90 "Eureka Server"

    # Stage 3 - Business services
    Write-Host ""
    Write-Host "   [Stage 3/3] Starting all business services..." -ForegroundColor Cyan
    Write-Host ""

    $services = @(
        @{ Name="User Service    "; Port=8081; Dir="user-service";         Log="user-service.log"         },
        @{ Name="Product Service "; Port=8082; Dir="product-service";      Log="product-service.log"      },
        @{ Name="Order Service   "; Port=8083; Dir="order-service";        Log="order-service.log"        },
        @{ Name="Inventory Svc   "; Port=8084; Dir="inventory-service";    Log="inventory-service.log"    },
        @{ Name="Quotation Svc   "; Port=8085; Dir="quotation-service";    Log="quotation-service.log"    },
        @{ Name="Billing Service "; Port=8086; Dir="billing-service";      Log="billing-service.log"      },
        @{ Name="API Gateway     "; Port=8087; Dir="api-gateway";          Log="api-gateway.log"          },
        @{ Name="Notification Svc"; Port=8089; Dir="notification-service"; Log="notification-service.log" }
    )

    foreach ($svc in $services) {
        Run-Background "$ROOT\$($svc.Dir)" "mvn spring-boot:run" $svc.Log
        Write-Host "   [ >> ] $($svc.Name)  (port $($svc.Port))" -ForegroundColor DarkCyan
    }

    Write-Host ""
    Write-Info "All services launched. Waiting 2 minutes for full startup..."
    Show-Progress 120

    Write-Host ""
    Write-Host "   Status:" -ForegroundColor Cyan
    Write-Host "   ----------------------------------------------------------------" -ForegroundColor DarkGray
    Show-Status
}

# --------------------------------------------------------------------------
# MENU LOOP
# --------------------------------------------------------------------------
while ($true) {
    Write-Header

    Write-Host "   SERVICES" -ForegroundColor White
    Write-Host "   ----------------------------------------------------------------" -ForegroundColor DarkGray
    Write-Host "   [1]  Start Backend Only        (10 Microservices)" -ForegroundColor White
    Write-Host "   [2]  Start Frontend            (Angular - port 4200)" -ForegroundColor White
    Write-Host "   [3]  Start Log Server          (port 9090)" -ForegroundColor White
    Write-Host "   [4]  Start WhatsApp Server     (WPPConnect - port 9091)" -ForegroundColor White
    Write-Host "   [5]  Start Everything          (Kafka + Backend + Frontend + Logs + WhatsApp)" -ForegroundColor White
    Write-Host ""
    Write-Host "   QUICK OPEN" -ForegroundColor White
    Write-Host "   ----------------------------------------------------------------" -ForegroundColor DarkGray
    Write-Host "   [S]  Open Swagger UI           (http://localhost:8087/swagger-ui/index.html)" -ForegroundColor White
    Write-Host "   [E]  Open Eureka Dashboard     (http://localhost:8761)" -ForegroundColor White
    Write-Host "   [L]  Open Log Viewer           (http://localhost:4200/admin/logs)" -ForegroundColor White
    Write-Host ""
    Write-Host "   MONITORING" -ForegroundColor White
    Write-Host "   ----------------------------------------------------------------" -ForegroundColor DarkGray
    Write-Host "   [6]  Check Service Status" -ForegroundColor White
    Write-Host "   [7]  View Live Logs            (tail in this window)" -ForegroundColor White
    Write-Host ""
    Write-Host "   CONTROL" -ForegroundColor White
    Write-Host "   ----------------------------------------------------------------" -ForegroundColor DarkGray
    Write-Host "   [9]  Stop All Services" -ForegroundColor White
    Write-Host "   [0]  Exit" -ForegroundColor White
    Write-Host ""

    $choice = (Read-Host "   Enter choice").ToUpper().Trim()

    switch ($choice) {

        "1" {
            Write-Header
            Write-Host "   Starting 10 Backend Microservices..." -ForegroundColor Cyan
            Start-Backend
            Write-Host ""
            Read-Host "   Press Enter to return to menu"
        }

        "2" {
            Write-Header
            Write-Info "Starting Angular Frontend (port 4200)..."
            if (-not (Test-Path "$FRONTEND\node_modules")) {
                Write-Warn "node_modules missing - running npm install first..."
                Start-Process "cmd" -ArgumentList "/c cd /d `"$FRONTEND`" && npm install >> `"$LOGDIR\frontend.log`" 2>&1" -WindowStyle Hidden -Wait
            }
            Run-Background $FRONTEND "npx ng serve --port 4200" "frontend.log"
            Write-OK "Frontend started in background."
            Write-Host "   URL: http://localhost:4200" -ForegroundColor Cyan
            Start-Sleep -Seconds 2
        }

        "3" {
            Write-Header
            Write-Info "Starting Log Server (port 9090)..."
            Stop-Port 9090
            Run-Background $ROOT "node log-server.js" "log-server.log"
            Write-OK "Log Server started."
            Write-Host "   API: http://localhost:9090/logs" -ForegroundColor Cyan
            Start-Sleep -Seconds 2
        }

        "4" {
            Write-Header
            Write-Info "Starting WhatsApp Server (port 9091)..."
            if (-not (Test-Path "$ROOT\node_modules\@wppconnect-team")) {
                Write-Warn "WPPConnect not installed - running npm install first..."
                Start-Process "cmd" -ArgumentList "/c cd /d `"$ROOT`" && npm install >> `"$LOGDIR\whatsapp-server.log`" 2>&1" -WindowStyle Hidden -Wait
            }
            Run-Background $ROOT "node whatsapp-server.js" "whatsapp-server.log"
            Write-OK "WhatsApp Server started."
            Write-Host "   QR code (first time): $LOGDIR\whatsapp-server.log" -ForegroundColor Yellow
            Write-Host "   Status: http://localhost:9091/status" -ForegroundColor Cyan
            Start-Sleep -Seconds 2
        }

        "5" {
            Write-Header
            Write-Host "   Starting Everything..." -ForegroundColor Cyan
            Write-Host ""

            Start-Kafka

            Write-Host ""
            Write-Info "Starting WhatsApp Server..."
            Run-Background $ROOT "node whatsapp-server.js" "whatsapp-server.log"
            Write-OK "WhatsApp Server started."

            Write-Host ""
            Write-Info "Starting Log Server..."
            Stop-Port 9090
            Run-Background $ROOT "node log-server.js" "log-server.log"
            Write-OK "Log Server started."

            Start-Backend

            Write-Host ""
            Write-Info "Waiting 60s then starting Frontend..."
            Start-Sleep -Seconds 60
            Run-Background $FRONTEND "npx ng serve --port 4200" "frontend.log"
            Write-OK "Frontend started."

            Write-Host ""
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host "   Everything running in background  (this window only)" -ForegroundColor Green
            Write-Host "   Kafka    : localhost:9092" -ForegroundColor White
            Write-Host "   Config   : http://localhost:8888" -ForegroundColor White
            Write-Host "   Eureka   : http://localhost:8761" -ForegroundColor White
            Write-Host "   API GW   : http://localhost:8087" -ForegroundColor White
            Write-Host "   Swagger  : http://localhost:8087/swagger-ui/index.html" -ForegroundColor White
            Write-Host "   Frontend : http://localhost:4200" -ForegroundColor White
            Write-Host "   WhatsApp : http://localhost:9091/status" -ForegroundColor White
            Write-Host "   Logs     : http://localhost:9090/logs" -ForegroundColor White
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host ""
            Read-Host "   Press Enter to return to menu"
        }

        "S" {
            if (Test-Port 8087) {
                Start-Process "http://localhost:8087/swagger-ui/index.html"
            } else {
                Write-Warn "API Gateway not running. Start backend first."
                Start-Sleep -Seconds 2
            }
        }

        "E" { Start-Process "http://localhost:8761" }

        "L" {
            if (-not (Test-Port 9090)) {
                Write-Warn "Log Server not running - starting it..."
                Run-Background $ROOT "node log-server.js" "log-server.log"
                Start-Sleep -Seconds 3
            }
            Start-Process "http://localhost:4200/admin/logs"
        }

        "6" {
            Write-Header
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host "   Service Status" -ForegroundColor Yellow
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host ""
            Show-Status
            Write-Host ""
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host "   Config   : http://localhost:8888" -ForegroundColor White
            Write-Host "   Eureka   : http://localhost:8761" -ForegroundColor White
            Write-Host "   Swagger  : http://localhost:8087/swagger-ui/index.html" -ForegroundColor White
            Write-Host "   Frontend : http://localhost:4200" -ForegroundColor White
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host ""
            Read-Host "   Press Enter to return to menu"
        }

        "7" {
            Write-Header
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host "   Live Log Viewer  (Ctrl+C to stop tailing)" -ForegroundColor Yellow
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host ""
            Write-Host "   [1] Config Server      [6] Inventory Service" -ForegroundColor White
            Write-Host "   [2] Eureka Server       [7] Quotation Service" -ForegroundColor White
            Write-Host "   [3] User Service        [8] Billing Service" -ForegroundColor White
            Write-Host "   [4] Product Service     [9] API Gateway" -ForegroundColor White
            Write-Host "   [5] Order Service       [A] Notification Service" -ForegroundColor White
            Write-Host "   [0] Back to Menu" -ForegroundColor DarkGray
            Write-Host ""

            $lc = (Read-Host "   Choose").ToUpper().Trim()
            $lf = switch ($lc) {
                "1" { "config-server.log"        }
                "2" { "eureka-server.log"         }
                "3" { "user-service.log"          }
                "4" { "product-service.log"       }
                "5" { "order-service.log"         }
                "6" { "inventory-service.log"     }
                "7" { "quotation-service.log"     }
                "8" { "billing-service.log"       }
                "9" { "api-gateway.log"           }
                "A" { "notification-service.log"  }
                default { $null }
            }
            if ($lf) {
                $path = "$LOGDIR\$lf"
                Clear-Host
                Write-Host "   Tailing: $path   (Ctrl+C to stop)" -ForegroundColor Cyan
                Write-Host ""
                if (Test-Path $path) {
                    Get-Content $path -Wait -Tail 40
                } else {
                    Write-Warn "Log not found yet: $path"
                    Start-Sleep -Seconds 2
                }
            }
        }

        "9" {
            Write-Header
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host "   Stopping All Services" -ForegroundColor Yellow
            Write-Host "  ================================================================" -ForegroundColor Cyan
            Write-Host ""

            Write-Info "Stopping Kafka..."
            Stop-Port 9092; Stop-Port 2181
            @("C:\tmp\zookeeper","C:\tmp\kafka-logs") | ForEach-Object {
                if (Test-Path $_) { Remove-Item $_ -Recurse -Force -ErrorAction SilentlyContinue }
            }
            Write-OK "Kafka stopped."

            Write-Info "Stopping Java (microservices)..."
            Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force
            Write-OK "Java stopped."

            Write-Info "Stopping Node (log + whatsapp)..."
            Get-Process -Name "node" -ErrorAction SilentlyContinue | Stop-Process -Force
            Write-OK "Node stopped."

            Write-Info "Stopping Angular (port 4200)..."
            Stop-Port 4200
            Write-OK "Frontend stopped."

            Write-Host ""
            Write-Host "   All services stopped." -ForegroundColor Green
            Write-Host ""
            Read-Host "   Press Enter to return to menu"
        }

        "0" {
            Clear-Host
            Write-Host ""
            Write-Host "   Goodbye! - Godavari Building Materials" -ForegroundColor Yellow
            Write-Host ""
            exit 0
        }

        default {
            Write-Warn "Invalid choice '$choice'. Try again."
            Start-Sleep -Seconds 1
        }
    }
}
