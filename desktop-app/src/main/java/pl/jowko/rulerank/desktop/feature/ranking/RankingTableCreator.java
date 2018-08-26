package pl.jowko.rulerank.desktop.feature.ranking;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import pl.jowko.rulerank.desktop.feature.internationalization.LanguageService;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTable;
import pl.jowko.rulerank.desktop.feature.learningtable.LearningTableHelper;
import pl.jowko.rulerank.desktop.feature.tabs.RankingInitializationException;
import pl.jowko.rulerank.feature.customfx.IndexedTableColumn;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.FloatField;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.jowko.rulerank.desktop.feature.internationalization.Labels.RANKING_EVALUATION;
import static pl.jowko.rulerank.desktop.feature.internationalization.Labels.RANKING_POSITION;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COLUMN_WIDTH_L;
import static pl.jowko.rulerank.desktop.feature.settings.RuleRankConst.COLUMN_WIDTH_S;

/**
 * This class creates table from provided .ranking file content and MemoryContainer. <br>
 * It creates rows and columns for ranking table. <br>
 * Table have such format: <br>
 * POSITION | EVALUATION | FIELDS FROM ISF TABLE ... <br>
 *  <br>
 * Created by Piotr on 2018-05-22.
 */
class RankingTableCreator {
	
	private String rankingFileContent;
	private LearningTable isfTable;
	private LearningTableHelper tableHelper;
	
	private LanguageService labels;
	
	private List<TableColumn<RankingRow, ?>> columns;
	private List<RankingRow> items;
	/**
	 * jRS supports saving ranking with starting positions from 0 or 1. <br>
	 * This variable helps to handle both cases
	 */
	private int increment;
	
	/**
	 * Create instance of this class. <br>
	 * Also creates all data for ranking table.
	 * @param rankingFileContent containing .ranking content as String
	 * @param isfTable containing learning data table from .isf file
	 */
	RankingTableCreator(String rankingFileContent, LearningTable isfTable) {
		this.rankingFileContent = rankingFileContent;
		this.isfTable = isfTable;
		
		labels = LanguageService.getInstance();
		tableHelper = new LearningTableHelper();
		increment = Integer.MIN_VALUE;
		
		initializeData();
	}
	
	/**
	 * Gets columns for ranking table <br>
	 * Columns have format: <br>
	 * POSITION | EVALUATION | ATTRIBUTES...
	 * @return list of columns
	 */
	List<TableColumn<RankingRow, ?>> getColumns() {
		return columns;
	}
	
	/**
	 * Gets items(rows) for ranking table. <br>
	 * Items have format: <br>
	 * POSITION | EVALUATION | Fields from examples...
	 * @return items(rows) for table
	 */
	List<RankingRow> getItems() {
		return items;
	}
	
	/**
	 * Initialize columns and rows for ranking table. <br>
	 * Also catch and rethrows some exceptions
	 */
	private void initializeData() {
		try {
			createColumns();
			createRows();
		} catch (NumberFormatException e) {
			throw new RankingInitializationException("Error when parsing numbers: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			throw new RankingInitializationException("Ranking contains indexes of examples that do not exists in learning table. " +
					"Did ranking was created with same data as current learning table?");
		}
	}
	
	/**
	 * Creates columns for ranking table. <br>
	 * First column is positions containing positions extracted from .ranking file. <br>
	 * Second column is evaluation containing evaluations extracted from .ranking file. <br>
	 * Rest of columns is created from learning data table.
	 */
	private void createColumns() {
		columns = new ArrayList<>();
		columns.add(createPositionColumn());
		columns.add(createEvaluationColumn());
		
		List<Attribute> attributes = isfTable.getAttributes();
		for(int i=0; i<attributes.size(); i++) {
			columns.add(createColumn(attributes.get(i), i));
		}
	}
	
