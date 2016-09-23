/**
 * AccountTest.java
 * 
 * Modification of an example in Cay Horstmann's "Computing Concepts with
 * Java 2 Essentials".
 * 
 * Tests the various bank account classes.
 *
 * @author Scot Drysdale on 4/23/00.  Modified 1/8/12.
 */
public class AccountTest {
  public static void main(String[] args) {
    SavingsAccount momsSavings = new SavingsAccount(0.5);
    TimeDepositAccount collegeFund = new TimeDepositAccount(1.0, 10000.00, 3, 0.5);
    ATMSavingsAccount harrysATMSavings = new ATMSavingsAccount(0.5, 5000);

    momsSavings.deposit(10000.00);

    momsSavings.transfer(harrysATMSavings, 2000);

    harrysATMSavings.withdraw(200);
    harrysATMSavings.withdraw(300);
    harrysATMSavings.withdraw(80);
    harrysATMSavings.withdraw(400);

    endOfMonth(momsSavings);
    endOfMonth(collegeFund);
    endOfMonth(harrysATMSavings);

    collegeFund.transfer(harrysATMSavings, 980);

    System.out.println("Mom's savings. " + momsSavings);
    // (10000 - 2000) * .5 % interest = 8040
    System.out.println("The college fund. " + collegeFund);
    // (10000 * 1% interest) * 0.5% penalty - 980 = 9069.50
    System.out.println("Harry's checking. " + harrysATMSavings);
    // 2000 - 200 - 300 - 80 - 400 - 2 trans. fees + 980 = 1999
  }

  // Handles end-of-month operations. Overloaded method, because
  // checking account does different things than savings account.

  /** 
   * Handle end of month interest for a savings account
   * @param savings the savings account to handle
   */
  public static void endOfMonth(SavingsAccount savings) {
    savings.addPeriodicInterest();
  }

  /** 
   * Handles end of month fee deduction for a checking account
   * @param checking the checking account to handle
   */
  public static void endOfMonth(CheckingAccount checking) {
    checking.deductFees();
  }
}