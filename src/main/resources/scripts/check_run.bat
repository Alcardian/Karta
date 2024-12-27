@echo off

:: Get the current directory
set CURRENT_DIR=%cd%

:: Check if the current directory contains the restricted path
echo %CURRENT_DIR% | findstr /i "src\main\resources\scripts" >nul
if %errorlevel% equ 0 (
    echo Error: You are trying to run the application from the source code directory.
    echo Please download the built project instead of source or use build.sh to build the project yourself.
    pause
    exit /b
)

:: Start the application
java com.alcardian.karta.ClipboardListener
pause
