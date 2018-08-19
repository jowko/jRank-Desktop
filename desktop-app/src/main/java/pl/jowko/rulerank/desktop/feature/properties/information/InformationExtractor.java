package pl.jowko.rulerank.desktop.feature.properties.information;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;
import pl.jowko.rulerank.desktop.utils.StringUtils;
import pl.poznan.put.cs.idss.jrs.ranking.PairOfIndices;
import pl.poznan.put.cs.idss.jrs.ranking.SimpleRanking;
import pl.poznan.put.cs.idss.jrs.ranking.SimpleRankingPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.jowko.rulerank.desktop.utils.BooleanUtils.not;

/**
 * This class allows to extract ranking and pair information from text format. <br>
 * Result is returned in jRS format. <br>
 *  <br>
 * Created by Piotr on 2018-06-04
 */
public class InformationExtractor {
	
	private InformationExtractor() {}
	
	/**
	 * Extract ranking from provided text. <br>
	 * Ranking should have such format: <br>
	 * 1, 2 4, 3 ... <br>
	 * Where numbers are indexes(starting from 1) representing items in MemoryContainer(isf table)
	 * @param text from properties form
	 * @return SimpleRanking object created from text
	 * @see SimpleRanking
	 */
	public static SimpleRanking extractRanking(String text) {
		if(StringUtils.isNullOrEmpty(text))
			return null;
		
		ArrayList<SimpleRankingPosition> rankingPositions = new ArrayList<>();
		
		String[] ranks = text.split(",");
		for(String rank : ranks) {
			List<String> positions = Arrays.stream(rank.split(" "))
					.filter(position -> not(position.isEmpty()))
					.collect(Collectors.toList());
			
			if(positions.isEmpty())
				continue;
			
			rankingPositions.add(getPosition(positions));
		}
		
		if(rankingPositions.isEmpty())
			return null;
		
		return new SimpleRanking(rankingPositions);
	}
	
	/**
	 * Extract pairs from provided text. <br>
	 * Pairs should have such format: <br>
	 * {1,2} S, {3,2} Sc, ... <br>
	 * Where numbers are indexes(starting from 1) representing items in MemoryContainer(isf table)
	 * @param text from properties form
	 * @return list of wrappers for PairOfIndices
	 * @see PairOfIndicesWrapper
	 */
	public static List<PairOfIndicesWrapper> extractPairs(String text) {
		if(StringUtils.isNullOrEmpty(text))
			return null;
		
		List<PairOfIndicesWrapper> pairs = new ArrayList<>();
		
		String[] values = text.split("[,{}]");
		values = Arrays.stream(values)
				.filter(value -> not(value.isEmpty() || value.trim().isEmpty()))
				.collect(Collectors.toList())
				.toArray(new String[]{});
		
		for(int i=0; i<values.length; i+=3) {
			PairOfIndices pair = new PairOfIndices(Integer.valueOf(values[i])-1, Integer.valueOf(values[i+1])-1);
			double relationValue = getRelation(values[i+2].trim());
			pairs.add(new PairOfIndicesWrapper(pair, relationValue));
		}
		
		return pairs;
	}
	
	/**
	 * This method extract all numbers from single ranking position
	 * @param positions extracted from properties form text
	 * @return SimpleRankingPosition representing single position in ranking
	 * @see SimpleRankingPosition
	 */
	private static SimpleRankingPosition getPosition(List<String> positions) {
		int[] numbers = new int[positions.size()];
		
		for(int i=0; i<positions.size(); i++) {
			numbers[i] = Integer.valueOf(positions.get(i))-1;
		}
		
		return new SimpleRankingPosition(numbers);
	}
	
	/**
	 * Gets double value for relation String
	 * @param relation in String format
	 * @return relation in double format
	 */
	private static double getRelation(String relation) {
		if("S".equalsIgnoreCase(relation)) {
			return 0.0;
		} else if ("Sc".equalsIgnoreCase(relation)) {
			return  -1.0;
		}
		throw new RuleRankRuntimeException("Relation string: [" + relation + "] is not recognized. Valid values are: S, Sc");
	}
	
}
