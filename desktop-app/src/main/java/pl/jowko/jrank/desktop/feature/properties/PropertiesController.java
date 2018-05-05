package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.feature.upperTabs.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

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
	}
	
	public void saveAction() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		//TODO validate form
		try {
			new PropertiesSaver(editableProperties).save(workspaceItem.getFilePath());
			JRankLogger.info("Properties: " + workspaceItem.getFileName() + " saved successfully in: " + workspaceItem.getFilePath());
			cancelAction();
		} catch (IOException e) {
			String msg = "Error when saving properties: "; //TODO make label
			JRankLogger.error( msg + workspaceItem.getFileName() + " - " + e.getMessage());
			new DialogsService().showErrorDialog(msg, e.getMessage());
		}
	}
	
	public void cancelAction() {
		UpperTabsController.getInstance().closeTab(propertiesTab);
	}
	
	public void setDefaultsAction() {
		try {
			editableProperties = new DefaultPropertiesProvider().getDefaultProperties();
			controllerHelper.setEditableProperties(editableProperties);
			controllerHelper.fillFieldsValues();
		} catch (IOException e) {
			JRankLogger.error("Error when reading default.properties: " + e.getMessage());
		}
	}
	
	public void clearFormAction() {
		controllerHelper.clearForm();
	}
	
	public void restoreOriginalValuesAction() {
		editableProperties = (JRankProperties) Cloner.deepClone(properties);
		controllerHelper.setEditableProperties(editableProperties);
		controllerHelper.fillFieldsValues();
	}
	
}
