package miniproject;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	// bank name.
	private String name;
	
	// list of user of this account.
	private ArrayList<User> users;
	
	// list of the account.
	private ArrayList<Account> accounts;
	
	/*
	 * create a new bank object with empty lists of users and accounts
	 * name   the name of the bank
	 */
	
	public Bank(String name) {
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	
	/*
	 * Generate a new universally unique ID for a user
	 * return   the UIDAI
	 */
	
	public String getNewUserUIDAI() {
		
		String UIDAI;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until we get a unique ID
		do {
			
			// generate the number
			UIDAI ="";
			for(int c=0;c<len;c++) {
				UIDAI += ((Integer)rng.nextInt(10)).toString();
			}
			// check to make sure it unique
			nonUnique = false;
			for(User u : this.users) {
				if(UIDAI.compareTo(u.getUIDAI()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return UIDAI;
		
	}
	
	/*
	 * Generate a new universally unique ID for a user
	 * return   the Account No
	 */
	public String getNewAccountNo() {
		
		String accountNo;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until we get a unique ID
		do {
			
			// generate the number
			accountNo ="";
			for(int c=0;c<len;c++) {
				accountNo += ((Integer)rng.nextInt(10)).toString();
			}
			// check to make sure it unique
			nonUnique = false;
			for(Account a : this.accounts) {
				if(accountNo.compareTo(a.getaccountNo()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while(nonUnique);
		
		return accountNo;
		
	}
	/*
	 * Add an account
	 * anAcct        the account to add
	 */
	
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}
	
	/*
	 * Create a new user of the bank
	 * firstName    the user's first name
	 * lastName     the user's last name
	 * pin          the user's pin
	 * return       the new user object
	 */
	
	
	
	public User addUser(String firstName, String lastName, String pin) {
		
		// create a new user object and add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create a saving account for the user and Bank account list
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		
		return newUser;
	}
	/*
	 * Get the user object associated with a particular userId and pin, if they are valid
	 * userId    the UIDAI of the user to log in
	 * pin       the pin of the user
	 * return    the User object, if the login was successful, or null, if it is not
	 */
	
	public User userLogin(String userID, String pin) {
		
		// search through list of users 
		for(User u : this.users) {
			
			// check user ID is correct
			if(u.getUIDAI().compareTo(userID)==0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		// if we haven't found the user or have an incorrect pin
		return null;
		
	}
	
	
	public String getName() {
		return this.name; 
	}
	
	
	

}
