/**
 * Created by Piotr on 2018-03-25.
 */
module jRank.desktop {
	
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	
 	requires jRankLogger;
 	
 	opens pl.jowko.jrank.desktop.controller;
 	
 	exports pl.jowko.jrank.desktop.settings;
 	exports pl.jowko.jrank.desktop.controller;
 	exports pl.jowko.jrank.desktop;
}