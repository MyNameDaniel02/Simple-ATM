package assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalendarTests {

     @Test
     public void isBeforeTest() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cFeb = new Calendar(3, 2, 2021);
      Calendar cMar = new Calendar(4, 3, 2021);
      Calendar cJun = new Calendar(3, 6, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearAhead = new Calendar(21, 9, 2022);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // Positive test cases
      assertTrue(cJan.isBefore(cFeb));
      assertTrue(cFeb.isBefore(cMar));
      assertTrue(cMar.isBefore(cDec));
      assertTrue(cJan.isBefore(cDec));
      assertTrue(cYearBehind.isBefore(cYearAhead));
      assertTrue(cYearBehind.isBefore(cJan));

      // Negative test cases
      assertFalse(cJun.isBefore(cJan));
      assertFalse(cFeb.isBefore(cJan));
      assertFalse(cMar.isBefore(cYearBehind));
      assertFalse(cYearAhead.isBefore(cYearBehind));
     }

     @Test
     public void isBeforeNow() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cJun = new Calendar(3, 6, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // These tests will all have to be positive as otherwise in the future these tests will fail due to time passing
      assertTrue(cJan.isBeforeNow());
      assertTrue(cJun.isBeforeNow());
      assertTrue(cYearBehind.isBeforeNow());
     }

     @Test
     public void getDayTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getDay(), 1);
      assertEquals(cApr.getDay(), 10);
      assertEquals(cDec.getDay(), 10);
      assertEquals(cYearBehind.getDay(), 21);
     }

     @Test
     public void getMonthTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getMonth(), 1);
      assertEquals(cApr.getMonth(), 4);
      assertEquals(cDec.getMonth(), 12);
      assertEquals(cYearBehind.getMonth(), 9);
   }

     @Test
     public void getYearTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearAhead = new Calendar(21, 9, 2022);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getYear(), 2021);
      assertEquals(cApr.getYear(), 2021);
      assertEquals(cDec.getYear(), 2021);
      assertEquals(cYearBehind.getYear(), 2020);
      assertEquals(cYearAhead.getYear(), 2022);
     }

     @Test
     public void getMonthNameTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getMonthName(), "Jan");
      assertEquals(cApr.getMonthName(), "Apr");
      assertEquals(cDec.getMonthName(), "Dec");
      assertEquals(cYearBehind.getMonthName(), "Sep");
     }

     @Test
     public void getDateTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getDate(), "01012021");
      assertEquals(cApr.getDate(), "10042021");
      assertEquals(cDec.getDate(), "10122021");
      assertEquals(cYearBehind.getDate(), "21092020");
     }

     @Test
     public void getStringShortTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getStringShort(), "01Jan2021");
      assertEquals(cApr.getStringShort(), "10Apr2021");
      assertEquals(cDec.getStringShort(), "10Dec2021");
      assertEquals(cYearBehind.getStringShort(), "21Sep2020");
     }
     
     @Test
     public void getStringFormattedTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cDec = new Calendar(10, 12, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
      assertEquals(cJan.getStringFormatted(), "01-Jan-2021");
      assertEquals(cApr.getStringFormatted(), "10-Apr-2021");
      assertEquals(cDec.getStringFormatted(), "10-Dec-2021");
      assertEquals(cYearBehind.getStringFormatted(), "21-Sep-2020");
     }

     @Test
     public void addTimeTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cJan2 = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cOct = new Calendar(3, 10, 2021);

      // Positive test cases
      cApr.addTime(3, 1, 0);
      assertEquals(cApr.getDate(), "13052021");

      cOct.addTime(5, 1, 9);
      assertEquals(cOct.getDate(), "08112030");

      // Edge test cases
      cJan.addTime(0, 0, 0);
      assertEquals(cJan.getDate(), "01012021");
      
      cJan.addTime(140, 0, 0);
      assertEquals(cJan.getDate(), "21052021");

      cJan2.addTime(0, 13, 0);
      assertEquals(cJan2.getDate(), "01022022");
     }

     @Test
     public void subtractTimeTests() {
      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cJan2 = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cOct = new Calendar(3, 10, 2021);

      // Positive test cases
      cApr.subtractTime(3, 1, 0);
      assertEquals(cApr.getDate(), "07032021");

      cOct.subtractTime(5, 1, 9);
      assertEquals(cOct.getDate(), "29082012");

      // Edge test cases
      cJan.subtractTime(0, 0, 0);
      assertEquals(cJan.getDate(), "01012021");

      cJan.subtractTime(0, 13, 0);
      assertEquals(cJan.getDate(), "01122020");

      cJan2.subtractTime(140, 0, 0);
      assertEquals(cJan2.getDate(), "14082020");
     }

     @Test
     public void stringToCalendarTests() {

      Calendar cJan = new Calendar(1, 1, 2021);
      Calendar cApr = new Calendar(10, 4, 2021);
      Calendar cOct = new Calendar(3, 10, 2021);
      Calendar cYearBehind = new Calendar(21, 9, 2020);

      // No other possible cases than positive
         
      Calendar cJanEquivalent = Calendar.stringToCalendar("01012021");
      assertEquals(cJanEquivalent.getDate(), cJan.getDate());

      Calendar cOctEquivalent = Calendar.stringToCalendar("03102021");
      assertEquals(cOctEquivalent.getDate(), cOct.getDate());

      Calendar cAprEquivalent = Calendar.stringToCalendar("10042021");
      assertEquals(cAprEquivalent.getDate(), cApr.getDate());

      Calendar cYearBehindEquivalent = Calendar.stringToCalendar("21092020");
      assertEquals(cYearBehindEquivalent.getDate(), cYearBehind.getDate());
     }
     
}