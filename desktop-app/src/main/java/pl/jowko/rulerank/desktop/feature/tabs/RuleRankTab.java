package pl.jowko.rulerank.desktop.feature.tabs;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import pl.jowko.rulerank.desktop.ResourceLoader;
import pl.jowko.rulerank.desktop.exception.ErrorMessageParser;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;

import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * This class contains functionality common for tabs in this application. <br>
 * <br>
 * Created by Piotr on 2018-04-29.
 */
public abstract class RuleRankTab extends Tab {
	
	protected WorkspaceItem workspaceItem;
	
	private final String tabName;
	private boolean tabEdited;
	
	/**
	 * Creates instance of this class. <br>
	 * Also setting tab text.
	 * @param tabName to display on table header and with will be final
	 */
	protected RuleRankTab(String tabName) {
		this.tabName = tabName;
		setText(tabName);
	}
	
	/**
	 * This method returns path to fxml resource.
	 * @return path to fxml resource
	 */
	protected abstract String getResourceName();
	
	/**
	 * Initialize tab from provided workspace item. <br>
	 * It loads fxml file for tab by getResourceName method <br>
	 * It will return Controller from fxml file.
	 * @param workspaceItem with will be loaded
	 * @param <T> controller from fxml file
	 * @return controller loaded from fxml file
	 * @throws IOException when something goes wrong
	 */
	protected <T> T initializeTabAndGetController(WorkspaceItem workspaceItem) throws IOException {
		this.workspaceItem = workspaceItem;
		
		ResourceLoader loader = new ResourceLoader(getResourceName());
		Parent tabContent = loader.load();
		super.setContent(tabContent);
		
		return loader.getController();
	}
	
	/**
	 * This method throws TabInitializationException. <br>
	 * It should be used when some error occurred on tab initialization. <br>
	 * It appends tab name and file name to exception message. <br>
	 * Also gets messages from all nested exceptions and pass it to exception message without stacktrace.
	 * @param tabName where error occurred
	 * @param fileName from with tab had to be created
	 * @param exception with occurred on tab initialization
	 * @throws TabInitializationException with information about error
	 */
	protected void throwInitializationException(String tabName, String fileName, Throwable exception) throws TabInitializationException {
		StringBuilder builder = new StringBuilder();
		builder.append("Error while reading ")
				.append(tabName);
		
		if(nonNull(fileName)) {
			builder.append(" file: [")
					.append(fileName)
					.append("]. ");
		}
		
		builder.append(ErrorMessageParser.parseException(exception));
		
		throw new TabInitializationException(builder.toString());
	}
	
	/**
	 * Gets original tab name. <br>
	 * This value doesn't change, so if tab is in edit mode and has modified name, original value will be returned by this method. <br>
	 * To get current tab text(header), use getText() method
	 */
	public String getTabName() {
		return tabName;
	}
	
	/**
	 * Checks if tab was edited. <br>
	 * If tab was edited, it should have * character in tab name(header).
	 * @return true if tab was edited, false otherwise
	 */
	public boolean isTabEdited() {
		return tabEdited;
	}
	
	/**
	 * Inform tab about its edition state. <br>
	 * When tab is edited, * character is added on beginning of name. <br>
	 * If tab edition is canceled, * character is removed from beginning of tab name when this character was added before.
	 * @param tabEdited with inform tab about edition state
	 */
	public void setTabEdited(boolean tabEdited) {
		renameTabWhenEdited(tabEdited);
		this.tabEdited = tabEdited;
	}
	
	private void renameTabWhenEdited(boolean tabEdited) {
		if(tabEdited && this.tabEdited)
			return;
		
		if(tabEdited) {
			setText('*' + getText());
		} else if(this.tabEdited) { // edition canceled
			setText(tabName);
		}
	}
	
}