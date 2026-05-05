@echo off
echo Compilando Pac-Man...
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\*.java
if %ERRORLEVEL% == 0 (
    echo.
    echo Compilacion exitosa. Ejecuta jugar.bat para iniciar.
) else (
    echo.
    echo ERROR al compilar. Asegurate de tener Java instalado.
    echo Descarga Java en: https://adoptium.net
)
pause
