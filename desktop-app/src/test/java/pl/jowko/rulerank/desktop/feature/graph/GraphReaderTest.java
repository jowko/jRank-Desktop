package pl.jowko.rulerank.desktop.feature.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.*;

/**
 * Created by Piotr on 2018-08-22
 */
class GraphReaderTest extends AbstractGraphTest {
	
	private static String simpleGraphContent;
	private static String labeledGraphContent;
	
	@BeforeAll
	static void loadFiles() throws IOException {
		simpleGraphContent = getFileContent("/graph/simple.graph");
		labeledGraphContent = getFileContent("/graph/labeledEdges.graph");
	}
	
	@Test
	void simpleGraphExtractData() {
		GraphDto graph = getGraph(simpleGraphContent);
		assertThatGraphIsNotEmpty(graph);
	}
	
	@Test
	void labeledGraphExtractData() {
		GraphDto graph = getGraph(labeledGraphContent);
		assertThatGraphIsNotEmpty(graph);
	}
	
	private void assertThatGraphIsNotEmpty(GraphDto graph) {
		assertNotNull(graph);
		assertNotNull(graph.getCells());
		assertNotNull(graph.getEdges());
		assertEquals(3, graph.getCells().size());
		assertEquals(3, graph.getEdges().size());
	}
	
	@Test
	void simpleGraphExtractCells() {
		GraphDto graph = getGraph(simpleGraphContent);
		checkIfCellsAreExtractedCorrectly(graph);
	}
	
	@Test
	void labeledGraphExtractCells() {
		GraphDto graph = getGraph(labeledGraphContent);
		checkIfCellsAreExtractedCorrectly(graph);
	}
	
	private void checkIfCellsAreExtractedCorrectly(GraphDto graph) {
		int index = 1;
		for(var cell : graph.getCells()) {
			assertEquals(String.valueOf(index), cell.getCellId());
			assertEquals("test" + index, cell.getLabel());
			assertEquals(CELL_COLOR, cell.getColor());
			index++;
		}
	}
	
	@Test
	void simpleGraphExtractEdges() {
		List<EdgeDto> edges = getGraph(simpleGraphContent).getEdges();
		
		checkIfEdgesAreExtractedCorrectly(edges);
		edges.forEach(edge ->
			assertEquals("", edge.getLabel())
		);
	}
	
	@Test
	void labeledGraphExtractEdges() {
		List<EdgeDto> edges = getGraph(labeledGraphContent).getEdges();
		
		checkIfEdgesAreExtractedCorrectly(edges);
		assertEquals("[0.84]", edges.get(0).getLabel());
		assertEquals("", edges.get(1).getLabel());
		assertEquals("[0.64]", edges.get(2).getLabel());
	}
	
	private void checkIfEdgesAreExtractedCorrectly(List<EdgeDto> edges) {
		EdgeDto first = edges.get(0);
		assertEquals("1", first.getSourceId());
		assertEquals("3", first.getTargetId());
		assertEquals(S_COLOR, first.getColor());
		
		EdgeDto second = edges.get(1);
		assertEquals("1", second.getSourceId());
		assertEquals("2", second.getTargetId());
		assertEquals(SC_COLOR, second.getColor());
		
		EdgeDto three = edges.get(2);
		assertEquals("2", three.getSourceId());
		assertEquals("3", three.getTargetId());
		assertEquals(ERROR_COLOR, three.getColor());
	}
	
}
