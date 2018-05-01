package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesController {
	
	@FXML
	private ComboBox<JRankParameter> testComboBox;
	
	private JRankParametersService parametersService;

	@FXML
	public void initialize() {
		parametersService = JRankParametersService.getInstance();
		testComboBox.getItems().setAll(parametersService.getNegativeExamplesTreatmentForVCDRSA());
	}
	
	public void onButtonAction() {
		System.out.println(testComboBox.getValue());
		System.out.println(testComboBox.getValue().getTextValue());
	}

}
