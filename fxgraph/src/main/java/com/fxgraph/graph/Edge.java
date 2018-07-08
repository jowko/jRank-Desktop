package com.fxgraph.graph;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 */
public class Edge extends Group {
	
	protected Cell source;
	protected Cell target;
	
	protected Line line;
	
	public Edge(Cell source, Cell target) {
		this(source, target, Color.GRAY);
	}
	
	public Edge(Cell source, Cell target, Color color) {
		
		this.source = source;
		this.target = target;
		
		source.addCellChild(target);
		target.addCellParent(source);
		
		line = new Line();
		line.setStroke(color);
		
		line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
		line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));
		
		line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
		line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));
		
		getChildren().add(line);
		
	}
	
	public Cell getSource() {
		return source;
	}
	
	public Cell getTarget() {
		return target;
	}
	
	public Line getLine() {
		return line;
	}
	
}
