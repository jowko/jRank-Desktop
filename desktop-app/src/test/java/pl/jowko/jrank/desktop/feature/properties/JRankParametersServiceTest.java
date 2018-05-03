package pl.jowko.jrank.desktop.feature.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.jrank.desktop.MasterTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by Piotr on 2018-05-02.
 */
class JRankParametersServiceTest extends MasterTest {
	
	private JRankParametersService service;
	
	@BeforeEach
	void setUpEach() {
		service = JRankParametersService.getInstance();
	}
	
	@Test
	void shouldNotFindValue() {
		assertNull(service.findByTextValue(service.getDominance(), "Some code"));
	}
	
	@Test
	void shouldNotFindNullValue() {
		assertNull(service.findByTextValue(service.getDominance(), null));
	}
	
	@Test
	void shouldFindTypeOFFamilyCriteria() {
		List<JRankParameter> params = service.getTypeOfFamilyOfCriteria();
		
		assertEquals(0, service.findByTextValue(params, "any").getValue());
		assertEquals(1, service.findByTextValue(params, "consistent").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindConsistencyMeasure() {
		List<JRankParameter> params = service.getConsistencyMeasure();
		
		assertEquals(0, service.findByTextValue(params, "epsilon").getValue());
		assertEquals(1, service.findByTextValue(params, "epsilon*").getValue());
		assertEquals(2, service.findByTextValue(params, "epsilon'").getValue());
		assertEquals(-1, service.findByTextValue(params, "rough-membership").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindTypeOfRules() {
		List<JRankParameter> params = service.getTypeOfRules();
		
		assertEquals(0, service.findByTextValue(params, "certain").getValue());
		assertEquals(1, service.findByTextValue(params, "possible").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindConsideredSetOfRules() {
		List<JRankParameter> params = service.getConsideredSetOfRules();
		
		assertEquals(0, service.findByTextValue(params, "minimal").getValue());
		assertEquals(1, service.findByTextValue(params, "exhaustive").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindSatisfactionDegreesInPreferenceGraph() {
		List<JRankParameter> params = service.getSatisfactionDegreesInPreferenceGraph();
		
		assertEquals(0, service.findByTextValue(params, "fuzzy").getValue());
		assertEquals(1, service.findByTextValue(params, "crisp").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindFuzzySatisfactionDegreeCalculationMethod() {
		List<JRankParameter> params = service.getFuzzySatisfactionDegreeCalculationMethod();
		
		assertEquals(0, service.findByTextValue(params, "max-credibility").getValue());
		assertEquals(1, service.findByTextValue(params, "max-credibility-x-coverage-factor").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindRankingProcedure() {
		List<JRankParameter> params = service.getRankingProcedure();
		
		assertEquals(0, service.findByTextValue(params, "nfs").getValue());
		assertEquals(1, service.findByTextValue(params, "rnfs").getValue());
		assertEquals(2, service.findByTextValue(params, "nfs-*").getValue());
		assertEquals(3, service.findByTextValue(params, "rnfs-*").getValue());
		assertEquals(4, service.findByTextValue(params, "nfs-p-*").getValue());
		assertEquals(5, service.findByTextValue(params, "rnfs-p-*").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindDominance() {
		List<JRankParameter> params = service.getDominance();
		
		assertEquals(0, service.findByTextValue(params, "pareto").getValue());
		assertEquals(1, service.findByTextValue(params, "lorenz").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindDominanceForPairsOfOrdinalValues() {
		List<JRankParameter> params = service.getDominanceForPairsOfOrdinalValues();
		
		assertEquals(4, service.findByTextValue(params, "strict").getValue());
		assertEquals(5, service.findByTextValue(params, "classic").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindNegativeExamplesTreatmentForVCDRSA() {
		List<JRankParameter> params = service.getNegativeExamplesTreatmentForVCDRSA();
		
		assertEquals(1, service.findByTextValue(params, "only-inconsistent").getValue());
		assertEquals(2, service.findByTextValue(params, "only-inconsistent-and-boundary").getValue());
		assertEquals(3, service.findByTextValue(params, "any").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindRuleConditionsSelectionMethodInVCDomLEM() {
		List<JRankParameter> params = service.getRuleConditionsSelectionMethodInVCDomLEM();
		
		assertEquals(0, service.findByTextValue(params, "base").getValue());
		assertEquals(1, service.findByTextValue(params, "mix").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindOptimizeRuleConsistencyInVCDomLEMWrt() {
		List<JRankParameter> params = service.getOptimizeRuleConsistencyInVCDomLEMWrt();
		
		assertEquals(0, service.findByTextValue(params, "approximation").getValue());
		assertEquals(1, service.findByTextValue(params, "set").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
}
