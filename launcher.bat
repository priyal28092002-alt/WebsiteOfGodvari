@echo off
setlocal enabledelayedexpansion

set "ROOT=d:\Microservice\Microservice"
set "LOGDIR=%ROOT%\logs"
if not exist "%LOGDIR%" mkdir "%LOGDIR%"

:MENU
cls
color 0F
echo.
echo  ================================================================
echo    ^^  Godavari Building Materials Nashik
echo       Application Launcher v2.0
echo  ================================================================
echo.
echo   SERVICES
echo   --------
echo   [1]  Start Backend Services    (9 Microservices)
echo   [2]  Start Frontend            (Angular - port 4200)
echo   [3]  Start Log Server          (port 9090)
echo   [4]  Start WhatsApp Server     (WPPConnect - port 9091)
echo   [5]  Start Everything          (Kafka + WhatsApp + Backend + Frontend + Logs)
echo.
echo   MONITORING
echo   ----------
echo   [6]  Check Service Status
echo   [7]  View Live Logs            (Choose service)
echo   [8]  Open Log Viewer in Browser
echo.
echo   CONTROL
echo   -------
echo   [9]  Stop All Services
echo   [0]  Exit
echo.
set /p "CHOICE=   Enter choice [0-9]: "

if "%CHOICE%"=="1" goto START_BACKEND
if "%CHOICE%"=="2" goto START_FRONTEND
if "%CHOICE%"=="3" goto START_LOGS
if "%CHOICE%"=="4" goto START_WHATSAPP
if "%CHOICE%"=="5" goto START_ALL
if "%CHOICE%"=="6" goto CHECK_STATUS
if "%CHOICE%"=="7" goto VIEW_LOGS
if "%CHOICE%"=="8" goto OPEN_LOGS
if "%CHOICE%"=="9" goto STOP_ALL
if "%CHOICE%"=="0" goto EXIT
echo.
echo   Invalid choice. Try again.
timeout /t 2 /nobreak >nul
goto MENU

:: ──────────────────────────────────────────
:START_BACKEND
cls
echo.
echo   Starting 9 Backend Microservices...
echo.
start "Godavari Backend" cmd /k "cd /d %ROOT% && call start-all.bat"
echo   [OK] Backend window opened.
echo   Wait 3-4 minutes for all services to compile and start.
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:START_FRONTEND
cls
echo.
echo   Starting Angular Frontend...
echo.
start "Godavari Frontend" cmd /k "cd /d %ROOT% && call start-frontend.bat"
echo   [OK] Frontend window opened.
echo   App will be available at: http://localhost:4200
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:START_LOGS
cls
echo.
echo   Starting Log Server on port 9090...
echo.
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9090" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)
start "Godavari Log Server" cmd /k "cd /d %ROOT% && call start-log-server.bat"
echo   [OK] Log Server window opened.
echo   Log viewer: http://localhost:4200/admin/logs
echo   API:        http://localhost:9090/logs
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:START_WHATSAPP
cls
echo.
echo   Starting WhatsApp Server (WPPConnect)...
echo.
start "Godavari WhatsApp" cmd /k "cd /d %ROOT% && call start-whatsapp.bat"
echo   [OK] WhatsApp server window opened.
echo   First time: Scan the QR code with your WhatsApp app.
echo   Status: http://localhost:9091/status
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:START_ALL
cls
echo.
echo  ================================================================
echo   Starting Everything...
echo  ================================================================
echo.

echo   [1/5] Starting Kafka (Zookeeper + Broker)...
if exist "C:\kafka" (
    echo         Cleaning stale Kafka data...
    for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9092" ^| findstr "LISTENING"') do taskkill /F /PID %%a >nul 2>&1
    for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":2181" ^| findstr "LISTENING"') do taskkill /F /PID %%a >nul 2>&1
    timeout /t 2 /nobreak >nul
    if exist "C:\tmp\zookeeper"  rmdir /s /q "C:\tmp\zookeeper"  >nul 2>&1
    if exist "C:\tmp\kafka-logs" rmdir /s /q "C:\tmp\kafka-logs" >nul 2>&1
    start "Zookeeper" cmd /k "cd /d C:\kafka && bin\windows\zookeeper-server-start.bat config\zookeeper.properties"
    echo         Zookeeper window opened. Waiting 12s...
    timeout /t 12 /nobreak >nul
    start "Kafka Broker" cmd /k "cd /d C:\kafka && bin\windows\kafka-server-start.bat config\server.properties"
    echo         Kafka Broker window opened. Waiting 10s...
    timeout /t 10 /nobreak >nul
    echo         [OK] Kafka started.
) else (
    echo         [SKIP] Kafka not found at C:\kafka - skipping.
)
echo.

