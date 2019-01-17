@echo off
cd /d %~dp0
lib\jre\bin\java -jar lib\aipo-migration-cybozuLive-${project.version}.jar
pause
exit /B 0