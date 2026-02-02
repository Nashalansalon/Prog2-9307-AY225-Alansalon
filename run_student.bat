@echo off
REM Compile and run StudentRecordSystem
REM Uses JAVA_HOME if set, otherwise uses javac/java from PATH

if defined JAVA_HOME (
  "%JAVA_HOME%\bin\javac.exe" --enable-preview -source 25 -d bin PrelimExam\JAVA\StudentRecordSystem.java
) else (
  javac --enable-preview -source 25 -d bin PrelimExam\JAVA\StudentRecordSystem.java
)

if errorlevel 1 (
  echo Compilation failed.
  pause
  exit /b 1
)

if defined JAVA_HOME (
  "%JAVA_HOME%\bin\java.exe" --enable-preview -cp bin StudentRecordSystem
) else (
  java --enable-preview -cp bin StudentRecordSystem
)

pause
