REM Script for creating jrank installation

@echo off

echo Building whole project
call mvn clean install

echo Removing old JRank files
if exist JRank\ del /s /f /q JRank\*.*

echo Creating JRank folder
md JRank

echo Creating default workspace folder
md JRank\workspace

echo Copying and renaming main jar file
COPY desktop-app\target\desktop-app-0.1-SNAPSHOT-jar-with-dependencies.jar "JRank\JRank-0.1-SNAPSHOT.jar"

echo Copying data directory
ROBOCOPY data "JRank\data"

echo Copying example experiments

echo Copying Houses7
ROBOCOPY workspace\Houses7 JRank\workspace\Houses7

echo Copying Houses11
ROBOCOPY workspace\Houses11 JRank\workspace\Houses11

echo creating run.bat file
@echo java -jar JRank-0.1-SNAPSHOT.jar -Xms128m -Xmx4G > JRank\run.bat