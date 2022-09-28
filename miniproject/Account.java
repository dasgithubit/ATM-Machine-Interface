package miniproject;

import java.util.ArrayList;

public class Account {
	
	// account type.
	private String accountType;
	
	
	// unique account no.
	private String accountNo;
	
	// user object that owns this account.
	private User holder;
	
	// list of transaction for this account.
	private ArrayList<Transaction> transactions;
	
	/*
	 * Create a new Account
	 * accountType    the type of the account
	 * holder         the user object that hold this account
	 * theBank        the bank that issues the account
	 */
	
	public Account(String accountType,User holder, Bank theBank) {
		
		// set the account name and holder
		this.accountType = accountType;
		this.holder = holder;
		
		// get new account No
		this.accountNo = theBank.getNewAccountNo();
		
		// Initialize transactions
		this.transactions = new ArrayList<Transaction>();
		
		
	}
	/*
	 * Get the account no
	 * return the accountNo
	 */
	
	public String getaccountNo() {
		return this.accountNo;
	}
	
	/*
	 * Get summary line for the account
	 * return   the string summary
	 */
	
	public String getSummaryLine() {
		
		// get the account's balance
		double balance = this.getBalance();
		
		// format the summary line, depending on the whether the balance is negative.
		if(balance >= 0) {
			return String.format("%s : Rs%.02f : %s", this.accountNo, balance,this.accountType);
		} else {
			return String.format("%s : Rs(%.02f) : %s", this.accountNo, balance,this.accountType);
		}
	}
	
	/*
	 * Get the balance of this account by adding the amount of the transactions
	 * return   the balance value
	 */
	
	public double getBalance() {
		
		double balance = 0;
		for(Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	/*
	 * print the transaction history of the account
	 */
	
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.accountNo);
		for(int t = this.transactions.size()-1;t>=0;t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	/*
	 * Add a new transaction in this account
	 * amount    the amount transacted
	 * memo      the transaction memo
	 */
	public void addTransaction(double amount, String memo) {
		
		// create a new transaction object and add it to our list
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
	}
	

}
