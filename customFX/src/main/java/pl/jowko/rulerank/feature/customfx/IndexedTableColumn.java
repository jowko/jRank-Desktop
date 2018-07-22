package pl.jowko.rulerank.feature.customfx;

import javafx.scene.control.TableColumn;

/**
 * Created by Piotr on 2018-05-27
 * This table column extends standard column by index and name.
 * This class is useful when table reordering is enabled and original columns indexes are needed.
 * JavaFX after columns reorder will change columns indexes but items(rows) positions remain original.
 * With original column index, we can easily access data from table items and access correct item index to get data for specified column.
 */
public class IndexedTableColumn<S,T> extends TableColumn<S,T> {
	
	private int index;
	private String name;
	
	public IndexedTableColumn(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public IndexedTableColumn(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
