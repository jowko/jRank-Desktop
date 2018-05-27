package pl.jowko.jrank.desktop.feature.ranking;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import pl.jowko.jrank.desktop.feature.internationalization.Labels;
import pl.jowko.jrank.desktop.feature.internationalization.LanguageService;
import pl.jowko.jrank.desktop.feature.learningtable.LearningTable;
import pl.jowko.jrank.desktop.feature.learningtable.UnknownFieldValidator;
import pl.jowko.jrank.desktop.feature.tabs.TabInitializationException;
import pl.jowko.jrank.desktop.service.DialogsService;
import pl.poznan.put.cs.idss.jrs.core.mem.MemoryContainer;

import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

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
	public void initializeRanking(String rankingFileContent, MemoryContainer isfTable) throws TabInitializationException {
		LearningTable table = new LearningTable(isfTable);
		checkIfTableHasUnknownFields(table);
		RankingTableCreator creator = new RankingTableCreator(rankingFileContent, table);
		rankingTable.getColumns().addAll(creator.getColumns());
		rankingTable.getItems().addAll(creator.getItems());
	}
	
	/**
	 * If table contains unknown fields, it means that ranking is not up to date with learning table.
	 * To generate ranking, all non decision fields in ranking should be known.
	 */
	private void checkIfTableHasUnknownFields(LearningTable table) throws TabInitializationException {
		UnknownFieldValidator validator = new UnknownFieldValidator(table);
		
		if(not(validator.isValid())) {
			LanguageService labels = LanguageService.getInstance();
			String msg = labels.get(Labels.RANKING_UNKNOWN_FIELDS);
			DialogsService.showActionFailedDialog(msg);
			
			throw new TabInitializationException(msg);
		}
	}
	
}
