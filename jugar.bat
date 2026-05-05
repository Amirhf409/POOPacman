@echo off
chcp 65001 >nul
if not exist bin (
    echo No se encontro el directorio bin. Ejecuta compilar.bat primero.
    pause
    exit /b
)
java -cp bin Main
pause
