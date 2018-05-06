package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.upperTabs.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesController {
	
	@FXML Label pctFileLabel;
	@FXML TextField pctFile;
	@FXML Label pctApxFileLabel;
	@FXML TextField pctApxFile;
	@FXML Label pctRulesFileLabel;
	@FXML TextField pctRulesFile;
	@FXML Label graphFileLabel;
	@FXML TextField graphFile;
	@FXML Label rankingFileLabel;
	@FXML TextField rankingFile;
	@FXML Label learningDataFileLabel;
	@FXML TextField learningDataFile;
	@FXML Label testDataFileLabel;
	@FXML TextField testDataFile;
	
	@FXML Label referenceRankingLabel;
	@FXML TextField referenceRanking;
	@FXML Label pairsLabel;
	@FXML TextField pairs;
	
	@FXML Label typeOfFamilyCriteriaLabel;
	@FXML ComboBox<JRankParameter> typeOfFamilyCriteria;
	@FXML Label typeOfRulesLabel;
	@FXML ComboBox<JRankParameter> typeOfRules;
	@FXML Label consideredSetOfRulesLabel;
	@FXML ComboBox<JRankParameter> consideredSetOfRules;
	
	@FXML Label consistencyMeasureLabel;
	@FXML ComboBox<JRankParameter> consistencyMeasure;
	@FXML Label consistencyMeasureThresholdLabel;
	@FXML TextField consistencyMeasureThreshold;
	@FXML Label precisionLabel;
	@FXML TextField precision;
	
	@FXML Label rankingProcedureLabel;
	@FXML ComboBox<JRankParameter> rankingProcedure;
	@FXML Label dominanceLabel;
	@FXML ComboBox<JRankParameter> dominance;
	@FXML Label dominanceForPairsLabel;
	@FXML ComboBox<JRankParameter> dominanceForPairs;
	
	@FXML Label satisfactionDegreesInGraphLabel;
	@FXML ComboBox<JRankParameter> satisfactionDegreesInGraph;
	@FXML Label fuzzyCalculationMethodLabel;
	@FXML ComboBox<JRankParameter> fuzzyCalculationMethod;
	
	@FXML Label negativeExamplesTreatmentLabel;
	@FXML ComboBox<JRankParameter> negativeExamplesTreatment;
	@FXML Label optimizeRuleConsistencyLabel;
	@FXML ComboBox<JRankParameter> optimizeRuleConsistency;
	@FXML Label ruleConditionsSelectionMethodLabel;
	@FXML ComboBox<JRankParameter> ruleConditionsSelectionMethod;
	
	@FXML CheckBox allowEmptyRules;
	@FXML CheckBox useEdgeRegions;
	
	@FXML CheckBox writeDominationInformation;
	@FXML CheckBox writeRulesStatistics;
	@FXML CheckBox writeLearningPositiveExamples;
	
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML Button setDefaultsButton;
	@FXML Button clearButton;
	@FXML Button restoreValuesButton;
	@FXML Button validateFormButton;
	@FXML Button validateFormDefaults;
	
	private PropertiesControllerHelper controllerHelper;
	private WorkspaceItem workspaceItem;
	private Tab propertiesTab;
	JRankProperties properties;
	JRankProperties editableProperties;
	
	public void initializeProperties(JRankProperties properties, WorkspaceItem workspaceItem, Tab propertiesTab) {
		this.properties = properties;
		this.workspaceItem = workspaceItem;
		this.propertiesTab = propertiesTab;
		editableProperties = (JRankProperties) Cloner.deepClone(properties);
		controllerHelper = new PropertiesControllerHelper(this);
		controllerHelper.fillComboBoxes();
		controllerHelper.fillFieldsValues();
		initializeCloseEvent();
	}
	
	public void saveAction() {
		if(not(isFormValid()))
			return;
		
		try {
			new PropertiesSaver(editableProperties).save(workspaceItem.getFilePath());
			JRankLogger.info("Properties: " + workspaceItem.getFileName() + " saved successfully in: " + workspaceItem.getFilePath());
			closeTab();
		} catch (IOException e) {
			String msg = "Error when saving properties: "; //TODO make label
			JRankLogger.error( msg + workspaceItem.getFileName() + " - " + e.getMessage());
			new DialogsService().showErrorDialog(msg, e.getMessage());
		}
	}
	
	public void cancelAction() {
		if(isUserWishToKeepChanges())
			return;
		closeTab();
	}
	
	public void setDefaultsAction() {
		if(isUserWishToKeepChanges())
			return;
		
		try {
			editableProperties = new DefaultPropertiesProvider().getDefaultProperties();
			controllerHelper.setEditableProperties(editableProperties);
			controllerHelper.fillFieldsValues();
		} catch (IOException e) {
			JRankLogger.error("Error when reading default.properties: " + e.getMessage());
		}
	}
	
	public void clearFormAction() {
		if(isUserWishToKeepChanges())
			return;
		controllerHelper.clearForm();
	}
	
	public void restoreOriginalValuesAction() {
		if(isUserWishToKeepChanges())
			return;
		
		editableProperties = (JRankProperties) Cloner.deepClone(properties);
		controllerHelper.setEditableProperties(editableProperties);
		controllerHelper.fillFieldsValues();
	}
	
	public void validateFormAction() {
		if(isFormValid()) {
			new DialogsService().showInfoDialog("Validation", "Form does not contain any errors."); //TODO make label
		}
	}
	
	public void validateFormDefaultsAction() {
		try {
			editableProperties = controllerHelper.getPropertiesFromForm();
			JRankProperties defaultProperties = new DefaultPropertiesProvider().getDefaultProperties();
			
			RunnerPropertiesProvider runnerPropertiesProvider = new RunnerPropertiesProvider(editableProperties, defaultProperties);
			JRankProperties propertiesWithDefaults = runnerPropertiesProvider.getPropertiesWithDefaults();
			
			PropertiesValidator validator = new PropertiesValidator(propertiesWithDefaults);
			
			if(not(validator.isValid())) {
				new DialogsService().showValidationFailedDialog("", validator.getErrorMessages());
			} else {
				new DialogsService().showInfoDialog("Validation with defaults", "Form does not contain any errors.");
			}
		} catch (IOException e) {
			JRankLogger.error("Error when reading default.properties: " + e.getMessage());
		}
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(propertiesTab);
	}
	
	private void initializeCloseEvent() {
		propertiesTab.setOnCloseRequest(event -> {
			if(isUserWishToKeepChanges()) {
				event.consume();
			}
		});
	}
	
	private boolean isFormValid() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		PropertiesValidator validator = new PropertiesValidator(editableProperties);
		
		if(not(validator.isValid())) {
			new DialogsService().showValidationFailedDialog("", validator.getErrorMessages());
		}
		
		return validator.isValid();
	}
	
	private boolean isUserWishToKeepChanges() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		return not(editableProperties.equals(properties)) && not(showConfirmActionDialog());
	}
	
	private boolean showConfirmActionDialog() {
		return new DialogsService().showConfirmationDialog("Do you want to abandon changes in form?"); //TODO make label
	}
	
}
