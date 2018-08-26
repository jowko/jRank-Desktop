package pl.jowko.rulerank.desktop.feature.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class test all cases for coloring graph(16 cases). <br>
 * Each test method has attached JavaDoc explaining test case. <br>
 * To avoid adding literals every where. {@literal -> } sign was replaced with: - <br>
 * <br>
 * Created by Piotr on 2018-08-25
 */
@Disabled
class GraphColorsTest extends AbstractGraphTest {
	
	//TODO Finish tests
	private static GraphDto graph;
	
	@BeforeAll
	static void loadFiles() throws IOException {
		String allColorsGraphContent = getFileContent("/graph/allColorsGraph.graph");
		graph = getGraph(allColorsGraphContent);
	}
	
	@Test
	void extractGraphData() {
		assertThatGraphIsNotEmpty(graph);
	}
	
	@Test
	void case1ShouldHaveNoEdges() {
		assertEquals(0, getEdgeBySourceId("1").size());
		assertEquals(0, getEdgeBySourceId("2").size());
	}
	
	/**
	 * 4 - 3 [color="red"]; <br>
	 * Should Give: <br>
	 * 4 - 3 Red
	 */
	@Test
	void case2() {
	
	}
	
	/**
	 * 6 - 5 [color="green"]; <br>
	 * Should Give: <br>
	 * 6 - 5 Green
	 */
	@Test
	void case3() {
	
	}
	
	/**
	 * 8 - 7 [color="green"]; <br>
	 * 8 - 7 [color="red"]; <br>
	 * Should Give: <br>
	 * 8 - 7 LightGray
	 */
	@Test
	void case4() {
	
	}
	
	/**
	 * 9 - 10 [color="red"]; <br>
	 * Should Give: <br>
	 * 9 - 10 Red
	 */
	@Test
	void case5() {
	
	}
	
	/**
	 * 11 - 12 [color="red"]; <br>
	 * 12 - 11 [color="red"]; <br>
	 * Should Give: <br>
	 * 11 - 12 Red <br>
	 * 12 - 11 Red
	 */
	@Test
	void case6() {
	
	}
	
	/**
	 * 13 - 14 [color="red"]; <br>
	 * 14 - 13 [color="green"]; <br>
	 * Should Give: <br>
	 * 13 - 14 Red <br>
	 * 14 - 13 Green
	 */
	@Test
	void case7() {
	
	}
	
	/**
	 * 15 - 16 [color="red"]; <br>
	 * 16 - 15 [color="red"]; <br>
	 * 16 - 15 [color="green"]; <br>
	 * Should Give: <br>
	 * 17 - 18 Red <br>
	 * 18 - 17 LightGray
	 */
	@Test
	void case8() {
	
	}
	
	/**
	 * 17 - 18 [color="green"]; <br>
	 * Should Give: <br>
	 * 17 - 18 Green
	 */
	@Test
	void case9() {
	
	}
	
	/**
	 * 19 - 20 [color="green"]; <br>
	 * 20 - 19 [color="red"]; <br>
	 * Should Give: <br>
	 * 19 - 20 Green <br>
	 * 20 - 19 Red
	 */
	@Test
	void case10() {
	
	}
	
	/**
	 * 21 - 22 [color="green"]; <br>
	 * 22 - 21 [color="green"]; <br>
	 * Should Give: <br>
	 * 21 - 22 Green <br>
	 * 22 - 21 Green
	 */
	@Test
	void case11() {
	
	}
	
	/**
	 * 23 - 24 [color="green"]; <br>
	 * 24 - 23 [color="green"]; <br>
	 * 24 - 23 [color="red"]; <br>
	 * Should Give: <br>
	 * 23 - 24 Green <br>
	 * 24 - 23 LightGray
	 */
	@Test
	void case12() {
	
	}
	
	/**
	 * 25 - 26 [color="green"]; <br>
	 * 25 - 26 [color="red"]; <br>
	 * Should Give: <br>
	 * 25 - 26 LightGray
	 */
	@Test
	void case13() {
	
	}
	
	/**
	 * 27 - 28 [color="green"]; <br>
	 * 27 - 28 [color="red"]; <br>
	 * 28 - 27 [color="red"]; <br>
	 * Should Give: <br>
	 * 27 - 28 LightGray <br>
	 * 28 - 27 Red
	 */
	@Test
	void case14() {
	
	}
	
	/**
	 * 29 - 30 [color="green"]; <br>
	 * 29 - 30 [color="red"]; <br>
	 * 30 - 29 [color="green"]; <br>
	 * Should Give: <br>
	 * 29 - 30 LightGray <br>
	 * 30 - 29 Green
	 */
	@Test
	void case15() {
	
	}
	
	/**
	 * 31 - 32 [color="red"]; <br>
	 * 31 - 32 [color="green"]; <br>
	 * 32 - 31 [color="red"]; <br>
	 * 32 - 31 [color="green"]; <br>
	 * Should Give: <br>
	 * 31 - 32 LightGray <br>
	 * 32 - 31 LightGray
	 */
	@Test
	void case16() {
	
	}
	
	private void assertThatGraphIsNotEmpty(GraphDto graph) {
		assertNotNull(graph);
		assertNotNull(graph.getCells());
		assertNotNull(graph.getEdges());
		assertEquals(32, graph.getCells().size());
		assertEquals(32, graph.getEdges().size());
	}
	
	
	private List<EdgeDto> getEdgeBySourceId(String sourceId) {
			return graph.getEdges().stream()
					.filter(edge -> edge.getSourceId().equals(sourceId))
					.collect(Collectors.toList());
	}
	
}
