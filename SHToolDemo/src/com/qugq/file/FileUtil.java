package com.qugq.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeFile(String data) {

	try {
	    File file = new File("javaio-appendfile.txt");
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    // true = append file
	    FileWriter fileWritter = new FileWriter(file.getName(), true);
	    fileWritter.write(data);
	    fileWritter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
