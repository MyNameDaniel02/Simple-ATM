package assignment;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Engine {

    private ArrayList<Card> cards = new ArrayList<Card>();
    private ATM selectedATM;
    private String atmInfo;
    private Card selectedCard;
    private String cardInfo;
    private int pinCounter = 0;
    private String atmDatabase;
    private String cardDatabase;

    public Engine() {
        this.selectedATM = null; // starts as null, but set immediately in importData
    
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path atmPath = Paths.get(currentPath.toString(), "src", "main", "java", "assignment","database", "atm.csv");
        this.atmDatabase = atmPath.toString();

        Path cardPath = Paths.get(currentPath.toString(), "src", "main", "java", "assignment","database", "card.csv");
        this.cardDatabase = cardPath.toString();
    } 

    public static void main(String[] args) {

        Engine engine = new Engine();
        Scanner sc = new Scanner(System.in);
        if (!engine.interfaceInit(sc)) {
            return;
        }
        ATM atm = engine.selectedATM;

        // Card Validation
        System.out.println("Please insert your card");
        String cardNum = sc.nextLine();
        Boolean cardValidationResult = engine.cardValidation(cardNum);
        if (!cardValidationResult) {
            return;
        }

        // If the program's made it here, then we have a valid card within its usable timeframe
        Card targetCard = engine.selectedCard;
        engine.setCardInfo(targetCard.toString());
        
        // Check for missing/stolen/blocked
        Boolean statusCheckResult = engine.statusCheck(targetCard);
        if (!statusCheckResult) {
            return;
        }
        
        // Pin is verified here - if incorrect three times, then blocked
        Boolean pinVerificationStatus = engine.pinVerification(sc, targetCard);
        if (!pinVerificationStatus) {
            return;
        }

        // First we check if admin - in which case we begin the restock loop
        if (targetCard.getAdminStatus()) {
            System.out.println("\n################################\nAdmininstrative status detected.\n################################\n");
            engine.adminLoop(sc, atm);
            return;
        }    

        // Transaction Loop
        engine.transactionLoop(sc, atm, targetCard);
        return;   
    }

    /**
     * Parses the external card and ATM databases and imports all relevant information
     * in order to conduct bank transactions, card verifications and restocking procedures
     * @return the chosen ATM, loaded with the list of viable cards
     * @param atmNum is the ATM chosen for use by the client
     */
    public ATM importData(String atmNum) {
        // Get Card data
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            ArrayList<String> cardLines = Utilities.readFile(this.cardDatabase);
            // All cards generated
            for (int i = 0; i < cardLines.size(); i++) {
                String[] cardParsing = cardLines.get(i).split(",");
                cards.add(new Card(cardParsing[0], cardParsing[1], cardParsing[2],
                                    Integer.parseInt(cardParsing[3]), Boolean.parseBoolean(cardParsing[4]), 
                                    cardParsing[5], Boolean.parseBoolean(cardParsing[6]), 
                                    Calendar.stringToCalendar(cardParsing[7]), Calendar.stringToCalendar(cardParsing[8])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Card database not found!");
        } 

        // Get ATM data 
        HashMap<Integer,Integer> denominations = new HashMap<Integer, Integer>();
        String[] atmParsing = new String[13];

        // Checking ATM number exists
        boolean exists = false;

        try {
            ArrayList<String> atmLines = Utilities.readFile(this.atmDatabase);
            for (int i = 0; i < atmLines.size(); i++) {
                if (String.valueOf(atmLines.get(i).charAt(0)).equals(atmNum)) {
                    exists = true;
                    this.setATMInfo(atmLines.get(i));
                    atmParsing = atmLines.get(i).split(",");
                    // Creating all denominations
                    int[] amounts = new int[] {5,10,20,50,100,200,500,1000,2000,5000,10000};
                    for (int j = 0; j < amounts.length; j++) { 
                        denominations.put(amounts[j],Integer.parseInt(atmParsing[j + 2]));
                    }
                    break;
                }  
            }

            if (exists == false){
                return null;
            };

        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("ATM database not found!");
        }
        ATM atm = new ATM(atmParsing[0], Integer.parseInt(atmParsing[1]), cards, denominations);
        this.selectedATM = atm;
        return atm;
    }
    /**
     * Exports all updates to the chosen ATM and card to the external database,
     * so that every instance of an Engine() object has the latest information
     */
    public void exportData(Boolean swallowed) {
        Utilities.exporter(atmDatabase, atmInfo, this.selectedATM.toString(), false);
        Utilities.exporter(cardDatabase, cardInfo, this.selectedCard.toString(), swallowed);
    }
  
    // GETTERS

    /** Gets the information on all ATMs */
    public String getATMInfo() {
        return this.atmInfo;
    }

    /** Gets the current ATM being worked on */
    public ATM getATM() {
        return this.selectedATM;
    }

    /** Gets the information on all Cards */ 
    public String getCardInfo() {
        return this.cardInfo;
    }

    /** Gets the current card being worked on */
    public Card getCard() {
        return this.selectedCard;
    }

    // SETTERS
    public void setATMInfo(String atmLines) {
        this.atmInfo = atmLines;
    }

    public void setATM(ATM atm) {
        this.selectedATM = atm;
    }

    public void setCardInfo(String cardLines) {
        this.cardInfo = cardLines;
    }

    public void setCard(Card card) {
        this.selectedCard = card;
    }
    // ENGINE HELPER FUNCTIONS

    /**
     * Checks whether the card exists in the database, and if it's still in it's valid timeframe of use
     * @param cardNum is the card number provided by the client
     * @return the success of the operation - true if a valid card, false if not
     */
    public Boolean cardValidation(String cardNum) {
        if (this.selectedATM.cardExists(cardNum) != null) {
            this.setCard(this.selectedATM.cardExists(cardNum));
            // Check for valid timeframe
            String useDates = this.selectedATM.checkDates(this.selectedCard);
            if (useDates.equals("Card dates valid")) {
                // if card is within timeframe - the program moves to the next stage to check status
                return true;    
            } else if (useDates.equals("Card has expired.")) {
                // If card is used after expiry date
                System.out.printf("Unfortunately your card has expired on %s!"
                                    + " Please contact your local XYZ branch to get a new card." 
                                    + " Thank you!%n", this.selectedCard.getExpiryString());
                return false;
            } else if (useDates.equals("Today's date is before the card's issue date!")) {
                // If card is used before its issue date
                System.out.printf("We love the enthusiasm in getting to use your brand new card" 
                                    + " - however, it can only be used from %s! We apologise for any" 
                                    + " inconvenience caused!%n", this.selectedCard.getIssueString());
                return false;
            } else {
                return false;
            }
        } else {
            System.out.println("The card was not found in the database, check to see if the card was inserted correctly!");
            return false;
        }
    }

    /**
     * Checks whether the card is safe, missing, stolen or blocked - and then deals with it appropriately
     * @param targetCard is the card being checked
     * @return whether the card is safe or not
     */
    public Boolean statusCheck(Card targetCard){
        String status = this.selectedATM.checkStatus(targetCard);
        if (status.equals("Card status OK")) {
            System.out.println("\n################################");
            System.out.println("####### PROCESSING CARD ########");
            System.out.println("################################\n");
            System.out.println("Your card has been processed successfully. Please enter your PIN!");
            return true;
        } else if (status.equals("Card not safe")) {
            System.out.printf("The system has detected a card that has been reported %s." 
                                + " Protocol requires the immediate confiscation of this card."
                                + " We'd like to apologise for any inconvenience caused!%n", targetCard.getSafetyStatus());
            System.out.println("\n################################");
            System.out.println("####### SWALLOWING CARD ########");
            System.out.println("################################\n");
            this.selectedATM.swallowCard(targetCard);
            this.exportData(true);                    
            return false;
        } else if (status.equals("Card has been blocked")) {
            System.out.println("Your card is currently blocked. Please contact your nearest" 
                                + " XYZ Bank branch to resolve this issue! We'd like to apologise for" 
                                + " any inconvenience caused!");
            this.exportData(false);
            return false;
        } else {
            return false;
        }
    }

    /**
     * Conducts withdraw functionality
     * @param withdrawAmount is the amount the client wishes to withdraw
     * @param targetCard is the client's valid card
     * @return success of withdraw operation, where 0 = failed but continue loop, 1 = failed and end program, 2 = success
     */
    public int withdrawFunction(int withdrawAmount, Card targetCard) {
        if (withdrawAmount % 5 != 0 || withdrawAmount < 0) {
            System.out.println("The lowest denomination is 5c coins! Please enter a valid amount.");
            return 0;
        }
        String message = this.selectedATM.withdraw(targetCard, withdrawAmount);
        System.out.println(message);
        if (message.startsWith("Error")) {
            if (message.startsWith("Error: you")) {
                System.out.printf("Current card balance is %d.%n", targetCard.getBalance());
            }
            this.exportData(false);
            return 1;
        }
        System.out.println("Have a great day!");
        this.exportData(false);
        return 2;
    } 

    /**
     * Sets up the initial Command Line Interface and the chosen ATM
     * @param sc is the class Scanner 
     */
    public Boolean interfaceInit(Scanner sc) {
        String greeting = Utilities.timeDay();
        System.out.printf("%s! Welcome to XYZ Bank Inc! We have several ATMs,"
                            + " currently from 1 - 5. Which ATM are you using today?%n", greeting);
        String atmNumber = null;
        try {
            atmNumber = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Not a valid ATM! Good bye!");
            return false;
        }
        try {
            if (Integer.parseInt(atmNumber) > 5 || Integer.parseInt(atmNumber) < 1) {
                System.out.println("Not a valid ATM! Good bye!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Not a valid ATM! Good bye!");
            return false;
        }
        // ATM and list of cards loaded
        this.setATM(this.importData(atmNumber));
        return true;
    }

    /**
     * Conducts the pin verification process
     * @param sc is the class scanner
     * @param targetCard is the card being verified
     * @return whether the PIN stage was correctly passed
     */
    public Boolean pinVerification(Scanner sc, Card targetCard) {
        while (true) {
            String pin = sc.nextLine();
            if (this.pinCounter == 2) {
                //blocking card at 3 incorrect attempts 
                System.out.println("Unfortunately, you have entered the PIN incorrectly three times. Protocol requires the immediate blocking of this card.");
                targetCard.setBlocked(true);
                this.exportData(false);
                System.out.println("Have a good day!");
                return false;
            }
            if (!pin.equals(targetCard.getPin())) {
                //if pin incorrect
                String plural = "s";
                if (this.pinCounter == 1) {
                    plural = "";
                }
                System.out.printf("You have entered the PIN incorrectly -" 
                                + " %d attempt%s left. Please enter your PIN carefully!%n", 2 - this.pinCounter, plural);
                this.pinCounter++;
            } else {
                // if pin correct - move on to next stage: transaction process
                if (!targetCard.getAdminStatus()) { // If admin card then we go straight to resotkc function
                    System.out.printf("Pin approved!%nWelcome back, %s! What would you like to do today?", targetCard.getUserName());
                } 
                return true;  
            }     
        }
    }

    /**
     * Function is run when an admin card is detected - which begins the restock process
     * @param sc is the class scanner
     * @param atm is the ATM being used
     */
    public void adminLoop(Scanner sc, ATM atm) {
        int[] amounts = new int[] {5,10,20,50,100,200,500,1000,2000,5000,10000};
        HashMap<Integer, Integer> toRestock = new HashMap<Integer,Integer>();
        // admin loop
        while (true) {
            System.out.println("Which denomination would you like to restock? (Choose the appropriate index)\n(1) Cancel    (7)  $2\n(2) 5c        (8)  $5\n(3) 10c       (9)  $10\n(4) 20c       (10) $20\n(5) 50c       (11) $50\n(6) $1        (12) $100");
            Integer denom = null;
            Integer frequency = null;
            try {
                denom = sc.nextInt(); 
            } catch (InputMismatchException e) {
                System.out.println("Please choose a valid index!");
                sc.next();
                continue;
            }
            if (denom == 1) {
                System.out.println("Restock cancelled. Have a good day!");
                this.exportData(false);
                return;
            } else if (denom >= 2 && denom <= 12) {
                int den;
                String sign = null;
                String material = "notes"; 
                if (amounts[denom - 2] > 50) {
                    den = amounts[denom - 2] / 100; 
                    sign = "dollar"; 
                    if (denom < 8) {
                        material = "coins"; 
                    }
                } else {
                    den = amounts[denom - 2];
                    sign = "cent";
                    material = "coins";
                }
                System.out.printf("How many %d %s %s are you restocking?%n", den, sign, material);
                try {
                    frequency = sc.nextInt(); 
                } catch (InputMismatchException e) {
                    System.out.println("Please choose a valid index!");
                    continue;
                }
                if (frequency < 0) {
                    System.out.println("Please choose a valid number!");
                    continue;
                }
                if (sign.equals("dollar")) {
                    den *= 100; 
                }
                if (toRestock.containsKey(den)) {
                    toRestock.replace(den, toRestock.get(den), toRestock.get(den) + frequency);
                } else {
                    toRestock.put(den, frequency);
                }
                System.out.println("Would you like to restock another denomination?\n-Yes(Y)\n-No(any key)");
                String response = sc.next().toLowerCase();
                if (response.equals("y")) {
                    continue;
                } else {
                    atm.restock(toRestock);
                    System.out.printf("Restock complete! The new balance for ATM %s is %s.%n", atm.getAtmID(), Utilities.printMoney(atm.getAtmBalance()));
                    System.out.println("Have a good day!");
                    this.exportData(false);
                    return;
                }
            } else {
                System.out.println("Please choose a valid index!");
                continue;
            }
        }
    }

    /**
     * Used for regular bank clients to run normal transaction processes: deposit, withdraw, check balance
     * @param sc is the class scanner
     * @param atm is the ATM being used
     * @param targetCard is the client's card
     */
    public void transactionLoop(Scanner sc, ATM atm, Card targetCard) {
        // Transaction loop
        boolean print = true; 
        Boolean transactionRunning = true;
        while (transactionRunning) {
            if (print) {
                System.out.println("\n- Make a withdrawal(W)\n- Make a deposit(D)\n- Check Balance(B)\n- Cancel(C)");
            }
            print = true;
            String instruction = sc.nextLine().toLowerCase();

            // WITHDRAW FUNCTION
            if (instruction.equals("w")) {
                System.out.println("You have selected withdrawal.");
                while (true) {
                    System.out.println("How much cash would you like to withdraw today?" 
                                    + "\nNote: Enter amount in the format 'D.cc'");
                    int withdrawAmount;
                    try {
                        withdrawAmount = (int)(sc.nextDouble() * 100);
                    } catch (InputMismatchException e) {
                        System.out.println("Please provide a valid amount to withdraw!");
                        sc.next();
                        continue;
                    }
                    
                    int withdrawStatus = this.withdrawFunction(withdrawAmount, targetCard);
                    if (withdrawStatus == 0) {
                        print = false;
                        continue;
                    } else if (withdrawStatus == 1) {
                        return;
                    } else {
                        return;
                    }
                }
            // DEPOSIT FUNCTION
            } else if (instruction.equals("d")) {
                System.out.println("You have selected deposit. Please note that we only accept valid Australian notes!\n");
                Boolean depositing = true;
                int[] amounts = new int[] {500,1000,2000,5000,10000};
                HashMap<Integer, Integer> toDeposit = new HashMap<Integer,Integer>();
                // deposit loop
                while (depositing) {
                    System.out.println("Which denomination would you like to deposit? (Choose the appropriate index)\n(1) Cancel    (2) $5\n(3) $10       (4) $20\n(5) $50       (6) $100\n");
                    Integer denom = null;
                    Integer frequency = null;
                    try {
                        denom = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Please choose a valid index!");
                        sc.next();
                        continue;
                    }
                    if (denom == 1) {
                        System.out.println("Deposit cancelled. Have a good day!");
                        this.exportData(false);
                        return;
                    } else if (denom >= 2 && denom <= 6) {
                        int den = amounts[denom - 2] / 100;
                        System.out.printf("How many %d dollar notes are you depositing?%n", den);
                        try {
                            frequency = sc.nextInt();
                            if (frequency < 0) {
                                System.out.println("Please choose a valid amount!");
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Please choose a valid index!");
                            continue;
                        }
                        if (frequency < 0) {
                            System.out.println("Please choose a valid number!");
                            continue;
                        }
                        den *= 100; // convert back to cents
                        if (toDeposit.containsKey(den)) {
                            toDeposit.replace(den, toDeposit.get(den), toDeposit.get(den) + frequency);
                        } else {
                            toDeposit.put(den, frequency);
                        }
                        System.out.println("Would you like to deposit another denomination?\n-Yes(Y)\n-No(any key)");
                        String response = sc.next().toLowerCase();
                        if (response.equals("y")) {
                            continue;
                        } else {
                            Boolean success = atm.deposit(targetCard, toDeposit);
                            if (!success) {
                                System.out.println("Invalid amount provided - please note the ATM only accepts valid Australian notes!");
                                continue;
                            } else {
                                System.out.println("Have a good day!");
                            }
                            this.exportData(false);
                            return;
                        }
                    } else {
                        System.out.println("Please choose a valid index!");
                        continue;
                    }
                }
            // CHECK BALANCE
            } else if (instruction.equals("b")) {
                System.out.printf("Your account balance is currently %s.%n", Utilities.printMoney(targetCard.getBalance()));
                System.out.println("Would you like to make a transaction today?");
                continue;

            // CANCEL
            } else if (instruction.equals("c")) {
                System.out.println("Thank you for choosing XYZ Bank, have a good day!");
                this.exportData(false);
                return;

            // INVALID OPTION
            } else {
                System.out.print("Please enter a valid option: ");
                continue;
            }
        }   
    }
}

    