package pl.jowko.rulerank.desktop.feature.tabs.lower;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.output.OM;
import pl.poznan.put.cs.idss.jrs.output.SystemOut;

import java.io.OutputStream;
import java.io.PrintStream;

import static java.util.Objects.nonNull;

/**
 * Controller for logs tab. <br>
 * It sets output stream to TextArea in logs tab. <br>
 * <br>
 * Created by Piotr on 2018-04-10.
 */
public class LogsController {
	
	@FXML
	TextArea logsTextArea;
	
	/**
	 * Initializes logs tab. <br>
	 * It replaces output and error stream with custom solution. <br>
	 * This enables to add all output text to TextArea component.
	 */
	@FXML
	public void initialize() {
		Console console = new Console(logsTextArea);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		initializeContextMenu();
		initializeFocusLogTabEvent();
		initializeJrsLogging();
		RuleRankLogger.init("Initialized logs tab");
	}
	
	private void initializeJrsLogging() {
		SystemOut systemOut = new SystemOut();
		OM.addOutput(systemOut);
		OM.setDefaultOutput(systemOut.getKey());
	}
	
	/**
	 * This method add listener to TextArea text property. <br>
	 * On each text change(new logs), log tab will be focused. <br>
	 * Instance of controller is got by each time, because on the start of application instance of LowerTabsController can be null.
	 * @see LowerTabsController
	 */
	private void initializeFocusLogTabEvent() {
		logsTextArea.textProperty().addListener((oo, o, n) -> {
			LowerTabsController controller = LowerTabsController.getInstance();
			if(nonNull(controller))
				controller.focusOnLogTab();
		});
	}
	
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createClearLogMenuItem());
		logsTextArea.setContextMenu(menu);
	}
	
	/**
	 * Create context menu item for clear action. <br>
	 * This action will clear all logs from TextArea.
	 * @return MenuItem with clear action
	 */
	private MenuItem createClearLogMenuItem() {
		MenuItem item = new MenuItem("Clear logs");
		item.setOnAction(event -> logsTextArea.clear());
		return item;
	}
	
	/**
	 * This class overrides output stream. <br>
	 * It appends all output text to TextArea.
	 */
	public static class Console extends OutputStream {
		
		private TextArea output;
		
		Console(TextArea textArea) {
			this.output = textArea;
		}
		
		@Override
		public void write(int i) {
			output.appendText(String.valueOf((char) i));
		}
		
		@Override
		public void write(byte[] b, int off, int len) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0 ; i < len ; i++) {
				builder.append((char) b[off + i]);
			}
			output.appendText(builder.toString());
		}
	}
	
}
