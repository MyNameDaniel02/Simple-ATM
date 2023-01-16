package assignment;

import java.math.BigInteger;
/**
 * Calendar class enables the storage of time in UNIX format (milliseconds), for quick access and arithmetic.
 */
public class Calendar {
    // The time of this calendar; held as BigInteger expressing ms since Unix Epoch in UTC time.
    private BigInteger time;

    // Number of ms in year / month / day respectively
    final private BigInteger year_to_ms = new BigInteger("31556952000");
    final private BigInteger month_to_ms = new BigInteger("2629800000");
    final private BigInteger day_to_ms = new BigInteger("86400000");

    // Gives UTC offset
    final private BigInteger utcOffset = new BigInteger(String.valueOf((long)10*60*60*1000));

    // Number of days in a non-leap year per month
    final private int[] daysInYear = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    // Variables to hold the day, month and year of this given date
    private int year;
    private int month;
    private int day;

    private String monthName;
    private boolean isLeap;

    /**
     * Constuctor: When no arguments provided, time held is set to now.
     */
    public Calendar() {
        // Sets time to current time since epoch
        this.time = BigInteger.valueOf(System.currentTimeMillis());
        this.time.add(this.utcOffset);
        
        // Uses time to parse into year, month, day
        this.parseTime();

        // Sets month name given month value set above
        this.parseMonthName();
    }

    /**
     * Constuctor: When arguments provided, time is custom set and fixed in time.
     */
    public Calendar(int day, int month, int year) {     
        // Sets time to given time since epoch
        this.time = year_to_ms.multiply(BigInteger.valueOf(year-1970)).add(month_to_ms.multiply(BigInteger.valueOf(month-1))).add(day_to_ms.multiply(BigInteger.valueOf(day)));

        // Sets constructors
        this.day = day;
        this.month = month;
        this.year = year;

        // Determins if leap year, then parses month value into month name
        this.isLeap = isLeapYear(this.year);
        this.parseMonthName();
    }

    /**
     * Takes the time held in ms and parses into years, months and days.
     * Used for when setting the time based on "now", and for adding/subtracting time.
     * @param - none
     * @return - void 
     */
    private void parseTime() {
        // Finds years since epocj, adds 1970 to it as want Gregorian year
        this.year = this.time.divide(year_to_ms).intValue() + 1970;

        // Finds months since epoch, then takes modulo 12, then adds 1 to find Gregorian month
        this.month = this.time.divide(month_to_ms).mod(BigInteger.valueOf(12)).intValue() + 1;

        // Finds days since epoch, then subtracts how many days have passed in years as ms, then turns into days passed in the year
        this.day = this.time.subtract(BigInteger.valueOf(this.year - 1970).multiply(year_to_ms)).divide(day_to_ms).intValue();

        // Loop to find how many days have passed so far this year up to the start of the current month
        this.isLeap = isLeapYear(this.year);
        int numDaysSoFar = 0;
        if (this.isLeap) {
            numDaysSoFar = 1;
        }

        for (int i = 0; i < this.month - 1; i++) {
            numDaysSoFar += daysInYear[i];
        }

        // Subtracts days to start of current month from total days passed in the year to give the final day of the Gregorian month
        this.day -= numDaysSoFar;
    }

