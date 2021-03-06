package pl.jowko.rulerank.desktop.feature.ranking;

import java.util.List;

/**
 * This class serves as container for data in ranking tab. <br>
 *  <br>
 * Created by Piotr on 2018-05-22.
 */
class RankingRow {
	
	private int position;
	private Double evaluation;
	private List<String> cells;
	
	/**
	 * Creates instance of this class.
	 * @param position of row in ranking
	 * @param evaluation score
	 * @param cells from MemoryContainer containing data from learning table
	 */
	RankingRow(int position, Double evaluation, List<String> cells) {
		this.position = position;
		this.evaluation = evaluation;
		this.cells = cells;
	}
	
	int getPosition() {
		return position;
	}
	
	Double getEvaluation() {
		return evaluation;
	}
	
	List<String> getCells() {
		return cells;
	}
	
}
