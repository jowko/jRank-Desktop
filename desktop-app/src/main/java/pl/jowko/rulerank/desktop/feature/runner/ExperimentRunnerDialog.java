package pl.jowko.rulerank.desktop.feature.runner;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.service.DialogsService;

import java.util.Optional;

/**
 * This class creates dialog where user can chose information source for experiment. <br>
 * He will be asked, if we configured more than one information source. <br>
 *  <br>
 * Created by Piotr on 2018-06-04
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
	 * Creates instance of this class. <br>
	 * Each param flag indicates what information source user configured. <br>
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
		LanguageService labels = LanguageService.getInstance();
		dialog.setTitle(labels.get(Labels.RUN_SOURCE_TITLE));
		dialog.setContentText(labels.get(Labels.RUN_SOURCE_CONTENT));
		DialogsService.resizeDialogPane(dialog.getDialogPane());
		
		ButtonType rankingButton = new ButtonType(labels.get(Labels.RUN_SOURCE_RANKING), ButtonBar.ButtonData.OK_DONE);
		ButtonType pairsButton = new ButtonType(labels.get(Labels.RUN_SOURCE_PAIRS), ButtonBar.ButtonData.OK_DONE);
		ButtonType attributeButton = new ButtonType(labels.get(Labels.RUN_SOURCE_DECISION_ATTRIBUTE), ButtonBar.ButtonData.OK_DONE);
		
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