    /**
     * Private function to check whether date is a leap year or not.
     * Necessary for parsing dates properly
     * @param year is the year which want to check if it is a leap year as an int.
     * @return boolean whether the given year is a leep year.
     */
    private boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }
    /**
     * Function takes month value and parses into name.
     * @param none
     * @return void 
     */
    private void parseMonthName() {
        String[] monthNames = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        this.monthName = monthNames[this.month - 1];
    }

    /**
     * Takes in a String of format ddmmyyyy and returns a new calendar object.
     * @param calendar string for what want to return calendar object for
     * @return Calendar object with date held equivalent to string given
     */
    public static Calendar stringToCalendar(String calendar) {
        if (calendar == null) {
             return null;
        }
        
        String dayVal = calendar.substring(0, 2);
        String monthVal = calendar.substring(2, 4);
        String yearVal = calendar.substring(4);

        return new Calendar(Integer.valueOf(dayVal), Integer.valueOf(monthVal), Integer.valueOf(yearVal));
     } 

    /**
     * Function takes in parameter of another Calendar c, and checks whether the current calendar is before that parameter date or not.
     * @param calendar if the current isntance is before this calendar or not
     * @return boolean whether given instance is before the calendar or not
     */
    public boolean isBefore(Calendar c) {
        int curCalTotal = this.day;
        curCalTotal += this.year * 365;

        for (int x = 0; x < (this.month - 1); x ++) {
            curCalTotal += this.daysInYear[x];
        }

        int cCalTotal = c.getDay();
        cCalTotal += c.getYear() * 365;

        for (int x = 0; x < (c.getMonth() - 1); x ++) {
            cCalTotal += this.daysInYear[x];
        }

        if (cCalTotal >= curCalTotal) {
            return true;
        }
        return false;
    }

    /**
     * Function checks whether the current calendar is before current date.
     * @param none 
     * @return boolean whether given instance is before current date or not
     */
    public boolean isBeforeNow() {
        Calendar cNow = new Calendar();
        return this.isBefore(cNow);
    }

    /**
     * Retrieves the current time as a BigInteger; ms passed since Unix Epoch.
     * @param none
     * @return current time in ms as BigInteger
     */
    public BigInteger getTime() {
        return this.time;
    }

    /**
     * Retrieves the day held as int.
     * @param none
     * @return instance day as int
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Retrieves the month held as int.
     * @param none
     * @return instance month as int
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Retrieves the held month as a String name as String.
     * @param none
     * @return instance month name as String
     */
    public String getMonthName() {
        return String.valueOf(this.monthName);
    }

    /**
     * Retrieves the held year as int.
     * @param none
     * @return instance year as int
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the date held in the format ddmmyyyy as a String
     * @param none
     * @return instance date as String in format ddmmyyyy
     */
    public String getDate() {
        String dayF = String.format("%02d", this.day);
        String monthF = String.format("%02d", this.month);
        return dayF + monthF + String.valueOf(this.year);
    }

    /**
     * Returns the date held in the format ddMmmyyyy as a String
     * @param none
     * @return instance date as String in format ddMmmyyyy
     */
    public String getStringShort() {
        String dayF = String.format("%02d", this.day);
        return dayF + this.monthName + String.valueOf(this.year);
    }

    /**
     * Returns the date held in the format dd-Mmm-yyyy
     * @param none
     * @return instance date as String in format dd-Mmm-yyyy
     */
    public String getStringFormatted() {
        String dayF = String.format("%02d", this.day);
        return dayF + "-" + this.monthName + "-" + String.valueOf(this.year);
    }

    /**
     * Adds a specified amount of time onto the time held.
     * @param day (int), month (int), years (int), which are the days, months and years want to add to instance time.
     * @return void
     */
    public void addTime(int day, int month, int years) {
        this.time.add(year_to_ms.multiply(BigInteger.valueOf(years)));
        this.time.add(month_to_ms.multiply(BigInteger.valueOf(month)));
        this.time.add(day_to_ms.multiply(BigInteger.valueOf(day)));

        this.day += day;
        this.month += month;
        this.year += years;
        
        if (this.month > 12) {
            this.year += this.month / 12;
            this.month = ((this.month - 1) % 12) + 1;
        }

        while (this.day > this.daysInYear[this.month - 1]) {
            this.day -= this.daysInYear[this.month - 1];
            this.month += 1;

            if (this.month > 12) {
                this.month = 1;
                this.year += 1;
            }
        }

    }

    /**
     * Subtracts a specified amount of time onto the time held.
     * @param day (int), month (int), years (int), which are the days, months and years want to subtract to instance time.
     * @return void
     */
    public void subtractTime(int day, int month, int years) {
        this.time.subtract(year_to_ms.multiply(BigInteger.valueOf(years)));
        this.time.subtract(month_to_ms.multiply(BigInteger.valueOf(month)));
        this.time.subtract(day_to_ms.multiply(BigInteger.valueOf(day)));

        this.day -= day;
        this.month -= month;
        this.year -= years;

        if (this.month < 0 ) {
            this.year -= Math.abs(this.month) / 12;
            this.month = (Math.abs(this.month + 1) % 12) + 1;
        } 

        while (this.day < 0) {
            this.month -= 1;

            if (this.month < 1) {
                this.month = 12;
                this.year -= 1;
            }

            this.day += this.daysInYear[this.month - 1];
        }
    }

}