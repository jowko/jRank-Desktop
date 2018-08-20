package pl.jowko.rulerank.desktop.feature.runner;

import pl.jowko.rulerank.desktop.exception.RuleRankRuntimeException;

/**
 * This exception is thrown when something goes wrong with running ruleRank experiment. <br>
 *  <br>
 * Created by Piotr on 2018-06-04
 */
public class RunnerException extends RuleRankRuntimeException {
	
	private static final long serialVersionUID = 5847206752826734057L;
	
	public RunnerException(String msg) {
		super(msg);
	}
	
	public RunnerException() {
		super();
	}
	
}
