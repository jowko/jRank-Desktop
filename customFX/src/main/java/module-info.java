/**
 * This module contains custom JavaFX control with are used in RuleRank application.<br>
 * They can be used both in java code and in fxml files.<br>
 * They can be used in any other project, because they are independent from RuleRank.<br>
 * Jar of this project could be also loaded for SceneBuilder.<br>
 * But something went wrong on SceneBuilder doesn't see any components in this jar for some reason.<br>
 *<br>
 * Created by Piotr on 2018-05-07.
 */
module customFX {
	requires javafx.controls;
	
 	exports pl.jowko.rulerank.feature.customfx;
}