package assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

class EngineTests {
    // Unit test functions for all methods and workings of the Engine class

    /*
    Below are tests on the importData(int atmNum) method
    */
    @Test
    public void validAtmImport() {
        Engine engine = new Engine();

        ATM atm1 = engine.importData("1");
        ATM atm2 = engine.importData("5");

        assertEquals(atm1.getAtmID(), "1");
        assertEquals(atm1.getTransactionNum(), 0);

        assertEquals(atm2.getAtmID(), "5");
        assertEquals(atm2.getTransactionNum(), 0);

        int[] denoms = new int[]{5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000};

        for (int i = 0; i < denoms.length; i++) {
            assertEquals(atm1.getDenominations().get(denoms[i]), 500);
        }

        for (int i = 0; i < denoms.length; i++) {
            assertEquals(atm2.getDenominations().get(denoms[i]), 0);
        }
    }

    @Test
    public void invalidAtmImport() {
        Engine engine = new Engine();

        assertNull(engine.importData("10000"));
    }

    @Test
    public void atmInfoTest() {
        Engine engine = new Engine();

        String[] tests = new String[]{"Hello World", "asdasdas", "37493274923874932493274987237498234", ""};

        for (int i = 0; i < tests.length; i++) {
            engine.setATMInfo(tests[i]);
            assertEquals(engine.getATMInfo(), tests[i]);
        }

    }

    @Test
    public void atmTest() {
        Engine engine = new Engine();

        ATM atm1 = engine.importData("1");
        ATM atm2 = engine.importData("5");

        engine.setATM(atm1);
        assertEquals(engine.getATM(), atm1);

        engine.setATM(atm2);
        assertEquals(engine.getATM(), atm2);
    }

    @Test
    public void cardInfoTest() {
        Engine engine = new Engine();

        String[] tests = new String[]{"Hello World", "asdasdas", "37493274923874932493274987237498234", ""};

        for (int i = 0; i < tests.length; i++) {
            engine.setCardInfo(tests[i]);
            assertEquals(engine.getCardInfo(), tests[i]);
        }
    }

    @Test
    public void cardTest() {
        Engine engine = new Engine();

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 3);

        Card cValid = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cExpired = new Card("testUser3", "00003", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);
        Card cStolen = new Card("testUser6", "00006", "1234", 100, false, "stolen", false, calYearBehind, calThreeYearsAhead);

        engine.setCard(cValid);
        assertEquals(engine.getCard(), cValid);

        engine.setCard(cExpired);
        assertEquals(engine.getCard(), cExpired);

        engine.setCard(cStolen);
        assertEquals(engine.getCard(), cStolen);
    }

    @Test
    public void cardValidationTest() {
        String cValid = "01342";
        String cExpired = "60206";
        String cStolen = "73902";
        String cNonCurrent = "34845";

        Engine engine = new Engine();
        engine.importData("1");

        assertTrue(engine.cardValidation(cValid));
        assertFalse(engine.cardValidation(cExpired));
        assertTrue(engine.cardValidation(cStolen));
        assertFalse(engine.cardValidation(cNonCurrent));
    }
    
    @Test
    public void statusCheckTest() {
        Engine engine = new Engine();

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 3);

        Card cValid = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cExpired = new Card("testUser3", "00003", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);
        Card cStolen = new Card("testUser6", "00006", "1234", 100, false, "stolen", false, calYearBehind, calThreeYearsAhead);
        Card cNonCurrent = new Card("testUser8", "00009", "1234", 100, false, "stolen", false, calYearAhead, calThreeYearsAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(cValid);
        cards.add(cExpired);
        cards.add(cStolen);
        cards.add(cNonCurrent);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(5, 1000);
        denom.put(10, 1000);
        denom.put(20, 1000);
        denom.put(50, 1000);
        denom.put(100, 1000);
        denom.put(200, 1000);
        denom.put(500, 1000);
        denom.put(1000, 1000);
        denom.put(2000, 1000);
        denom.put(5000, 1000);
        denom.put(10000, 1000);
        
        ATM atm1 = new ATM("1", 1, cards, denom);

        engine.setATM(atm1);
        engine.setATMInfo(atm1.toString());

        engine.setCard(cValid);
        engine.setCardInfo(cValid.toString());
        assertTrue(engine.statusCheck(cValid));

        engine.setCard(cStolen);
        engine.setCardInfo(cStolen.toString());
        assertFalse(engine.statusCheck(cStolen));
    }
    
    @Test
    public void withdrawTest() {
        Engine engine = new Engine();

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        int valid1 = 100;
        int valid2 = 20000;
        int valid3 = 0;

        int invalid1 = 7;
        int invalid2 = -5;

        Card cValid = new Card("testUser1", "00001", "1234", 1000000000, false, "safe", false, calYearBehind, calYearAhead);
        Card cInvalid = new Card("testUser1", "00001", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(cValid);
        cards.add(cInvalid);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(5, 1000);
        denom.put(10, 1000);
        denom.put(20, 1000);
        denom.put(50, 1000);
        denom.put(100, 1000);
        denom.put(200, 1000);
        denom.put(500, 1000);
        denom.put(1000, 1000);
        denom.put(2000, 1000);
        denom.put(5000, 1000);
        denom.put(10000, 1000);
        
        // 0 = Invalid monetary input
        // 1 = Invalid card or invalid amount relative to card balance
        // 2 = Good

        ATM atm1 = new ATM("1", 1, cards, denom);
        engine.setATMInfo(atm1.toString());
        engine.setATM(atm1);

        engine.setCard(cValid);
        engine.setCardInfo(cValid.toString());
        assertEquals(engine.withdrawFunction(valid1, cValid), 2);
        assertEquals(engine.withdrawFunction(valid2, cValid), 2);
        assertEquals(engine.withdrawFunction(valid3, cValid), 1);

        engine.setCard(cInvalid);
        engine.setCardInfo(cInvalid.toString());
        assertEquals(engine.withdrawFunction(valid1, cInvalid), 1);
        assertEquals(engine.withdrawFunction(valid2, cInvalid), 1);
        assertEquals(engine.withdrawFunction(valid3, cInvalid), 1);

        engine.setCard(cValid);
        engine.setCardInfo(cValid.toString());
        assertEquals(engine.withdrawFunction(invalid1, cValid), 0);
        assertEquals(engine.withdrawFunction(invalid2, cValid), 0);

        engine.setCard(cInvalid);
        engine.setCardInfo(cInvalid.toString());
        assertEquals(engine.withdrawFunction(invalid1, cInvalid), 0);
        assertEquals(engine.withdrawFunction(invalid2, cInvalid), 0);
    }

}