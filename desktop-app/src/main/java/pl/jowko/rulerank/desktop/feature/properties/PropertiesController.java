package pl.jowko.rulerank.desktop.feature.properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import pl.jowko.rulerank.desktop.Main;
import pl.jowko.rulerank.desktop.ResourceLoader;
import pl.jowko.rulerank.desktop.exception.ConfigurationException;
import pl.jowko.rulerank.desktop.exception.ErrorMessageParser;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.properties.information.AbstractInformationController;
import pl.jowko.rulerank.desktop.feature.properties.information.TextParseFailException;
import pl.jowko.rulerank.desktop.feature.runner.ExperimentRunner;
import pl.jowko.rulerank.desktop.feature.runner.RunnerException;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;
import pl.jowko.rulerank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.rulerank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.rulerank.desktop.service.AbandonableTabForm;
import pl.jowko.rulerank.desktop.service.DialogsService;
import pl.jowko.rulerank.desktop.service.JRSFileMediator;
import pl.jowko.rulerank.desktop.utils.Cloner;
import pl.jowko.rulerank.desktop.utils.StringUtils;
import pl.jowko.rulerank.feature.customfx.DecimalField;
import pl.jowko.rulerank.feature.customfx.IntegerField;
import pl.jowko.rulerank.logger.RuleRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;
import static pl.jowko.rulerank.desktop.utils.PathUtils.getAbsoluteExperimentFilePath;

/**
 * Created by Piotr on 2018-04-29.
 * Controller for properties form tab.
 */
public class PropertiesController implements AbandonableTabForm {
	
	@FXML Button runExperimentButton;
	
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
	@FXML ComboBox<RuleRankParameter> typeOfFamilyCriteria;
	@FXML Label typeOfRulesLabel;
	@FXML ComboBox<RuleRankParameter> typeOfRules;
	@FXML Label consideredSetOfRulesLabel;
	@FXML ComboBox<RuleRankParameter> consideredSetOfRules;
	
	@FXML Label consistencyMeasureLabel;
	@FXML ComboBox<RuleRankParameter> consistencyMeasure;
	@FXML Label consistencyMeasureThresholdLabel;
	@FXML DecimalField consistencyMeasureThreshold;
	@FXML Label precisionLabel;
	@FXML IntegerField precision;
	
	@FXML Label rankingProcedureLabel;
	@FXML ComboBox<RuleRankParameter> rankingProcedure;
	@FXML Label dominanceLabel;
	@FXML ComboBox<RuleRankParameter> dominance;
	@FXML Label dominanceForPairsLabel;
	@FXML ComboBox<RuleRankParameter> dominanceForPairs;
	
	@FXML Label satisfactionDegreesInGraphLabel;
	@FXML ComboBox<RuleRankParameter> satisfactionDegreesInGraph;
	@FXML Label fuzzyCalculationMethodLabel;
	@FXML ComboBox<RuleRankParameter> fuzzyCalculationMethod;
	
	@FXML Label negativeExamplesTreatmentLabel;
	@FXML ComboBox<RuleRankParameter> negativeExamplesTreatment;
	@FXML Label optimizeRuleConsistencyLabel;
	@FXML ComboBox<RuleRankParameter> optimizeRuleConsistency;
	@FXML Label ruleConditionsSelectionMethodLabel;
	@FXML ComboBox<RuleRankParameter> ruleConditionsSelectionMethod;
	
	@FXML Label allowEmptyRulesLabel;
	@FXML ComboBox<RuleRankParameter> allowEmptyRules;
	@FXML Label useEdgeRegionsLabel;
	@FXML ComboBox<RuleRankParameter> useEdgeRegions;
	
	@FXML TitledPane additionalInfoPane;
	@FXML Label writeDominationInformationLabel;
	@FXML ComboBox<RuleRankParameter> writeDominationInformation;
	@FXML Label writeRulesStatisticsLabel;
	@FXML ComboBox<RuleRankParameter> writeRulesStatistics;
	@FXML Label writeLearningPositiveExamplesLabel;
	@FXML ComboBox<RuleRankParameter> writeLearningPositiveExamples;
	
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML Button clearButton;
	@FXML Button restoreValuesButton;
	@FXML Button validateFormButton;
	@FXML Button validateFormDefaults;
	
