@echo off
echo ========================================
echo WEEK 11 - RUN SCRIPT
echo ========================================

REM Check if compiled
if not exist bin (
    echo Error: Please compile first!
    echo Run compile.bat
    pause
    exit /b 1
)

REM Run the program
echo Running Week 11 DAO Demo...
java -cp bin com.upb.agripos.MainDAOTest

pause