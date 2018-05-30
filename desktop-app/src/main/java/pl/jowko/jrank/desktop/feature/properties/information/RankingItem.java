package pl.jowko.jrank.desktop.feature.properties.information;

/**
 * Created by Piotr on 2018-05-30
 * This class is used in ranking dialog for TreeView.
 * It contains data needed in ranking positions processing.
 * There are three types of nodes in ranking TreeView:
 * Root - not editable and mandatory by JavaFX, it contains label and has flag isRootNode set to true
 * Ranking - it is initialized with label, serves as container for fields on positions
 * Field - it contains FieldItem with represents row from MemoryTable.
 */
class RankingItem {
	
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
	 * Sets this instance as root node.
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
	
}
