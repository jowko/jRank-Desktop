package pl.jowko.jrank.desktop.feature.rules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RuleStatistics;
import pl.poznan.put.cs.idss.jrs.utilities.HumanReadableListOfNumbers;

import static pl.jowko.jrank.desktop.feature.rules.RuleConstantsTranslator.getRuleType;
import static pl.jowko.jrank.desktop.feature.rules.RuleConstantsTranslator.getUsageType;

/**
 * Created by Piotr on 2018-05-21.
 * This is controller for rule statistics.
 * It shows rule statistics in tab.
 */
public class StatisticsController {
	
	@FXML Label ruleTypeLabel;
	@FXML Label ruleType;
	@FXML Label usageTypeLabel;
	@FXML Label usageType;
	@FXML Label characteristicClassLabel;
	@FXML Label characteristicClass;
	
	
	@FXML Label supportLabel;
	@FXML Label supportExamplesLabel;
	@FXML Label strengthLabel;
	@FXML Label confidenceLabel;
	@FXML Label coverageFactorLabel;
	@FXML Label coverageLabel;
	@FXML Label coveredExamplesLabel;
	@FXML Label negativeCoverageLabel;
	@FXML Label negativeExamplesLabel;
	@FXML Label inconsistencyMeasureLabel;
	@FXML Label fMeasureLabel;
	@FXML Label aMeasureLabel;
	@FXML Label zMeasureLabel;
	@FXML Label lMeasureLabel;
	
	
	@FXML Label support;
	@FXML Label supportExamples;
	@FXML Label strength;
	@FXML Label confidence;
	@FXML Label coverageFactor;
	@FXML Label coverage;
	@FXML Label coveredExamples;
	@FXML Label negativeCoverage;
	@FXML Label negativeExamples;
	@FXML Label inconsistencyMeasure;
	@FXML Label fMeasure;
	@FXML Label aMeasure;
	@FXML Label zMeasure;
	@FXML Label lMeasure;
	
	@FXML
	private void initialize() {
	
	}
	
	/**
	 * This methods will display statistics from provided rule to statistics grid in tab.
	 * Calling this method more than once will replace values in UI with new from provided rule.
	 * @param rule from with statistics will be extracted
	 */
	void showRule(Rule rule) {
		reloadFieldsValues(rule);
	}
	
	/**
	 * Sets text values for labels in statistics grid.
	 * @param rule from with statistics will be extracted
	 */
	private void reloadFieldsValues(Rule rule) {
		ruleType.setText(getRuleType(rule.getType()));
		usageType.setText(getUsageType(rule.getCharacteristicDecisionClassUsageTip()));
		characteristicClass.setText(rule.getCharacteristicDecisionClass().toString());
		
		RuleStatistics stats = rule.getRuleStatistics();
		
		support.setText(String.valueOf(stats.getSupport()));
		String supportingExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfSupportingExamples());
		supportExamples.setText(supportingExamplesText);
		
		strength.setText(String.valueOf(stats.getStrength()));
		confidence.setText(String.valueOf(stats.getConfidence()));
		
		coverageFactor.setText(String.valueOf(stats.getCoverageFactor()));
		coverage.setText(String.valueOf(stats.getQuantityOfCoveredExamples()));
		String coveredExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfCoveredExamples());
		coveredExamples.setText(coveredExamplesText);
		
		negativeCoverage.setText(String.valueOf(stats.getQuantityOfNegativeCoveredExamples()));
		String negativeExamplesText = HumanReadableListOfNumbers.getIntArrayAsText(stats.getNumbersOfNegativeCoveredExamples());
		negativeExamples.setText(negativeExamplesText);
		
		inconsistencyMeasure.setText(String.valueOf(stats.getInconsistencyMeasureValue()));
		
		fMeasure.setText(String.valueOf(stats.getFConfirmationMeasureValue()));
		aMeasure.setText(String.valueOf(stats.getAConfirmationMeasureValue()));
		zMeasure.setText(String.valueOf(stats.getZConfirmationMeasureValue()));
		lMeasure.setText(String.valueOf(stats.getLConfirmationMeasureValue()));
	}
	
}
