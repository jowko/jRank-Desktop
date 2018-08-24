package com.fxgraph.layout;

import com.fxgraph.graph.Edge;
import javafx.scene.shape.Line;

import java.util.List;

/**
 * This class sets stroke array for edges line. <br>
 * <br>
 * Created by Piotr on 2018-08-24
 */
public class EdgeStrokeArraySetter {
	
	private EdgeStrokeArraySetter() {}
	
	/**
	 * Sets stroke array for lines in provided edges list. <br>
	 * Two strokes will be created: one of width lineLength - 40, second with width 40. <br>
	 * Such array will create impression, that line ends before cell. <br>
	 * Line ends before target cell, so in such case: <br>
	 * {@literal 1 -> 2} - line starts from cell 1 and ends before cell 2
	 * @param edges for with line ends will be simulated
	 */
	public static void setStrokeArray(List<Edge> edges) {
		for(Edge edge : edges) {
			Line line = edge.getLine();
			double lineLength = Math.hypot(line.getStartX() - line.getEndX(), line.getStartY() - line.getEndY());
			double invisibleLineLength;
			if(lineLength > 41d) {
				invisibleLineLength = 40d;
			} else {
				invisibleLineLength = 0d;
			}
			
			line.getStrokeDashArray().setAll(lineLength - invisibleLineLength, invisibleLineLength);
		}
	}
	
}
