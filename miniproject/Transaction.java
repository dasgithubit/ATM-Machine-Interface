package miniproject;

import java.util.Date;

public class Transaction {
	
	// amount of the transaction.
	private double amount;
	
	// time and date of the transaction.
	private Date timestamp;
	
	// unique transaction ID.
	private String transactionId;
	
	// memo of this transaction.
	private String memo;
	
	// account in which the transaction was performed.
	private Account inAccount;
	
	/*
	 * Create a new transaction
	 * amount      the amount transacted
	 * inAccount   the account the transaction belongs to
	 */
	
	public Transaction(double amount, Account inAccount) {
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
		
	}
	/*
	 * Create a new transaction
	 * amount      the amount transacted
	 * memo        the memo for transaction
	 * inAccount   the account the transaction belongs to
	 */
	
	public Transaction(double amount, String memo, Account inAccount) {
		
		// call the two-args constructor
		this(amount, inAccount);
		
		// set the memo
		this.memo = memo;
	}
	
	/*
	 * Get the amount of the transaction
	 * return   the amount
	 */
	
	public double getAmount() {
		return this.amount;
	}
	
	/*
	 * get a String summarizing the transaction
	 * return   the summary string
	 */
	
	public String getSummaryLine() {
		
		if(this.amount >= 0) {
			return String.format("%s : Rs%.02f : %s", this.timestamp.toString(),
					this.amount, this.memo);
		}
		else {
			return String.format("%s : Rs(%.02f) : %s", this.timestamp.toString(),
					-this.amount, this.memo);
			
		}
	}

}
