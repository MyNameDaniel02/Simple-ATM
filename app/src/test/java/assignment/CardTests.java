package assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTests {
    // Unit test functions for all methods and workings of the card class

    /* Methods to cover:
        isValid(); returns bool                 [COMPLETE]
        isMissing(); returns bool               [COMPLETE]
        isStolen(); returns bool                [COMPLETE]
        isSafe(); returns bool                  [COMPLETE]
        isNotExpired(); returns bool            [COMPLETE]
        isCurrent(); returns bool               [COMPLETE]
        getIssueString(); returns String        [COMPLETE]
        getExpiryString(); returns String       [COMPLETE]
        toString(); returns String              [COMPLETE]
    */

    /**
    * Below are tests on the isValid() method
    */
    @Test
    public void cardIsValid() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        // Different cards, all valid, simply changing issue date & expiry, as well as different admin status
        // as well as different spellings of safe
        Card cValid1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cValid2 = new Card("testUser4", "00004", "1234", 100, true, "saFe", true, calYearBehind, calYearAhead);
        Card cValid3 = new Card("testUser5", "00005", "1234", 100, false, "SAFE", false, calYearBehind, calYearAhead);

        assertTrue(cValid1.isValid());
        assertTrue(cValid2.isValid());
        assertTrue(cValid3.isValid());

    }

    @Test
    public void cardIsInvalid() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 3);

        Calendar calDayBehind = new Calendar();
        calYearBehind.subtractTime(1, 0, 0);

        Calendar calDayAhead = new Calendar();
        calYearBehind.subtractTime(1, 0, 0);
            
        Calendar calNow = new Calendar();

        // Card with non current +1 year
        Card cNonCurrent1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calThreeYearsAhead);
        
        // Card with non current +3 year
        Card cNonCurrent2 = new Card("testUser2", "00002", "1234", 100, false, "safe", false, calThreeYearsAhead, calThreeYearsAhead);
        
        // Card which expired a year ago
        Card cExpired = new Card("testUser3", "00003", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);

        // Card which is lost (spelled lowercase)
        Card cLost1 = new Card("testUser4", "00004", "1234", 100, false, "lost", false, calYearBehind, calThreeYearsAhead);

        // Card which is lost (spelled mixed case)
        Card cLost2 = new Card("testUser5", "00005", "1234", 100, false, "LoSt", false, calYearBehind, calThreeYearsAhead);
        
        // Card which is stolen (spelled lowercase)
        Card cStolen1 = new Card("testUser6", "00006", "1234", 100, false, "stolen", false, calYearBehind, calThreeYearsAhead);

        // Card which is stolen (spelled mixed case)
        Card cStolen2 = new Card("testUser7", "00007", "1234", 100, false, "sToLeN", false, calYearBehind, calThreeYearsAhead);

        assertFalse(cNonCurrent1.isValid());
        assertFalse(cNonCurrent2.isValid());
        assertFalse(cExpired.isValid());
        assertFalse(cLost1.isValid());
        assertFalse(cLost2.isValid());
        assertFalse(cStolen1.isValid());
        assertFalse(cStolen2.isValid());
    }

    @Test
    public void cardValidityEdgeCases() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calNow = new Calendar();

        // Card is issued in 3 years but expires in one (non current & expired)
        Card cExpiredNonCurrent = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calThreeYearsAhead, calYearAhead);
        
        assertFalse(cExpiredNonCurrent.isValid());
    }


    @Test
    public void isMissingTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card cardNotMissing = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cardNotMissing2 = new Card("testUser2", "00002", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);

        Card cardMissing = new Card("testUser3", "00003", "1234", 100, false, "missing", false, calYearBehind, calYearAhead);
        Card cardMissing2 = new Card("testUser4", "00004", "1234", 100, false, "MISSING", false, calYearBehind, calYearAhead);

        Card cardAdminMissing = new Card("testUser5", "00005", "1234", 100, true, "missing", false, calYearBehind, calYearAhead);
        Card cardAdminMissing2 = new Card("testUser6", "00006", "1234", 100, true, "Missing", false, calYearBehind, calYearAhead);

        assertFalse(cardNotMissing.isMissing());
        assertFalse(cardNotMissing2.isMissing());

        assertTrue(cardMissing.isMissing());
        assertTrue(cardMissing2.isMissing());

        assertTrue(cardAdminMissing.isMissing());
        assertTrue(cardAdminMissing2.isMissing());
    }

    @Test
    public void isStolenTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card cardNotStolen = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cardNotStolen2 = new Card("testUser2", "00002", "1234", 100, false, "missing", false, calYearBehind, calYearAhead);

        Card cardStolen = new Card("testUser3", "00003", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);
        Card cardStolen2 = new Card("testUser4", "00004", "1234", 100, false, "STOLEN", false, calYearBehind, calYearAhead);

        Card cardAdminStolen = new Card("testUser5", "00005", "1234", 100, true, "stolen", false, calYearBehind, calYearAhead);
        Card cardAdminStolen2 = new Card("testUser6", "00006", "1234", 100, true, "sTolEn", false, calYearBehind, calYearAhead);

        assertFalse(cardNotStolen.isStolen());
        assertFalse(cardNotStolen2.isStolen());

        assertTrue(cardStolen.isStolen());
        assertTrue(cardStolen2.isStolen());

        assertTrue(cardAdminStolen.isStolen());
        assertTrue(cardAdminStolen2.isStolen());
    }

    @Test
    public void isSafeTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card cardSafe = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card cardSafe2 = new Card("testUser2", "00002", "1234", 100, false, "SAFe", false, calYearBehind, calYearAhead);

        Card cardStolen = new Card("testUser3", "00003", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);
        Card cardStolen2 = new Card("testUser4", "00004", "1234", 100, false, "STOLEN", false, calYearBehind, calYearAhead);

        Card cardMissing = new Card("testUser5", "00005", "1234", 100, true, "stolen", false, calYearBehind, calYearAhead);
        Card cardMissing2 = new Card("testUser6", "00006", "1234", 100, true, "sTolEn", false, calYearBehind, calYearAhead);
        
        Card cardBlockedSafe = new Card("testUse7", "00007", "1234", 100, false, "safe", true, calYearBehind, calYearAhead);
        Card cardBlockedUnsafe = new Card("testUser8", "00008", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);

        assertTrue(cardSafe.isSafe());
        assertTrue(cardSafe2.isSafe());

        assertFalse(cardStolen.isSafe());
        assertFalse(cardStolen2.isSafe());

        assertFalse(cardMissing.isSafe());
        assertFalse(cardMissing2.isSafe());

        assertTrue(cardBlockedSafe.isSafe());
        assertFalse(cardBlockedUnsafe.isSafe());
    }

    @Test
    public void isNotExpiredTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calThreeYearsBehind.subtractTime(0, 0, 3);

        Calendar calDayBehind = new Calendar();
        calDayBehind.subtractTime(1, 0, 0);

        Calendar calDayAhead = new Calendar();
        calDayAhead.subtractTime(1, 0, 0);
            
        // Card with non current +1 year
        Card cNonCurrent1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calThreeYearsAhead);
        
        // Card with non current +1 day
        Card cNonCurrent2 = new Card("testUser2", "00002", "1234", 100, false, "safe", false, calDayAhead, calThreeYearsAhead);
        
        // Card which expired a year ago
        Card cExpired = new Card("testUser3", "00003", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);

        // Card which is not expired
        Card cCurrent = new Card("testUser4", "00004", "1234", 100, false, "safe", false, calYearBehind, calThreeYearsAhead);

        // Card which is not expired 2
        Card cCurrent2 = new Card("testUser5", "00005", "1234", 100, false, "safe", false, calYearBehind, calThreeYearsAhead);
        
        assertTrue(cCurrent.isNotExpired());
        assertTrue(cCurrent2.isNotExpired());
       
        assertTrue(cNonCurrent1.isNotExpired());
        assertTrue(cNonCurrent2.isNotExpired());

        assertFalse(cExpired.isNotExpired());
    }

    @Test
    public void isCurrentTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calThreeYearsBehind.subtractTime(0, 0, 3);

        Calendar calDayBehind = new Calendar();
        calDayBehind.subtractTime(1, 0, 0);

        Calendar calDayAhead = new Calendar();
        calDayAhead.addTime(1, 0, 0);
            
        // Card with non current +1 year
        Card cNonCurrent1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calThreeYearsAhead);
        
        // Card with non current +1 day
        Card cNonCurrent2 = new Card("testUser2", "00002", "1234", 100, false, "safe", false, calDayAhead, calThreeYearsAhead);
        
        // Card which expired a year ago
        Card cExpired = new Card("testUser3", "00003", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);

        // Card which is not expired
        Card cCurrent = new Card("testUser4", "00004", "1234", 100, false, "safe", false, calYearBehind, calThreeYearsAhead);

        // Card which is not expired 2
        Card cCurrent2 = new Card("testUser5", "00005", "1234", 100, false, "safe", false, calYearBehind, calThreeYearsAhead);

        assertTrue(cCurrent.isCurrent());
        assertTrue(cCurrent2.isCurrent());
       
        assertFalse(cNonCurrent1.isCurrent());
        assertFalse(cNonCurrent2.isCurrent());

        assertTrue(cExpired.isCurrent());
    }
    
    @Test
    public void getIssueStringTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calThreeYearsBehind.subtractTime(0, 0, 3);
    
        // Card with issue +1yr, expiry +3yr
        Card c1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calThreeYearsAhead);
        
        // Card with issue -3yr, expiry -1yr
        Card c2 = new Card("testUser2", "00002", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);

        String c1Issue = calYearAhead.getDate();
        String c2Issue = calThreeYearsBehind.getDate();

        assertEquals(c1.getIssueString(), c1Issue);
        assertEquals(c2.getIssueString(), c2Issue);
    }

    @Test
    public void getExpiryStringTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calThreeYearsAhead = new Calendar();
        calThreeYearsAhead.addTime(0, 0, 3);

        Calendar calThreeYearsBehind = new Calendar();
        calThreeYearsBehind.subtractTime(0, 0, 3);
    
        // Card with issue +1yr, expiry +3yr
        Card c1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calThreeYearsAhead);
        
        // Card with issue -3yr, expiry -1yr
        Card c2 = new Card("testUser2", "00002", "1234", 100, false, "safe", false, calThreeYearsBehind, calYearBehind);

        assertEquals(c1.getExpiryString(), calThreeYearsAhead.getDate());
        assertEquals(c2.getExpiryString(), calYearBehind.getDate());
    }

    @Test
    public void toStringTests() {
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);
    
        // Card with issue -1yr, expiry +1yr
        Card c1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        
        String c1Issue = calYearBehind.getDate();
        String c1Expire = calYearAhead.getDate();

        String c1ToString = "testUser1,00001,1234,100,false,safe,false," + c1Issue + "," + c1Expire;
        assertEquals(c1.toString(), c1ToString);

        // Card with issue +1yr, expiry +3yr
        Card c2 = new Card("testuser2", "20000", "4321", 0, true, "missing", true, calYearBehind, calYearAhead);
        
        String c2Issue = calYearBehind.getDate();
        String c2Expire = calYearAhead.getDate();

        String c2ToString = "testuser2,20000,4321,0,true,missing,true," + c2Issue + "," + c2Expire;
        assertEquals(c2.toString(), c2ToString);

    }

    @Test
    public void testGettersSetters(){

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card c1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        
        c1.setAdminStatus(false);
        assertFalse(c1.getAdminStatus());

        c1.setAdminStatus(true);
        assertTrue(c1.getAdminStatus());

        c1.setBlocked(false);
        assertFalse(c1.isBlocked());

        c1.setBlocked(true);
        assertTrue(c1.isBlocked());

        c1.setPin("4321");
        assertEquals("4321",c1.getPin());

        c1.setUserName("testUser2");
        assertEquals("testUser2",c1.getUserName());

        c1.setSafetyStatus("safe");
        assertEquals("safe",c1.getSafetyStatus());

        c1.setSafetyStatus("stolen");
        assertEquals("stolen",c1.getSafetyStatus());

        c1.setSafetyStatus("missing");
        assertEquals("missing",c1.getSafetyStatus());

        


    }
}