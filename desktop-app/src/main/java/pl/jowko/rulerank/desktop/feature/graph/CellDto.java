package pl.jowko.rulerank.desktop.feature.graph;

import javafx.scene.paint.Color;

/**
 * Contains data for creating cells in Graph. <br>
 *  <br>
 * Created by Piotr on 2018-08-22
 */
class CellDto {
	
	private String cellId;
	private String label;
	private Color color;
	
	/**
	 * @param cellId with identifies cell
	 * @param label with is displayed on cell
	 * @param color of cell
	 */
	CellDto(String cellId, String label, Color color) {
		this.cellId = cellId;
		this.label = label;
		this.color = color;
	}
	
	String getCellId() {
		return cellId;
	}
	
	String getLabel() {
		return label;
	}
	
	Color getColor() {
		return color;
	}
	
}
