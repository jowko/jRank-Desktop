package pl.jowko.jrank.desktop.feature.ranking;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

/**
 * Created by Piotr on 2018-05-22.
 * Controller for ranking tab and table.
 * It creates table from provided ranking and MemoryContainer.
 */
public class RankingController {
	
	@FXML
	TableView<RankingRow> rankingTable;
	
	/**
	 * Initializes ranking from provided data.
	 * @param rankingFileContent from .ranking file
	 * @param isfTable containing learning data table
	 */
	public void initializeRanking(String rankingFileContent, MemoryContainer isfTable) {
		RankingTableCreator creator = new RankingTableCreator(rankingFileContent, isfTable);
		rankingTable.getColumns().addAll(creator.getColumns());
		rankingTable.getItems().addAll(creator.getItems());
	}
	
}
