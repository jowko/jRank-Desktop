package pl.jowko.rulerank.desktop.feature.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.S_SC_COLOR;

/**
 * Created by Piotr on 2018-08-26
 */
class GraphReducerTest extends AbstractGraphTest {
	
	private static GraphDto graph;
	
	@BeforeAll
	static void loadFiles() throws IOException {
		String allColorsGraphContent = getFileContent("/graph/full.graph");
		GraphDto dto = getGraph(allColorsGraphContent);
		graph = new GraphReducer().reduce(dto);
	}
	
	@Test
	void shouldMapData() {
		assertEquals(3, graph.getCells().size());
		assertEquals(6, graph.getEdges().size());
	}
	
	@Test
	void shouldMapColorsAsGrey() {
		assertFalse(graph.getEdges().isEmpty());
		graph.getEdges().forEach(edge ->
			assertEquals(S_SC_COLOR, edge.getColor())
		);
	}
	
	@Test
	void shouldMapFirstCase() {
		var edges = getEdgesBySourceId("1");
		assertEquals(2, edges.size());
		assertEquals("2", edges.get(0).getTargetId());
		assertEquals("3", edges.get(1).getTargetId());
	}
	
	@Test
	void shouldMapSecondCase() {
		var edges = getEdgesBySourceId("2");
		assertEquals(2, edges.size());
		assertEquals("1", edges.get(0).getTargetId());
		assertEquals("3", edges.get(1).getTargetId());
	}
	
	@Test
	void shouldMapThirdCase() {
		var edges = getEdgesBySourceId("3");
		assertEquals(2, edges.size());
		assertEquals("1", edges.get(0).getTargetId());
		assertEquals("2", edges.get(1).getTargetId());
	}
	
	private List<EdgeDto> getEdgesBySourceId(String sourceId) {
		return graph.getEdges().stream()
				.filter(edge -> edge.getSourceId().equals(sourceId))
				.collect(Collectors.toList());
	}
	
}
