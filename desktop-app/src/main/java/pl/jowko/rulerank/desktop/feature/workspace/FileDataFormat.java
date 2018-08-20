package pl.jowko.rulerank.desktop.feature.workspace;

import java.io.File;
import java.io.Serializable;

/**
 * This class is used in copy and paste action in workspace tree. <br>
 * It is saved in user ClipBoard. <br>
 *  <br>
 * Created by Piotr on 2018-06-10
 */
class FileDataFormat implements Serializable {
	
	private static final long serialVersionUID = 8474915788917219946L;
	
	private File file;
	private boolean isCut;
	
	/**
	 * Creates instance of this class
	 * @param file to store
	 * @param isCut determines, if was cut or copy action
	 */
	FileDataFormat(File file, boolean isCut) {
		this.file = file;
		this.isCut = isCut;
	}
	
	File getFile() {
		return file;
	}
	
	boolean isCut() {
		return isCut;
	}
	
}
