package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.properties.pairs.PropertiesPairsController;
import pl.jowko.jrank.desktop.feature.properties.ranking.PropertiesRankingController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.feature.customfx.DecimalField;
import pl.jowko.jrank.feature.customfx.IntegerField;
import pl.jowko.jrank.logger.JRankLogger;

import java.io.IOException;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesController {
	
	@FXML TitledPane filesPane;
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
	
	@FXML Label dataPanelLabel;
	@FXML Label referenceRankingLabel;
	@FXML TextArea referenceRanking;
	@FXML Button editRankingButton;
	@FXML Label pairsLabel;
	@FXML TextArea pairs;
	@FXML Button editPairsButton;
	
	@FXML TitledPane parametersPane;
	@FXML Label typeOfFamilyCriteriaLabel;
	@FXML ComboBox<JRankParameter> typeOfFamilyCriteria;
	@FXML Label typeOfRulesLabel;
	@FXML ComboBox<JRankParameter> typeOfRules;
	@FXML Label consideredSetOfRulesLabel;
	@FXML ComboBox<JRankParameter> consideredSetOfRules;
	
	@FXML Label consistencyMeasureLabel;
	@FXML ComboBox<JRankParameter> consistencyMeasure;
	@FXML Label consistencyMeasureThresholdLabel;
	@FXML DecimalField consistencyMeasureThreshold;
	@FXML Label precisionLabel;
	@FXML IntegerField precision;
	
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
	
	@FXML Label allowEmptyRulesLabel;
	@FXML ComboBox<JRankParameter> allowEmptyRules;
	@FXML Label useEdgeRegionsLabel;
	@FXML ComboBox<JRankParameter> useEdgeRegions;
	
	@FXML TitledPane additionalInfoPane;
	@FXML Label writeDominationInformationLabel;
	@FXML ComboBox<JRankParameter> writeDominationInformation;
	@FXML Label writeRulesStatisticsLabel;
	@FXML ComboBox<JRankParameter> writeRulesStatistics;
	@FXML Label writeLearningPositiveExamplesLabel;
	@FXML ComboBox<JRankParameter> writeLearningPositiveExamples;
	
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML Button clearButton;
	@FXML Button restoreValuesButton;
	@FXML Button validateFormButton;
	@FXML Button validateFormDefaults;
	
	private LanguageService labels;
	private PropertiesControllerHelper controllerHelper;
	private WorkspaceItem workspaceItem;
	private JRankTab propertiesTab;
	JRankProperties properties;
	JRankProperties editableProperties;
	
	public void initializeProperties(JRankProperties properties, WorkspaceItem workspaceItem, JRankTab propertiesTab) {
		this.properties = properties;
		this.workspaceItem = workspaceItem;
		this.propertiesTab = propertiesTab;
		
		labels = LanguageService.getInstance();
		editableProperties = (JRankProperties) Cloner.deepClone(properties);
		controllerHelper = new PropertiesControllerHelper(this);
		
		initializeCloseEvent();
		new PropertiesTooltipsHelper(this).initializeTooltips();
		new PropertiesTranslatorHelper(this).translateFields();
		new PropertiesChangeListener(this, propertiesTab).setUpListeners();
	}
	
	public void saveAction() {
		if(not(isFormValidForSave()))
			return;
		
		try {
			new PropertiesSaver(editableProperties).save(workspaceItem.getFilePath());
			JRankLogger.info("Properties: " + workspaceItem.getFileName() + " saved successfully in: " + workspaceItem.getFilePath());
			closeTab();
		} catch (IOException e) {
			String msg = labels.get(Labels.PROP_ERROR_SAVE);
			JRankLogger.error( msg + workspaceItem.getFileName() + " - " + e.getMessage());
			DialogsService.showErrorDialog(msg, e.getMessage());
		}
	}
	
	public void cancelAction() {
		if(isUserWishToKeepChanges())
			return;
		closeTab();
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
			String title = labels.get(Labels.PROP_VALIDATE_DIALOG_TITLE);
			String content = labels.get(Labels.PROP_VALIDATE_DIALOG_CONTENT);
			DialogsService.showInfoDialog(title, content);
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
				DialogsService.showValidationFailedDialog("", validator.getErrorMessages());
			} else {
				String title = labels.get(Labels.PROP_VD_DIALOG_TITLE);
				String content = labels.get(Labels.PROP_VD_DIALOG_CONTENT);
				DialogsService.showInfoDialog(title, content);
			}
		} catch (IOException e) {
			JRankLogger.error("Error when reading default.properties: " + e.getMessage());
		}
	}
	
	public void editRankingAction() throws IOException {
		ResourceLoader loader = new ResourceLoader("/fxml/upperTabs/properties/rankingDialog.fxml");
		Parent root = loader.load();
		PropertiesRankingController controller = loader.getController();
		controller.createWindow(root, Main.getScene(), labels.get(Labels.PROP_RANKING_TITLE));
	}
	
	public void editPairsAction() throws IOException {
		ResourceLoader loader = new ResourceLoader("/fxml/upperTabs/properties/pairsDialog.fxml");
		Parent root = loader.load();
		PropertiesPairsController controller = loader.getController();
		controller.createWindow(root, Main.getScene(), labels.get(Labels.PROP_PAIRS_TITLE));
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
			DialogsService.showValidationFailedDialog("", validator.getErrorMessages());
		}
		
		return validator.isValid();
	}
	
	private boolean isFormValidForSave() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		PropertiesValidator validator = new PropertiesValidator(editableProperties);
		
		if(not(validator.isValid())) {
			String header = labels.get(Labels.PROP_SAVE_ERROR_CONFIRM);
			return DialogsService.showConfirmationDialog(header, validator.getErrorMessages());
		}
		return true;
	}
	
	private boolean isUserWishToKeepChanges() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		return propertiesTab.isTabEdited() && not(showConfirmActionDialog());
	}
	
	private boolean showConfirmActionDialog() {
		String header = labels.get(Labels.PROP_ABANDON_CHANGES);
		return DialogsService.showConfirmationDialog(header);
	}
	
}
