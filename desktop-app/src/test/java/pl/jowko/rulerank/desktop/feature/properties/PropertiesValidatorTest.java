package pl.jowko.rulerank.desktop.feature.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.jowko.rulerank.desktop.MasterTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Piotr on 2018-05-06.
 */
class PropertiesValidatorTest extends MasterTest {
	
	private JRankProperties properties;
	
	@BeforeEach
	void setUpEach() {
		properties = new JRankProperties();
		properties.setLearningDataFile("dataFile");
	}
	
	@Test
	void shouldBeValid() {
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void shouldNotBeValidBecauseOfNullLearningDataFile() {
		PropertiesValidator validator = new PropertiesValidator(new JRankProperties());
		assertFalse(validator.isValid());
	}
	
	@Test
	void shouldBeValidConsistencyMeasureThreshold() {
		properties.setConsistencyMeasureThreshold(0.5d);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void shouldNotBeValidConsistencyMeasureThreshold() {
		properties.setConsistencyMeasureThreshold(-0.5d);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	// "Incorrect consistency measure threshold. Value has to be not greater than 1.0."
	@ParameterizedTest
	@ValueSource(strings = {"epsilon", "epsilon*", "rough-membership"})
	void shouldNotBeValidConsistencyMeasure(String consistencyMeasure) {
		properties.setConsistencyMeasure(createParameter(consistencyMeasure));
		properties.setConsistencyMeasureThreshold(1.5d);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"epsilon", "epsilon*", "rough-membership"})
	void shouldBeValidConsistencyMeasure(String consistencyMeasure) {
		properties.setConsistencyMeasure(createParameter(consistencyMeasure));
		properties.setConsistencyMeasureThreshold(1.0d);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@ParameterizedTest
	@CsvSource({"rough-membership", "epsilon", "epsilon*", "epsilon'"})
	void shouldBeValidTypeOfRules(String consistencyMeasure) {
		properties.setConsistencyMeasure(createParameter(consistencyMeasure));
		properties.setConsistencyMeasureThreshold(0.5);
		properties.setTypeOfRules(createParameter("certain"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@ParameterizedTest
	@CsvSource({"rough-membership", "epsilon", "epsilon*", "epsilon'"})
	void shouldNotBeValidTypeOfRules(String consistencyMeasure) {
		properties.setConsistencyMeasure(createParameter(consistencyMeasure));
		properties.setConsistencyMeasureThreshold(0.5d);
		properties.setTypeOfRules(createParameter("possible"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@CsvSource({
			"crisp,max-credibility,minimal",
			"crisp,max-credibility-x-coverage-factor,minimal",
			"crisp,max-credibility,exhaustive",
			"crisp,max-credibility-x-coverage-factor,exhaustive",
			"fuzzy,max-credibility,minimal",
			"fuzzy,max-credibility-x-coverage-factor,minimal",
			"fuzzy,max-credibility,exhaustive"
	})
	void shouldBeValidFuzzySatisfactionDegreeCalculationMethod(String satisfactionDegree, String fuzzyMethod, String consideredSetOfRules) {
		properties.setSatisfactionDegreesInPreferenceGraph(createParameter(satisfactionDegree));
		properties.setFuzzySatisfactionDegreeCalculationMethod(createParameter(fuzzyMethod));
		properties.setConsideredSetOfRules(createParameter(consideredSetOfRules));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void shouldNotBeValidFuzzySatisfactionDegreeCalculationMethod() {
		properties.setSatisfactionDegreesInPreferenceGraph(createParameter("fuzzy"));
		properties.setFuzzySatisfactionDegreeCalculationMethod(createParameter("max-credibility-x-coverage-factor"));
		properties.setConsideredSetOfRules(createParameter("exhaustive"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@CsvSource({
			"rough-membership,minimal",
			"epsilon,minimal",
			"epsilon*,minimal",
			"epsilon',minimal",
			"epsilon,exhaustive",
			"epsilon*,exhaustive",
			"epsilon',exhaustive"
	})
	void shouldBeValidConsideredSetOfRules(String consistencyMeasure, String consideredSetOfRules) {
		properties.setConsistencyMeasure(createParameter(consistencyMeasure));
		properties.setConsistencyMeasureThreshold(0.5d);
		properties.setConsideredSetOfRules(createParameter(consideredSetOfRules));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void shouldNotBeValidConsideredSetOfRules() {
		properties.setConsistencyMeasure(createParameter("rough-membership"));
		properties.setConsistencyMeasureThreshold(0.5d);
		properties.setConsideredSetOfRules(createParameter("exhaustive"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	
	@Test
	void shouldNotBeValidFuzzyDegreesInExhaustiveSetAndRoughMembership() {
		properties.setConsistencyMeasure(createParameter("rough-membership"));
		properties.setConsistencyMeasureThreshold(1.0d);
		properties.setSatisfactionDegreesInPreferenceGraph(createParameter("fuzzy"));
		properties.setTypeOfRules(createParameter("possible"));
		properties.setConsideredSetOfRules(createParameter("exhaustive"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@CsvSource({
			"minimal,possible,approximation",
			"minimal,certain,set",
			"minimal,certain,approximation",
			"exhaustive,possible,set",
			"exhaustive,possible,approximation",
			"exhaustive,certain,set",
			"exhaustive,certain,approximation",
	})
	void shouldBeValidOptimizeSetPossibleRules(String consideredSetOfRules, String typeOfRules, String optimizeRuleConsistency) {
		properties.setConsideredSetOfRules(createParameter(consideredSetOfRules));
		properties.setTypeOfRules(createParameter(typeOfRules));
		properties.setOptimizeRuleConsistencyInVCDomLEMWrt(createParameter(optimizeRuleConsistency));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void shouldNotBeValidOptimizeSetPossibleRules() {
		properties.setConsideredSetOfRules(createParameter("minimal"));
		properties.setTypeOfRules(createParameter("possible"));
		properties.setOptimizeRuleConsistencyInVCDomLEMWrt(createParameter("set"));
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@Test
	void fileExtensionsShouldBeValid() {
		properties.setLearningDataFile("data.isf");
		properties.setTestDataFile("test.isf");
		properties.setPctFile("pct.isf");
		properties.setPctApxFile("pct.apx");
		properties.setPctRulesFile("pct.rules");
		properties.setPreferenceGraphFile("test.graph");
		properties.setRankingFile("test.ranking");
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void fileExtensionsShouldBeValidWhenEmptyFilePath() {
		properties.setLearningDataFile("test.isf");
		properties.setTestDataFile("");
		properties.setPctFile("");
		properties.setPctApxFile("");
		properties.setPctRulesFile("");
		properties.setPreferenceGraphFile("");
		properties.setRankingFile("");
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void fileExtensionsShouldBeValidWhenNullFilePath() {
		properties.setLearningDataFile("test.isf");
		properties.setTestDataFile(null);
		properties.setPctFile(null);
		properties.setPctApxFile(null);
		properties.setPctRulesFile(null);
		properties.setPreferenceGraphFile(null);
		properties.setRankingFile(null);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@Test
	void fileExtensionsShouldBeValidWhenNoExtensionsWereProvided() {
		properties.setLearningDataFile("data");
		properties.setTestDataFile("test");
		properties.setPctFile("pct");
		properties.setPctApxFile("pct");
		properties.setPctRulesFile("pct");
		properties.setPreferenceGraphFile("test");
		properties.setRankingFile("test");
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertTrue(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.isf.a", "test.isf."})
	void learningFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.isf.a", "test.isf."})
	void testFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setTestDataFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.isf.a", "test.isf."})
	void pctFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setPctFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.apx.a", "test.apx."})
	void apxFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setPctApxFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.rules.a", "test.rules."})
	void rulesFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setPctRulesFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.graph.a", "test.graph."})
	void graphFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setPreferenceGraphFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test.a", "test.ranking.a", "test.ranking."})
	void rankingFileExtensionShouldNotBeValid(String value) {
		properties.setLearningDataFile("test.isf");
		properties.setRankingFile(value);
		PropertiesValidator validator = new PropertiesValidator(properties);
		assertFalse(validator.isValid());
	}
	
	private JRankParameter createParameter(String text) {
		return new JRankParameter("", text, 0);
	}
	
}
