package pl.jowko.rulerank.desktop.feature.rules;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import pl.jowko.rulerank.desktop.feature.internationalization.Labels;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.feature.customfx.IndexedTableColumn;
import pl.poznan.put.cs.idss.jrs.rules.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COLUMN_WIDTH_L;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COLUMN_WIDTH_S;

/**
 * This class creates columns and row items for rule table. <br>
 * It converts list of rules to table containing decisions and conditions from rules <br>
 *  <br>
 * Created by Piotr on 2018-05-21.
 */
class RuleTableCreator {
	
	private LanguageService labels;
	
	private List<Rule> rules;
	
	private List<TableColumn<RuleRow, ?>> columns;
	private List<RuleRow> items;
	
	private int rowId;
	/**
	 * Index of {@literal <=} operator in row
	 */
	private int thenOperatorColumnID;
	/**
	 * Number of columns to create
	 */
	private int columnsCount;
	/**
	 * Index of first decision cell in row
	 */
	private int firstDecisionPartColumnID;
	/**
	 * Index of first condition cell in row
	 */
	private int firstConditionColumnID;
	
	/**
	 * Create instance of this class.
	 * @param rules from with table columns and rows will be calculated.
	 */
	RuleTableCreator(List<Rule> rules) {
		this.rules = rules;
		rowId = 1;
		labels = LanguageService.getInstance();
		calculateColumnsCounts();
		createColumns();
		createItems();
	}
	
	/**
	 * Gets columns
	 * @return list of table columns with appropriate headers and column properties
	 */
	List<TableColumn<RuleRow, ?>> getColumns() {
		return columns;
	}
	
	/**
	 * Gets items
	 * @return List of RuleRow containing rules and text values for cells in table
	 */
	List<RuleRow> getItems() {
		return items;
	}
	
	/**
	 * Creates columns for rules table from rules list. <br>
	 * <br>
	 * Columns have format: <br>
	 * ID <br>
	 * Decision Part 1 <br>
	 * Separator Column <br>
	 * Decision Part N <br>
	 * {@literal Then column(<=)} <br>
	 * Condition 1 <br>
	 * Separator Column <br>
	 * Condition M <br>
	 * <br>
	 * Where: <br>
	 * N is maximum number of decision for all provided rules <br>
	 * M is maximum number of conditions for all provided rules <br>
	 * <br>
	 * For each decision and conditions two columns are created. <br>
	 */
	private void createColumns() {
		columns = new ArrayList<>();
		columns.add(createIDColumn());
		
		int conditionColumnNumber = 1;
		
		for(int i=0; i<columnsCount-1; i++) {
			if(i%2 == 1 && i!= thenOperatorColumnID-1) { // create column for separators and operators
				var column = createColumn("", i);
				setSmallSize(column);
				columns.add(column);
				
			} else if(i < thenOperatorColumnID-1) {
				columns.add(createColumn(labels.get(Labels.RULES_DECISION_PART), i));
				
			} else if(i == thenOperatorColumnID-1) {
				var column = createColumn("<=", i);
				column.setSortable(false);
				setSmallSize(column);
				columns.add(column);
				
			} else {
				columns.add(createColumn(labels.get(Labels.RULES_CONDITION_PART) + conditionColumnNumber++, i));
			}
		}
	}
	
	private void setSmallSize(TableColumn<RuleRow, String> column) {
		column.setResizable(false);
		column.setMinWidth(COLUMN_WIDTH_S);
		column.setMaxWidth(COLUMN_WIDTH_S);
	}
	
	/**
	 * Create JavaFX read only column for rules table. <br>
	 * When creating column, index for IndexedTableColumn is incremented by 1, because we already created one column(ID), and index starts from 0.
	 * @param headerText with will be displayed in header
	 * @param columnIndex from with cell values will be extracted from rows
	 * @return column for rules table
	 */
	private IndexedTableColumn<RuleRow, String> createColumn(String headerText, int columnIndex) {
		IndexedTableColumn<RuleRow, String> column = new IndexedTableColumn<>(headerText, columnIndex+1);
		column.setText(headerText);
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getCells().get(columnIndex))
		);
		column.setReorderable(false);
		column.setMinWidth(COLUMN_WIDTH_L);
		
		return column;
	}
	
	/**
	 * Creates ID column with have Integer type. <br>
	 * Integer type is needed for proper number sorting.
	 * @return column with integer type for ID
	 */
	private TableColumn<RuleRow, Integer> createIDColumn() {
		IndexedTableColumn<RuleRow, Integer> column = new IndexedTableColumn<>(labels.get(Labels.RULES_ID), 0);
		column.setText(labels.get(Labels.RULES_ID));
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getId())
		);
		column.setReorderable(false);
		column.setMinWidth(COLUMN_WIDTH_S);
		
		return column;
	}
	
	/**
	 * Creates items(rows) for rules table.
	 */
	private void createItems() {
		items = new ArrayList<>();
		for(Rule rule : rules) {
			items.add(createItem(rule));
		}
	}
	
	/**
	 * This method create RuleRow item. Most of the code was copied from jMAF application. <br>
	 * It fills appropriate string values to row cells basing on decision and conditions count.
	 * @param rule from with row item will be created
	 * @return RuleRow containing rule and text for all columns in row
	 */
	private RuleRow createItem(Rule rule) {
		List<String> cells = new ArrayList<>(Collections.nCopies(columnsCount, ""));
		
		for (int j = 0; j < rule.getDecisions().length; j++) {
			cells.set(firstDecisionPartColumnID + 2 * j, translateDecision(rule.getDecisions()[0]));
			if (j>0) {
				cells.set(firstDecisionPartColumnID + 2 * j - 1, "OR");
			}
		}
		cells.set(thenOperatorColumnID, "<=");
		
		Condition[] conditions = rule.getConditionsAsArray();
		for (int j = 0; j < conditions.length; j++)
		{
			Condition condition = conditions[j];
			if (condition != null) {
				cells.set(firstConditionColumnID + 2 * j, condition.toString());
			}
			if (j > 0) {
				cells.set(firstConditionColumnID + 2 * j - 1, "&");
			}
		}
		cells.remove(0); // remove empty cell
		
		return new RuleRow(rowId++, cells, rule);
	}
	
	/**
	 * Checks decision type and returns label for provided type.
	 * @param decisionCondition with is translated to label
	 * @return String value of decision condition
	 */
	private String translateDecision(Condition decisionCondition) {
		SingleCondition condition = (SingleCondition) decisionCondition;
		if(condition.getRelation() instanceof RelationAtLeast) {
			return labels.get(Labels.RULES_DECISION_S);
		} else if(condition.getRelation() instanceof RelationAtMost) {
			return labels.get(Labels.RULES_DECISION_SC);
		}
		return "";
	}
	
	/**
	 * This methods calculates indexes for different types of columns
	 */
	private void calculateColumnsCounts() {
		int maxDecisionPartsCount = rules.stream()
				.map(Rule::getQuantityOfDecisions)
				.max(Integer::compareTo)
				.orElse(0);
		
		int maxConditionsCount = rules.stream()
				.map(Rule::getQuantityOfConditions)
				.max(Integer::compareTo)
				.orElse(0);
		
		thenOperatorColumnID = 2 * maxDecisionPartsCount;
		columnsCount = 2 * (maxDecisionPartsCount + maxConditionsCount);
		firstDecisionPartColumnID = 1;
		firstConditionColumnID = thenOperatorColumnID + 1;
	}
	
}
