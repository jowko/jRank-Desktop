package pl.jowko.rulerank.desktop.feature.approximations;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller for approximations file(.apx). <br>
 * For now content of this file is displayed only as text. <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 */
public class ApproximationsController {
	
	@FXML
	private TextArea approximationsFileView;
	
	/**
	 * Initialize approximations tab. <br>
	 * It shows .apx file content in TextArea.
	 * @param fileContent of .apx file
	 */
	public void initializeTab(String fileContent) {
		approximationsFileView.setText(fileContent);
	}
	
}
