package pl.jowko.jrank.desktop.feature.rules;

import javafx.fxml.FXML;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.feature.customfx.SelectableLabel;
import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RuleStatistics;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.utilities.HumanReadableListOfNumbers;

import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.feature.rules.RuleConstantsTranslator.getRuleType;
import static pl.jowko.jrank.desktop.feature.rules.RuleConstantsTranslator.getUsageType;

/**
 * Created by Piotr on 2018-05-21.
 * This is controller for rule statistics.
 * It shows rule statistics in tab.
 */
public class StatisticsController {
	
	@FXML SelectableLabel ruleTypeLabel;
	@FXML SelectableLabel ruleType;
	@FXML SelectableLabel usageTypeLabel;
	@FXML SelectableLabel usageType;
	@FXML SelectableLabel characteristicClassLabel;
	@FXML SelectableLabel characteristicClass;
	
	
	@FXML SelectableLabel supportLabel;
	@FXML SelectableLabel supportExamplesLabel;
	@FXML SelectableLabel strengthLabel;
	@FXML SelectableLabel confidenceLabel;
	@FXML SelectableLabel coverageFactorLabel;
	@FXML SelectableLabel coverageLabel;
	@FXML SelectableLabel coveredExamplesLabel;
	@FXML SelectableLabel negativeCoverageLabel;
	@FXML SelectableLabel negativeExamplesLabel;
	@FXML SelectableLabel inconsistencyMeasureLabel;
	@FXML SelectableLabel fMeasureLabel;
	@FXML SelectableLabel aMeasureLabel;
	@FXML SelectableLabel zMeasureLabel;
	@FXML SelectableLabel lMeasureLabel;
	
	
	@FXML SelectableLabel support;
	@FXML SelectableLabel supportExamples;
	@FXML SelectableLabel strength;
	@FXML SelectableLabel confidence;
	@FXML SelectableLabel coverageFactor;
	@FXML SelectableLabel coverage;
	@FXML SelectableLabel coveredExamples;
	@FXML SelectableLabel negativeCoverage;
	@FXML SelectableLabel negativeExamples;
	@FXML SelectableLabel inconsistencyMeasure;
	@FXML SelectableLabel fMeasure;
	@FXML SelectableLabel aMeasure;
	@FXML SelectableLabel zMeasure;
	@FXML SelectableLabel lMeasure;
	
	private LanguageService labels;
	private Rule previousRule;
	
	@FXML
	private void initialize() {
		labels = LanguageService.getInstance();
		new StatisticsTranslator(this).translateFields();
	}
	
	/**
	 * This methods will display statistics from provided rule to statistics grid in tab.
	 * Calling this method more than once will replace values in UI with new from provided rule.
	 * @param rule from with statistics will be extracted
	 */
	void showRule(Rule rule) {
		if(nonNull(previousRule) && previousRule.equals(rule)) {
			return; // Rule already displayed
		}
		previousRule = rule;
		
		try {
			reloadFieldsValues(rule);
		} catch (RuntimeException e) {
			setStatisticsUnavailable();
		}
		
	}
	
	/**
	 * Sets text values for labels in statistics grid.
	 * Also remove error style from support field, because it is only used when statistics are not available.
	 * @param rule from with statistics will be extracted
	 */
	private void reloadFieldsValues(Rule rule) {
		support.getStyleClass().remove("error-label");
		
		ruleType.setText(getRuleType(rule.getType()));
		usageType.setText(getUsageType(rule.getCharacteristicDecisionClassUsageTip()));
		characteristicClass.setText(translateCharacteristicDecisionClass(rule.getCharacteristicDecisionClass().toString()));
		
		RuleStatistics stats = rule.getRuleStatistics();
		
		support.setText(String.valueOf(stats.getSupport()));
		String supportingExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfSupportingExamples());
		supportExamples.setText(supportingExamplesText);
		
		strength.setText(doubleToString(stats.getStrength()));
		confidence.setText(doubleToString(stats.getConfidence()));
		
		coverageFactor.setText(doubleToString(stats.getCoverageFactor()));
		coverage.setText(String.valueOf(stats.getQuantityOfCoveredExamples()));
		String coveredExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfCoveredExamples());
		coveredExamples.setText(coveredExamplesText);
		
		negativeCoverage.setText(String.valueOf(stats.getQuantityOfNegativeCoveredExamples()));
		String negativeExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfNegativeCoveredExamples());
		negativeExamples.setText(negativeExamplesText);
		
		inconsistencyMeasure.setText(doubleToString(stats.getInconsistencyMeasureValue()));
		
		fMeasure.setText(doubleToString(stats.getFConfirmationMeasureValue()));
		aMeasure.setText(doubleToString(stats.getAConfirmationMeasureValue()));
		zMeasure.setText(doubleToString(stats.getZConfirmationMeasureValue()));
		lMeasure.setText(doubleToString(stats.getLConfirmationMeasureValue()));
	}
	
	/**
	 * Retrurns String for provided characteristic decision
	 */
	private String translateCharacteristicDecisionClass(String characteristicDecision) {
		if("-1.0".equals(characteristicDecision))
			return labels.get(Labels.STAT_CHARACTERISTIC_SC);
		else if("0.0".equals(characteristicDecision))
			return labels.get(Labels.STAT_CHARACTERISTIC_S);
		return "";
	}
	
	/**
	 * Sets information on statistics tab, that statistics for provided rule are not available.
	 * Also clears all fields on GridPane.
	 */
	private void setStatisticsUnavailable() {
		support.setText(labels.get(Labels.STAT_UNAVAILABLE));
		support.getStyleClass().add("error-label");
		
		supportExamples.setText("");
		strength.setText("");
		confidence.setText("");
		coverageFactor.setText("");
		coverage.setText("");
		coveredExamples.setText("");
		negativeCoverage.setText("");
		negativeExamples.setText("");
		inconsistencyMeasure.setText("");
		fMeasure.setText("");
		aMeasure.setText("");
		zMeasure.setText("");
		lMeasure.setText("");
	}
	
	private String doubleToString(Double number) {
		if(number.isInfinite())
			return labels.get(Labels.STAT_DOUBLE_INFINITY);
		
		return String.valueOf(number);
	}
	
}
