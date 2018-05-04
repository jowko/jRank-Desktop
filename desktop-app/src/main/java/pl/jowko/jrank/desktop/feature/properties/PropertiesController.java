package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesController {
	
//	@FXML
//	private ComboBox<JRankParameter> testComboBox;
	
	private JRankParametersService parametersService;
	private JRankParameter emptyParameter;

	@FXML
	public void initialize() {
		parametersService = JRankParametersService.getInstance();
		emptyParameter = parametersService.getEmptyParameter();
//		testComboBox.getItems().setAll(parametersService.getNegativeExamplesTreatmentForVCDRSA());
//		testComboBox.getSelectionModel().select(emptyParameter);
	}

}
