@echo off
echo ========================================
echo WEEK 11 - COMPILATION SCRIPT
echo ========================================

REM Clean bin folder
if exist bin rmdir /s /q bin
mkdir bin

REM Compile in correct order
echo Compiling models...
javac -d bin src\main\java\com\upb\agripos\model\*.java

echo Compiling config...
javac -d bin -cp bin src\main\java\com\upb\agripos\config\*.java

echo Compiling DAO interfaces...
javac -d bin -cp bin src\main\java\com\upb\agripos\dao\ProductDAO.java
javac -d bin -cp bin src\main\java\com\upb\agripos\dao\TransactionDAO.java

echo Compiling DAO implementations...
javac -d bin -cp bin src\main\java\com\upb\agripos\dao\JdbcProductDAO.java
javac -d bin -cp bin src\main\java\com\upb\agripos\dao\JdbcTransactionDAO.java

echo Compiling main program...
javac -d bin -cp bin src\main\java\com\upb\agripos\MainDAOTest.java

echo.
echo ========================================
echo COMPILATION COMPLETE!
echo ========================================
echo Run with: java -cp bin com.upb.agripos.MainDAOTest
pause