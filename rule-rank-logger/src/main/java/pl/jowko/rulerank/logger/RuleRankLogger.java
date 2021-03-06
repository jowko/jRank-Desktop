package pl.jowko.rulerank.logger;

/**
 * This class serves as mediator between application and logger.<br>
 * Because of this, logger implementation can be easily changed.<br>
 * All logging should be performed using this class.<br>
 * Ignoring this may result in death penalty.<br>
 *<br>
 * Created by Piotr on 2018-03-17.
 */
public class RuleRankLogger {
	
	private static CustomLogger logger;
	
	static {
		logger = new CustomLogger();
	}
	
	private RuleRankLogger() {}
	
	public static void gen(String msg) {
		logger.info("GENERATOR", msg);
	}
	
	public static void init(String msg) {
		logger.info("INIT", msg);
	}
	
	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void info(String category, String msg) {
		logger.info(category, msg);
	}
	
	public static void warn(String msg) {
		logger.warn(msg);
	}
	
	public static void error(String msg, Throwable throwable) {
		logger.error(msg, throwable);
	}
	
	public static void error(String msg) {
		logger.error(msg);
	}
	
	/**
	 * None level serves to log message without log level text.
	 * It is used to log output from ruleRank with have logging level in messages.
	 * @param msg to log
	 */
	public static void none(String msg) {
		logger.none(msg);
	}
	
}
