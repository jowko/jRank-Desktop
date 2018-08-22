package pl.jowko.rulerank.desktop.feature.graph;

import javafx.scene.paint.Color;

/**
 * Contains data needed to create edge in Graph. <br>
 *  <br>
 * Created by Piotr on 2018-08-22
 * @see CellDto
 */
class EdgeDto {
	
	private String sourceId;
	private String targetId;
	private String label;
	private Color color;
	
	/**
	 * @param sourceId of cell in edge
	 * @param targetId of cell in edge
	 * @param label from edge with will be displayed in arcs tab
	 * @param color of edge
	 */
	EdgeDto(String sourceId, String targetId, String label, Color color) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.label = label;
		this.color = color;
	}
	
	String getSourceId() {
		return sourceId;
	}
	
	String getTargetId() {
		return targetId;
	}
	
	String getLabel() {
		return label;
	}
	
	Color getColor() {
		return color;
	}
	
}
