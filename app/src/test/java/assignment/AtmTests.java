package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AtmTests {
    // Unit test functions for all methods and workings of the ATM class

    /**
    Tests on the checkStatus(Card card) method
    */
    @Test
    public void testValidCard() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 100, false, "Safe", false, calYearBehind, calYearAhead);
        Card card3 = new Card("testUser3", "00003", "1234", 100, false, "sAfE", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);
        
        // Checking all three cards for different spellings of safe
        assertEquals(atm1.checkStatus(card1), "Card status OK");
        assertEquals(atm1.checkStatus(card2), "Card status OK");
        assertEquals(atm1.checkStatus(card3), "Card status OK");
    }

    @Test
    public void testInvalidCard() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card cardStolen1 = new Card("testUser1", "00001", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);
        Card cardStolen2 = new Card("testUser2", "00002", "1234", 100, false, "stolen", false, calYearBehind, calYearAhead);
        Card cardLost1 = new Card("testUser3", "00003", "1234", 100, false, "missing", false, calYearBehind, calYearAhead);
        Card cardLost2 = new Card("testUser3", "00003", "1234", 100, false, "missing", false, calYearBehind, calYearAhead);
        Card cardBlocked1 = new Card("testUser3", "00003", "1234", 100, false, "safe", true, calYearBehind, calYearAhead);
        Card cardBlocked2 = new Card("testUser3", "00003", "1234", 100, false, "stolen", true, calYearBehind, calYearAhead);
        Card cardBlocked3 = new Card("testUser3", "00003", "1234", 100, false, "missing", true, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(cardStolen1);
        cards.add(cardStolen2);
        cards.add(cardLost1);
        cards.add(cardLost2);
        cards.add(cardBlocked1);
        cards.add(cardBlocked2);
        cards.add(cardBlocked3);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        // Checking stolen cards
        assertEquals(atm1.checkStatus(cardStolen1), "Card not safe");
        assertEquals(atm1.checkStatus(cardStolen2), "Card not safe");

        // Checking lost cards
        assertEquals(atm1.checkStatus(cardLost1), "Card not safe");
        assertEquals(atm1.checkStatus(cardLost2), "Card not safe");

        // Checking blocked but safe card
        assertEquals(atm1.checkStatus(cardBlocked1), "Card has been blocked");

        // Checking stolen and blocked card
        assertEquals(atm1.checkStatus(cardBlocked2), "Card not safe");

        // Checking missing and blocked card
        assertEquals(atm1.checkStatus(cardBlocked3), "Card not safe");
    }

    /**
    Tests on the deposit(Card card, int amount) method
    */
    @Test
    public void testValidDeposit() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        // Checking both card deposit changed and ATM returns true
        assertTrue(atm1.deposit(card1, denom));
        assertEquals(card1.getBalance(), 100100);

        denom.remove(100);
        denom.put(100, 1000);
        // Checking both card deposit changed and ATM returns true
        assertTrue(atm1.deposit(card2, denom));
        assertEquals(card2.getBalance(), 100000);
    }

    @Test
    public void testEdgeDeposit() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 0);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        // Checking a deposit of 0 is valid but doesn't change the card's balance
        assertFalse(atm1.deposit(card1, denom));
        assertEquals(card1.getBalance(), 100);

        HashMap<Integer, Integer> denom1 = new HashMap<Integer, Integer>();
        denom1.put(-100, 1);
        // Checking that a negative deposit amount returns false & doesn't change card balance
        assertFalse(atm1.deposit(card2, denom1));
        assertEquals(card2.getBalance(), 0);
    }


    /**
    Tests on the withdraw(Card card, int amount) method
    */
    @Test
    public void testValidWithdrawal() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

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
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking withdraw a small amount
        assertEquals(atm1.withdraw(card1, 100), atm1.printReceipt(card1, 100, "Withdrawal"));
        assertEquals(atm1.balance(card1), 900);

        //Checking withdraw all 
        assertEquals(atm1.withdraw(card2, 1000), atm1.printReceipt(card2, 1000, "Withdrawal"));
        assertEquals(atm1.balance(card2), 0);
    }

    @Test
    public void testInvalidWithdrawal() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

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
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking withdraw over card balance 
        assertEquals(atm1.withdraw(card1, 10000), "Error: you do not have sufficent funds on this card!");
        assertEquals(atm1.balance(card1), 1000);

        //Checking withdraw when withdrawal amount is 0
        assertEquals(atm1.withdraw(card2, 0), "Error: this is not a valid amount.");
        assertEquals(atm1.balance(card2), 0);
    }

    @Test
    public void testEdgeWithdrawal() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 1000000, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(5, 1);
        denom.put(10, 1);
        denom.put(20, 1);
        denom.put(50, 1);
        denom.put(100, 1);
        denom.put(200, 1);
        denom.put(500, 1);
        denom.put(1000, 1);
        denom.put(2000, 1);
        denom.put(5000, 1);
        denom.put(10000, 1);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking negative withdraw
        assertEquals(atm1.withdraw(card1, -1000), "Error: this is not a valid amount.");
        assertEquals(atm1.balance(card1), 1000);

        //Checking ATM insufficient funds
        assertEquals(atm1.withdraw(card2, 1000000), "Unfortunately this atm does not have sufficient funds! We'd like to apologise for any convenience caused!");
        assertEquals(atm1.balance(card2), 1000000);
    }

    /*
    Below are tests on the balance(Card card) method
    Negative and edge cases are covered in Card tests
    */
    @Test
    public void testValidBalance() {
         // Initialization of parameters 
         Calendar calYearAhead = new Calendar();
         calYearAhead.addTime(0, 0, 1);
 
         Calendar calYearBehind = new Calendar();
         calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking balance of card1
        assertEquals(atm1.balance(card1), 100);

        //Checking balance of card2
        assertEquals(atm1.balance(card2), 1000);
    }

    /*
    Below are tests on the getAtmBalance() method
    */
    @Test
    public void testValidAtmBalance() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        denom.put(50, 100);
        denom.put(10000, 10);
        int atmBal = 100*1000 + 50*100 + 10000*10;

        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking ATM 
        assertEquals(atm1.getAtmBalance(), atmBal);
    }

    @Test
    public void testEdgeAtmBalance() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();

        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking ATM 
        assertEquals(atm1.getAtmBalance(), 0);
    }

    /*

Below are tests on the generateReciept(int amount, String action, Card card) method
    */
    @Test
    public void testValidReciept() {

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

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
        
        ATM atm1 = new ATM("12345", 1, cards, denom);
        ATM atm2 = new ATM("12345", 2, cards, denom);

        HashMap<Integer, Integer> depositDenom = new HashMap<Integer, Integer>();
        depositDenom.put(10000, 1);

        atm1.deposit(card1, depositDenom);
        atm2.withdraw(card2, 500);

        String depositReceipt = "Transaction Number: 2";
        depositReceipt += "\nTransaction Type: Deposit";
        depositReceipt += "\nAmount Deposited: $100.00";
        depositReceipt += "\nAccount Balance: $110.00";

        String withdrawReceipt = "Transaction Number: 3";
        withdrawReceipt += "\nTransaction Type: Withdrawal";
        withdrawReceipt += "\nAmount Withdrawn: $5.00";
        withdrawReceipt += "\nAccount Balance: $5.00";
        

        assertEquals(atm1.printReceipt(card1, 10000, "Deposit"), depositReceipt);
        assertEquals(atm2.printReceipt(card2, 500, "Withdrawal"), withdrawReceipt);
    }

    @Test
    public void testEdgeReciept() {

        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 0, false, "safe", false, calYearBehind, calYearAhead); //valid card
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead); //invalid card

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

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
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        atm1.withdraw(card1, 0); 
        atm1.withdraw(card2, 0);

        String withdrawReceipt1 = "Transaction Number: 1";
        withdrawReceipt1 += "\nTransaction Type: Withdrawal";
        withdrawReceipt1 += "\nAmount Withdrawn: $0.00";
        withdrawReceipt1 += "\nAccount Balance: $0.00";

        String withdrawReceipt2 = "Transaction Number: 1";
        withdrawReceipt2 += "\nTransaction Type: Withdrawal";
        withdrawReceipt2 += "\nAmount Withdrawn: $0.00";
        withdrawReceipt2 += "\nAccount Balance: $0.00";

        assertEquals(atm1.printReceipt(card1, 0, "Withdrawal"), withdrawReceipt1);
        assertEquals(atm1.printReceipt(card2, 0, "Withdrawal"), withdrawReceipt2);
    }

    /*

    Below are tests on the restock(HashMap<Integer, Integer>, denominations) method
    NB: Unsure what this method does as of yet (6/9/2021)
    */
    @Test
    public void testValidRestock() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);
        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 0);
        denom.put(10000, 0);
        denom.put(50, 0);
        denom.put(1000, 0);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //restock $100 note supply
        HashMap<Integer, Integer> resupply = new HashMap<Integer, Integer>();
        resupply.put(10000, 10);

        //restock $1 coin supply
        HashMap<Integer, Integer> oneResupply = new HashMap<Integer, Integer>();
        oneResupply.put(100, 10);

        //restock $0.50 coin supply
        HashMap<Integer, Integer> centResupply = new HashMap<Integer, Integer>();
        centResupply.put(50, 10);

        //restock 0 supply
        HashMap<Integer, Integer> zeroSupply = new HashMap<Integer, Integer>();
        zeroSupply.put(1000, 0);

        atm1.restock(resupply);
        atm1.restock(oneResupply);
        atm1.restock(centResupply);
        atm1.restock(zeroSupply);

        //Checking $100 note supply was added to ATM
        assertEquals(atm1.getDenominations().get(10000), 10);

        //Checking $1 coin supply was added to ATM
        assertEquals(atm1.getDenominations().get(100), 10);

        //Checking $0.50 coin supply was added to ATM
        assertEquals(atm1.getDenominations().get(50), 10);
        
        //Checking 0 supply was added to ATM
        assertEquals(atm1.getDenominations().get(1000), 0);
    }

    @Test
    public void testInvalidRestock() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //restock negative supply
        HashMap<Integer, Integer> resupply = new HashMap<Integer, Integer>();
        resupply.put(100, -1000);

        //Checking $100 note supply not added to ATM
        assertEquals(atm1.getAtmBalance(), 100000);


    }

    @Test
    public void testEdgeRestock() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(10, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //restock supply
        HashMap<Integer, Integer> resupply = new HashMap<Integer, Integer>();
        resupply.put(10, 0);

        atm1.restock(resupply);
        //Checking nothing added to ATM
        assertEquals(atm1.getAtmBalance(), 10000);

    }

    /*
    Below are tests on the checkDates(Card card) method
    */
    @Test
    public void testValidDates() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking card1 date is valid
        assertEquals(atm1.checkDates(card1), "Card dates valid");

        //Checking card2 date is valid
        assertEquals(atm1.checkDates(card2), "Card dates valid");
    }

    @Test
    public void testInvalidDates() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Calendar calTwoYearsAhead = new Calendar();
        calYearAhead.addTime(0, 0, 2);

        Calendar calTwoYearsBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 2);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearAhead, calTwoYearsAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calTwoYearsBehind, calYearBehind);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking card with future issue date
        assertEquals(atm1.checkDates(card1), "Today's date is before the card's issue date!");

        //Checking expired card
        assertEquals(atm1.checkDates(card2), "Card has expired.");
    }

    /*
    Below are tests on the cardExists(String cardNum) method
    */
    @Test
    public void testValidCardExists() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        // Checking card1 exists in ATM System
        assertEquals(atm1.cardExists("00001"), card1);

        // Checking card2 exists in ATM System
        assertEquals(atm1.cardExists("00002"), card2);
    }

    @Test
    public void testCardNotExist() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);
        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "00002", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking card2 doesn't exist in ATM System
        assertEquals(atm1.cardExists("00002"), null);

    }

    @Test
    public void testEdgeCardExists() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "666666", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);
        Card card2 = new Card("testUser2", "4444", "1234", 1000, false, "safe", false, calYearBehind, calYearAhead);
        Card card3 = new Card("testUser3", "-1", "1234", 0, false, "safe", false, calYearBehind, calYearAhead);
        Card card4 = new Card("testUser4", "00004", "1234", 4000, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card4);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(100, 1000);
        
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        //Checking overflow of card number doesn't exist in system
        assertEquals(atm1.cardExists("666666"), null);

        //Checking underflow of card number doesn't exist in system
        assertEquals(atm1.cardExists("4444"), null);

        //Checking negative card number doesn't exist in system
        assertEquals(atm1.cardExists("-1"), null);
    }

    @Test
    public void testToString() {
        // Initialization of parameters 
        Calendar calYearAhead = new Calendar();
        calYearAhead.addTime(0, 0, 1);

        Calendar calYearBehind = new Calendar();
        calYearBehind.subtractTime(0, 0, 1);

        Card card1 = new Card("testUser1", "00001", "1234", 100, false, "safe", false, calYearBehind, calYearAhead);

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card1);

        HashMap<Integer, Integer> denom = new HashMap<Integer, Integer>();
        denom.put(5, 500);
        denom.put(10, 500);
        denom.put(20, 500);
        denom.put(50, 500);
        denom.put(100, 500);
        denom.put(200, 500);
        denom.put(500, 500);
        denom.put(1000, 500);
        denom.put(2000, 500);
        denom.put(5000, 500);
        denom.put(10000, 500);
        
        ATM atm1 = new ATM("12345", 1, cards, denom);

        assertEquals(atm1.toString(), "12345,1,500,500,500,500,500,500,500,500,500,500,500");

    }
}