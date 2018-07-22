package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.exception.ErrorMessageParser;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.learningtable.MemoryContainerAssembler;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankParameter;
import pl.jowko.rulerank.desktop.feature.properties.RuleRankProperties;
import pl.jowko.rulerank.desktop.feature.properties.information.InformationExtractor;
import pl.jowko.rulerank.desktop.feature.properties.information.PairOfIndicesWrapper;
import pl.poznan.put.cs.idss.jrs.core.ContainerFailureException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.ranking.PairOfIndices;
import pl.poznan.put.cs.idss.jrs.ranking.RankerParameters;

import java.util.List;

import static java.util.Objects.isNull;
import static pl.jowko.rulerank.desktop.utils.StringUtils.isNotNullOrEmpty;

/**
 * Created by Piotr on 2018-06-04
 * This class creates instance of RankerParameters class.
 * It will fill it with properties and data from isf tables.
 * @see RankerParameters
 * @see pl.poznan.put.cs.idss.jrs.ranking.Ranker
 */
class RankerParametersAssembler {
	
	private RuleRankProperties properties;
	private RankerParameters parameters;
	
	/**
	 * Create instance of this class and creates RankerParameters instance
	 * @param properties from with most settings are extracted
	 * @param learningTable containing learning data from isf table
	 * @param testTable containing test data from isf table
	 */
	RankerParametersAssembler(RuleRankProperties properties, LearningTable learningTable, LearningTable testTable) {
		this.properties = properties;
		parameters = new RankerParameters();
		assemble(learningTable, testTable);
	}
	
	/**
	 * Gets created RankerParameters
	 * @return RankerParameters with properties and isf table configured
	 */
	RankerParameters getParameters() {
		return parameters;
	}
	
	/**
	 * Fills RankerParameters with data.
	 * It will configure ruleRank algorithm settings, learning and test isf table, and pairs or ranking
	 * @param learningTable containing learning data
	 * @param testTable containing test data
	 */
	private void assemble(LearningTable learningTable, LearningTable testTable) {
		assembleExperimentSettings();
		parameters.learningInformationTable = getContainer(learningTable);
		parameters.testInformationTable = getContainer(testTable);
		
		if(isNotNullOrEmpty(properties.getReferenceRanking())) {
			extractRanking();
		} else if(isNotNullOrEmpty(properties.getPairs())) {
			extractPairs();
		}
	}
	
	/**
	 * Extract ranking from text format to jRS format.
	 * @see InformationExtractor
	 */
	private void extractRanking() {
		try {
			parameters.referenceRanking = InformationExtractor.extractRanking(properties.getReferenceRanking());
		} catch (RuntimeException e) {
			throwErrorOnTextParsing(e);
		}
	}
	
	/**
	 * Extract pairs from text format to jRS format.
	 * @see InformationExtractor
	 */
	private void extractPairs() {
		List<PairOfIndicesWrapper> list = null;
		try{
			list = InformationExtractor.extractPairs(properties.getPairs());
		} catch (RuntimeException e) {
			throwErrorOnTextParsing(e);
		}
		
		if(isNull(list))
			return;
		
		parameters.pairs = new PairOfIndices[list.size()];
		parameters.preferences = new Double[list.size()];
		for(int i=0; i<list.size(); i++) {
			parameters.pairs[i] = list.get(i).getPair();
			parameters.preferences[i] = list.get(i).getRelation();
		}
	}
	
	private void throwErrorOnTextParsing(RuntimeException e) {
		LanguageService labels = LanguageService.getInstance();
		String msg = labels.get(Labels.RUN_TEXT_PARSE_ERROR) + ErrorMessageParser.parseException(e);
		throw new RunnerException(msg);
	}
	
	/**
	 * Fills RankerParameters with algorithm settings
	 */
	private void assembleExperimentSettings() {
		parameters.typeOfFamilyOfCriteria = properties.getTypeOfFamilyOfCriteria().getValue();
		parameters.consistencyMeasure = properties.getConsistencyMeasure().getValue();
		parameters.consistencyMeasureThreshold = properties.getConsistencyMeasureThreshold();
		parameters.typeOfRules = properties.getTypeOfRules().getValue();
		parameters.consideredSetOfRules = properties.getConsideredSetOfRules().getValue();
		parameters.satisfactionDegreesInPreferenceGraph = properties.getSatisfactionDegreesInPreferenceGraph().getValue();
		parameters.fuzzySatisfactionDegreeCalculationMethod = properties.getFuzzySatisfactionDegreeCalculationMethod().getValue();
		parameters.rankingProcedure = properties.getRankingProcedure().getValue();
		parameters.dominance = properties.getDominance().getValue();
		parameters.dominanceForPairsOfOrdinalValues = properties.getDominanceForPairsOfOrdinalValues().getValue();
		parameters.negativeExamplesTreatmentForVCDRSA = properties.getNegativeExamplesTreatmentForVCDRSA().getValue();
		parameters.ruleConditionsSelectionMethodInVCDomLEM = properties.getRuleConditionsSelectionMethodInVCDomLEM().getValue();
		parameters.allowEmptyRulesInVCDomLEM = getBooleanValue(properties.getAllowEmptyRulesInVCDomLEM());
		parameters.useEdgeRegionsInVCDomLEM = getBooleanValue(properties.getUseEdgeRegionsInVCDomLEM());
		parameters.optimizeRuleConsistencyInVCDomLEMWrt = properties.getOptimizeRuleConsistencyInVCDomLEMWrt().getValue();
	}
	
	/**
	 * Converts LearningTable to MemoryContainer
	 * @param learningTable from with data will be extracted
	 */
	private MemoryContainer getContainer(LearningTable learningTable) {
		MemoryContainer dataContainer;
		try {
			dataContainer = MemoryContainerAssembler.assembleContainerFromTable(learningTable);
		} catch (ContainerFailureException e) {
			throw new RunnerException(e.getMessage());
		}
		return dataContainer;
	}
	
	private boolean getBooleanValue(RuleRankParameter parameter) {
		return parameter.getTextValue().equalsIgnoreCase("true");
	}
	
}
