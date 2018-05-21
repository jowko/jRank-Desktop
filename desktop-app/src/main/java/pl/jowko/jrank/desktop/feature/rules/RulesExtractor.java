package pl.jowko.jrank.desktop.feature.rules;

import pl.poznan.put.cs.idss.jrs.rules.Rule;
import pl.poznan.put.cs.idss.jrs.rules.RulesContainer;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by Piotr on 2018-05-21.
 * This class extracts all rules from RulesContainer into one List.
 */
class RulesExtractor {
	
	private List<Rule> rules;
	private RulesContainer container;
	
	/**
	 * Create instance of RulesExtractor class.
	 * @param container with rules to extract
	 */
	RulesExtractor(RulesContainer container) {
		this.container =container;
		rules = new ArrayList<>();
	}
	
	/**
	 * Extract all rules types from RulesContainer.
	 * Nine rules lists are loaded into one. (Certain, Possible, Approximate) rules with (At least, At most, Equal) characteristics.
	 * @return union of all rules from RulesContainer
	 */
	List<Rule> extract() {
		initializeCertainRules();
		initializePossibleRules();
		initializeApproximateRules();
		return rules;
	}
	
	private void initializeCertainRules() {
		List<Rule> certainAtLeastRules = container.getRules(Rule.CERTAIN, Rule.AT_LEAST);
		if(nonNull(certainAtLeastRules))
			rules.addAll(certainAtLeastRules);
		
		List<Rule> certainAtMostRules = container.getRules(Rule.CERTAIN, Rule.AT_MOST);
		if(nonNull(certainAtMostRules))
			rules.addAll(certainAtMostRules);
		
		List<Rule> certainEqualRules = container.getRules(Rule.CERTAIN, Rule.EQUAL);
		if(nonNull(certainEqualRules))
			rules.addAll(certainEqualRules);
	}
	
	private void initializePossibleRules() {
		List<Rule> possibleAtLeastRules = container.getRules(Rule.POSSIBLE, Rule.AT_LEAST);
		if(nonNull(possibleAtLeastRules))
			rules.addAll(possibleAtLeastRules);
		
		List<Rule> possibleAtMostRules = container.getRules(Rule.POSSIBLE, Rule.AT_MOST);
		if(nonNull(possibleAtMostRules))
			rules.addAll(possibleAtMostRules);
		
		List<Rule> possibleEqualRules = container.getRules(Rule.POSSIBLE, Rule.EQUAL);
		if(nonNull(possibleEqualRules))
			rules.addAll(possibleEqualRules);
	}
	
	private void initializeApproximateRules() {
		List<Rule> approximateAtLeastRules = container.getRules(Rule.APPROXIMATE, Rule.AT_LEAST);
		if(nonNull(approximateAtLeastRules))
			rules.addAll(approximateAtLeastRules);
		
		List<Rule> approximateAtMostRules = container.getRules(Rule.APPROXIMATE, Rule.AT_MOST);
		if(nonNull(approximateAtMostRules))
			rules.addAll(approximateAtMostRules);
		
		List<Rule> approximateEqualRules = container.getRules(Rule.APPROXIMATE, Rule.EQUAL);
		if(nonNull(approximateEqualRules))
			rules.addAll(approximateEqualRules);
	}
	
}
