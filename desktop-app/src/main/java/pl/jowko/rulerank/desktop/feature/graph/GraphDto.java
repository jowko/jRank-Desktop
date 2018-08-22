package pl.jowko.rulerank.desktop.feature.graph;

import java.util.List;

/**
 * Contains data needed to create graph. <br>
 *  <br>
 * Created by Piotr on 2018-08-22
 */
class GraphDto {
	
	private List<CellDto> cells;
	private List<EdgeDto> edges;
	
	GraphDto(List<CellDto> cells, List<EdgeDto> edges) {
		this.cells = cells;
		this.edges = edges;
	}
	
	List<CellDto> getCells() {
		return cells;
	}
	
	List<EdgeDto> getEdges() {
		return edges;
	}
	
}
