package pl.jowko.rulerank.desktop.feature.properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.MasterTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.poznan.put.cs.idss.jrs.Settings.ANY_FAMILY_OF_CRITERIA;
import static pl.poznan.put.cs.idss.jrs.Settings.CONSISTENT_FAMILY_OF_CRITERIA;
import static pl.poznan.put.cs.idss.jrs.approximations.MonotonicUnion.*;
import static pl.poznan.put.cs.idss.jrs.ranking.PreferenceGraphGenerator.MAX_CREDIBILITY;
import static pl.poznan.put.cs.idss.jrs.ranking.PreferenceGraphGenerator.MAX_PRODUCT_OF_CREDIBILITY_AND_COVERAGE_FACTOR;
import static pl.poznan.put.cs.idss.jrs.ranking.RankerParameters.*;
import static pl.poznan.put.cs.idss.jrs.rules.Rule.CERTAIN;
import static pl.poznan.put.cs.idss.jrs.rules.Rule.POSSIBLE;
import static pl.poznan.put.cs.idss.jrs.rules.RuleConstants.*;
import static pl.poznan.put.cs.idss.jrs.rules.VCDomLem.CHOOSE_CONDITIONS_FROM_ONE_OBJECT;
import static pl.poznan.put.cs.idss.jrs.rules.VCDomLem.MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS;
import static pl.poznan.put.cs.idss.jrs.types.PairField.CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD;
import static pl.poznan.put.cs.idss.jrs.types.PairField.STRICT_ORDINAL_DOMINANCE_CHECK_METHOD;

/**
 * Created by Piotr on 2018-05-02.
 */
class RuleRankParametersServiceTest extends MasterTest {
	
	private RuleRankParametersService service;
	
	@BeforeEach
	void setUpEach() {
		service = RuleRankParametersService.getInstance();
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
		List<RuleRankParameter> params = service.getTypeOfFamilyOfCriteria();
		
		assertEquals(ANY_FAMILY_OF_CRITERIA, service.findByTextValue(params, "any").getValue());
		assertEquals(CONSISTENT_FAMILY_OF_CRITERIA, service.findByTextValue(params, "consistent").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindConsistencyMeasure() {
		List<RuleRankParameter> params = service.getConsistencyMeasure();
		
		assertEquals(EPSILON, service.findByTextValue(params, "epsilon").getValue());
		assertEquals(EPSILON_STAR, service.findByTextValue(params, "epsilon*").getValue());
		assertEquals(EPSILON_PRIM, service.findByTextValue(params, "epsilon'").getValue());
		assertEquals(ROUGH_MEMBERSHIP, service.findByTextValue(params, "rough-membership").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindTypeOfRules() {
		List<RuleRankParameter> params = service.getTypeOfRules();
		
		assertEquals(CERTAIN, service.findByTextValue(params, "certain").getValue());
		assertEquals(POSSIBLE, service.findByTextValue(params, "possible").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindConsideredSetOfRules() {
		List<RuleRankParameter> params = service.getConsideredSetOfRules();
		
		assertEquals(MINIMAL_SET_OF_RULES, service.findByTextValue(params, "minimal").getValue());
		assertEquals(EXHAUSTIVE_SET_OF_RULES, service.findByTextValue(params, "exhaustive").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindSatisfactionDegreesInPreferenceGraph() {
		List<RuleRankParameter> params = service.getSatisfactionDegreesInPreferenceGraph();
		
		assertEquals(FUZZY, service.findByTextValue(params, "fuzzy").getValue());
		assertEquals(CRISP, service.findByTextValue(params, "crisp").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindFuzzySatisfactionDegreeCalculationMethod() {
		List<RuleRankParameter> params = service.getFuzzySatisfactionDegreeCalculationMethod();
		
		assertEquals(MAX_CREDIBILITY, service.findByTextValue(params, "max-credibility").getValue());
		assertEquals(MAX_PRODUCT_OF_CREDIBILITY_AND_COVERAGE_FACTOR, service.findByTextValue(params, "max-credibility-x-coverage-factor").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindRankingProcedure() {
		List<RuleRankParameter> params = service.getRankingProcedure();
		
		assertEquals(NFS, service.findByTextValue(params, "nfs").getValue());
		assertEquals(REPEATED_NFS, service.findByTextValue(params, "rnfs").getValue());
		assertEquals(NFS_CLOSURE, service.findByTextValue(params, "nfs-*").getValue());
		assertEquals(REPEATED_NFS_CLOSURE, service.findByTextValue(params, "rnfs-*").getValue());
		assertEquals(NFS_P_CLOSURE, service.findByTextValue(params, "nfs-p-*").getValue());
		assertEquals(REPEATED_NFS_P_CLOSURE, service.findByTextValue(params, "rnfs-p-*").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindDominance() {
		List<RuleRankParameter> params = service.getDominance();
		
		assertEquals(PARETO, service.findByTextValue(params, "pareto").getValue());
		assertEquals(LORENZ, service.findByTextValue(params, "lorenz").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindDominanceForPairsOfOrdinalValues() {
		List<RuleRankParameter> params = service.getDominanceForPairsOfOrdinalValues();
		
		assertEquals(STRICT_ORDINAL_DOMINANCE_CHECK_METHOD, service.findByTextValue(params, "strict").getValue());
		assertEquals(CLASSIC_ORDINAL_DOMINANCE_CHECK_METHOD, service.findByTextValue(params, "classic").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindNegativeExamplesTreatmentForVCDRSA() {
		List<RuleRankParameter> params = service.getNegativeExamplesTreatmentForVCDRSA();
		
		assertEquals(COVER_ONLY_INCONSISTENT_NEGATIVE_EXAMPLES, service.findByTextValue(params, "only-inconsistent").getValue());
		assertEquals(COVER_ONLY_INCONSISTENT_AND_BOUNDARY_NEGATIVE_EXAMPLES, service.findByTextValue(params, "only-inconsistent-and-boundary").getValue());
		assertEquals(COVER_ANY_NEGATIVE_EXAMPLES, service.findByTextValue(params, "any").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindRuleConditionsSelectionMethodInVCDomLEM() {
		List<RuleRankParameter> params = service.getRuleConditionsSelectionMethodInVCDomLEM();
		
		assertEquals(CHOOSE_CONDITIONS_FROM_ONE_OBJECT, service.findByTextValue(params, "base").getValue());
		assertEquals(MIX_CONDITIONS_FROM_DIFFERENT_OBJECTS, service.findByTextValue(params, "mix").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldFindOptimizeRuleConsistencyInVCDomLEMWrt() {
		List<RuleRankParameter> params = service.getOptimizeRuleConsistencyInVCDomLEMWrt();
		
		assertEquals(APPROXIMATION, service.findByTextValue(params, "approximation").getValue());
		assertEquals(SET, service.findByTextValue(params, "set").getValue());
		assertEquals(Integer.MIN_VALUE, service.findByTextValue(params, "").getValue());
	}
	
	@Test
	void shouldReturnNewDefaultParameter() {
		RuleRankParameter parameter = service.getDefaultParameter();
		parameter.setLabel("new label");
		
		assertNotEquals("new label", service.getDefaultParameter().getLabel());
	}
	
}
