package pl.jowko.rulerank.desktop.feature.properties.information;

import pl.poznan.put.cs.idss.jrs.types.Field;

import java.util.List;
import java.util.Objects;

/**
 * This class is used to display pairs with relations on pairs dialog. <br>
 * <br>
 * It will display them in such format: <br>
 * Field_1_Label Relation Field_2_Label <br>
 * <br>
 * Labels are stored in lists of fields. <br>
 *  <br>
 * Created by Piotr on 2018-05-29
 * @see PropertiesPairsController
 * @see FieldItem
 */
class PairsItem {
	
	private List<Field> leftSide;
	private List<Field> rightSide;
	private String relation;
	private int displayedFieldIndex;
	
	/**
	 * Creates instance of this class.
	 * @param leftSide for first item in relation
	 * @param rightSide for last item in relation
	 * @param relation between left and right item
	 * @param displayedFieldIndex with indicates current displayed label index
	 */
	PairsItem(List<Field> leftSide, List<Field> rightSide, String relation, int displayedFieldIndex) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.relation = relation;
		this.displayedFieldIndex = displayedFieldIndex;
	}
	
	void setDisplayedFieldIndex(int displayedFieldIndex) {
		this.displayedFieldIndex = displayedFieldIndex;
	}
	
	/**
	 * @return ID of left element in relation triple
	 */
	String getLeftId() {
		return leftSide.get(0).toString();
	}
	
	/**
	 * @return ID of right element in relation triple
	 */
	String getRightId() {
		return rightSide.get(0).toString();
	}
	
	/**
	 * @return relation from relation triple
	 */
	String getRelation() {
		return relation;
	}
	
	/**
	 * @return String with format: Field_1_Label Relation Field_2_Label
	 */
	@Override
	public String toString() {
		return leftSide.get(displayedFieldIndex).toString() + ' ' + relation + ' ' + rightSide.get(displayedFieldIndex).toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PairsItem pairsItem = (PairsItem) o;
		return Objects.equals(leftSide, pairsItem.leftSide) &&
				Objects.equals(rightSide, pairsItem.rightSide) &&
				Objects.equals(relation, pairsItem.relation);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(leftSide, rightSide, relation);
	}
	
}
