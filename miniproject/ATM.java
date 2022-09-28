package miniproject;

import java.util.Scanner;


public class ATM {
	
	public static void main(String[] args) {
		
		// initialize scanner 
		Scanner sc = new Scanner(System.in);
		
		// initialize Bank
		Bank theBank = new Bank("Bank of Maharashtra");
		
		//  add a user, which also creates a saving account
		User aUser = theBank.addUser("Subrato", "Das", "****");
		
		//  add a user, which also creates a checking account
		Account newAccount = new Account("Current", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			
			// stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		// inits
		String userID;
		String pin;
		User authUser;
		
		// prompt the user for user ID/pin combo until a correct one is reached
		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			// try to get the user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userID,pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + "please try again.");
				
			}
			
			// 
		}while(authUser == null); // continue looping until successful login
		
		return authUser;
		
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		
		// print a summary of the user's accounts
		theUser.printAccountsSummary();
		
		// init
		int choice;
		
		// user menu
		do {
			System.out.printf("Welcome %s, what you like to do?\n", 
					theUser.getFirstName());
			System.out.println(" 1) Show transaction history");
			System.out.println(" 2) Withdrawal");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice = sc.nextInt();
			
			if(choice <1 || choice>5) {
				System.out.println("Invalid Choice. Please choose 1-5");
			}
		}while(choice<1 || choice>5);
		
		// process the choice
		switch(choice) {
		
		case 1: 
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawlFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			// gobble up rest of previous input
			sc.nextLine();
			break;
		}
		
		// redisplay this menu unless the user want to quit
		// using recursion same method name here
		if(choice!=5) {
			ATM.printUserMenu(theUser, sc);
		}
	}
	
	/*
	 * Show the transaction history for an account
	 * theUser    the logged-in User object
	 * sc         the Scanner object used for user input
	 */
	
	public static void showTransHistory(User theUser, Scanner sc) {
		
		int theAcct;
		
		// get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "whose transaction you want to see: ",theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if(theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(theAcct < 0 || theAcct >= theUser.numAccounts());
		
		// print the transaction history
		theUser.printAcctTransHistory(theAcct);
	}
	
	/*
	 * process transferring funds from one account to another
	 * theUser     the logged-in User Object
	 * sc          the Scanner object used for user input
	 */
	
	public static void transferFunds(User theUser, Scanner sc) {
		
		// init
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try again: ");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get the account to transfer to
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try again: ");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max Rs%.02f): Rs",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero. ");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" + "balance of Rs%.02f.\n",acctBal);
			}
			
		}while(amount <0 || amount > acctBal);
		
		// finally do the transfer 
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", theUser.getAcctNo(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", theUser.getAcctNo(fromAcct)));
	}
	
	/*
	 * Process a fund withdraw from an account
	 * theUser    the logged-in User object
	 * sc         the Scanner object user for user input
	 */
	
	public static void withdrawlFunds(User theUser, Scanner sc) {
		
		// init
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try again: ");
			}
			
		}while(fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		
		// get the account to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max Rs%.02f): Rs",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero. ");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n" + "balance of Rs%.02f.\n",acctBal);
			}
			
		}while(amount <0 || amount > acctBal);
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// get a memo 
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		// do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);	
	}
	
	/*
	 * Process a fund deposit to an account
	 * theUser    the logged-in User object
	 * sc         the Scanner object used for user input
	 */
	
	public static void depositFunds(User theUser, Scanner sc) {
		
		// init
		int toAcct;
		double amount;
		double acctBal;
		String memo;
		
		// get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. please try again: ");
			}
			
		}while(toAcct < 0 || toAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(toAcct);
		
		
		// get the account to transfer
		do {
			System.out.printf("Enter the amount to transfer (max Rs%.02f): Rs",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero. ");
			}	
		}while(amount <0 );
		
		// gobble up rest of previous input
		sc.nextLine();
		
		// get a memo 
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		// do the withdraw
		theUser.addAcctTransaction(toAcct, amount, memo);	
	}
	

}
