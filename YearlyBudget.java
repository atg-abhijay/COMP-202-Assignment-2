public class YearlyBudget {
  public static void main(String[] args) {
    
    double yearlyIncome = Double.parseDouble(args[0]);
    double creditCardBalance = Double.parseDouble(args[1]);
    double yearlyInterestRate = Double.parseDouble(args[2]);
    long creditCardNumber = Long.parseLong(args[3]);
    double monthlyRent = Double.parseDouble(args[4]);
    
    boolean result = validateCreditCard(creditCardNumber);
    if (!result) {
      System.out.println("Invalid card");
    }
    else {
      // Printing monthly credit card balances
      
      /* getting the expenses for each month using the method buildExpenses
       * which takes as input the monthly rent */
      double[] expensesByMonth = buildExpenses(monthlyRent);
      // yearly post tax income = yearly income - total tax
      double yearlyPostTaxIncome = yearlyIncome - calculateTax(yearlyIncome, 10000, 20, 20000, 30, 45000, 50);
      /* getting the payments made for each month using the method buildPayments
       * which takes as input the yearly post tax income */
      double[] paymentsByMonth = buildPayments(yearlyPostTaxIncome);
      // the method printBalance prints the credit card balances for each month
      printBalance(creditCardBalance, yearlyInterestRate, expensesByMonth, paymentsByMonth);
      
      System.out.println();
      
      // Monthly additions to Savings account and total cumulative savings for the year
      
      /* defining a new double array that will
       * store the savings for each month */
      double[] monthlyAdditionsToSavings = new double[12];
      // the monthly income (after tax) is the yearly income post tax divided by 12
      double monthlyIncomeAfterTax = yearlyPostTaxIncome/12;
      // making a double variable that will store the cumulative savings for the year
      double totalCumulativeSavings = 0;
      // making a for loop to find and store the savings for each individual month
      for (int i = 0; i < 12; i++) {
        // expensesByMonth[i] will give us the expenses for each month as the loop runs
        monthlyAdditionsToSavings[i] = monthlySavings(expensesByMonth[i], monthlyIncomeAfterTax);
        System.out.println("Monthly addition to savings account in month " + (i+1) + " is $" + monthlyAdditionsToSavings[i]);
        totalCumulativeSavings += monthlyAdditionsToSavings[i];
      }
      System.out.println();
      System.out.println("The total cumulative savings for the year is $" + totalCumulativeSavings);
    }

  }
  
  
    // Question 1
  public static double calculateTax(double yearlyIncome, double bracket1Dollars, double bracket1Rate, double bracket2Dollars, double bracket2Rate, double bracket3Dollars, double bracket3Rate) {
    // the first tax is the difference between the 1st and 2nd bracket amounts multiplied by the interest rate for that bracket
    double tax1 = (bracket2Dollars - bracket1Dollars)*bracket1Rate/100;
    // second tax is the difference between 2nd and 3rd bracket amounts multiplied by interest rate for that bracket
    double tax2 = (bracket3Dollars - bracket2Dollars)*bracket2Rate/100;
    // third tax is the difference between 3rd bracket and the yearly income multiplied by interest rate for that bracket
    double tax3 = (yearlyIncome - bracket3Dollars)*bracket3Rate/100;
    // total tax to be paid is the sum of the above 3 taxes
    double totalTax = tax1 + tax2 + tax3;
    return totalTax;
  }
  
  
    // Question 2
  public static double monthlySavings (double monthlyExpenses, double monthlyIncomeAfterTax) {
    /* Monthly Savings is the money that is left after subtracting the
     * monthly expenses from the monthly income (after tax) */
    double monthlySavings = monthlyIncomeAfterTax - monthlyExpenses;
    return monthlySavings;
  }
  
  
    // Question 3
  public static boolean validateCreditCard (long creditCardNumber) {
    /* This method performs a checksum to validate the
     * credit card number that is input by the user*/
    
    /* FINDING SUM OF DIGITS AT ODD LOCATIONS FROM THE RIGHT
     
     * storing the creditCardNumber in a new (and shorter) variable */
    long ccnumber1 = creditCardNumber;
    /* defining a variable that will store the sum
     * of the digits at odd locations from the right*/
    long sumDigitOddLocation = 0;
    /*creating a for loop to find the sum of the
     * digits at odd locations from the right*/
    for (int i = 0; ccnumber1 >=1; i++) { 
      long digit = ccnumber1 % 10; // extracting the digit
      sumDigitOddLocation = sumDigitOddLocation + digit; // updating the variable that holds the sum of the digits
      ccnumber1 = ccnumber1/((long) (Math.pow(10,2))); // updating the variable so as to extract next digit
    }
    
    /* FINDING SUM OF DIGITS AT EVEN LOCATIONS FROM THE RIGHT
     
     * storing the creditCardNumber in a new (and shorter) variable */
    long ccnumber2 = creditCardNumber;
    // dividing by 10, removing last digit to start off at 1st even location
    ccnumber2 = ccnumber2/10;
    /*defining a long array that will store the values of the digits at even
     * locations. There are 8 even locations, therefore size of array is 8 */
    long [] digitEvenLocation = new long[8];
    /* defining a variable that will store the following sum: 
     * digits at even locations doubled % 9 (from the right) */
    long sumDigitEvenLocation = 0;
    /* creating a for loop to find the sum:
     * digits at even locations doubled % 9 (from the right) */
    for (int i = 0; ccnumber2 >= 1; i++) { // comments are on the side here so as to make it easier to read the for loop (at least for me!)
      digitEvenLocation[i] = ((2 * (ccnumber2 % 10)) % 9); // extracting the digit, multiplying it by 2 and taking modulo 9 of it 
      sumDigitEvenLocation = sumDigitEvenLocation + digitEvenLocation[i]; // updating the variable that holds the sum: digits at even locations doubled % 9
      ccnumber2 = ccnumber2/((long) (Math.pow(10,2))); // updating the variable so as to extract next digit
    }
    
    /* defining a variable that stores the sum of the digits at
     * odd locations and even locations from the above two results*/
    long sumBoth = sumDigitOddLocation + sumDigitEvenLocation;
    /* if the above sum is divisible by 10, then the credit
     * card number is valid. If not divisible, then it is invalid */
    if (sumBoth % 10 == 0) {
      return true;
    }
    else {
      return false;
    }
  }
  
  
    // Question 4
  public static double[] buildExpenses(double monthlyRent) {
    /* creating an array of type double that stores the
     * expenses of the 12 months of a year */
    double[] expensesInAYear = new double[12];
    for (int i = 0; i < expensesInAYear.length; i++) {
      expensesInAYear[i] += monthlyRent;
      expensesInAYear[i] += 600; // $600 per month for food, cell phone, internet etc.
      if (i == 0 || i == 5) {
        expensesInAYear[i] += 200; // $200 in Jan and June for dentist visits        
      }
      if (i == 8) {
        expensesInAYear[i] += 300; // $300 in Sep for textbooks. (Free Comp 202 book! Yipppeeee!)
      }
      if (i == 3 || i == 6 || i == 8) {
        expensesInAYear[i] += 100; // $100 in Apr, July, Sep for a family member's birthday
      }
      if (i == 11) {
        expensesInAYear[i] += 200; // $200 in Dec for the holidays (Great chance to relax!)
      }
    }
    return expensesInAYear; // returning the array containing the expenses for the 12 months
  }
  
  
   // Question 5
  public static double[] buildPayments(double yearlyPostTaxIncome) {
    /* creating an array of type double that stores
     * the credit card payments to be made for the 12 months */
    double[] monthlyCardPayments = new double[12];
    // the monthly income will be the yearly post tax income divided by 12
    double monthlyIncome = yearlyPostTaxIncome/12;
    /* creating a for loop to fill the elements, that is,
     * credit card payments, for the 12 months */
    for (int i = 0; i < monthlyCardPayments.length; i++) {
      monthlyCardPayments[i] += monthlyIncome * 0.1; // spending at least 10% of the monthly income each month to pay off credit card
      if (i == 11) {
        monthlyCardPayments[i] += 150; // in December, using the monetary gift of $150 from family to pay off credit card
      }
      if (i == 8) {
        monthlyCardPayments[i] += 200; // in September, using the going-away-to-university gift of $200 to pay off credit card
      }
    }
    return monthlyCardPayments; // returning the array containing the credit card payments for each of the 12 months
  }
  
  
    // Question 6
  public static void printBalance(double creditCardBalance, double yearlyInterestRate, double[] expensesByMonth, double[] paymentsByMonth) {
    // the monthly interest rate will be the yearly interest rate divided by 12
    double monthlyInterestRate = yearlyInterestRate/12;
    /* creating a for loop that finds the
     * credit card balance for each month */
    for (int i = 0; i < 12; i++) {
      /* the credit card balance will be the existing balance on it 
       * plus the expenses for that month minus the payments made for that month */
      creditCardBalance += expensesByMonth[i] - paymentsByMonth[i];
      if (creditCardBalance > 0) { // interest will only be added if the balance for that month is positive
        double monthlyInterest = creditCardBalance * monthlyInterestRate/100;
        creditCardBalance += monthlyInterest;
      }
      System.out.println("Month " + (i + 1) + " balance " + creditCardBalance); // printing the balance for each month
    }
  }
  
}