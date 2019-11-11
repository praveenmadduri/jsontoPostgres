package com.pratice;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeFileLastModifiedDate {
	public static void main(String[] args) {
        try {
        	final String filepath = "E:\\Report_Sonar\\New Text Document (3).txt";
            File file = new File(filepath);
 
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
 
            // print the original Last Modified date
            System.out.println("Original Last Modified Date : "
                    + dateFormat.format(file.lastModified()));
 
            // set this date
            String newLastModifiedString = "01/31/2019";
 
            // we have to convert the above date to milliseconds...
            Date newLastModifiedDate = dateFormat.parse(newLastModifiedString);
            file.setLastModified(newLastModifiedDate.getTime());
 
            // print the new Last Modified date
            System.out.println("Lastest Last Modified Date : " + dateFormat.format(file.lastModified()));
 
        } catch (ParseException e) {
 
            e.printStackTrace();
 
        }
 
    }
 

}
