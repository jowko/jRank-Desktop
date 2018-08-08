package pl.jowko.rulerank.desktop.feature.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;

/**
 * Created by Piotr on 2018-08-08
 * This class adds change listener to any text input.
 * If any change in fields occurs, this class marks tab as edited.
 * After this listener removes itself.
 */
public class RemovableChangeListener implements ChangeListener<String> {
	
	private RuleRankTab tab;
	private TextInputControl field;
	
	/**
	 * Create instance of this class
	 * @param tab with will be marked as edited when change event occurs
	 * @param textField on with this listener will be added
	 */
	public RemovableChangeListener(RuleRankTab tab, TextInputControl textField) {
		this.tab = tab;
		this.field = textField;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		tab.setTabEdited(true);
		field.textProperty().removeListener(this);
	}
	
}
