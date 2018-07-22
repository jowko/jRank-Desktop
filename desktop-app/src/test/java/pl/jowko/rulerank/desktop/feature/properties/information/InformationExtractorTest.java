package pl.jowko.rulerank.desktop.feature.properties.information;

import org.junit.jupiter.api.Test;
import pl.jowko.rulerank.desktop.exception.JRankRuntimeException;
import pl.poznan.put.cs.idss.jrs.ranking.PairOfIndices;
import pl.poznan.put.cs.idss.jrs.ranking.SimpleRanking;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.jowko.rulerank.desktop.feature.properties.information.InformationExtractor.extractPairs;
import static pl.jowko.rulerank.desktop.feature.properties.information.InformationExtractor.extractRanking;

/**
 * Created by Piotr on 2018-06-05
 */
class InformationExtractorTest {
	
	
	@Test
	void shouldExtractRanking() {
		String text = "1, 2, 3 4";
		SimpleRanking ranking = extractRanking(text);
		
		assertNotNull(ranking);
		assertEquals(text, ranking.toSingleLineString());
	}
	
	@Test
	void shouldExtractRankingWithoutSpaces() {
		String text = "1 2,3,4";
		SimpleRanking ranking = extractRanking(text);
		
		assertNotNull(ranking);
		assertEquals("1 2, 3, 4", ranking.toSingleLineString());
	}
	
	@Test
	void shouldExtractPairs() {
		String text = "{1,2} S, {3,4} Sc";
		List<PairOfIndicesWrapper> pairs = extractPairs(text);
		
		assertNotNull(pairs);
		assertEquals(2, pairs.size());
		assertEquals(0.0d, pairs.get(0).getRelation());
		assertEquals(-1.0d, pairs.get(1).getRelation());
		assertEquals(new PairOfIndices(0,1), pairs.get(0).getPair());
		assertEquals(new PairOfIndices(2,3), pairs.get(1).getPair());
	}
	
	@Test
	void shouldNotExtractPairsWhenWrongRelation() {
		String text = "{1,2} S, {3,4} scd";
		assertThrows(JRankRuntimeException.class, () -> extractPairs(text));
	}
	
}
