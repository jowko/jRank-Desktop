package pl.jowko.jrank.desktop.feature.runner;

import pl.jowko.jrank.desktop.exception.JRankRuntimeException;

/**
 * Created by Piotr on 2018-06-04
 * This exception is thrown when something goes wrong with running ruleRank experiment.
 */
public class RunnerException extends JRankRuntimeException {
	
	private static final long serialVersionUID = 5847206752826734057L;
	
	public RunnerException(String msg) {
		super(msg);
	}
	
	public RunnerException() {
		super();
	}
	
}
