package com.fxgraph.graph;

import com.fxgraph.cells.CircleCell;
import com.fxgraph.cells.RectangleCell;
import com.fxgraph.cells.TriangleCell;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This code was copied from StackOverflow:
 * https://stackoverflow.com/a/30696075/8906826
 */
public class Model {
	
	Cell graphParent;
	
	List<Cell> allCells;
	List<Cell> addedCells;
	List<Cell> removedCells;
	
	List<Edge> allEdges;
	List<Edge> addedEdges;
	List<Edge> removedEdges;
	
	Map<String,Cell> cellMap; // <id,cell>
	
	public Model() {
		
		graphParent = new Cell( "_ROOT_");
		
		// clear model, create lists
		clear();
	}
	
	public void clear() {
		
		allCells = new ArrayList<>();
		addedCells = new ArrayList<>();
		removedCells = new ArrayList<>();
		
		allEdges = new ArrayList<>();
		addedEdges = new ArrayList<>();
		removedEdges = new ArrayList<>();
		
		cellMap = new HashMap<>(); // <id,cell>
		
	}
	
	public void clearAddedLists() {
		addedCells.clear();
		addedEdges.clear();
	}
	
	public List<Cell> getAddedCells() {
		return addedCells;
	}
	
	public List<Cell> getRemovedCells() {
		return removedCells;
	}
	
	public List<Cell> getAllCells() {
		return allCells;
	}
	
	public List<Edge> getAddedEdges() {
		return addedEdges;
	}
	
	public List<Edge> getRemovedEdges() {
		return removedEdges;
	}
	
	public List<Edge> getAllEdges() {
		return allEdges;
	}
	
	public void addCell(String id, CellType type) {
		
		switch (type) {
			
			case RECTANGLE:
				RectangleCell rectangleCell = new RectangleCell(id);
				addCell(rectangleCell);
				break;
			
			case TRIANGLE:
				TriangleCell triangleCell = new TriangleCell(id);
				addCell(triangleCell);
				break;
				
			case CIRCLE:
				CircleCell circleCell = new CircleCell(id);
				addCell(circleCell);
				break;
			
			default:
				throw new UnsupportedOperationException("Unsupported type: " + type);
		}
	}
	
	public void addCell( Cell cell) {
		
		addedCells.add(cell);
		
		cellMap.put( cell.getCellId(), cell);
		
	}
	
	public void addEdge(String sourceId, String targetId) {
		addEdge(sourceId, targetId, "", Color.GRAY);
	}
	
	public void addEdge(String sourceId, String targetId, String label, Color color) {
		Cell sourceCell = cellMap.get(sourceId);
		Cell targetCell = cellMap.get(targetId);
		
		LabeledEdge edge = new LabeledEdge(sourceCell, targetCell, label, color);
		
		addedEdges.add(edge);
	}
	
	/**
	 * Attach all cells which don't have a parent to graphParent
	 * @param cellList with will be added to graph
	 */
	public void attachOrphansToGraphParent( List<Cell> cellList) {
		
		for( Cell cell: cellList) {
			if( cell.getCellParents().size() == 0) {
				graphParent.addCellChild( cell);
			}
		}
		
	}
	
	/**
	 * Remove the graphParent reference if it is set
	 * @param cellList with will be added to graph
	 */
	public void disconnectFromGraphParent( List<Cell> cellList) {
		
		for( Cell cell: cellList) {
			graphParent.removeCellChild( cell);
		}
	}
	
	public void merge() {
		
		// cells
		allCells.addAll( addedCells);
		allCells.removeAll( removedCells);
		
		addedCells.clear();
		removedCells.clear();
		
		// edges
		allEdges.addAll( addedEdges);
		allEdges.removeAll( removedEdges);
		
		addedEdges.clear();
		removedEdges.clear();
		
	}
	
}
