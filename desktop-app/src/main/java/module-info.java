/**
 * Created by Piotr on 2018-03-25.
 * This is module-info file for Jigsaw module system.
 * It configures all dependencies for module system.
 * Also opens all packages with are needed by JavaFX framework.
 * Also exports catalog related with settings and internationalization, because file-generator project uses them.
 * File Generator projects generates json files with default settings and labels.
 */
module jRank.desktop {
	
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	
 	requires jRankLogger;
	requires customFX;
	
	requires com.fasterxml.jackson.databind;
	requires jrs;
	
	opens pl.jowko.jrank.desktop.controller;
	opens pl.jowko.jrank.desktop.feature.approximations;
	opens pl.jowko.jrank.desktop.feature.learningtable;
	opens pl.jowko.jrank.desktop.feature.learningtable.dialogs;
	opens pl.jowko.jrank.desktop.feature.properties;
	opens pl.jowko.jrank.desktop.feature.ranking;
	opens pl.jowko.jrank.desktop.feature.rules;
	opens pl.jowko.jrank.desktop.feature.settings;
	opens pl.jowko.jrank.desktop.feature.tabs.lower;
	opens pl.jowko.jrank.desktop.feature.tabs.upper;
	opens pl.jowko.jrank.desktop.feature.textfile;
	opens pl.jowko.jrank.desktop.feature.unknown;
	opens pl.jowko.jrank.desktop.feature.workspace;
 	
 	exports pl.jowko.jrank.desktop.feature.internationalization;
 	exports pl.jowko.jrank.desktop.feature.settings;
 	exports pl.jowko.jrank.desktop;
}