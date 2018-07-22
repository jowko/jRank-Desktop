package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import pl.jowko.rulerank.desktop.feature.internationalization.AbstractTranslator;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;

/**
 * Created by Piotr on 2018-05-21.
 * This class translates UI fields on Attribute form
 */
class AttributeDialogTranslator extends AbstractTranslator {
	
	private AttributeDialogController controller;
	
	AttributeDialogTranslator(AttributeDialogController controller) {
		this.controller = controller;
	}
	
	@Override
	public void translateFields() {
		controller.nameLabel.setText(get(Labels.ATT_DIALOG_NAME));
		controller.activeLabel.setText(get(Labels.ATT_DIALOG_ACTIVE));
		controller.typeLabel.setText(get(Labels.ATT_DIALOG_TYPE));
		controller.kindLabel.setText(get(Labels.ATT_DIALOG_KIND));
		controller.preferenceLabel.setText(get(Labels.ATT_DIALOG_PREFERENCE));
		controller.enumsLabel.setText(get(Labels.ATT_DIALOG_ENUMS));
		controller.saveButton.setText(get(Labels.ATT_DIALOG_SAVE_BUTTON));
		controller.cancelButton.setText(get(Labels.ATT_DIALOG_CANCEL_BUTTON));
		controller.clearButton.setText(get(Labels.ATT_DIALOG_CLEAR_BUTTON));
	}
	
}
