package com.cg.banking;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.banking.bean.Customer;
import com.cg.banking.exception.BankingException;
import com.cg.banking.service.BankingService;
import com.cg.banking.service.BankingServiceImpl;
import com.cg.banking.util.DBUtilException;


public class BankingMain {

	private BankingService bService;

	public BankingMain() throws DBUtilException, BankingException {
		bService = new BankingServiceImpl();
		Scanner scan = new Scanner(System.in);		
		while(true) {
			int choice=0;
			choice = getChoice(scan);
			switch (choice) {
			case 1:
				System.out.println("Create Customer");
				System.out.println("Enter <A/c No> <Name> <Mobile> <A/c Type>");
				Customer cust = new Customer(scan.nextLong(),scan.next(),scan.nextLong(),scan.next());
				boolean success;
				try {
					success = bService.createCustomer(cust);
					if(success) {
						System.out.println("Customer created successfully");
					} else {
						System.out.println("Failed to create customer");
					}
				} catch (DBUtilException e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case 2:
				System.out.println("Deposit amount");
				System.out.println("Enter <account No> <amount to deposit>");
				long accNo = scan.nextLong();
				double amount = scan.nextDouble();
				double balance;
				try {
					balance = bService.deposit(accNo,amount);
					System.out.println("Amount deposited, balance: " + balance);
				} catch (BankingException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("Withdraw amount");
				System.out.println("Enter <account No> <amount to withdraw>");
				accNo = scan.nextLong();
				amount = scan.nextDouble();
				try {
					balance = bService.withdraw(accNo,amount);
					System.out.println("Amount withdrawn, balance: " + balance);
				} catch (BankingException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Display balance");
				System.out.println("Enter <account number>");
				accNo = scan.nextLong();
				balance = bService.getBalance(accNo);
				System.out.println("Balance: " + balance);
				break;
			case 5:
				System.out.println("Exiting system. Thank you.");
				scan.close();
				System.exit(0);
				break;

			default:
				System.out.println("Please enter only 1 to 4");
				break;
			}
		}
		
	}
	private int getChoice(Scanner scan) {
		int choice = 0;
		System.out.println("BANKING SYSTEM");
		System.out.println("1. Create Customer");
		System.out.println("2. Deposit amount");
		System.out.println("3. Withdraw amount");
		System.out.println("4. Display balance");
		System.out.println("5. Exit system");
		System.out.println("Please enter choice");
		try {
		choice = scan.nextInt();
		}catch (InputMismatchException e) {
			System.out.println("Please enter numbers only");
			scan.nextLine(); // consume the value in keyboard memory
		}
		return choice;
	}

	public static void main(String[] args) throws DBUtilException, BankingException {
		new BankingMain();
	}
}



