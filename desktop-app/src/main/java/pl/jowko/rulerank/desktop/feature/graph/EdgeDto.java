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
	private Color color;
	
	/**
	 * @param sourceId of cell in edge
	 * @param targetId of cell in edge
	 * @param color of edge
	 */
	EdgeDto(String sourceId, String targetId, Color color) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.color = color;
	}
	
	String getSourceId() {
		return sourceId;
	}
	
	String getTargetId() {
		return targetId;
	}
	
	Color getColor() {
		return color;
	}
	
}
