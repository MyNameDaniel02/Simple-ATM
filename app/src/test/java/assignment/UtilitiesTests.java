package assignment;

import java.util.Calendar;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTests {

// Unit test functions for all methods and workings of the Utilities class\
    @Test
    public void printMoneyTests() {
        // Positive test cases
        assertEquals(Utilities.printMoney(100), "$1.00");
        assertEquals(Utilities.printMoney(10), "$0.10");
        assertEquals(Utilities.printMoney(3323432), "$33234.32");

        // Negative test cases
        assertEquals(Utilities.printMoney(-100), "Error");
        assertEquals(Utilities.printMoney(-1), "Error");

        // Edge test cases
        assertEquals(Utilities.printMoney(0), "$0.00");
    }

    @Test
    public void timeDayTests() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if (hour >= 0 && hour < 12) {
            assertEquals(Utilities.timeDay(), "Good Morning"); 
        } else if (hour >= 12 && hour < 17){
            assertEquals(Utilities.timeDay(), "Good Afternoon");
        } else if (hour >= 17 && hour < 24){
            assertEquals(Utilities.timeDay(), "Good Evening");
        }
    }

    @Test
    public void readFileTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path atmPath = Paths.get(currentPath.toString(), "src", "main", "java", "assignment","database", "atm.csv");
        String atmDatabase = atmPath.toString();

        Path cardPath = Paths.get(currentPath.toString(), "src", "main", "java", "assignment","database", "card.csv");
        String cardDatabase = cardPath.toString();

        ArrayList<String> cardExpected = new ArrayList<String>();
        ArrayList<String> atmExpected = new ArrayList<String>();

        cardExpected.add("Harry Kane,24345,2634,100000,false,safe,false,12112021,12112024");
        cardExpected.add("Gareth Bale,01342,2364,100000,false,safe,false,01092020,01092023");
        cardExpected.add("Heung Min Son,35346,9585,100000,false,safe,false,01032019,01032022");
        cardExpected.add("Hugo Lloris,29485,2374,100000,false,missing,false,28072021,28072024");
        cardExpected.add("Dele Alli,02567,1234,100000,false,safe,false,30112018,30112021");
        cardExpected.add("Lucas Moura,73902,3643,100000,false,stolen,false,15102019,15102022");
        cardExpected.add("Steve Bergwijn,34745,1254,100000,false,safe,false,04072019,04072022");
        cardExpected.add("Daniel Levy,17456,7456,100000,false,safe,false,13072020,13072023");
        cardExpected.add("Japhet Tanganga,34845,2346,100000,false,safe,false,15102021,15102024");
        cardExpected.add("Sergio Reguilon,13456,1273,100000,false,safe,false,09112020,09112023");
        cardExpected.add("Pierre Emile Hojbjerg,00000,0000,100000,false,safe,false,01012021,01012024");
        cardExpected.add("Admin Malpass,01010,0101,1000000,true,safe,false,12092021,12092024");
        cardExpected.add("Elliot Rotenstein,60206,0602,602602,false,stolen,false,01012019,01012021");
        cardExpected.add("William Douglas Walter Gun,42069,3825,0,false,stolen,false,13072020,13072023");

        atmExpected.add("1,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("2,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("3,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("4,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("5,0,0,0,0,0,0,0,0,0,0,0,0");
        
        try {
            ArrayList<String> cardActual = Utilities.readFile(cardDatabase);
            for (int i = 0; i < cardExpected.size(); i++) {
                assertEquals(cardActual.get(i).trim(), cardExpected.get(i).trim());
            }
            
        } catch (FileNotFoundException e) {}
        
        try {
            ArrayList<String> atmActual = Utilities.readFile(atmDatabase);
            for (int i = 0; i < atmExpected.size(); i++) {
                assertEquals(atmActual.get(i).trim(), atmExpected.get(i).trim());
            }

        } catch (FileNotFoundException e) {}
    }

    @Test
    public void exporterTest() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path atmPath = Paths.get(currentPath.toString(), "src", "test", "java", "assignment","testDatabase", "testAtm.csv");
        String atmDatabase = atmPath.toString();

        Path cardPath = Paths.get(currentPath.toString(), "src", "test", "java", "assignment","testDatabase", "testCard.csv");
        String cardDatabase = cardPath.toString();
        
        String cPrev1 = "Harry Kane,24345,2634,100000,false,safe,false,12112021,12112024";
        String cAfter1 = "Harry Smith,24345,2634,100000,false,stolen,false,12112021,12112024";

        String cPrev2 = "Japhet Tanganga,34845,2346,100000,false,safe,false,15102021,15102024";
        String cAfter2 = "Japhet Tanganga,522,988,0,false,safe,false,15102021,15102024";

        String aPrev1 = "3,0,500,500,500,500,500,500,500,500,500,500,500";
        String aAfter1 = "3,600,500,4,500,4,500,4,500,4,500,4,500";

        String aPrev2 = "5,0,500,500,500,500,500,500,500,500,500,500,500";
        String aAfter2 = "10,10,10,10,10,10,10,10,10,10,10,10,10";

        ArrayList<String> cardExpected = new ArrayList<String>();
        ArrayList<String> atmExpected = new ArrayList<String>();

        cardExpected.add("Harry Smith,24345,2634,100000,false,stolen,false,12112021,12112024");
        cardExpected.add("Gareth Bale,01342,2364,100000,false,safe,false,01092020,01092023");
        cardExpected.add("Heung Min Son,35346,9585,100000,false,safe,false,01032019,01032022");
        cardExpected.add("Hugo Lloris,29485,2374,100000,false,missing,false,28072021,28072024");
        cardExpected.add("Dele Alli,02567,1234,100000,false,safe,false,30112018,30112021");
        cardExpected.add("Lucas Moura,73902,3643,100000,false,stolen,false,15102019,15102022");
        cardExpected.add("Steve Bergwijn,34745,1254,100000,false,safe,false,04072019,04072022");
        cardExpected.add("Daniel Levy,17456,7456,100000,false,safe,false,13072020,13072023");
        cardExpected.add("Japhet Tanganga,522,988,0,false,safe,false,15102021,15102024");
        cardExpected.add("Sergio Reguilon,13456,1273,100000,false,safe,false,09112020,09112023");
        cardExpected.add("Pierre Emile Hojbjerg,00000,0000,100000,false,safe,false,01012021,01012024");
        cardExpected.add("Admin Malpass,01010,0101,1000000,true,safe,false,12092021,12092024");
        cardExpected.add("Elliot Rotenstein,60206,0602,602602,false,stolen,false,01012018,01012021");
        cardExpected.add("William Douglas Walter Gun,42069,3825,0,false,stolen,false,13072020,13072023");

        atmExpected.add("1,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("2,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("3,600,500,4,500,4,500,4,500,4,500,4,500");
        atmExpected.add("4,0,500,500,500,500,500,500,500,500,500,500,500");
        atmExpected.add("10,10,10,10,10,10,10,10,10,10,10,10,10");

        Utilities.exporter(atmDatabase, aPrev1, aAfter1, false);
        Utilities.exporter(atmDatabase, aPrev2, aAfter2, false);

        try {
            ArrayList<String> atmActual = Utilities.readFile(atmDatabase);
            for (int i = 0; i < atmActual.size(); i++) {
                assertEquals(atmActual.get(i).trim(), atmExpected.get(i).trim());
            }
        } catch (FileNotFoundException e) {}

        Utilities.exporter(cardDatabase, cPrev1, cAfter1, false);
        Utilities.exporter(cardDatabase, cPrev2, cAfter2, false);

        try {
            ArrayList<String> cardActual = Utilities.readFile(cardDatabase);
            for (int i = 0; i < cardActual.size(); i++) {
                assertEquals(cardActual.get(i).trim(), cardExpected.get(i).trim());
            }
        } catch (FileNotFoundException e) {}

    }

}