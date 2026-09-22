@echo off
chcp 65001 > NUL
echo =========================================================
echo  Compilando y ejecutando Gestion de Productos (Java Swing)
echo =========================================================

IF NOT EXIST bin (
    mkdir bin
)

echo Compilando codigo fuente...
javac -encoding UTF-8 -source 1.8 -target 1.8 -cp "lib/*" -d bin src/com/gestion/modelo/*.java src/com/gestion/config/*.java src/com/gestion/dao/*.java src/com/gestion/vista/*.java src/com/gestion/main/*.java

IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo la compilacion. Por favor revisa que tengas Java JDK instalado.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Compilacion exitosa. Iniciando la aplicacion...
echo.
java -cp "bin;lib\mssql-jdbc-12.4.2.jre8.jar;lib\mysql-connector-j-8.0.33.jar" com.gestion.main.Main

pause