	/**
	 * Creates position column
	 * @return position column
	 */
	private IndexedTableColumn<RankingRow, Integer> createPositionColumn() {
		IndexedTableColumn<RankingRow, Integer> column = new IndexedTableColumn<>(labels.get(RANKING_POSITION), 0);
		column.setText(labels.get(RANKING_POSITION));
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getPosition())
		);
		column.setMinWidth(COLUMN_WIDTH_S);
		
		return column;
	}
	
	/**
	 * Creates evaluation column.
	 * @return evaluation column.
	 */
	private IndexedTableColumn<RankingRow, Double> createEvaluationColumn() {
		IndexedTableColumn<RankingRow, Double> column = new IndexedTableColumn<>(labels.get(RANKING_EVALUATION), 1);
		column.setText(labels.get(RANKING_EVALUATION));
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getEvaluation())
		);
		column.setMinWidth(COLUMN_WIDTH_L);
		
		return column;
	}
	
	/**
	 * Create column from provided attribute. <br>
	 * Attribute name is used as column header. <br>
	 * Column have also tooltip containing information about attribute. <br>
	 * When creating column, index for IndexedTableColumn is incremented by 2, because we already created two columns, and index starts from 0.
	 * @param attribute from with column is created
	 * @param columnIndex with is used to extract correct cell from row
	 * @return table column created from attribute
	 */
	private IndexedTableColumn<RankingRow, Object> createColumn(Attribute attribute, int columnIndex) {
		IndexedTableColumn<RankingRow, Object> column = new IndexedTableColumn<>(attribute.getName(), columnIndex+2);
		setCellValueFactory(column, attribute.getInitialValue(), columnIndex);
		column.setMinWidth(COLUMN_WIDTH_L);
		column.setPrefWidth(tableHelper.getColumnPrefWidth(attribute));
		column.setGraphic(tableHelper.getColumnLabel(attribute));
		
		return column;
	}
	
	/**
	 * Sets cell factory for column. <br>
	 * This method check field type for column. <br>
	 * Without converting numbers to number types, sorting for them will not work correctly. <br>
	 * If field type is not number type, column use String to display rows content.
	 * @param column for with cellValueFactory will be created
	 * @param fieldType from attribute
	 * @param columnIndex with is used to extract correct cell from row
	 */
	private void setCellValueFactory(TableColumn<RankingRow, Object> column, Field fieldType, int columnIndex) {
		if(fieldType instanceof IntegerField) {
			column.setCellValueFactory(param -> {
				String value = param.getValue().getCells().get(columnIndex);
				if(value.isEmpty() || "?".equals(value))
					return new ReadOnlyObjectWrapper<>(value);
				return new ReadOnlyObjectWrapper<>(Integer.valueOf(value));
			});
		} else if(fieldType instanceof FloatField) {
			column.setCellValueFactory(param -> {
				String value = param.getValue().getCells().get(columnIndex);
				if(value.isEmpty() || "?".equals(value))
					return new ReadOnlyObjectWrapper<>(value);
				return new ReadOnlyObjectWrapper<>(Double.valueOf(value));
			});
		} else {
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(param.getValue().getCells().get(columnIndex))
			);
		}
	}
	
	/**
	 * Creates rows from provided .ranking file. <br>
	 * Ranking file can have following format: <br>
	 * <br>
	 * 1:	1, 2	10.0 <br>
	 * 2:	5, 7	1.0 <br>
	 * 3:	3	-5.0 <br>
	 * <br>
	 * Where first column is position in ranking, <br>
	 * second contains indexes of objects on this position <br>
	 * and last contains evaluation.
	 */
	private void createRows() {
		items = new ArrayList<>();
		try (Scanner scanner = new Scanner(rankingFileContent)) {
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if(line.isEmpty())
					continue;
				
				createRowsForPosition(line.split("\\s+"));
			}
		}
	}
	
	/**
	 * Creates data for row from one line of file. <br>
	 * Positions and indexes for examples can be indexes from 0 or 1. <br>
	 * Increment variable is used to handle both cases. <br>
	 * New row in table is created for each example. <br>
	 * So if position 2 have 3 examples, three rows will be created with position 2.
	 * @param values from scanner split by whitespaces
	 */
	private void createRowsForPosition(String[] values) {
		int position = Integer.parseInt(values[0].replace(":", ""));
		double evaluation = Double.parseDouble(values[values.length-1]);
		calculateIncrement(position);
		
		for(int i=1; i<values.length - 1; i++) {
			int exampleNumber = Integer.parseInt(values[i].replace(",", ""));
			Field[] fields = isfTable.getExamples().get(exampleNumber - increment).getFields();
			addNewRow(position, evaluation, fields);
		}
	}
	
	/**
	 * Adds new row from provided data.
	 * @param position of row
	 * @param evaluation of row
	 * @param fields extracted from example
	 */
	private void addNewRow(int position, double evaluation, Field[] fields) {
		List<String> cells = new ArrayList<>(fields.length);
		
		for(Field field : fields) {
			cells.add(field.toString());
		}
		
		items.add(new RankingRow(position, evaluation, cells));
	}
	
	/**
	 * Checks from what index data is incremented. <br>
	 * Positions and examples indexes can be indexed from 0 or 1. <br>
	 * This methods checks, if first position starts from 0 or 1. <br>
	 * Integer.MIN_VALUE is used to determine, if increment was already calculated.
	 * @param firstPosition from first line of .ranking file
	 */
	private void calculateIncrement(int firstPosition) {
		if(firstPosition == 0) {
			increment = 0;
		} else if(firstPosition == 1 && increment == Integer.MIN_VALUE) {
			increment = 1;
		}
	}
	
}
