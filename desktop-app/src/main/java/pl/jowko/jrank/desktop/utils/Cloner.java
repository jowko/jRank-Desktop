package pl.jowko.jrank.desktop.utils;

import pl.jowko.jrank.logger.JRankLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Piotr on 2018-05-04.
 */
public class Cloner {
	
	private Cloner() {}
	
	/**
	 * This method makes a "deep clone" of any Java object it is given.
	 * https://alvinalexander.com/java/java-deep-clone-example-source-code
	 */
	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception e) {
			JRankLogger.error("Error when making deep copy of object: " + e);
			return null;
		}
	}
	
}
