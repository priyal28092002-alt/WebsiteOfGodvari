@echo off
setlocal

set "APPDIR=d:\Microservice\Microservice\frontend\godavari-app"

cls
echo.
echo ===========================================================
echo   Godavari Building Materials - Frontend
echo ===========================================================
echo.
echo   App : http://localhost:4200
echo   Dir : %APPDIR%
echo.

cd /d "%APPDIR%"
if not exist "angular.json" (
    echo   [ERROR] angular.json not found in %APPDIR%
    echo   Please verify the path exists.
    pause
    exit /b 1
)

:: Install dependencies if node_modules missing
if not exist "node_modules" (
    echo   node_modules not found. Running npm install...
    echo   Please wait 3-5 minutes...
    echo.
    call npm install
    echo.
    echo   [OK] Packages installed.
    echo.
)

echo   Starting Angular dev server...
echo   Browser will open at http://localhost:4200
echo.
echo   Press Ctrl+C to stop
echo ===========================================================
echo.

call npx ng serve --port 4200 --open

echo.
echo   Server stopped.
pause
