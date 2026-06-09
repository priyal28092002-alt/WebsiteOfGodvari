@echo off
title WhatsApp Server (WPPConnect)
color 0A
echo.
echo  =============================================
echo    Godavari WhatsApp Server - Port 9091
echo  =============================================
echo.

cd /d %~dp0

if not exist "node_modules\@wppconnect-team" (
    echo  [SETUP] Installing WPPConnect - this takes 2-3 minutes...
    echo  Please wait...
    call npm install
    echo  [OK] Installation complete.
    echo.
)

echo  Starting WhatsApp server...
echo  First time: A QR code will appear - scan with your WhatsApp
echo.
node whatsapp-server.js
