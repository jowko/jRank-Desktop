package pl.jowko.jrank.desktop.feature.properties;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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

	@FXML
	public void initialize() {
		parametersService = JRankParametersService.getInstance();
		emptyParameter = parametersService.getEmptyParameter();
	}
	
	public void initializeProperties(JRankProperties properties) {
		this.properties = properties;
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
		learningDataFile.setText(properties.getLearningDataFile());
		testDataFile.setText(properties.getTestDataFile());
		pctFile.setText(properties.getPctFile());
		pctApxFile.setText(properties.getPctApxFile());
		pctRulesFile.setText(properties.getPctRulesFile());
		graphFile.setText(properties.getPreferenceGraphFile());
		rankingFile.setText(properties.getRankingFile());
		
		referenceRanking.setText(properties.getReferenceRanking());
		pairs.setText(properties.getPairs());
		
		typeOfFamilyCriteria.getSelectionModel().select(properties.getTypeOfFamilyOfCriteria());
		typeOfRules.getSelectionModel().select(properties.getTypeOfRules());
		consideredSetOfRules.getSelectionModel().select(properties.getConsideredSetOfRules());
		
		consistencyMeasure.getSelectionModel().select(properties.getConsistencyMeasure());
		consistencyMeasureThreshold.setText(getStringOrNull(properties.getConsistencyMeasureThreshold()));
		precision.setText(getStringOrNull(properties.getPrecision()));
		
		rankingProcedure.getSelectionModel().select(properties.getRankingProcedure());
		dominance.getSelectionModel().select(properties.getDominance());
		dominanceForPairs.getSelectionModel().select(properties.getDominanceForPairsOfOrdinalValues());
		
		satisfactionDegreesInGraph.getSelectionModel().select(properties.getSatisfactionDegreesInPreferenceGraph());
		fuzzyCalculationMethod.getSelectionModel().select(properties.getFuzzySatisfactionDegreeCalculationMethod());
		
		negativeExamplesTreatment.getSelectionModel().select(properties.getNegativeExamplesTreatmentForVCDRSA());
		optimizeRuleConsistency.getSelectionModel().select(properties.getOptimizeRuleConsistencyInVCDomLEMWrt());
		ruleConditionsSelectionMethod.getSelectionModel().select(properties.getRuleConditionsSelectionMethodInVCDomLEM());
		
		allowEmptyRules.setSelected(getBoolean(properties.getAllowEmptyRulesInVCDomLEM()));
		useEdgeRegions.setSelected(getBoolean(properties.getUseEdgeRegionsInVCDomLEM()));
		
		writeDominationInformation.setSelected(getBoolean(properties.getWriteDominationInformation()));
		writeRulesStatistics.setSelected(getBoolean(properties.getWriteRulesStatistics()));
		writeLearningPositiveExamples.setSelected(getBoolean(properties.getWriteLearningPositiveExamples()));
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
