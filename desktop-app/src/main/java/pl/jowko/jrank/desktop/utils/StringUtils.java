package pl.jowko.jrank.desktop.utils;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.jowko.jrank.desktop.utils.BooleanUtils.not;

/**
 * Created by Piotr on 2018-05-05.
 */
public class StringUtils {
	
	private StringUtils() {}
	
	public static boolean isNotNullOrEmpty(String value) {
		return nonNull(value) && not(value.isEmpty()) && not(value.trim().isEmpty());
	}
	
	public static boolean isNullOrEmpty(String value) {
		return isNull(value) || value.isEmpty() || value.trim().isEmpty();
	}
	
}
