package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jowko.jrank.desktop.utils.Cloner;

import static java.util.Objects.isNull;

/**
 * Created by Piotr on 2018-04-29.
 */
public class PropertiesController {
	
	@FXML private Label pctFileLabel;
	@FXML private TextField pctFile;
	@FXML private Label pctApxFileLabel;
	@FXML private TextField pctApxFile;
	@FXML private Label pctRulesFileLabel;
	@FXML private TextField pctRulesFile;
	@FXML private Label graphFileLabel;
	@FXML private TextField graphFile;
	@FXML private Label rankingFileLabel;
	@FXML private TextField rankingFile;
	@FXML private Label learningDataFileLabel;
	@FXML private TextField learningDataFile;
	@FXML private Label testDataFileLabel;
	@FXML private TextField testDataFile;
	
	@FXML private Label referenceRankingLabel;
	@FXML private TextField referenceRanking;
	@FXML private Label pairsLabel;
	@FXML private TextField pairs;
	
	@FXML private Label typeOfFamilyCriteriaLabel;
	@FXML private ComboBox<JRankParameter> typeOfFamilyCriteria;
	@FXML private Label typeOfRulesLabel;
	@FXML private ComboBox<JRankParameter> typeOfRules;
	@FXML private Label consideredSetOfRulesLabel;
	@FXML private ComboBox<JRankParameter> consideredSetOfRules;
	
	@FXML private Label consistencyMeasureLabel;
	@FXML private ComboBox<JRankParameter> consistencyMeasure;
	@FXML private Label consistencyMeasureThresholdLabel;
	@FXML private TextField consistencyMeasureThreshold;
	@FXML private Label precisionLabel;
	@FXML private TextField precision;
	
	@FXML private Label rankingProcedureLabel;
	@FXML private ComboBox<JRankParameter> rankingProcedure;
	@FXML private Label dominanceLabel;
	@FXML private ComboBox<JRankParameter> dominance;
	@FXML private Label dominanceForPairsLabel;
	@FXML private ComboBox<JRankParameter> dominanceForPairs;
	
	@FXML private Label satisfactionDegreesInGraphLabel;
	@FXML private ComboBox<JRankParameter> satisfactionDegreesInGraph;
	@FXML private Label fuzzyCalculationMethodLabel;
	@FXML private ComboBox<JRankParameter> fuzzyCalculationMethod;
	
	@FXML private Label negativeExamplesTreatmentLabel;
	@FXML private ComboBox<JRankParameter> negativeExamplesTreatment;
	@FXML private Label optimizeRuleConsistencyLabel;
	@FXML private ComboBox<JRankParameter> optimizeRuleConsistency;
	@FXML private Label ruleConditionsSelectionMethodLabel;
	@FXML private ComboBox<JRankParameter> ruleConditionsSelectionMethod;
	
	@FXML private CheckBox allowEmptyRules;
	@FXML private CheckBox useEdgeRegions;
	
	@FXML private CheckBox writeDominationInformation;
	@FXML private CheckBox writeRulesStatistics;
	@FXML private CheckBox writeLearningPositiveExamples;
	
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private Button setDefaultsButton;
	
	private JRankParametersService parametersService;
	private JRankParameter emptyParameter;
	private JRankProperties properties;
	private JRankProperties editableProperties;

	@FXML
	public void initialize() {
		parametersService = JRankParametersService.getInstance();
		emptyParameter = parametersService.getEmptyParameter();
	}
	
	public void initializeProperties(JRankProperties properties) {
		this.properties = properties;
		editableProperties = (JRankProperties) Cloner.deepClone(properties);
		fillComboBoxes();
		initializeFieldsValues();
	}
	
