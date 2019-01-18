@echo off
echo ================================================
echo;
echo AipoMigration-CybozuLive Ver. ${project.version}
echo;
echo Copyright (C) TOWN, Inc.
echo https://aipo.com
echo;
echo ================================================
echo;
cd /d %~dp0
lib\jre\bin\java -jar lib\aipo-migration-cybozuLive-${project.version}.jar
pause
exit /B 0