package assignment;
/**
 * Represents each user's bank card, receiving input from the ATM class.
 */
public class Card {
    private boolean adminStatus; //Allows you to deposit coins
    private boolean blocked; //Default is false
    private Calendar issueDate;
    private Calendar expiryDate;
    private String safetyStatus; //(stolen,missing,safe)
    private String cardNumber;
    private String userName;
    private String pin;
    private int balance;

    public Card( 
        String userName,
        String cardNumber,
        String pin,
        int balance,
        boolean adminStatus, 
        String safetyStatus, 
        boolean blocked, 
        Calendar issueDate, 
        Calendar expiryDate 
        ){
            this.userName = userName;
            this.cardNumber = cardNumber;
            this.pin = pin;
            this.balance = balance;
            this.adminStatus = adminStatus;
            this.safetyStatus = safetyStatus.toLowerCase();
            this.blocked = blocked;
            this.issueDate = issueDate;
            this.expiryDate = expiryDate;  
        }


    //GETTERS
    /**
     * Returns boolean of the card admin status.
     */
    public boolean getAdminStatus(){
        return adminStatus;
    }
    /**
     * Returns boolean of blocked status
     */
    public boolean isBlocked(){
        return blocked;
    }
    /**
     * Returns Calendar object representing card issue date.
     */
    public Calendar getIssueDate(){
        return issueDate;
    }
    /**
     * Returns Calendar object representing card expiry date.
     */
    public Calendar getExpiryDate(){
        return expiryDate;
    }
    /**
     * Returns String of safety status. ("safe", "missing", "blocked")
     */
    public String getSafetyStatus(){
        return safetyStatus;
    }
    /**
     * Returns String representation of card number.
     */
    public String getCardNumber(){
        return cardNumber;
    }
    /**
     * Returns String representation of user name.
     */
    public String getUserName(){
        return userName;
    }
    /**
     * Returns String representation of personal identification number.
     */
    public String getPin(){
        return pin;
    }
    /**
     * Returns integer representation of balance in cents.
     */
    public int getBalance(){
        return balance;
    }
    /**
     * Returns String representation of issue date.
     */
    public String getIssueString(){
        return this.issueDate.getDate();
    }
    /** 
     * Returns String representation of expiry date.
     */
    public String getExpiryString(){
        return this.expiryDate.getDate();
    }
    

    //SETTERS
    /**
     * @param boolean the desired admin status to be set.
     * Sets boolean admin status based on boolean input.
     */
    public void setAdminStatus(boolean status){
        this.adminStatus = status;
    }
    /**
     * Sets boolean blocked status based on boolean input.
     * @param boolean the desired blocked status to be set.
     */
    public void setBlocked(boolean status){
        this.blocked = status;
    }
    /**
     * @param String the desired safety status to be set ("safe", "missing", "stolen")
     * Sets safety status based on String input.
     */
    public void setSafetyStatus(String status){
        this.safetyStatus = status;
    }
    /**
     * @param String the desired PIN to be set.
     * Sets PIN based on String input.
     */
    public void setPin(String newPin){
        this.pin = newPin;
    }
    /**
     * @param int the desired balance (in cents) to be set.
     * Sets balance based on int input.
     */
    public void setBalance(int newBalance){
        this.balance = newBalance;
    }

    public void setUserName(String newUserName){
        this.userName = newUserName;
    }

    //HELPER

    /**
    * Returns true if current date is after issue date and before expiry date, and if status is "safe" 
    * @return a boolean, true if the Card object is valid.
    * @param none
    */
    public boolean isValid(){
        return(this.isCurrent() && this.isNotExpired() && this.isSafe());
    }

    /**
    * True if the status is "missing" 
    * @return a boolean, true if the status is "missing".
    * @param none
    */
    public boolean isMissing(){
        return (this.safetyStatus.equals("missing"));
    }
    /**
    * True if the status is "stolen" 
    * @return a boolean, true if the status is "stolen".
    * @param none
    */
    public boolean isStolen(){
        return (this.safetyStatus.equals("stolen"));
    }

    /**
    * True if the status is "safe"
    * @return a boolean, true if the status is "sage".
    * @param none
    */
    public boolean isSafe(){
        return (this.safetyStatus.equals("safe"));
    }

    /**
    * True if the current date is before the expiry date.
    * @return a boolean, true current date is before the expiry date.
    * @param none
    */
    public boolean isNotExpired(){
        return(!(this.expiryDate.isBeforeNow()));
    }

    /**
    * True if the current date is after the issue date.
    * @return a boolean, True if the current date is after the issue date.
    * @param none
    */
    public boolean isCurrent(){
        return(this.issueDate.isBeforeNow());
    }

    /**
     * Combines all elements of the card into a single string
     * Format: userName,cardNumber,PIN,balance,adminStatus,safetyStatus,blockedStatus,issueDate,expiryDate
     * @return String with all elements of the card, deliminated by ","
     * @param none
     */
    public String toString(){
        //userName,cardNumber,PIN,balance,adminStatus,safetyStatus,blockedStatus,issueDate,expiryDate
        String returnString = "";

        //CONCATENATE

        returnString += userName + ",";
        returnString += cardNumber + ",";
        returnString += pin + ",";
        returnString += balance + ",";
        returnString += adminStatus +",";
        returnString += safetyStatus + ",";
        returnString += blocked + ",";
        returnString += getIssueString() + ",";
        returnString += getExpiryString();

        return returnString;
    }
}