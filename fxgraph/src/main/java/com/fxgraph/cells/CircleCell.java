package com.fxgraph.cells;

import com.fxgraph.graph.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class allows to draw cell as circle.<br>
 * Created by Piotr on 2018-06-02
 */
public class CircleCell extends Cell {
	
	private Circle circle;
	
	public CircleCell(String cellId) {
		this(cellId, null, Color.RED);
	}
	
	public CircleCell(String cellId, String label, Color color) {
		super(cellId);
		
		circle = new Circle(25, 25, 25);
		circle.setStroke(color);
		circle.setFill(color);
		
		Text text = new Text(label);
		text.setFont(Font.font("Verdana", 20));
		
		getChildren().addAll(circle, text);
	}
	
	public Circle getCircle() {
		return circle;
	}
	
}
