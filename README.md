# RuleRank Ultimate Desktop Edition
This project has a goal to write desktop app for solving ranking problems with DRSA methods.
It is UI wrapper for ruleRank console application.

## Modules

### customFX
Contains custom javaFX components used in this project. Can be imported to SceneBuilder.

### desktop-app
Project with JavaFx application. Here is whole UI and logic.

### file-generator
Used only in development. Generates config files for desktop app.

### rule-rank-logger
Logging mediator for whole project.


## Other projects

### jRS
Java Rough Set library contains ruleRank console application with its dependencies.
Library is located in lib folder.
Before running application, you need to install this jar into your local maven repository. 
See installation section.

## Installation
Before installation of this application, ensure that:
- Maven is installed.
- On Windows maven\bin catalog should be in PATH variable.
- Java JDK 10 is installed.

### Installation steps:
1. Run install.bat script. On linux you have to create own script.
2. Go to created RuleRank directory.
3. Run run.bat. Whole folder is standalone instance of RuleRank Ultimate Desktop Edition application.

install.bat script will perform following actions:
- installation of lib\jRS.jar to local maven repository
- clean build of whole application
- creation of RuleRank directory
- copying of all necessary files to RuleRank directory
- copy Manual.pdf file from doc directory to RuleRank directory
- create run.bat script to run this app

## Editing manual
Manual files are located in doc folder.
LaTeX needs to be installed to edit and compile pdf file.
PDF file is not automatically compiled by install.bat file!

## Generating JavaDoc
JavaDoc can be easily generated using Intellij Idea.
To do so, open Tools -> Generate JavaDoc... -> choose options and add -html5 parameter(Other command line arguments).


## Author
- Piotr Jowko
