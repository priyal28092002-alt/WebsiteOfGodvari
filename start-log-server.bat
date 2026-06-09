@echo off
title Godavari Log Server
color 0A
echo.
echo  =============================================
echo    Godavari Log Server  ^|  Port 9090
echo  =============================================
echo.

:: Kill anything on port 9090
echo  [1] Checking port 9090...
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9090"') do (
    taskkill /F /PID %%a >nul 2>&1
)
echo  [2] Port 9090 cleared.
echo  [3] Starting log server...
echo.
echo  Logs viewer : http://localhost:4200/admin/logs
echo  API         : http://localhost:9090/logs
echo  Stats       : http://localhost:9090/stats
echo.
echo  Press Ctrl+C to stop
echo  =============================================
echo.

"C:\Program Files\node.exe" "d:\Microservice\Microservice\log-server.js"

echo.
echo  Log server stopped.
pause
