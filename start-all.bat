@echo off
setlocal enabledelayedexpansion

set "ROOT=d:\Microservice\Microservice"
set "LOGDIR=%ROOT%\logs"
if not exist "%LOGDIR%" mkdir "%LOGDIR%"

cls
echo.
echo ===========================================================
echo   Godavari Building Materials Nashik
echo   Backend Services Launcher
echo ===========================================================
echo.
echo   All service logs saved in: %LOGDIR%
echo.

:: Start all services in background immediately
:: Start Redis if installed
echo   Starting Redis Cache Server (port 6379)...
where redis-server >nul 2>&1
if %errorlevel%==0 (
    start /b cmd /c "redis-server > %LOGDIR%\redis.log 2>&1"
    echo   [OK] Redis started.
) else (
    echo   [SKIP] Redis not found - skipping. Install from https://github.com/microsoftarchive/redis/releases
)
echo.

echo   [1/9] Launching Eureka Server   (port 8761)...
start /b cmd /c "cd /d %ROOT%\eureka-server     && mvn spring-boot:run > %LOGDIR%\eureka-server.log 2>&1"

echo   [2/9] Launching Config Server   (port 8888)...
start /b cmd /c "cd /d %ROOT%\config-server     && mvn spring-boot:run > %LOGDIR%\config-server.log 2>&1"

echo   [3/9] Launching User Service    (port 8081)...
start /b cmd /c "cd /d %ROOT%\user-service      && mvn spring-boot:run > %LOGDIR%\user-service.log 2>&1"

echo   [4/9] Launching Product Service (port 8082)...
start /b cmd /c "cd /d %ROOT%\product-service   && mvn spring-boot:run > %LOGDIR%\product-service.log 2>&1"

echo   [5/9] Launching Order Service   (port 8083)...
start /b cmd /c "cd /d %ROOT%\order-service     && mvn spring-boot:run > %LOGDIR%\order-service.log 2>&1"

echo   [6/9] Launching Inventory Svc   (port 8084)...
start /b cmd /c "cd /d %ROOT%\inventory-service && mvn spring-boot:run > %LOGDIR%\inventory-service.log 2>&1"

echo   [7/9] Launching Quotation Svc   (port 8085)...
start /b cmd /c "cd /d %ROOT%\quotation-service && mvn spring-boot:run > %LOGDIR%\quotation-service.log 2>&1"

echo   [8/9] Launching Billing Service (port 8086)...
start /b cmd /c "cd /d %ROOT%\billing-service   && mvn spring-boot:run > %LOGDIR%\billing-service.log 2>&1"

echo   [9/9] Launching API Gateway     (port 8087)...
start /b cmd /c "cd /d %ROOT%\api-gateway       && mvn spring-boot:run > %LOGDIR%\api-gateway.log 2>&1"

echo   Starting Notification Service (port 8089)...
start /b cmd /c "cd /d %ROOT%\notification-service && mvn spring-boot:run > %LOGDIR%\notification-service.log 2>&1"

echo.
echo   All 9 services launched in background.
echo   Waiting 3 minutes for all services to fully start...
echo   (Services compile + download dependencies on first run)
echo.

:: Wait 3 minutes then check all ports
call :countdown 180

echo.
echo ===========================================================
echo   Service Status Check
echo ===========================================================
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

echo.
echo ===========================================================
echo   Eureka Dashboard : http://localhost:8761
echo   API Gateway      : http://localhost:8087
echo   Angular App      : http://localhost:4200
echo ===========================================================
echo.
echo   Services are running in background.
echo   Press Ctrl+C to stop all services.
echo   Check logs folder for detailed output.
echo.

:monitor
timeout /t 60 /nobreak >nul
echo   [%time%] Running... (Ctrl+C to stop all)
goto monitor

goto :eof

:countdown
set /a TOTAL=%~1
echo|set /p="   Progress: "
for /l %%i in (1,1,%TOTAL%) do (
    echo|set /p="."
    timeout /t 1 /nobreak >nul
)
echo   Done!
goto :eof

:chk
set "PORT=%~1"
set "NAME=%~2"
netstat -ano 2>nul | findstr ":%PORT% " | findstr "LISTENING" >nul 2>&1
if %errorlevel%==0 (
    echo   [ UP ]  %NAME%  --  http://localhost:%PORT%
) else (
    echo   [WAIT]  %NAME%  --  port %PORT% not yet ready
)
goto :eof