	private LanguageService labels;
	private PropertiesControllerHelper controllerHelper;
	private WorkspaceItem workspaceItem;
	private RuleRankTab propertiesTab;
	RuleRankProperties properties;
	RuleRankProperties editableProperties;
	RuleRankProperties defaultProperties;
	
	
	/**
	 * Initialize properties form with provided data.
	 * @param properties loaded from .properties file
	 * @param workspaceItem from workspace tree with represents properties file
	 * @param propertiesTab on with properties form is located
	 */
	public void initializeProperties(RuleRankProperties properties, WorkspaceItem workspaceItem, RuleRankTab propertiesTab) throws IOException {
		this.properties = properties;
		this.workspaceItem = workspaceItem;
		this.propertiesTab = propertiesTab;
		
		labels = LanguageService.getInstance();
		editableProperties = (RuleRankProperties) Cloner.deepClone(properties);
		defaultProperties = new DefaultPropertiesProvider().getDefaultProperties();
		controllerHelper = new PropertiesControllerHelper(this);
		
		initializeCloseEvent();
		new PropertiesTooltipsHelper(this).initializeTooltips();
		new PropertiesTranslatorHelper(this).translateFields();
		new PropertiesChangeListener(this, propertiesTab).setUpListeners();
	}
	
	/**
	 * This action performs experiment using settings from current and default properties.
	 * It also uses configured isf tables.
	 * @see ExperimentRunner
	 */
	public void runExperimentAction() {
		RuleRankProperties currentProperties = controllerHelper.getPropertiesFromForm();
		try {
			ExperimentRunner runner = new ExperimentRunner(currentProperties, workspaceItem);
			runner.run();
		} catch (RunnerException e) {
			DialogsService.showErrorDialog("", e.getMessage());
		}
	}
	
	/**
	 * Save properties form to .properties file.
	 * It will validate, if form is correct.
	 * Then it will save file to it original location.
	 */
	public void saveAction() {
		if(not(isFormValidForSave()))
			return;
		
		try {
			new PropertiesSaver(editableProperties).save(workspaceItem.getFilePath());
			RuleRankLogger.info("Properties: " + workspaceItem.getFileName() + " saved successfully in: " + workspaceItem.getFilePath());
			closeTab();
		} catch (IOException e) {
			String msg = labels.get(Labels.PROP_ERROR_SAVE);
			RuleRankLogger.error( msg + workspaceItem.getFileName() + " - " + e.getMessage());
			DialogsService.showErrorDialog(msg, e.getMessage());
		}
	}
	
