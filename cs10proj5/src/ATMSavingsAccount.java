
public class ATMSavingsAccount extends SavingsAccount {
	private static final int FREE_TRANSACTIONS = 2;
	private static final double TRANSACTION_FEE = 1.5;
	private int transactionCount;

	/**
	 * Constructor that sets interest rate.
	 * 
	 * @param rate
	 *            the interest rate paid by the account
	 */
	public ATMSavingsAccount(double rate) {
		super(rate);
		transactionCount = 0;
	}

	/**
	 * Constructor that sets rate and initial balance.
	 * 
	 * @param rate
	 *            the interest rate paid by the account
	 * @param initialAmount
	 *            the amount in the account when it is created
	 */
	public ATMSavingsAccount(double rate, double initialAmount) {
		super(rate, initialAmount);
		transactionCount = 0;
	}
	
	public void withdraw(double amount) {
		super.withdraw(amount);
		transactionCount++;
	}
	
	public void deposit(double amount) {
		super.deposit(amount);
		
	}
	
	public void transfer(BankAccount other, double amount) {
		withdraw(amount);
		other.deposit(amount);
	}

	/**
	 * Charge transaction fees (if any) to the account
	 */
	public void deductFees() {
		if (transactionCount > FREE_TRANSACTIONS) {
			double fee = TRANSACTION_FEE * (transactionCount - FREE_TRANSACTIONS);
			super.withdraw(fee);
		}
		transactionCount = 0; // Start over because new time period.
	}
	
	public void addPeriodicInterest() {
		super.addPeriodicInterest();
		deductFees();
	}
}
