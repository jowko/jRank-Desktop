REM Script for creating RuleRank installation

@echo off

echo Copying jRS.jar to local maven repo
call mvn install:install-file -Dfile=lib\jRS.jar -DgroupId=pl.poznan.put.cs.idss -DartifactId=jrs -Dversion=2018.05.15 -Dpackaging=jar

echo Building whole project
call mvn clean install

echo Generating labels and config files
call java -jar file-generator\target\file-generator-1.0-SNAPSHOT-jar-with-dependencies.jar

echo Removing old RuleRank files
if exist RuleRank\ del /s /f /q RuleRank\*.*

echo Creating RuleRank folder
md RuleRank

echo Creating default workspace folder
md RuleRank\workspace

echo Copying and renaming main jar file
COPY desktop-app\target\desktop-app-1.0-SNAPSHOT-jar-with-dependencies.jar "RuleRank\RuleRank-1.0-SNAPSHOT.jar"

echo Copying data directory
ROBOCOPY data "RuleRank\data"

echo Copying example experiments

echo Copying Airlines
ROBOCOPY workspace\Airlines RuleRank\workspace\Airlines

echo Copying Houses7
ROBOCOPY workspace\Houses7 RuleRank\workspace\Houses7

echo Copying Houses11
ROBOCOPY workspace\Houses11 RuleRank\workspace\Houses11

echo Copying default.properties file
ROBOCOPY workspace\ RuleRank\workspace\

echo creating run.bat file
@echo java -jar RuleRank-1.0-SNAPSHOT.jar -Xms128m > RuleRank\run.bat