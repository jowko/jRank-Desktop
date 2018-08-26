package pl.jowko.rulerank.desktop.feature.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.*;

/**
 * This class test all cases for coloring graph(16 cases). <br>
 * Each test method has attached JavaDoc explaining test case. <br>
 * To avoid adding literals every where. {@literal -> } sign was replaced with: - <br>
 * <br>
 * Created by Piotr on 2018-08-25
 */
class GraphColorsTest extends AbstractGraphTest {
	
	private static GraphDto graph;
	
	@BeforeAll
	static void loadFiles() throws IOException {
		String allColorsGraphContent = getFileContent("/graph/allColorsGraph.graph");
		GraphDto dto = getGraph(allColorsGraphContent);
		graph = new GraphReducer().reduce(dto);
	}
	
	@Test
	void extractGraphData() {
		assertThatGraphIsNotEmpty(graph);
	}
	
	@Test
	void case1ShouldHaveNoEdges() {
		assertEquals(0, getEdgesBySourceId("1").size());
		assertEquals(0, getEdgesBySourceId("2").size());
	}
	
	/**
	 * 4 - 3 [color="red"]; <br>
	 * Should Give: <br>
	 * 4 - 3 Red
	 */
	@Test
	void case2() {
		List<EdgeDto> edges = getEdgesBySourceId("3");
		List<EdgeDto> edges2 = getEdgesBySourceId("4");
		
		assertEquals(0, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(SC_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 6 - 5 [color="green"]; <br>
	 * Should Give: <br>
	 * 6 - 5 Green
	 */
	@Test
	void case3() {
		List<EdgeDto> edges = getEdgesBySourceId("5");
		List<EdgeDto> edges2 = getEdgesBySourceId("6");
		
		assertEquals(0, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 8 - 7 [color="green"]; <br>
	 * 8 - 7 [color="red"]; <br>
	 * Should Give: <br>
	 * 8 - 7 Grey
	 */
	@Test
	void case4() {
		List<EdgeDto> edges = getEdgesBySourceId("7");
		List<EdgeDto> edges2 = getEdgesBySourceId("8");
		
		assertEquals(0, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_SC_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 9 - 10 [color="red"]; <br>
	 * Should Give: <br>
	 * 9 - 10 Red
	 */
	@Test
	void case5() {
		List<EdgeDto> edges = getEdgesBySourceId("9");
		List<EdgeDto> edges2 = getEdgesBySourceId("10");
		
		assertEquals(1, edges.size());
		assertEquals(0, edges2.size());
		assertEquals(SC_COLOR, edges.get(0).getColor());
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
		List<EdgeDto> edges = getEdgesBySourceId("11");
		List<EdgeDto> edges2 = getEdgesBySourceId("12");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(SC_COLOR, edges.get(0).getColor());
		assertEquals(SC_COLOR, edges2.get(0).getColor());
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
		List<EdgeDto> edges = getEdgesBySourceId("13");
		List<EdgeDto> edges2 = getEdgesBySourceId("14");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(SC_COLOR, edges.get(0).getColor());
		assertEquals(S_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 15 - 16 [color="red"]; <br>
	 * 16 - 15 [color="red"]; <br>
	 * 16 - 15 [color="green"]; <br>
	 * Should Give: <br>
	 * 15 - 16 Red <br>
	 * 16 - 15 Grey
	 */
	@Test
	void case8() {
		List<EdgeDto> edges = getEdgesBySourceId("15");
		List<EdgeDto> edges2 = getEdgesBySourceId("16");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(SC_COLOR, edges.get(0).getColor());
		assertEquals(S_SC_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 17 - 18 [color="green"]; <br>
	 * Should Give: <br>
	 * 17 - 18 Green
	 */
	@Test
	void case9() {
		List<EdgeDto> edges = getEdgesBySourceId("17");
		List<EdgeDto> edges2 = getEdgesBySourceId("18");
		
		assertEquals(1, edges.size());
		assertEquals(0, edges2.size());
		assertEquals(S_COLOR, edges.get(0).getColor());
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
		List<EdgeDto> edges = getEdgesBySourceId("19");
		List<EdgeDto> edges2 = getEdgesBySourceId("20");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_COLOR, edges.get(0).getColor());
		assertEquals(SC_COLOR, edges2.get(0).getColor());
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
		List<EdgeDto> edges = getEdgesBySourceId("21");
		List<EdgeDto> edges2 = getEdgesBySourceId("22");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_COLOR, edges.get(0).getColor());
		assertEquals(S_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 23 - 24 [color="green"]; <br>
	 * 24 - 23 [color="green"]; <br>
	 * 24 - 23 [color="red"]; <br>
	 * Should Give: <br>
	 * 23 - 24 Green <br>
	 * 24 - 23 Grey
	 */
	@Test
	void case12() {
		List<EdgeDto> edges = getEdgesBySourceId("23");
		List<EdgeDto> edges2 = getEdgesBySourceId("24");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_COLOR, edges.get(0).getColor());
		assertEquals(S_SC_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 25 - 26 [color="green"]; <br>
	 * 25 - 26 [color="red"]; <br>
	 * Should Give: <br>
	 * 25 - 26 Grey
	 */
	@Test
	void case13() {
		List<EdgeDto> edges = getEdgesBySourceId("25");
		List<EdgeDto> edges2 = getEdgesBySourceId("26");
		
		assertEquals(1, edges.size());
		assertEquals(0, edges2.size());
		assertEquals(S_SC_COLOR, edges.get(0).getColor());
	}
	
	/**
	 * 27 - 28 [color="green"]; <br>
	 * 27 - 28 [color="red"]; <br>
	 * 28 - 27 [color="red"]; <br>
	 * Should Give: <br>
	 * 27 - 28 Grey <br>
	 * 28 - 27 Red
	 */
	@Test
	void case14() {
		List<EdgeDto> edges = getEdgesBySourceId("27");
		List<EdgeDto> edges2 = getEdgesBySourceId("28");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_SC_COLOR, edges.get(0).getColor());
		assertEquals(SC_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 29 - 30 [color="green"]; <br>
	 * 29 - 30 [color="red"]; <br>
	 * 30 - 29 [color="green"]; <br>
	 * Should Give: <br>
	 * 29 - 30 Grey <br>
	 * 30 - 29 Green
	 */
	@Test
	void case15() {
		List<EdgeDto> edges = getEdgesBySourceId("29");
		List<EdgeDto> edges2 = getEdgesBySourceId("30");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_SC_COLOR, edges.get(0).getColor());
		assertEquals(S_COLOR, edges2.get(0).getColor());
	}
	
	/**
	 * 31 - 32 [color="red"]; <br>
	 * 31 - 32 [color="green"]; <br>
	 * 32 - 31 [color="red"]; <br>
	 * 32 - 31 [color="green"]; <br>
	 * Should Give: <br>
	 * 31 - 32 Grey <br>
	 * 32 - 31 Grey
	 */
	@Test
	void case16() {
		List<EdgeDto> edges = getEdgesBySourceId("31");
		List<EdgeDto> edges2 = getEdgesBySourceId("32");
		
		assertEquals(1, edges.size());
		assertEquals(1, edges2.size());
		assertEquals(S_SC_COLOR, edges.get(0).getColor());
		assertEquals(S_SC_COLOR, edges2.get(0).getColor());
	}
	
	private void assertThatGraphIsNotEmpty(GraphDto graph) {
		assertNotNull(graph);
		assertNotNull(graph.getCells());
		assertNotNull(graph.getEdges());
		assertEquals(32, graph.getCells().size());
		assertEquals(24, graph.getEdges().size());
	}
	
	
	private List<EdgeDto> getEdgesBySourceId(String sourceId) {
			return graph.getEdges().stream()
					.filter(edge -> edge.getSourceId().equals(sourceId))
					.collect(Collectors.toList());
	}
	
}
