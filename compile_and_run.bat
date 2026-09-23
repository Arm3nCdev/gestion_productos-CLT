@echo off
chcp 65001 >nul 2>&1
echo =========================================================
echo  Compilando y ejecutando Gestion de Productos (Java Swing)
echo =========================================================

if not exist "bin" (
    mkdir bin
)

echo Compilando codigo fuente Java...
javac -encoding UTF-8 -cp "lib/*" -d bin src\com\gestion\modelo\*.java src\com\gestion\config\*.java src\com\gestion\dao\*.java src\com\gestion\vista\*.java src\com\gestion\main\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Fallo la compilacion. Verifique que Java JDK este instalado.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Compilacion exitosa. Iniciando la aplicacion...
echo.
java -cp "bin;lib\mssql-jdbc-12.4.2.jre8.jar;lib\mysql-connector-j-8.0.33.jar" com.gestion.main.Main

pause