	private void fillComboBoxes() {
		typeOfFamilyCriteria.getItems().addAll(parametersService.getTypeOfFamilyOfCriteria());
		typeOfRules.getItems().addAll(parametersService.getTypeOfRules());
		consideredSetOfRules.getItems().addAll(parametersService.getConsideredSetOfRules());
		
		consistencyMeasure.getItems().addAll(parametersService.getConsistencyMeasure());
		
		rankingProcedure.getItems().addAll(parametersService.getRankingProcedure());
		dominance.getItems().addAll(parametersService.getDominance());
		dominanceForPairs.getItems().addAll(parametersService.getDominanceForPairsOfOrdinalValues());
		
		satisfactionDegreesInGraph.getItems().addAll(parametersService.getSatisfactionDegreesInPreferenceGraph());
		fuzzyCalculationMethod.getItems().addAll(parametersService.getFuzzySatisfactionDegreeCalculationMethod());
		
		negativeExamplesTreatment.getItems().addAll(parametersService.getNegativeExamplesTreatmentForVCDRSA());
		optimizeRuleConsistency.getItems().addAll(parametersService.getOptimizeRuleConsistencyInVCDomLEMWrt());
		ruleConditionsSelectionMethod.getItems().addAll(parametersService.getRuleConditionsSelectionMethodInVCDomLEM());
	}
	
	private void initializeFieldsValues() {
		learningDataFile.setText(editableProperties.getLearningDataFile());
		testDataFile.setText(editableProperties.getTestDataFile());
		pctFile.setText(editableProperties.getPctFile());
		pctApxFile.setText(editableProperties.getPctApxFile());
		pctRulesFile.setText(editableProperties.getPctRulesFile());
		graphFile.setText(editableProperties.getPreferenceGraphFile());
		rankingFile.setText(editableProperties.getRankingFile());
		
		referenceRanking.setText(editableProperties.getReferenceRanking());
		pairs.setText(editableProperties.getPairs());
		
		typeOfFamilyCriteria.getSelectionModel().select(editableProperties.getTypeOfFamilyOfCriteria());
		typeOfRules.getSelectionModel().select(editableProperties.getTypeOfRules());
		consideredSetOfRules.getSelectionModel().select(editableProperties.getConsideredSetOfRules());
		
		consistencyMeasure.getSelectionModel().select(editableProperties.getConsistencyMeasure());
		consistencyMeasureThreshold.setText(getStringOrNull(editableProperties.getConsistencyMeasureThreshold()));
		precision.setText(getStringOrNull(editableProperties.getPrecision()));
		
		rankingProcedure.getSelectionModel().select(editableProperties.getRankingProcedure());
		dominance.getSelectionModel().select(editableProperties.getDominance());
		dominanceForPairs.getSelectionModel().select(editableProperties.getDominanceForPairsOfOrdinalValues());
		
		satisfactionDegreesInGraph.getSelectionModel().select(editableProperties.getSatisfactionDegreesInPreferenceGraph());
		fuzzyCalculationMethod.getSelectionModel().select(editableProperties.getFuzzySatisfactionDegreeCalculationMethod());
		
		negativeExamplesTreatment.getSelectionModel().select(editableProperties.getNegativeExamplesTreatmentForVCDRSA());
		optimizeRuleConsistency.getSelectionModel().select(editableProperties.getOptimizeRuleConsistencyInVCDomLEMWrt());
		ruleConditionsSelectionMethod.getSelectionModel().select(editableProperties.getRuleConditionsSelectionMethodInVCDomLEM());
		
		allowEmptyRules.setSelected(getBoolean(editableProperties.getAllowEmptyRulesInVCDomLEM()));
		useEdgeRegions.setSelected(getBoolean(editableProperties.getUseEdgeRegionsInVCDomLEM()));
		
		writeDominationInformation.setSelected(getBoolean(editableProperties.getWriteDominationInformation()));
		writeRulesStatistics.setSelected(getBoolean(editableProperties.getWriteRulesStatistics()));
		writeLearningPositiveExamples.setSelected(getBoolean(editableProperties.getWriteLearningPositiveExamples()));
	}

	private String getStringOrNull(Object value) {
		if(isNull(value)) {
			return null;
		}
		return value.toString();
	}
	
	private boolean getBoolean(Boolean value) {
		if(isNull(value)) {
			return false;
		}
		return value;
	}
	
}
