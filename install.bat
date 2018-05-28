REM Script for creating jrank installation

@echo off

echo Copying jRS.jar to local maven repo
call mvn install:install-file -Dfile=lib\jRS.jar -DgroupId=pl.poznan.put.cs.idss -DartifactId=jrs -Dversion=2018.05.15 -Dpackaging=jar

echo Building whole project
call mvn clean install

echo Generating labels and config files
call java -jar file-generator\target\file-generator-0.4-SNAPSHOT-jar-with-dependencies.jar

echo Removing old JRank files
if exist JRank\ del /s /f /q JRank\*.*

echo Creating JRank folder
md JRank

echo Creating default workspace folder
md JRank\workspace

echo Copying and renaming main jar file
COPY desktop-app\target\desktop-app-0.4-SNAPSHOT-jar-with-dependencies.jar "JRank\JRank-0.4-SNAPSHOT.jar"

echo Copying data directory
ROBOCOPY data "JRank\data"

echo Copying example experiments

echo Copying Airlines
ROBOCOPY workspace\Airlines JRank\workspace\Airlines

echo Copying Houses7
ROBOCOPY workspace\Houses7 JRank\workspace\Houses7

echo Copying Houses11
ROBOCOPY workspace\Houses11 JRank\workspace\Houses11

echo Copying default.properties file
ROBOCOPY workspace\ JRank\workspace\

echo creating run.bat file
@echo java -jar JRank-0.4-SNAPSHOT.jar -Xms128m > JRank\run.bat