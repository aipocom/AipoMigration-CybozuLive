@echo off
cd /d %~dp0
bin\jre\bin\java -jar bin\aipo-migration-cybozuLive.jar
pause
exit /B 0