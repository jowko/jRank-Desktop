package com.fxgraph.cells;

import com.fxgraph.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 */
public class RectangleCell extends Cell {
	
	public RectangleCell( String id) {
		super( id);
		
		Rectangle view = new Rectangle( 50,50);
		
		view.setStroke(Color.DODGERBLUE);
		view.setFill(Color.DODGERBLUE);
		
		setView( view);
		
	}
	
}
