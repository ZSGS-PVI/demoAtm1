package demoA;

import java.util.LinkedList;

import demoA.Transaction;

public class Customer {
	 
	 private int accountNumber;
	 private String accountHolder;
	 private int pinNumber;
	 private int accountBalance;
	 private LinkedList<Transaction> transactions = new LinkedList<>();
	
	public Customer(int accountNumber, String accountHolder, int pinNumber, int accountBalance) {
		
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.pinNumber = pinNumber;
		this.accountBalance = accountBalance;
	}
	
	
	


	@Override
	public String toString() {
		return "Customer [accountNumber=" + accountNumber + ", accountHolder=" + accountHolder + ", pinNumber="
				+ pinNumber + ", accountBalance=" + accountBalance + "]";
	}




	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public int getAccountBalance() {
		return accountBalance;
	}


   public void setAccountBalance(int accountBalance) {
       this.accountBalance = accountBalance;
   }

   public LinkedList<Transaction> getTransactions() {
       return transactions;
   }

   public void addTransaction(Transaction transaction) {
       if (transactions.size() >= 5) {
           transactions.removeFirst();
       }
       transactions.addLast(transaction);
   }
}
