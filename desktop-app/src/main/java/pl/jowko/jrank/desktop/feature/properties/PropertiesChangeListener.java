package pl.jowko.jrank.desktop.feature.properties;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import pl.jowko.jrank.desktop.feature.tabs.JRankTab;

/**
 * Created by Piotr on 2018-05-20.
 * This class is used to detect changes on properties form.
 * It creates listeners for ComboBox and TextField fields.
 * When value in some field changes, this class sets JRankTab to edit mode.
 * @see PropertiesController
 * @see JRankTab
 */
class PropertiesChangeListener {
	
	private PropertiesController controller;
	private JRankTab propertiesTab;
	
	private ChangeListener<? super String> textFieldListener;
	private ChangeListener<? super JRankParameter> comboBoxListener;
	
	/**
	 * Create instance of this class
	 * @param controller from with editable fields are extracted
	 * @param propertiesTab with will be set to edit mode after change in properties field
	 */
	PropertiesChangeListener(PropertiesController controller, JRankTab propertiesTab) {
		this.controller = controller;
		this.propertiesTab = propertiesTab;
	}
	
	/**
	 * Set ups listeners for all editable fields in properties form
	 */
	void setUpListeners() {
		initializeListeners();
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
	
	private void initializeListeners() {
		textFieldListener = (observable, oldValue, newValue) -> propertiesChanged();
		comboBoxListener = (observable, oldValue, newValue) -> propertiesChanged();
	}
	
	private void setUpTextFieldListener(TextInputControl field) {
		field.textProperty().addListener(textFieldListener);
	}
	
	private void setUpComboBoxListener(ComboBox<JRankParameter> comboBox) {
		comboBox.valueProperty().addListener(comboBoxListener);
	}
	
	private void propertiesChanged() {
		propertiesTab.setTabEdited(true);
	}
	
}
