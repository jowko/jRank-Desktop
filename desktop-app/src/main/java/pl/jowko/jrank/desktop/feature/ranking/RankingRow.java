package pl.jowko.jrank.desktop.feature.ranking;

import java.util.List;

/**
 * Created by Piotr on 2018-05-22.
 * This class serves as container for data in ranking tab.
 */
class RankingRow {
	
	private int position;
	private double evaluation;
	private List<String> cells;
	
	/**
	 * Creates instance of this class.
	 * @param position of row in ranking
	 * @param evaluation score
	 * @param cells from MemoryContainer containing data from learning table
	 */
	RankingRow(int position, double evaluation, List<String> cells) {
		this.position = position;
		this.evaluation = evaluation;
		this.cells = cells;
	}
	
	int getPosition() {
		return position;
	}
	
	double getEvaluation() {
		return evaluation;
	}
	
	List<String> getCells() {
		return cells;
	}
	
}
