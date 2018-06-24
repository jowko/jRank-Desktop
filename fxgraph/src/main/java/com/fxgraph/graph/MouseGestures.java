package com.fxgraph.graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 */
public class MouseGestures {
	
	private final DragContext dragContext = new DragContext();
	
	private Graph graph;
	
	private MouseClickAction mouseClickAction;
	
	public MouseGestures(Graph graph, MouseClickAction mouseClickAction) {
		this.graph = graph;
		this.mouseClickAction = mouseClickAction;
	}
	
	public void makeDraggable(final Node node) {
		
		
		node.setOnMousePressed(onMousePressedEventHandler);
		node.setOnMouseDragged(onMouseDraggedEventHandler);
		node.setOnMouseReleased(onMouseReleasedEventHandler);
		
	}
	
	public void makeClickable(Cell cell) {
		cell.setOnMouseClicked(event ->
				mouseClickAction.performAction(cell)
		);
	}
	
	EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {
		
		@Override
		public void handle(MouseEvent event) {
			
			Node node = (Node) event.getSource();
			
			double scale = graph.getScale();
			
			dragContext.x = node.getBoundsInParent().getMinX() * scale - event.getScreenX();
			dragContext.y = node.getBoundsInParent().getMinY() * scale - event.getScreenY();
			
		}
	};
	
	EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<>() {
		
		@Override
		public void handle(MouseEvent event) {
			
			Node node = (Node) event.getSource();
			
			double offsetX = event.getScreenX() + dragContext.x;
			double offsetY = event.getScreenY() + dragContext.y;
			
			// adjust the offset in case we are zoomed
			double scale = graph.getScale();
			
			offsetX /= scale;
			offsetY /= scale;
			
			node.relocate(offsetX, offsetY);
			
		}
	};
	
	EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
	
	};
	
	class DragContext {
		
		double x;
		double y;
		
	}
	
}
