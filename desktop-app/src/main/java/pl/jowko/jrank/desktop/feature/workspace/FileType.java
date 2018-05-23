package pl.jowko.jrank.desktop.feature.workspace;

/**
 * Created by Piotr on 2018-04-21.
 * This enum helps to recognize file type of workspace item from workspace tree
 *
 * Values represent:
 * COMPARISION_TABLE - partial comparision table - partialPCT.isf files
 * LEARNING_TABLE - learning or test data table - .isf files
 * APPROXIMATION - .apx files
 * DIRECTORY - catalog
 * GRAPH - graph file - .graph files
 * JRANK_SETTINGS - properties file with settings for ruleRank experiment - .properties files
 * RANKING - file containing ranking of examples - .ranking files
 * ROOT - main catalog in with all experiments are located. See workspacePath setting in UserSettings
 * RULES - file containing rules induced in experiment - .rules files
 * TEXT - simple text files - .txt files
 * UNKNOWN - all other files
 *
 * @see WorkspaceItem
 * @see pl.jowko.jrank.desktop.feature.settings.UserSettings
 */
public enum FileType {
	
	COMPARISION_TABLE,
	LEARNING_TABLE,
	APPROXIMATION,
	DIRECTORY,
	GRAPH,
	JRANK_SETTINGS,
	RANKING,
	ROOT,
	RULES,
	TEXT,
	UNKNOWN

}
