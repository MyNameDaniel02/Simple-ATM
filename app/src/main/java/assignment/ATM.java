package assignment;


import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;
/**
 * ATM class works between the Engine and Card classes, for computing transactions.
 */
public class ATM {

    private int transactionNum;
    private String atmID;
    HashMap<Integer, Integer> denominations;
    ArrayList<Card> cards;

    public ATM(String atmID, int transactionNum, ArrayList<Card> cards, HashMap<Integer, Integer> denominations) {
        this.atmID = atmID;
        this.transactionNum = transactionNum;
        this.cards = cards;
        this.denominations = denominations;
        
    }
    /**
     * Retrieves the ATM balance
     * @return the ATM balance in cents
     */
    public int getAtmBalance(){
        int balance = 0;
        for (Map.Entry<Integer, Integer> elem : this.getDenominations().entrySet()){
            balance = balance + (elem.getKey().intValue() * elem.getValue().intValue()); 
        }

        return balance;
    }

    /**
     * Retrieves the transaction number of the ATM
     * @return the transaction number
     */
    public int getTransactionNum(){
        return this.transactionNum;
    }

    /**
     * Retrieves the ATM ID number
     * @return the atm ID number
     */
    public String getAtmID(){
        return this.atmID;
    }

    /**
     * retrives the ATM's current denominations
     * @return the denominations stored in the ATM
     */
    public HashMap<Integer, Integer> getDenominations(){
        return this.denominations;
    }
    
    /**
     * Retrieves the cards stored in the ATM database
     * @return the cards stored in the ATM
     */
    public ArrayList<Card> getCards(){
        return this.cards;
    }

    /**
     * Allows the user to deposit money in the form of specified denominations
     * @param card - the user's card
     * @param denoms - the denominations that the user wants to deposit
     * @return whether the deposit is successful
     */
    public boolean deposit(Card card,  HashMap<Integer, Integer> denoms){
        int amount = 0;

        for (Map.Entry<Integer, Integer> elem : denoms.entrySet()){
            amount += elem.getValue().intValue() * elem.getKey().intValue();
        }

        if ((amount % 5) != 0 || amount < 500){ // if amount is not divisble by 5 or is less than 5 dollars
            return false;
        }

        if (amount <= 0){
            return false;
        }
        
        card.setBalance(card.getBalance() + amount);

        HashMap<Integer, Integer> atm_funds = this.getDenominations();

        for (Map.Entry<Integer, Integer> elem : denoms.entrySet()){
            Integer currentVal = atm_funds.get(elem.getKey());
            Integer supplyVal = elem.getValue();
            Integer newVal = currentVal.intValue() + supplyVal.intValue();
            atm_funds.put(elem.getKey(), newVal);
        }
        
        this.transactionNum += 1;
        System.out.println(this.printReceipt(card, amount, "Deposit"));
        return true;
    }

    /**
     * Allows the user to withdraw a specified amount of money if the ATM and the user have sufficient funds
     * @param card - the user's card
     * @param amount - the amount the user wants to withdraw
     * @return relevant message regarding the withdrawal status
     */
    public String withdraw(Card card, int amount){

        if ((amount % 5) != 0){
            System.exit(0);
        }

        if ( this.getAtmBalance() < amount) {
            return "Unfortunately this atm does not have sufficient funds! We'd like to apologise for any convenience caused!";
        }

        if (card.getBalance() < amount){
            return "Error: you do not have sufficent funds on this card!";
        }

        if (amount <= 0){
            return "Error: this is not a valid amount.";
        }

        HashMap<Integer, Integer> toBeDispensed = new HashMap<Integer, Integer>();
        int[] values = {10000, 5000, 2000, 1000, 500, 100, 50 , 20, 10, 5};

        int i = 0;
        int amountRemaining = amount;

        while (i<values.length){
            if (amountRemaining >= values[i]){
                Integer numNotes = Integer.valueOf( (int) Math.floor(amountRemaining / values[i]));
                if (this.getDenominations().get(Integer.valueOf(values[i])) >= numNotes){
                    amountRemaining = amountRemaining % values[i];
                    Integer currentNotesVal = this.getDenominations().get(Integer.valueOf(values[i]));
                    this.getDenominations().put(Integer.valueOf(values[i]), Integer.valueOf(currentNotesVal.intValue() - numNotes.intValue()));
                    toBeDispensed.put(Integer.valueOf(values[i]), Integer.valueOf(numNotes));
                }
            }
            i += 1;

        }

        int balance = 0;
        for (Map.Entry<Integer, Integer> elem : toBeDispensed.entrySet()){
            balance = balance + (elem.getKey().intValue() * elem.getValue().intValue()); 
        }

        if (balance == amount){
            card.setBalance(card.getBalance() - amount);
            this.transactionNum += 1;
    
            return this.printReceipt(card, amount, "Withdrawal");
        }

        else {
            return "This ATM needs to be restocked as we have insufficient funds. XYZ Bank has been notified.";
        }
    }

