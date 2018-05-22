package pl.jowko.jrank.desktop.feature.ranking;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTableHelper;
import pl.jowko.jrank.desktop.feature.tabs.RankingInitializationException;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;
import pl.poznan.put.cs.idss.jrs.types.Attribute;
import pl.poznan.put.cs.idss.jrs.types.Field;
import pl.poznan.put.cs.idss.jrs.types.FloatField;
import pl.poznan.put.cs.idss.jrs.types.IntegerField;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.jowko.jrank.desktop.feature.internationalization.Labels.RANKING_EVALUATION;
import static pl.jowko.jrank.desktop.feature.internationalization.Labels.RANKING_POSITION;

/**
 * Created by Piotr on 2018-05-22.
 * This class creates table from provided .ranking file content and MemoryContainer.
 * It creates rows and columns for ranking table.
 * Table have such format:
 * POSITION | EVALUATION | FIELDS FROM ISF TABLE ...
 */
class RankingTableCreator {
	
	private String rankingFileContent;
	private MemoryContainer isfTable;
	private LearningTableHelper tableHelper;
	
	private LanguageService labels;
	
	private List<TableColumn<RankingRow, ?>> columns;
	private List<RankingRow> items;
	/**
	 * jRS supports saving ranking with starting positions from 0 or 1.
	 * This variable helps to handle both cases
	 */
	private int increment;
	
	/**
	 * Create instance of this class.
	 * Also creates all data for ranking table.
	 * @param rankingFileContent containing .ranking content as String
	 * @param isfTable containing learning data table from .isf file
	 */
	RankingTableCreator(String rankingFileContent, MemoryContainer isfTable) {
		this.rankingFileContent = rankingFileContent;
		this.isfTable = isfTable;
		
		labels = LanguageService.getInstance();
		tableHelper = new LearningTableHelper();
		increment = Integer.MIN_VALUE;
		
		initializeData();
	}
	
	/**
	 * Gets columns for ranking table
	 * Columns have format:
	 * POSITION | EVALUATION | ATTRIBUTES...
	 * @return list of columns
	 */
	List<TableColumn<RankingRow, ?>> getColumns() {
		return columns;
	}
	
	/**
	 * Gets items(rows) for ranking table.
	 * Items have format:
	 * POSITION | EVALUATION | Fields from examples...
	 * @return items(rows) for table
	 */
	List<RankingRow> getItems() {
		return items;
	}
	
	/**
	 * Initialize columns and rows for ranking table.
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
	 * Creates columns for ranking table.
	 * First column is positions containing positions extracted from .ranking file.
	 * Second column is evaluation containing evaluations extracted from .ranking file.
	 * Rest of columns is created from learning data table.
	 */
	private void createColumns() {
		columns = new ArrayList<>();
		columns.add(createPositionColumn());
		columns.add(createEvaluationColumn());
		
		Attribute[] attributes = isfTable.getAttributes();
		for(int i=0; i<attributes.length; i++) {
			columns.add(createColumn(attributes[i], i));
		}
	}
	
	/**
	 * Creates position column
	 * @return position column
	 */
	private TableColumn<RankingRow, Integer> createPositionColumn() {
		TableColumn<RankingRow, Integer> column = new TableColumn<>(labels.get(RANKING_POSITION));
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getPosition())
		);
		column.setMinWidth(50d);
		
		return column;
	}
	
	/**
	 * Creates evaluation column.
	 * @return evaluation column.
	 */
	private TableColumn<RankingRow, Double> createEvaluationColumn() {
		TableColumn<RankingRow, Double> column = new TableColumn<>(labels.get(RANKING_EVALUATION));
		column.setCellValueFactory(param ->
				new ReadOnlyObjectWrapper<>(param.getValue().getEvaluation())
		);
		column.setMinWidth(50d);
		
		return column;
	}
	
	/**
	 * Create column from provided attribute.
	 * Attribute name is used as column header.
	 * Column have also tooltip containing information about attribute.
	 * @param attribute from with column is created
	 * @param columnIndex with is used to extract correct cell from row
	 * @return table column created from attribute
	 */
	private TableColumn<RankingRow, Object> createColumn(Attribute attribute, int columnIndex) {
		TableColumn<RankingRow, Object> column = new TableColumn<>();
		setCellValueFactory(column, attribute.getInitialValue(), columnIndex);
		column.setMinWidth(50d);
		column.setPrefWidth(tableHelper.getColumnPrefWidth(attribute));
		column.setGraphic(tableHelper.getColumnLabel(attribute));
		
		return column;
	}
	
	/**
	 * Sets cell factory for column.
	 * This method check field type for column.
	 * Without converting numbers to number types, sorting for them will not work correctly.
	 * If field type is not number type, column use String to display rows content.
	 * @param column for with cellValueFactory will be created
	 * @param fieldType from attribute
	 * @param columnIndex with is used to extract correct cell from row
	 */
	private void setCellValueFactory(TableColumn<RankingRow, Object> column, Field fieldType, int columnIndex) {
		if(fieldType instanceof IntegerField) {
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(Integer.valueOf(param.getValue().getCells().get(columnIndex)))
			);
		} else if(fieldType instanceof FloatField) {
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(Double.valueOf(param.getValue().getCells().get(columnIndex)))
			);
		} else {
			column.setCellValueFactory(param ->
					new ReadOnlyObjectWrapper<>(param.getValue().getCells().get(columnIndex))
			);
		}
	}
	
	/**
	 * Creates rows from provided .ranking file.
	 * Ranking file can have following format:
	 *
	 * 1:	1, 2	10.0
	 * 2:	5, 7	1.0
	 * 3:	3	-5.0
	 *
	 * Where first column is position in ranking,
	 * second contains indexes of objects on this position
	 * and last contains evaluation.
	 */
	private void createRows() {
		items = new ArrayList<>();
		Scanner scanner = new Scanner(rankingFileContent);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if(line.isEmpty())
				continue;
			
			createRowsForPosition(line.split("\\s+"));
		}
	}
	
	/**
	 * Creates data for row from one line of file.
	 * Positions and indexes for examples can be indexes from 0 or 1.
	 * Increment variable is used to handle both cases.
	 * New row in table is created for each example.
	 * So if position 2 have 3 examples, three rows will be created with position 2.
	 * @param values from scanner split by whitespaces
	 */
	private void createRowsForPosition(String[] values) {
		int position = Integer.valueOf(values[0].replace(":", ""));
		double evaluation = Double.valueOf(values[values.length-1]);
		calculateIncrement(position);
		
		for(int i=1; i<values.length - 1; i++) {
			int exampleNumber = Integer.valueOf(values[i].replace(",", ""));
			Field[] fields = isfTable.getExample(exampleNumber - increment).getFields();
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
	 * Checks from what index data is incremented.
	 * Positions and examples indexes can be indexed from 0 or 1.
	 * This methods checks, if first position starts from 0 or 1.
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
