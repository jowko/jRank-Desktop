package pl.jowko.rulerank.desktop.feature.graph;

import pl.jowko.rulerank.logger.RuleRankLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.*;

/**
 * This class aggregates edges in graph to reduce edges number <br>
 * If graph contains two edges: {@literal 1 -> 2 Red, 1 -> 2 Green, it will be reduced to 1 -> 2 LightGrey} <br>
 * Graph will have 4 types of edges colors for cases: <br>
 * 1 S 2 - Green <br>
 * 1 Sc 2 - Red <br>
 * 1 S 2, 1 Sc 2 - LightGrey <br>
 * Some unexpected input - Black <br>
 * <br>
 * Created by Piotr on 2018-08-26
 */
class GraphReducer {
	
	/**
	 * Reduces edges between same pairs of cells. <br>
	 * If graph contains two edges: {@literal 1 -> 2 Red, 1 -> 2 Green, it will be reduced to 1 -> 2 LightGrey} <br>
	 * In first step, it will map sourceId -> Related Edges
	 * @param dto with will be reduced
	 * @return reduced graph with Red, Green, LightGrey or Black edges
	 */
	GraphDto reduce(GraphDto dto) {
		Map<String, List<EdgeDto>> sourceMap = dto.getEdges().stream()
				.collect(groupingBy(EdgeDto::getSourceId));
		
		return new GraphDto(dto.getCells(), reduceEdges(sourceMap));
	}
	
	/**
	 * Reduces number of edges
	 * @param map of sourceId -> Related Edges
	 * @return list of reduced edges, with maximum of 2 connections between cells, example: {@literal 1 -> 2, 2 -> 1}
	 */
	private List<EdgeDto> reduceEdges(Map<String, List<EdgeDto>> map) {
		List<EdgeDto> edges = new ArrayList<>();
		
		map.forEach((sourceId, list) -> {
			Map<String, List<EdgeDto>> targetMap = list.stream()
					.collect(groupingBy(EdgeDto::getTargetId));
			
			targetMap.forEach((targetId, targets) ->
				edges.add(reduceEdge(targets))
			);
		});
		
		return edges;
	}
	
	/**
	 * Reduces list of edges to one edge <br>
	 * @param edges with same source and target ids
	 * @return reduced edge containing provided edge, if it was singleton list, or LightGrey edge if it was two edges, or Black in case of errors
	 */
	private EdgeDto reduceEdge(List<EdgeDto> edges) {
		if(edges.size() == 1) {
			return edges.get(0);
		}
		
		if(edges.size() == 2 && validateColor(edges.get(0)) && validateColor(edges.get(1))) {
			EdgeDto dto = edges.get(0);
			return new EdgeDto(dto.getSourceId(), dto.getTargetId(), dto.getLabel(), S_SC_COLOR);
		}
		
		RuleRankLogger.warn("Some error occurred when reducing edges, edges count for node was different than 1 or 2 or color was not recognized: " + edges.toString());
		return new EdgeDto("0", "0", "0", ERROR_COLOR);
	}
	
	/**
	 * Checks if edge have valid color
	 * @param edge to check
	 * @return true if edge has valid color, false otherwise
	 */
	private boolean validateColor(EdgeDto edge) {
		return edge.getColor().equals(SC_COLOR) || edge.getColor().equals(S_COLOR);
	}
	
}
