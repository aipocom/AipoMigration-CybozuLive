@echo off
cd /d %~dp0
lib\jre\bin\java -jar lib\aipo-migration-cybozuLive.jar
pause
exit /B 0