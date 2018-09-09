package pl.jowko.rulerank.desktop.feature.graph;

import javafx.scene.paint.Color;
import pl.jowko.rulerank.logger.RuleRankLogger;

import static pl.jowko.rulerank.desktop.feature.graph.GraphColor.ERROR_COLOR;

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
	private String secondLabel;
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
		
		if(color.equals(ERROR_COLOR)) {
			RuleRankLogger.warn("Color was not recognized for edge: " + toString());
		}
	}
	
	/**
	 * @param sourceId of cell in edge
	 * @param targetId of cell in edge
	 * @param label from edge with will be displayed in arcs tab
	 * @param secondLabel from edge with will be displayed in arcs tab in case of edge reduction. In case of reduction, it is assumed that second label represents At most relation.
	 * @param color of edge
	 */
	EdgeDto(String sourceId, String targetId, String label, String secondLabel, Color color) {
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.label = label;
		this.secondLabel = secondLabel;
		this.color = color;
		
		if(color.equals(ERROR_COLOR)) {
			RuleRankLogger.warn("Color was not recognized for edge: " + toString());
		}
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
	
	String getSecondLabel() {
		return secondLabel;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EdgeDto{");
		sb.append("sourceId='").append(sourceId).append('\'');
		sb.append(", targetId='").append(targetId).append('\'');
		sb.append(", label='").append(label).append('\'');
		sb.append(", color=").append(color);
		sb.append('}');
		return sb.toString();
	}
	
}