echo   [2/5] Starting WhatsApp Server (WPPConnect)...
start "Godavari WhatsApp" cmd /k "cd /d %ROOT% && call start-whatsapp.bat"
echo         WhatsApp server window opened. Scan QR code when it appears.
echo.

echo   [3/5] Opening Backend (9 services)...
start "Godavari Backend"    cmd /k "cd /d %ROOT% && call start-all.bat"
echo         Backend window opened.
echo.

echo   [4/5] Opening Log Server (port 9090)...
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9090" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)
start "Godavari Log Server" cmd /k "cd /d %ROOT% && call start-log-server.bat"
echo         Log server window opened.
echo.

echo   [5/5] Waiting 2 minutes then opening Frontend...
echo         (Backend needs time to compile on first run)
echo.
for /l %%i in (1,1,12) do (
    set /a PCT=%%i*8
    echo|set /p="   Progress !PCT!%%  "
    timeout /t 10 /nobreak >nul
    echo.
)

echo.
echo   Opening Frontend (Angular)...
start "Godavari Frontend"   cmd /k "cd /d %ROOT% && call start-frontend.bat"
echo   [OK] Frontend window opened.
echo.
echo  ================================================================
echo   All Started!
echo   Kafka     : localhost:9092
echo   WhatsApp  : http://localhost:9091/status
echo   Backend   : http://localhost:8087
echo   Frontend  : http://localhost:4200
echo   Logs      : http://localhost:9090/logs
echo   Eureka    : http://localhost:8761
echo  ================================================================
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:CHECK_STATUS
cls
echo.
echo  ================================================================
echo   Service Status Check
echo  ================================================================
echo.
call :chk 8761 "Eureka Server   "
call :chk 8888 "Config Server   "
call :chk 8081 "User Service    "
call :chk 8082 "Product Service "
call :chk 8083 "Order Service   "
call :chk 8084 "Inventory Svc   "
call :chk 8085 "Quotation Svc   "
call :chk 8086 "Billing Service "
call :chk 8087 "API Gateway     "
call :chk 4200 "Angular App     "
call :chk 9090 "Log Server      "
call :chk 9091 "WhatsApp Server "
echo.
echo  ================================================================
echo   URLs (if UP):
echo   Frontend  : http://localhost:4200
echo   Admin     : http://localhost:4200/admin/login
echo   API GW    : http://localhost:8087
echo   Eureka    : http://localhost:8761
echo   Logs API  : http://localhost:9090/logs
echo   WhatsApp  : http://localhost:9091/status
echo  ================================================================
echo.
pause
goto MENU

:chk
set "P=%~1"
set "N=%~2"
netstat -an 2>nul | find ":%P% " | find "LISTENING" >nul 2>&1
if %errorlevel%==0 (
    echo   [  UP  ]  %N%  --  http://localhost:%P%
) else (
    echo   [ DOWN ]  %N%  --  port %P% not running
)
goto :eof

:: ──────────────────────────────────────────
:VIEW_LOGS
cls
echo.
echo  ================================================================
echo   Live Log Viewer  (Ctrl+C to stop following)
echo  ================================================================
echo.
echo   [1]  User Service       (auth, login, register)
echo   [2]  Product Service    (products, categories)
echo   [3]  Order Service      (orders, dashboard)
echo   [4]  Inventory Service  (stock levels)
echo   [5]  Quotation Service  (RFQ, quotations)
echo   [6]  Billing Service    (invoices)
echo   [7]  API Gateway        (routing)
echo   [8]  Eureka Server      (service discovery)
echo   [9]  Config Server      (configuration)
echo   [A]  All Services       (combined - last 30 lines each)
echo   [0]  Back to Menu
echo.
set /p "LC=   Choose log [0-9 or A]: "

