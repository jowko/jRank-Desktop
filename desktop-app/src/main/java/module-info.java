/**
 * Created by Piotr on 2018-03-25.
 */
module jRank.desktop {
	
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	
 	requires jRankLogger;
	requires customFX;
	
	requires com.fasterxml.jackson.databind;
	requires jRS;
	
	opens pl.jowko.jrank.desktop.controller;
	opens pl.jowko.jrank.desktop.feature.learningtable;
	opens pl.jowko.jrank.desktop.feature.properties;
	opens pl.jowko.jrank.desktop.feature.tabs.upper;
	opens pl.jowko.jrank.desktop.feature.workspace;
	opens pl.jowko.jrank.desktop.settings;
 	
 	exports pl.jowko.jrank.desktop.settings;
 	exports pl.jowko.jrank.desktop.controller;
 	exports pl.jowko.jrank.desktop;
}