@echo off
setlocal enabledelayedexpansion
title Kafka + Zookeeper
color 0B

echo.
echo  =============================================
echo    Apache Kafka - Port 9092
echo    Zookeeper - Port 2181
echo  =============================================
echo.

:: Check Kafka installation
if not exist "C:\kafka" (
    echo  [ERROR] Kafka not found at C:\kafka
    pause
    exit /b 1
)

:: ── Step 0: Kill any existing Kafka/Zookeeper processes ──────────────────────
echo  [0/2] Cleaning up existing processes and stale data...

for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9092" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":2181" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)

timeout /t 2 /nobreak >nul

:: Clean stale data every time — fixes NodeExists error permanently
if exist "C:\tmp\zookeeper"   rmdir /s /q "C:\tmp\zookeeper"   >nul 2>&1
if exist "C:\tmp\kafka-logs"  rmdir /s /q "C:\tmp\kafka-logs"  >nul 2>&1
if exist "C:\kafka\kafka-logs"     rmdir /s /q "C:\kafka\kafka-logs"     >nul 2>&1
if exist "C:\kafka\zookeeper-data" rmdir /s /q "C:\kafka\zookeeper-data" >nul 2>&1
echo  [OK] Stale data cleaned.
echo.

:: ── Step 1: Start Zookeeper ──────────────────────────────────────────────────
echo  [1/2] Starting Zookeeper on port 2181...
start "Zookeeper" cmd /k "cd /d C:\kafka && bin\windows\zookeeper-server-start.bat config\zookeeper.properties"

echo  Waiting 12 seconds for Zookeeper...
timeout /t 12 /nobreak >nul

:: ── Step 2: Start Kafka ──────────────────────────────────────────────────────
echo  [2/2] Starting Kafka Broker on port 9092...
start "Kafka Broker" cmd /k "cd /d C:\kafka && bin\windows\kafka-server-start.bat config\server.properties"

echo  Waiting 10 seconds for Kafka...
timeout /t 10 /nobreak >nul

echo.
echo  =============================================
echo   Kafka is running!
echo   Zookeeper : localhost:2181
echo   Broker    : localhost:9092
echo  =============================================
echo.
echo  NOTE: Close Zookeeper and Kafka windows separately
echo  OR press any key here to STOP EVERYTHING cleanly.
echo.
pause >nul

:: ── Cleanup on exit ──────────────────────────────────────────────────────────
:CLEANUP
echo.
echo  Stopping Kafka and Zookeeper...

for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":9092" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":2181" ^| findstr "LISTENING"') do (
    taskkill /F /PID %%a >nul 2>&1
)

:: Also kill by window title
taskkill /F /FI "WINDOWTITLE eq Zookeeper" >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq Kafka Broker" >nul 2>&1

timeout /t 2 /nobreak >nul

:: Clean data so next start is always fresh
if exist "C:\tmp\zookeeper"  rmdir /s /q "C:\tmp\zookeeper"  >nul 2>&1
if exist "C:\tmp\kafka-logs" rmdir /s /q "C:\tmp\kafka-logs" >nul 2>&1

echo  [OK] Kafka and Zookeeper stopped cleanly.
echo  [OK] Stale data cleared — next start will be clean.
echo.
