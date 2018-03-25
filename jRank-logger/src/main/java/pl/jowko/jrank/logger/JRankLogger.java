package pl.jowko.jrank.logger;

import com.esotericsoftware.minlog.Log;

/**
 * Created by Piotr on 2018-03-17.
 * This class serves as mediator between application and logger.
 */
public class JRankLogger {
	
	private JRankLogger() {}
	
	public static void gen(String msg) {
		Log.info("GENERATOR", msg);
	}
	
	public static void init(String msg) {
		Log.info("INIT", msg);
	}
	
	public static void initialized(String name) {
		Log.info(name + " initialized.");
	}
	
	public static void closed(String name) {
		Log.info(name + " closed.");
	}
	
	public static void info(String msg) {
		Log.info(msg);
	}
	
	public static void info(String category, String msg) {
		Log.info(category, msg);
	}
	
	public static void error(String msg, Throwable throwable) {
		Log.error(msg, throwable);
	}
	
}
