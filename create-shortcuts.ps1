$desktop = [Environment]::GetFolderPath("Desktop")
$root = "d:\Microservice\Microservice"

# Shortcut 1 — Master Launcher
$ws = New-Object -ComObject WScript.Shell
$sc = $ws.CreateShortcut("$desktop\Godavari Launcher.lnk")
$sc.TargetPath = "$root\launcher.bat"
$sc.WorkingDirectory = $root
$sc.IconLocation = "C:\Windows\System32\shell32.dll,162"
$sc.Description = "Godavari Building Materials — App Launcher"
$sc.Save()

# Shortcut 2 — Backend Only
$sc2 = $ws.CreateShortcut("$desktop\Godavari Backend.lnk")
$sc2.TargetPath = "$root\start-all.bat"
$sc2.WorkingDirectory = $root
$sc2.IconLocation = "C:\Windows\System32\shell32.dll,21"
$sc2.Description = "Start All Backend Microservices"
$sc2.Save()

# Shortcut 3 — Frontend Only
$sc3 = $ws.CreateShortcut("$desktop\Godavari Frontend.lnk")
$sc3.TargetPath = "$root\start-frontend.bat"
$sc3.WorkingDirectory = $root
$sc3.IconLocation = "C:\Windows\System32\shell32.dll,14"
$sc3.Description = "Start Angular Frontend"
$sc3.Save()

Write-Host ""
Write-Host "✔ 3 desktop shortcuts created:" -ForegroundColor Green
Write-Host "  • Godavari Launcher.lnk  (Menu with all options)" -ForegroundColor Cyan
Write-Host "  • Godavari Backend.lnk   (Backend only)" -ForegroundColor Yellow
Write-Host "  • Godavari Frontend.lnk  (Frontend only)" -ForegroundColor Cyan
Write-Host ""
Write-Host "Double-click any shortcut on your Desktop to launch!" -ForegroundColor White
