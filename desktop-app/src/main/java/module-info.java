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
	requires fxgraph;
	
	requires com.fasterxml.jackson.databind;
	requires jrs;
	requires org.apache.commons.io;
	
	opens pl.jowko.rulerank.desktop.controller;
	opens pl.jowko.rulerank.desktop.feature.graph;
	opens pl.jowko.rulerank.desktop.feature.approximations;
	opens pl.jowko.rulerank.desktop.feature.learningtable;
	opens pl.jowko.rulerank.desktop.feature.learningtable.dialogs;
	opens pl.jowko.rulerank.desktop.feature.pct;
	opens pl.jowko.rulerank.desktop.feature.properties;
	opens pl.jowko.rulerank.desktop.feature.properties.information;
	opens pl.jowko.rulerank.desktop.feature.ranking;
	opens pl.jowko.rulerank.desktop.feature.rules;
	opens pl.jowko.rulerank.desktop.feature.settings;
	opens pl.jowko.rulerank.desktop.feature.tabs.lower;
	opens pl.jowko.rulerank.desktop.feature.tabs.upper;
	opens pl.jowko.rulerank.desktop.feature.textfile;
	opens pl.jowko.rulerank.desktop.feature.unknown;
	opens pl.jowko.rulerank.desktop.feature.workspace;
 
	exports pl.jowko.rulerank.desktop.controller;
 	exports pl.jowko.rulerank.desktop.feature.internationalization;
 	exports pl.jowko.rulerank.desktop.feature.settings;
 	exports pl.jowko.rulerank.desktop;
}