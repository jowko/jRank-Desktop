package com.fxgraph;

import com.fxgraph.layout.Layout;
import com.fxgraph.layout.RandomLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.fxgraph.graph.CellType;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 *
 * This class contains class with main method and example graph.
 */
public class Main extends Application {
	
	Graph graph = new Graph();
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		graph = new Graph();
		
		root.setCenter(graph.getScrollPane());
		
		Scene scene = new Scene(root, 1024, 768);
		scene.getStylesheets().add("graph.css");
		
		addGraphComponents();
		
		Layout layout = new RandomLayout(graph);
		layout.execute();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void addGraphComponents() {
		
		Model model = graph.getModel();
		
		graph.beginUpdate();
		
		model.addCell("Cell A", CellType.RECTANGLE);
		model.addCell("Cell B", CellType.CIRCLE);
		model.addCell("Cell C", CellType.RECTANGLE);
		model.addCell("Cell D", CellType.TRIANGLE);
		model.addCell("Cell E", CellType.TRIANGLE);
		model.addCell("Cell F", CellType.RECTANGLE);
		model.addCell("Cell G", CellType.RECTANGLE);
		
		model.addEdge("Cell A", "Cell B");
		model.addEdge("Cell A", "Cell C");
		model.addEdge("Cell B", "Cell C");
		model.addEdge("Cell C", "Cell D");
		model.addEdge("Cell B", "Cell E");
		model.addEdge("Cell D", "Cell F");
		model.addEdge("Cell D", "Cell G");
		
		graph.endUpdate();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
