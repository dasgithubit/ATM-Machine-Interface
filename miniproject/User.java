package miniproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	// first name of the user.
	private String firstName;
	
	// last name of the user.
	private String lastName;
	
	// unique ID number of the user.
	private String UIDAI;
	
	// unique pin number of the user.
	private byte pinHash[];
	
	// list of account of the user.
	private ArrayList<Account> accounts;
	
	/*
	 * Create a new user
	 * firstName the user's firstName
	 * lastName the user's lastName
	 * pin  the user's account pin number
	 * theBank the bank object that the user is the customer of	
	 */
	
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		// set user name.
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pin's in MD5 hash, rather than the original value, for
		// security reason.
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		// get a unique universal ID for the user.
		this.UIDAI = theBank.getNewUserUIDAI();
		
		// create empty list of account.
		this.accounts = new ArrayList<Account>();
		
		// print log message.
		System.out.printf("New user %s, %s with ID %s created.\n", lastName,firstName,this.UIDAI);
	}
	
	/*
	 * Add an account for the user
	 * anAcct     the account to add
	 */
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
				
		/*
		 *  Return the user's UIDAI
		 *  return    the UIDAI
		 */
		
	public String getUIDAI() {
		return this.UIDAI;
	}

	
		/*
		 * Check whether a given pin matches the true user pin
		 * aPin    the pin to check
		 * return  whether the pin is valid or not
		 */
		
	public boolean validatePin(String aPin) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),
					this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}
	
	/*
	 * Return the user's first name.
	 * return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	
	/*
	 * Print summaries for the accounts of this user.
	 */
	
	
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for(int a=0;a<this.accounts.size();a++) {
			System.out.printf("  %d) %s\n", a+1, 
					this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	/*
	 * Get the number of the account of the user
	 * return   the number of accounts
	 */
	
	public int numAccounts() {
		return this.accounts.size();
	}
	
	/*
	 * print transaction history for a particular account,
	 * acctIdx   the index of the account to use
	 */
	
	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
		
	}
	
	/*
	 * Get the balance of the particular account
	 * acctIdx    the index of the account to use
	 * return     the balance of the account
	 */
	
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}
	
	/*
	 * Get the No of a particular account
	 * acctIdx      the index of the account to use
	 * return       the no of the account
	 */
	
	public String getAcctNo(int acctIdx) {
		return this.accounts.get(acctIdx).getaccountNo();
		
	}
	
	/*
	 * Add transaction to the particular account
	 * acctIdx    the index of the account
	 * amount     the amount of the transaction
	 * memo       the memo of the transaction
	 * 
	 */
	
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
	
	

}
