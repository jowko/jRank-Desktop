package pl.jowko.rulerank.desktop.feature.learningtable.dialogs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import pl.poznan.put.cs.idss.jrs.types.Attribute;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-19.
 * This class handle change event in attributes ListView.
 * When attribute is selected, attribute from form is validated and saved if it is valid.
 */
class AttributeChangeListener implements ChangeListener<AttributeItem> {
	
	private AttributeDialogController controller;
	
	/**
	 * Boolean flag used for revering select action in ListView when attribute form was invalid
	 */
	private boolean changing;
	/**
	 * Boolean flag used to prevent save of invalid data in attribute form when select action from ListView is reverted.
	 */
	private boolean formWasNotValid;
	
	AttributeChangeListener(AttributeDialogController controller) {
		this.controller = controller;
	}
	
	/**
	 * This method is called when change event is called in attribute ListView.
	 * It checks, if attribute form is valid, save valid attribute and change edited attribute in form to selected.
	 * @param observable from ListView
	 * @param oldValue from ListView
	 * @param newValue from ListView
	 */
	@Override
	public void changed(ObservableValue<? extends AttributeItem> observable, AttributeItem oldValue, AttributeItem newValue) {
		if(not(isFormValid(oldValue)))
			return;
		
		// Save attribute
		int indexToChange = controller.getAttributes().indexOf(oldValue.getAttribute());
		Attribute newAttribute = controller.createAttributeFromForm();
		controller.getAttributes().set(indexToChange, newAttribute);
		
		// Switch to selected attribute
		controller.setEditedAttribute(newValue.getAttribute());
		controller.attributesList.getItems().set(indexToChange, new AttributeItem(newAttribute));
		controller.initializeFieldsForEdit();
	}
	
	/**
	 * Checks if attribute form is valid.
	 * If form is not valid, this method changes current selected item to previous one.
	 * Two boolean flags are used, because when this method change value of selected item, changed event is fired again.
	 * Flags prevent this code from infinite loop and from saving invalid data.
	 * @param oldValue from changed method
	 * @return true if form is valid, false otherwise
	 */
	private boolean isFormValid(AttributeItem oldValue) {
		if(not(changing) && not(controller.isFormValid())) {
			changing = true;
			formWasNotValid = true;
			Platform.runLater(() -> {
				controller.attributesList.getSelectionModel().select(oldValue);
				changing = false;
			});
			return false;
		}
		
		if(formWasNotValid) {
			formWasNotValid = false;
			return false;
		}
		return true;
	}
	
}
