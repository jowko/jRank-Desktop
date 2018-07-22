package pl.jowko.rulerank.desktop.feature.learningtable;

import javafx.scene.control.Label;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.internationalization.Translator;

/**
 * Created by Piotr on 2018-05-21.
 * This class translates UI fields from Learning table.
 */
class LearningTableTranslator implements Translator {
	
	private LearningTableController controller;
	private LanguageService labels;
	
	LearningTableTranslator(LearningTableController controller) {
		this.controller = controller;
		labels = LanguageService.getInstance();
	}
	
	@Override
	public void translateFields() {
		controller.learningTable.setPlaceholder(new Label(labels.get(Labels.LEARN_TABLE_EMPTY)));
		controller.selectAttributeLabel.setText(labels.get(Labels.LEARN_TABLE_SELECT_ATTRIBUTE));
		controller.removeAttributeButton.setText(labels.get(Labels.LEARN_TABLE_REMOVE_ATTRIBUTE));
		controller.removeAllExamplesButton.setText(labels.get(Labels.LEARN_TABLE_REMOVE_ALL_EXAMPLES));
		controller.saveButton.setText(labels.get(Labels.LEARN_TABLE_SAVE_BUTTON));
		controller.cancelButton.setText(labels.get(Labels.LEARN_TABLE_CANCEL_BUTTON));
	}
	
}
