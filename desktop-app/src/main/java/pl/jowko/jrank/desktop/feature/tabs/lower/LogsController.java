package pl.jowko.jrank.desktop.feature.tabs.lower;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Piotr on 2018-04-10.
 * Controller for logs tab.
 * It sets output stream to TextArea in logs tab.
 */
public class LogsController {
	
	@FXML
	TextArea logsTextArea;
	
	/**
	 * Initializes logs tab.
	 * It replaces output and error stream with custom solution.
	 * This enables to add all output text to TextArea component.
	 */
	@FXML
	public void initialize() {
		Console console = new Console(logsTextArea);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		initializeContextMenu();
		JRankLogger.init("Initialized logs tab");
	}
	
	private void initializeContextMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().add(createClearLogMenuItem());
		logsTextArea.setContextMenu(menu);
	}
	
	/**
	 * Create context menu item for clear action.
	 * This action will clear all logs from TextArea.
	 * @return MenuItem with clear action
	 */
	private MenuItem createClearLogMenuItem() {
		MenuItem item = new MenuItem("Clear logs");
		item.setOnAction(event -> logsTextArea.clear());
		return item;
	}
	
	/**
	 * This class overrides output stream.
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