if /i "%LC%"=="0" goto MENU
if /i "%LC%"=="1" goto LOG_USER
if /i "%LC%"=="2" goto LOG_PRODUCT
if /i "%LC%"=="3" goto LOG_ORDER
if /i "%LC%"=="4" goto LOG_INVENTORY
if /i "%LC%"=="5" goto LOG_QUOTATION
if /i "%LC%"=="6" goto LOG_BILLING
if /i "%LC%"=="7" goto LOG_GATEWAY
if /i "%LC%"=="8" goto LOG_EUREKA
if /i "%LC%"=="9" goto LOG_CONFIG
if /i "%LC%"=="A" goto LOG_ALL
goto VIEW_LOGS

:LOG_USER
start "User Service Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\user-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_PRODUCT
start "Product Service Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\product-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_ORDER
start "Order Service Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\order-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_INVENTORY
start "Inventory Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\inventory-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_QUOTATION
start "Quotation Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\quotation-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_BILLING
start "Billing Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\billing-service.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_GATEWAY
start "API Gateway Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\api-gateway.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_EUREKA
start "Eureka Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\eureka-server.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_CONFIG
start "Config Log" powershell -NoExit -Command "Get-Content '%LOGDIR%\config-server.log' -Wait -Tail 40"
goto VIEW_LOGS

:LOG_ALL
cls
echo.
echo   Showing last 20 lines from each service...
echo   Press any key to go back.
echo.
echo === USER SERVICE ===
powershell -Command "if(Test-Path '%LOGDIR%\user-service.log'){Get-Content '%LOGDIR%\user-service.log' -Tail 20}else{Write-Host 'No log yet'}"
echo.
echo === PRODUCT SERVICE ===
powershell -Command "if(Test-Path '%LOGDIR%\product-service.log'){Get-Content '%LOGDIR%\product-service.log' -Tail 20}else{Write-Host 'No log yet'}"
echo.
echo === ORDER SERVICE ===
powershell -Command "if(Test-Path '%LOGDIR%\order-service.log'){Get-Content '%LOGDIR%\order-service.log' -Tail 20}else{Write-Host 'No log yet'}"
echo.
echo === API GATEWAY ===
powershell -Command "if(Test-Path '%LOGDIR%\api-gateway.log'){Get-Content '%LOGDIR%\api-gateway.log' -Tail 20}else{Write-Host 'No log yet'}"
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:OPEN_LOGS
cls
echo.
echo   Opening Log Viewer in browser...
echo   (Log Server must be running on port 9090)
echo.
netstat -an 2>nul | find ":9090 " | find "LISTENING" >nul 2>&1
if %errorlevel% neq 0 (
    echo   [WARN] Log Server is NOT running!
    echo   Starting it now...
    for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9090" ^| findstr "LISTENING"') do (
        taskkill /F /PID %%a >nul 2>&1
    )
    start "Godavari Log Server" cmd /k "cd /d %ROOT% && call start-log-server.bat"
    echo   Waiting 3 seconds for log server to start...
    timeout /t 3 /nobreak >nul
)
start http://localhost:4200/admin/logs
echo   [OK] Opened: http://localhost:4200/admin/logs
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:STOP_ALL
cls
echo.
echo  ================================================================
echo   Stopping All Services
echo  ================================================================
echo.
echo   Stopping Kafka and Zookeeper...
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9092" ^| findstr "LISTENING"') do taskkill /F /PID %%a >nul 2>&1
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":2181" ^| findstr "LISTENING"') do taskkill /F /PID %%a >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq Zookeeper*" >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq Kafka Broker*" >nul 2>&1
timeout /t 2 /nobreak >nul
if exist "C:\tmp\zookeeper"  rmdir /s /q "C:\tmp\zookeeper"  >nul 2>&1
if exist "C:\tmp\kafka-logs" rmdir /s /q "C:\tmp\kafka-logs" >nul 2>&1
echo   [OK] Kafka/Zookeeper stopped and stale data cleared.
echo.
echo   Stopping Java (all microservices)...
taskkill /f /im java.exe >nul 2>&1
echo   [OK] Java processes stopped.
echo.
echo   Stopping Node.js (log server + whatsapp server)...
taskkill /f /im node.exe >nul 2>&1
echo   [OK] Node processes stopped.
echo.
echo   Stopping Angular (ng serve)...
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":4200" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)
echo   [OK] Frontend stopped.
echo.
echo  ================================================================
echo   All services stopped successfully.
echo  ================================================================
echo.
pause
goto MENU

:: ──────────────────────────────────────────
:EXIT
cls
echo.
echo   Goodbye! - Godavari Building Materials
echo.
exit /b 0
