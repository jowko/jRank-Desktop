package pl.jowko.jrank.desktop.feature.rules;

import pl.jowko.jrank.desktop.feature.internationalization.AbstractTranslator;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;

/**
 * Created by Piotr on 2018-05-22.
 * This class translates labels on rule statistics screen.
 */
class StatisticsTranslator extends AbstractTranslator {
	
	private StatisticsController controller;
	
	StatisticsTranslator(StatisticsController controller) {
		this.controller = controller;
	}
	
	@Override
	public void translateFields() {
		controller.ruleTypeLabel.setText(get(Labels.STAT_RULE_TYPE));
		controller.usageTypeLabel.setText(get(Labels.STAT_USAGE_TYPE));
		controller.characteristicClassLabel.setText(get(Labels.STAT_CHARACTERISTIC_CLASS));
		
		controller.supportLabel.setText(get(Labels.STAT_SUPPORT));
		controller.supportExamplesLabel.setText(get(Labels.STAT_SUPPORT_EXAMPLES));
		controller.strengthLabel.setText(get(Labels.STAT_STRENGTH));
		controller.confidenceLabel.setText(get(Labels.STAT_CONFIDENCE));
		controller.coverageFactorLabel.setText(get(Labels.STAT_COVERAGE_FACTOR));
		controller.coverageLabel.setText(get(Labels.STAT_COVERAGE));
		controller.coveredExamplesLabel.setText(get(Labels.STAT_COVERED_EXAMPLES));
		controller.negativeCoverageLabel.setText(get(Labels.STAT_NEGATIVE_COVERAGE));
		controller.negativeExamplesLabel.setText(get(Labels.STAT_NEGATIVE_EXAMPLES));
		controller.inconsistencyMeasureLabel.setText(get(Labels.STAT_INCONSISTENCY_MEASURE));
		controller.fMeasureLabel.setText(get(Labels.STAT_F_MEASURE));
		controller.aMeasureLabel.setText(get(Labels.STAT_A_MEASURE));
		controller.zMeasureLabel.setText(get(Labels.STAT_Z_MEASURE));
		controller.lMeasureLabel.setText(get(Labels.STAT_L_MEASURE));
	}
	
}
