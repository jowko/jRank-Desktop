package pl.jowko.jrank.desktop.feature.runner;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

/**
 * Created by Piotr on 2018-06-04
 * This class creates dialog where user can chose information source for experiment.
 * He will be asked, if we configured more than one information source.
 * @see ExperimentRunnerValidator
 */
class ExperimentRunnerDialog {
	
	private static int RANKING_SELECTED = 1;
	private static int PAIRS_SELECTED = 2;
	private static int ATTRIBUTE_SELECTED = 3;
	
	private boolean isRankingAvailable;
	private boolean isPairsAvailable;
	private boolean isDecisionAttAvailable;
	
	private int chosenOption;
	
	/**
	 * Creates instance of this class.
	 * Each param flag indicates what information source user configured.
	 * All three options can be true at once.
	 * @param isRanking indicates that user configured ranking
	 * @param isPairs indicates that user configured pairs
	 * @param isDecisionAtt indicates that user configured decision attributes
	 */
	ExperimentRunnerDialog(boolean isRanking, boolean isPairs, boolean isDecisionAtt) {
		isRankingAvailable = isRanking;
		isPairsAvailable = isPairs;
		isDecisionAttAvailable = isDecisionAtt;
	}
	
	/**
	 * Create dialog with buttons, where user can choose information source.
	 */
	void showDialog() {
		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Choose information source");
		dialog.setContentText("More than one information source is configured. What source should be used in experiment?");
		
		ButtonType rankingButton = new ButtonType("Ranking", ButtonBar.ButtonData.OK_DONE);
		ButtonType pairsButton = new ButtonType("Pairs", ButtonBar.ButtonData.OK_DONE);
		ButtonType attributeButton = new ButtonType("Decision Attribute", ButtonBar.ButtonData.OK_DONE);
		
		if(isRankingAvailable)
			dialog.getDialogPane().getButtonTypes().add(rankingButton);
		if(isPairsAvailable)
			dialog.getDialogPane().getButtonTypes().add(pairsButton);
		if(isDecisionAttAvailable)
			dialog.getDialogPane().getButtonTypes().add(attributeButton);
		
		dialog.setResultConverter(dialogButton -> {
			if(dialogButton == rankingButton) {
				return RANKING_SELECTED;
			} else if(dialogButton == pairsButton) {
				return PAIRS_SELECTED;
			} else if(dialogButton == attributeButton) {
				return ATTRIBUTE_SELECTED;
			}
			return null;
		});
		
		Optional<Integer> result = dialog.showAndWait();
		
		result.ifPresent(value ->
			chosenOption = value
		);
	}
	
	boolean isRankingSelected() {
		return chosenOption == RANKING_SELECTED;
	}
	
	boolean isPairsSelected() {
		return chosenOption == PAIRS_SELECTED;
	}
	
	boolean isAttributeSelected() {
		return chosenOption == ATTRIBUTE_SELECTED;
	}
	
}
