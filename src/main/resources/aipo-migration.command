#!/bin/sh

echo '================================================'
echo ''
echo 'AipoMigration-CybozuLive Ver. ${project.version}'
echo ''
echo 'Copyright (C) TOWN, Inc.'
echo 'https://aipo.com'
echo ''
echo '================================================'
echo ''
cd `dirname $0`
lib/jre/Contents/Home/bin/java -jar lib/aipo-migration-cybozuLive-${project.version}.jar
read -p '続行するには何かキーを押してください . . .'
