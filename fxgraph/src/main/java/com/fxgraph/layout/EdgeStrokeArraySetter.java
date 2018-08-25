package com.fxgraph.layout;

import com.fxgraph.graph.Edge;
import javafx.scene.shape.QuadCurve;

import java.util.List;

/**
 * This class sets stroke array for edges line. <br>
 * <br>
 * Created by Piotr on 2018-08-24
 */
public class EdgeStrokeArraySetter {
	
	private static final double ZERO = 0d;
	private static final double TWO = 2d;
	private static final double DISTANCE = 8d;
	private static final double ADDITIONAL_LENGTH = 40d;
	
	private EdgeStrokeArraySetter() {}
	
	/**
	 * Sets stroke array for lines in provided edges list. <br>
	 * Two strokes will be created: one of width lineLength - 40, second with width 40. <br>
	 * Such array will create impression, that line ends before cell. <br>
	 * Line ends before target cell, so in such case: <br>
	 * {@literal 1 -> 2} - line starts from cell 1 and ends before cell 2 <br>
	 * Line is also slightly bended.
	 * @param edges for with line ends will be simulated
	 */
	public static void setStrokeArray(List<Edge> edges) {
		for(Edge edge : edges) {
			QuadCurve line = edge.getLine();
			
			double xDiff = line.getStartX() - line.getEndX();
			double yDiff = line.getStartY() - line.getEndY();
			double lineLength = Math.hypot(xDiff, yDiff);
			
			double invisibleLineLength;
			if(lineLength > ADDITIONAL_LENGTH) {
				invisibleLineLength = ADDITIONAL_LENGTH;
			} else {
				invisibleLineLength = ZERO;
			}
			
			line.getStrokeDashArray().setAll(lineLength - invisibleLineLength, invisibleLineLength);
			
			double angle = Math.atan2(yDiff, xDiff);
			
			double x = (line.getStartX() + line.getEndX())/TWO;
			double y = (line.getStartY() + line.getEndY())/TWO;
			
			double xCorrection = Math.sin(angle) * DISTANCE;
			double yCorrection = Math.cos(angle) * DISTANCE;
			
			line.setControlX(x + xCorrection);
			line.setControlY(y + yCorrection);
			line.setFill(null);
		}
	}
	
}
