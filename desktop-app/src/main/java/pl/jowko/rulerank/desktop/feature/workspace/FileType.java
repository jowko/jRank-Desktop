package pl.jowko.rulerank.desktop.feature.workspace;

/**
 * This enum helps to recognize file type of workspace item from workspace tree <br>
 * <br>
 * Values represent: <br>
 * ISF_TABLE - learning or test data table - .isf files <br>
 * APPROXIMATION - .apx files <br>
 * DIRECTORY - catalog <br>
 * GRAPH - graph file - .graph files <br>
 * PROPERTIES - properties file with settings for ruleRank experiment - .properties files <br>
 * RANKING - file containing ranking of examples - .ranking files <br>
 * ROOT - main catalog in with all experiments are located. See workspacePath setting in UserSettings <br>
 * RULES - file containing rules induced in experiment - .rules files <br>
 * TEXT - simple text files - .txt files <br>
 * UNKNOWN - all other files <br>
 * <br>
 * Created by Piotr on 2018-04-21.
 * @see WorkspaceItem
 * @see pl.jowko.rulerank.desktop.feature.settings.UserSettings
 */
public enum FileType {
	
	ISF_TABLE,
	APPROXIMATION,
	DIRECTORY,
	GRAPH,
	PROPERTIES,
	RANKING,
	ROOT,
	RULES,
	TEXT,
	UNKNOWN

}
