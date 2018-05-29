package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.Main;
import pl.jowko.jrank.desktop.ResourceLoader;
import pl.jowko.jrank.desktop.exception.ConfigurationException;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.properties.information.AbstractInformationController;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;
import pl.jowko.jrank.desktop.feature.tabs.upper.UpperTabsController;
import pl.jowko.jrank.desktop.feature.workspace.WorkspaceItem;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.jowko.jrank.desktop.service.JRSFileMediator;
import pl.jowko.jrank.desktop.utils.Cloner;
import pl.jowko.jrank.desktop.utils.StringUtils;
import pl.jowko.jrank.feature.customfx.DecimalField;
import pl.jowko.jrank.feature.customfx.IntegerField;
import pl.jowko.jrank.logger.JRankLogger;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
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
	
	/**
	 * Initializes edit ranking action.
	 * It will create ranking dialog for ranking edition.
	 * @throws IOException when something goes wrong with loading files
	 */
	public void editRankingAction() throws IOException {
		initializeInformationForm("/fxml/upperTabs/properties/rankingDialog.fxml", Labels.PROP_RANKING_TITLE);
	}
	
	/**
	 * Initializes edit pairs action.
	 * It will create ranking dialog for pairs edition.
	 * @throws IOException when something goes wrong with loading files
	 */
	public void editPairsAction() throws IOException {
		initializeInformationForm("/fxml/upperTabs/properties/pairsDialog.fxml", Labels.PROP_PAIRS_TITLE);
	}
	
	/**
	 * Initializes information form on with pairs or ranking is edited.
	 * @param fxmlPath to pairs or ranking dialog path
	 * @param titleLabel to display on modal window
	 * @throws IOException when something goes wrong with reading files
	 */
	private void initializeInformationForm(String fxmlPath, String titleLabel) throws IOException {
		MemoryContainer container = getContainerAndValidate();
		if(isNull(container)) {
			return;
		}
		
		ResourceLoader loader = new ResourceLoader(fxmlPath);
		Parent root = loader.load();
		AbstractInformationController controller = loader.getController();
		controller.createWindow(root, Main.getScene(), labels.get(titleLabel));
		controller.initializeForm(container);
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
		Path path = Paths.get(isfFilePath);
		if(not(path.isAbsolute())) {
			isfFilePath = workspaceItem.getExperimentPath() + isfFilePath;
		}
		
		return JRSFileMediator.loadMemoryContainer(isfFilePath);
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
