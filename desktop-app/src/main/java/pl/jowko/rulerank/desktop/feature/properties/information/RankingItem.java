package pl.jowko.rulerank.desktop.feature.properties.information;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is used in ranking dialog for TreeView. <br>
 * It contains data needed in ranking positions processing. <br>
 * There are three types of nodes in ranking TreeView: <br>
 * Root - not editable and mandatory by JavaFX, it contains label and has flag isRootNode set to true <br>
 * Ranking - it is initialized with label, serves as container for fields on positions <br>
 * Field - it contains FieldItem with represents row from MemoryTable. <br>
 *  <br>
 * Created by Piotr on 2018-05-30
 */
class RankingItem implements Serializable {
	
	private static final long serialVersionUID = 948864605809415765L;
	
	private FieldItem item;
	private String label;
	private boolean isRankingNode;
	private boolean isRootNode;
	
	/**
	 * Creates instance for item representing row in MemoryTable.
	 * @param item from MemoryTable
	 */
	RankingItem(FieldItem item) {
		this.item = item;
		this.isRankingNode = false;
	}
	
	/**
	 * Creates instance representing position with contains items from MemoryTable
	 * @param label to display
	 */
	RankingItem(String label) {
		this.label = label;
		this.isRankingNode = true;
	}
	
	FieldItem getItem() {
		return item;
	}
	
	void setLabel(String label) {
		this.label = label;
	}
	
	boolean isRankingNode() {
		return isRankingNode;
	}
	
	boolean isRootNode() {
		return isRootNode;
	}
	
	/**
	 * Sets this instance as root node. <br>
	 * It should be only used for first created instance of this class with label.
	 * @param rootNode with indicates is this instance is root item in TreeView
	 */
	void setRootNode(boolean rootNode) {
		isRootNode = rootNode;
	}
	
	@Override
	public String toString() {
		if(isRankingNode)
			return label;
		else
			return item.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RankingItem item1 = (RankingItem) o;
		return isRankingNode == item1.isRankingNode &&
				isRootNode == item1.isRootNode &&
				Objects.equals(item, item1.item) &&
				Objects.equals(label, item1.label);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(item, label, isRankingNode, isRootNode);
	}
	
}
