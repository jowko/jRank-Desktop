package pl.jowko.rulerank.desktop.feature.properties;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import pl.jowko.rulerank.desktop.feature.tabs.RuleRankTab;

/**
 * This class is used to detect changes on properties form. <br>
 * It creates listeners for ComboBox and TextField fields. <br>
 * When value in some field changes, this class sets RuleRankTab to edit mode. <br>
 *  <br>
 * Created by Piotr on 2018-05-20.
 * @see PropertiesController
 * @see RuleRankTab
 */
class PropertiesChangeListener {
	
	private PropertiesController controller;
	private RuleRankTab propertiesTab;
	
	/**
	 * Create instance of this class
	 * @param controller from with editable fields are extracted
	 * @param propertiesTab with will be set to edit mode after change in properties field
	 */
	PropertiesChangeListener(PropertiesController controller, RuleRankTab propertiesTab) {
		this.controller = controller;
		this.propertiesTab = propertiesTab;
	}
	
	/**
	 * Set ups listeners for all editable fields in properties form
	 */
	void setUpListeners() {
		setUpListenersForTextFields();
		setUpListenersForComboBoxes();
	}
	
	private void setUpListenersForTextFields() {
		setUpTextFieldListener(controller.learningDataFile);
		setUpTextFieldListener(controller.pctFile);
		setUpTextFieldListener(controller.pctApxFile);
		setUpTextFieldListener(controller.pctRulesFile);
		setUpTextFieldListener(controller.graphFile);
		setUpTextFieldListener(controller.rankingFile);
		setUpTextFieldListener(controller.testDataFile);
		setUpTextFieldListener(controller.referenceRanking);
		setUpTextFieldListener(controller.pairs);
		
		setUpTextFieldListener(controller.consistencyMeasureThreshold);
		setUpTextFieldListener(controller.precision);
	}
	
	private void setUpListenersForComboBoxes() {
		setUpComboBoxListener(controller.typeOfFamilyCriteria);
		setUpComboBoxListener(controller.typeOfRules);
		setUpComboBoxListener(controller.consideredSetOfRules);
		setUpComboBoxListener(controller.consistencyMeasure);
		setUpComboBoxListener(controller.rankingProcedure);
		setUpComboBoxListener(controller.dominance);
		setUpComboBoxListener(controller.dominanceForPairs);
		setUpComboBoxListener(controller.satisfactionDegreesInGraph);
		setUpComboBoxListener(controller.fuzzyCalculationMethod);
		setUpComboBoxListener(controller.negativeExamplesTreatment);
		setUpComboBoxListener(controller.optimizeRuleConsistency);
		setUpComboBoxListener(controller.ruleConditionsSelectionMethod);
		setUpComboBoxListener(controller.allowEmptyRules);
		setUpComboBoxListener(controller.useEdgeRegions);
		setUpComboBoxListener(controller.writeDominationInformation);
		setUpComboBoxListener(controller.writeRulesStatistics);
		setUpComboBoxListener(controller.writeLearningPositiveExamples);
	}
	
	private void setUpTextFieldListener(TextInputControl field) {
		field.textProperty().addListener((o, old, n) ->
			propertiesTab.setTabEdited(true)
		);
	}
	
	private void setUpComboBoxListener(ComboBox<RuleRankParameter> comboBox) {
		comboBox.valueProperty().addListener((o, old, n) ->
				propertiesTab.setTabEdited(true)
		);
	}
	
}