    /**
     * Generates the receipt string for the user's transaction
     * @param card - the user's card
     * @param amount - the amount used in the transaction
     * @param type - the transaction type (withdraw or deposit)
     * @return the receipt
     */
    public String printReceipt(Card card, int amount, String type){
        String process;

        if (type.equals("Withdrawal")) {
            process = "Withdrawn";
        } else {
            process = "Deposited";
        }

        String receipt = "Transaction Number: " +  String.valueOf(this.transactionNum);
        receipt += "\nTransaction Type: " + type;
        receipt += "\nAmount " + process + ": " + Utilities.printMoney(amount);
        receipt += "\nAccount Balance: " + Utilities.printMoney(card.getBalance());

        return receipt;
    }

    /**
     * Retrieves the balance of the card in cents
     * @param card - the user's card
     * @return the card balance in cents
     */
    public int balance(Card card){
        return card.getBalance();
    }

    /**
     * Checks if the card exists in the ATM database
     * @param cardNum - the user's card number
     * @return whether the card exists in the ATM database
     */
    public Card cardExists(String cardNum){
        Card targetCard = null;

        for (Card card: this.cards){
            if (card.getCardNumber().equals(cardNum)){
                targetCard = card;
                break;
            }
        }
        return targetCard;
    }

    /**
     * Checks if the user's card has expired or is being used after it's date of issue
     * @param targetCard - the user's card
     * @return whether the card is valid regarding it's date of issue and expiry date
     */
    public String checkDates(Card targetCard){ 
        Calendar cal = new Calendar();

        if (!(cal.isBefore(targetCard.getExpiryDate()))){
            return "Card has expired.";
        }
        
        if (cal.isBefore(targetCard.getIssueDate())){
            return "Today's date is before the card's issue date!";
            
        }

        return "Card dates valid";

    }

    /**
     * Checks if the user's card has been stolen, is missing or is blocked.
     * @param card - the user's card
     * @return the relevent message depending on if the user's card is stolen, missing or blocked
     */
    public String checkStatus(Card card) { 
        if (!card.getSafetyStatus().equals("safe")) {
            return "Card not safe";
        }

        if (card.isBlocked()){
            return "Card has been blocked";
        }

        return "Card status OK";

    }

    /**
     * Allows the admin to restock the ATM's denominations
     * @param supply - the denominations for the resupplying of the ATM
     */
    public void restock(HashMap<Integer, Integer> supply){

        HashMap<Integer, Integer> denoms = this.getDenominations();

        for (Map.Entry<Integer, Integer> elem : supply.entrySet()){
            Integer currentVal = denoms.get(elem.getKey());
            Integer supplyVal = elem.getValue();

            if(supplyVal.intValue() < 0){
                continue;
            }
            Integer newVal = currentVal.intValue() + supplyVal.intValue();
            
            denoms.put(elem.getKey(), newVal);
        }
    }

    /**
     * Generates a string representation of the ATM's data
     * @return the string representation of the ATM's information (ID, transaction number, denominations)
     */
    public String toString(){
        String returnString = "";
        returnString += this.atmID;
        returnString += ",";
        returnString += this.transactionNum;
        
        int[] values = {5,10,20,50,100,200,500,1000,2000,5000,10000};

        for (int i = 0; i < values.length; i++) {
            returnString += ",";
            returnString += this.getDenominations().get(values[i]).toString();
        }

        return returnString;
    }
    /**
     * Deletes the given card from the Card database.
     */
    public void swallowCard(Card card){
        this.getCards().remove(card);
    }


}