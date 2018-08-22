package pl.jowko.rulerank.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import static java.util.Objects.nonNull;

/**
 * Very light custom logging implementation.<br>
 * Can be replaced by any logging framework.<br>
 *<br>
 * Created by Piotr on 2018-05-25<br>
 *
 * @see RuleRankLogger
 */
class CustomLogger {
	
	void info(String msg) {
		log(Level.INFO, null, msg, null);
	}
	
	void info(String category, String msg) {
		log(Level.INFO, category, msg, null);
	}
	
	void warn(String msg) {
		log(Level.WARN, null, msg, null);
	}
	
	void error(String msg) {
		log(Level.ERROR, null, msg, null);
	}
	
	void error(String msg, Throwable throwable) {
		log(Level.ERROR, null, msg, throwable);
	}
	
	void none(String msg) {
		log(Level.NONE, null, msg, null);
	}
	
	private void log(Level level, String category, String message, Throwable throwable) {
		StringBuilder builder = new StringBuilder(256);
		
		Calendar calendar = Calendar.getInstance();
		
		long hours = calendar.get(Calendar.HOUR_OF_DAY);
		long minutes = calendar.get(Calendar.MINUTE);
		long seconds = calendar.get(Calendar.SECOND);
		
		if(hours <= 9)
			builder.append('0');
		builder.append(hours)
				.append(':');
		if (minutes <= 9)
			builder.append('0');
		builder.append(minutes)
				.append(':');
		if (seconds <= 9)
			builder.append('0');
		builder.append(seconds);
		
		switch (level) {
			case ERROR:
				builder.append(" ERROR: ");
				break;
			case WARN:
				builder.append(" WARN: ");
				break;
			case DEBUG:
				builder.append(" DEBUG: ");
				break;
			case INFO:
				builder.append(" INFO: ");
				break;
			default:
				break;
		}
		
		if (nonNull(category)) {
			builder.append('[');
			builder.append(category);
			builder.append("] ");
		}
		
		if(nonNull(message))
			builder.append(message);
		
		if (throwable != null) {
			StringWriter writer = new StringWriter(1024);
			throwable.printStackTrace(new PrintWriter(writer));
			builder.append('\n');
			builder.append(writer.toString().trim());
		}
		
		System.out.println(builder.toString());
	}
	
}
