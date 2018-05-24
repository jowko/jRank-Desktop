/**
 * Created by Piotr on 2018-05-07.
 * This module contains custom JavaFX control with are used in RuleRank application.
 * They can be used both in java code and in fxml files.
 * They can be used in any other project, because they are independent from jRank.
 * Jar of this project could be also loaded for SceneBuilder.
 * But something went wrong on SceneBuilder doesn't see any components in this jar for some reason.
 */
module customFX {
	requires javafx.controls;
	
 	exports pl.jowko.jrank.feature.customfx;
}