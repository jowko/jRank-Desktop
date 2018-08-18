package pl.jowko.rulerank.feature.customfx;

import javafx.scene.control.TextField;

/**
 * This component mimics label with selectable text.<br>
 * This class to work properly, needs selectable-label style class:<br>
 *<br>
 * .selectable-label {  <br>
 * 	-fx-background-color: transparent;  <br>
 * 	-fx-background-insets: 0;  <br>
 * 	-fx-background-radius: 0;  <br>
 * 	-fx-padding: 0;  <br>
 * }  <br>
 *<br>
 * Created by Piotr on 2018-05-21.
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
