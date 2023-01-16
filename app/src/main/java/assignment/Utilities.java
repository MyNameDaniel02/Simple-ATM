package assignment;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Calendar;
import java.text.ParseException;
/**
 * Utilities class contains helper methods for other classes.
 */
public class Utilities {
    /**
     * Used in Engine.importData() in order to extract all relevant information
     * @return the string format of a database, where each line is a separate string
     * in an ArrayList
     * @param database represents the chosen database to be parsed
     */
    public static ArrayList<String> readFile(String database) throws FileNotFoundException {
        File dataFile = new File(database);
        Scanner reader = new Scanner(dataFile);
        ArrayList<String> lines = new ArrayList<String>();
        String skip = reader.nextLine();
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            lines.add(data);
        }
        reader.close();
        return lines;
    }
    /**
     * Converts cents to client friendly format of money for printing
     * @return String formatted as $D.CC
     * @param amount is the software representation of money in cents 
     */
    public static String printMoney(int amount) {
        if (amount < 0) {
            return "Error";
        }
        int cents = amount % 100;
        int dollars = (amount - cents) / 100;

        return "$" + dollars + "." + String.format("%02d", cents);
    }

    /**
     * Used in the command line interface when greeting the client
     * @return greeting associated with the specific time of day
     */
    public static String timeDay() {
        Calendar cal = Calendar.getInstance();
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);
        String greeting = null;
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greeting = "Good Morning";        
        } else if(timeOfDay >= 12 && timeOfDay < 17){
            greeting = "Good Afternoon";
        } else if(timeOfDay >= 17 && timeOfDay < 24){
            greeting = "Good Evening";
        }
        return greeting;
    }

    /**
     * Used to facilitate the exportData() function - overrites a given database 
     * with updated information post-transaction (or restocking/updated card status)
     * @param database the database to be updated
     * @param previousInfo the specific entry to be updated prior to transaction
     * @param currentInfo the specific entry to be updated with post transaction information
     */
    public static void exporter(String database, String previousInfo, String currentInfo, Boolean swallowed) {
        try {
            File Data = new File(database);
            Scanner sc = new Scanner(Data);
            StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine() + System.lineSeparator());
            }
            String contents = sb.toString();
            sc.close();
            if (swallowed) {
                contents = contents.replaceAll(previousInfo + "\n", "");
            } else {
                contents = contents.replaceAll(previousInfo, currentInfo);
            }
            FileWriter fw = new FileWriter(database);
            fw.append(contents);
            fw.flush();
        } catch (IOException e) {
            System.out.println("Database not found!");
        }
    }
}