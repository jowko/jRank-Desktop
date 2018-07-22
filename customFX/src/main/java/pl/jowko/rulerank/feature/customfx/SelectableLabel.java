package pl.jowko.rulerank.feature.customfx;

import javafx.scene.control.TextField;

/**
 * Created by Piotr on 2018-05-21.
 * This component mimics label with selectable text.
 * This class to work properly, needs selectable-label style class:
 *
 * .selectable-label {
 * 	-fx-background-color: transparent;
 * 	-fx-background-insets: 0;
 * 	-fx-background-radius: 0;
 * 	-fx-padding: 0;
 * }
 */
public class SelectableLabel extends TextField {
	
	/**
	 * Default constructor
	 */
	public SelectableLabel() {
		setEditable(false);
		getStyleClass().add("selectable-label");
	}
	
	/**
	 * Constructor with text value for field
	 * @param text to display in component
	 */
	public SelectableLabel(String text) {
		setText(text);
		setEditable(false);
		getStyleClass().add("selectable-label");
	}
	
}
