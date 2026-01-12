@echo off
cls
echo Compiling...
if exist bin rmdir /s /q bin
mkdir bin
javac -d bin src\main\java\com\upb\agripos\*.java

if %errorlevel% neq 0 (
    echo Compilation FAILED!
    pause
    exit /b 1
)

echo Running...
echo ========================================
java -cp bin com.upb.agripos.MainExceptionDemo
echo ========================================
pause