	/**
	 * Close properties tab.
	 * If user made some changes in form, he will be asked if he want to keep changes.
	 */
	public void cancelAction() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		if(isUserWishToKeepChanges())
			return;
		closeTab();
	}
	
	/**
	 * Clears all form(sets empty or null values in form fields)
	 * If user made some changes in form, he will be asked if he want to keep changes.
	 */
	public void clearFormAction() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		if(isUserWishToKeepChanges())
			return;
		controllerHelper.clearForm();
	}
	
	/**
	 * Restore original values in properties form.
	 * It will ask user to confirm this action.
	 */
	public void restoreOriginalValuesAction() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		if(isUserWishToKeepChanges())
			return;
		
		editableProperties = (RuleRankProperties) Cloner.deepClone(properties);
		controllerHelper.setEditableProperties(editableProperties);
		controllerHelper.fillFieldsValues();
	}
	
	/**
	 * Validates form and show message if form is valid.
	 */
	public void validateFormAction() {
		if(isFormValid()) {
			String title = labels.get(Labels.PROP_VALIDATE_DIALOG_TITLE);
			String content = labels.get(Labels.PROP_VALIDATE_DIALOG_CONTENT);
			DialogsService.showInfoDialog(title, content);
		}
	}
	
	/**
	 * This methods validates properties form with default values.
	 * When running ruleRank application, all experiment settings are needed.
	 * So if user will leave some fields empty, they will be replaced with default values.
	 * Default values are taken from default.properties in main workspace directory.
	 * This method will load default properties and merge them with values from form.
	 * Then it will perform validation on result.
	 * @see RunnerPropertiesProvider
	 */
	public void validateFormDefaultsAction() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		
		RunnerPropertiesProvider runnerPropertiesProvider = new RunnerPropertiesProvider(editableProperties, defaultProperties);
		RuleRankProperties propertiesWithDefaults = runnerPropertiesProvider.getPropertiesWithDefaults();
		
		PropertiesValidator validator = new PropertiesValidator(propertiesWithDefaults);
		if(not(validator.isValid())) {
			DialogsService.showValidationFailedDialog("", validator.getErrorMessages());
			return;
		}
		
		PropertiesMandatoryFieldsValidator emptyFieldsValidator = new PropertiesMandatoryFieldsValidator(propertiesWithDefaults);
		if(not(emptyFieldsValidator.isValid())) {
			DialogsService.showValidationFailedDialog("", emptyFieldsValidator.getErrorMessages());
			return;
		}
		
		String title = labels.get(Labels.PROP_VD_DIALOG_TITLE);
		String content = labels.get(Labels.PROP_VD_DIALOG_CONTENT);
		DialogsService.showInfoDialog(title, content);
	}
	
	/**
	 * Initializes edit ranking action.
	 * It will create ranking dialog for ranking edition.
	 * @throws IOException when something goes wrong with loading files
	 */
	public void editRankingAction() throws IOException {
		String text = referenceRanking.getText();
		if(isNull(text))
			text = "";
		StringProperty result = new SimpleStringProperty(text);
		result.addListener((oo, old, newValue) -> referenceRanking.setText(newValue));
		initializeInformationForm("/fxml/upperTabs/properties/rankingDialog.fxml", Labels.PROP_RANKING_TITLE, result);
	}
	
	/**
	 * Initializes edit pairs action.
	 * It will create ranking dialog for pairs edition.
	 * @throws IOException when something goes wrong with loading files
	 */
	public void editPairsAction() throws IOException {
		String text = pairs.getText();
		if(isNull(text))
			text = "";
		StringProperty result = new SimpleStringProperty(text);
		result.addListener((oo, old, newValue) -> pairs.setText(newValue));
		initializeInformationForm("/fxml/upperTabs/properties/pairsDialog.fxml", Labels.PROP_PAIRS_TITLE, result);
	}
	
	@Override
	public RuleRankTab getTab() {
		return propertiesTab;
	}
	
	/**
	 * Initializes information form on with pairs or ranking is edited.
	 * If field contains invalid content, dialog will not show and user see errors message.
	 * @param fxmlPath to pairs or ranking dialog path
	 * @param titleLabel to display on modal window
	 * @throws IOException when something goes wrong with reading files
	 */
	private void initializeInformationForm(String fxmlPath, String titleLabel, StringProperty result) throws IOException {
		MemoryContainer container = getContainerAndValidate();
		if(isNull(container)) {
			return;
		}
		
		ResourceLoader loader = new ResourceLoader(fxmlPath);
		Parent root = loader.load();
		AbstractInformationController controller = loader.getController();
		
		try {
			controller.initializeForm(container, result);
		} catch (TextParseFailException e) {
			DialogsService.showActionFailedDialog(e.getMessage());
			String errorLog = "Error when parsing text from properties field: " + ErrorMessageParser.parseException(e.getCause());
			RuleRankLogger.error(errorLog);
			return;
		}
		
		controller.createWindow(root, Main.getScene(), labels.get(titleLabel));
	}
	
	/**
	 * Loads MemoryContainer using learning table path from properties form.
	 * It can show dialog containing error, when isf table is not configured.
	 * @return MemoryContainer or null
	 */
	private MemoryContainer getContainerAndValidate() {
		try {
			MemoryContainer container = getIsfTable();
			if(isNull(container)) {
				throw new ConfigurationException("Learning data table not found or is empty. Check your configuration.");
			}
			return container;
			
		} catch (ConfigurationException e) {
			DialogsService.showInfoDialog("Wrong configuration", e.getMessage());
			return null;
		}
	}
	
	/**
	 * Loads MemoryContainer(isf table) from learning data file path extracted from proporties form.
	 * If file path is not configured, ConfigurationException is thrown
	 * @return MemoryContainer
	 * @throws ConfigurationException when learning data file path is not configured
	 */
	private MemoryContainer getIsfTable() {
		String isfFilePath = learningDataFile.getText();
		if(StringUtils.isNullOrEmpty(isfFilePath)) {
			throw new ConfigurationException("Could not locate isf table. Learning data file is not configured in properties form.");
		}
		isfFilePath = getAbsoluteExperimentFilePath(workspaceItem.getExperimentPath(), isfFilePath);
		
		return JRSFileMediator.loadMemoryContainer(isfFilePath);
	}
	
	private void closeTab() {
		UpperTabsController.getInstance().closeTab(propertiesTab);
	}
	
	/**
	 * Checks, if form has valid values.
	 * If form containing errors, they will be displayed to user.
	 * @return true if form is valid, false otherwise
	 */
	private boolean isFormValid() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		PropertiesValidator validator = new PropertiesValidator(editableProperties);
		
		if(not(validator.isValid())) {
			DialogsService.showValidationFailedDialog("", validator.getErrorMessages());
		}
		
		return validator.isValid();
	}
	
	/**
	 * Checks, if form has valid values.
	 * It it contains errors, this errors are shown to user.
	 * Application also ask, if user still want to save form.
	 * @return true if form is valid or user wish to save form with invalid values.
	 */
	private boolean isFormValidForSave() {
		editableProperties = controllerHelper.getPropertiesFromForm();
		PropertiesValidator validator = new PropertiesValidator(editableProperties);
		
		if(not(validator.isValid())) {
			String header = labels.get(Labels.PROP_SAVE_ERROR_CONFIRM);
			return DialogsService.showConfirmationDialog(header, validator.getErrorMessages());
		}
		return true;
	}
	
}
