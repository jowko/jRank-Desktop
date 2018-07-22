package pl.jowko.rulerank.desktop.feature.approximations;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Created by Piotr on 2018-05-21.
 * Controller for approximations file(.apx).
 * For now content of this file is displayed only as text.
 */
public class ApproximationsController {
	
	@FXML
	private TextArea approximationsFileView;
	
	/**
	 * Initialize approximations tab.
	 * It shows .apx file content in TextArea.
	 * @param fileContent of .apx file
	 */
	public void initializeTab(String fileContent) {
		approximationsFileView.setText(fileContent);
	}
	
}