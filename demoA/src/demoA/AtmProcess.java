package demoA;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import demoA.Customer;

public class AtmProcess {
	private LoadCash cash;
    private DigitalBank digitalBank;
    Scanner in=new Scanner(System.in);
    
   

    public AtmProcess(DigitalBank digitalBank) {
        this.digitalBank = digitalBank;
        this.cash = digitalBank.getCash();
        // Assuming DigitalBank has a method to get cash instance
    }

    public void checkBalance() {
        Customer c = digitalBank.validateAccountAndPin();
        if (c != null) {
            System.out.println("Account Balance: " + c.getAccountBalance() + " ₹");
          //  digitalBank.start();
        }
    }

   
 // Withdraw amount
  public  void withdrawMoney() {
        Customer c = digitalBank.validateAccountAndPin();
        int withdrawAmount=0;
        if (c != null) {
            System.out.println("Your current balance is ₹" + c.getAccountBalance());
            System.out.println("Enter the amount to withdraw:");
            
            try {
             withdrawAmount = in.nextInt();
            }catch(InputMismatchException e) {
	        	System.out.println("Invalid input! Please enter a valid number.");
	        	in.next();
	        }

         
			if (withdrawAmount >= 100 && withdrawAmount <= 10000) {
                if (withdraw(c, withdrawAmount)) {
                    processWithdrawal(c, withdrawAmount);
                } else {
                    System.out.println("Insufficient funds or ATM balance. Please try a lower amount.");
                }
            } else {
                System.out.println("Enter a valid amount (more than ₹100 and less than ₹10000).");
            }
           // digitalBank.start();
        }
    }

    // Check the withdraw amount is >= Account balance and ATM balance
    private boolean withdraw(Customer c, int withdrawAmount) {
        System.out.println("Current Account Balance: " + c.getAccountBalance());
        System.out.println("ATM Cash Available: " + cash.getTotalAmount());
        System.out.println("Withdraw Amount: " + withdrawAmount);

        boolean sufficientFunds = c.getAccountBalance() >= withdrawAmount;
        boolean sufficientATM = cash.getTotalAmount() >= withdrawAmount;

        if (!sufficientFunds) {
            System.out.println("Insufficient funds in account.");
        }
        if (!sufficientATM) {
            System.out.println("Insufficient funds in ATM.");
        }

        return sufficientFunds && sufficientATM;
    }


    // Process the withdrawal
    private void processWithdrawal(Customer c, int withdrawAmount) {
        int balance = c.getAccountBalance();
        int originalWithdrawAmount = withdrawAmount;
        int num1000 = 0, num500 = 0, num100 = 0;

        if (withdrawAmount <= 5000) {
            num1000 = withdrawAmount >= 1000 ? 1 : 0;
            withdrawAmount -= num1000 * 1000;
            num500 = Math.min(6, withdrawAmount / 500);
            withdrawAmount -= num500 * 500;
            num100 = Math.min(10, withdrawAmount / 100);
            withdrawAmount -= num100 * 100;
        } else {
            num1000 = Math.min(3, withdrawAmount / 1000);
            withdrawAmount -= num1000 * 1000;
            num500 = Math.min(2 + (withdrawAmount / 500), cash.getNum500());
            withdrawAmount -= num500 * 500;
            num100 = Math.min(10, withdrawAmount / 100);
            withdrawAmount -= num100 * 100;
        }

        if (withdrawAmount == 0) {
            updateBalances(c, balance, num1000, num500, num100, originalWithdrawAmount);
        } else {
            System.out.println("Cannot dispense the exact amount with available denominations.");
            revertDenominations(num1000, num500, num100);
        }
    }

    private void revertDenominations(int num1000, int num500, int num100) {
        cash.setNum1000(cash.getNum1000() + num1000);
        cash.setNum500(cash.getNum500() + num500);
        cash.setNum100(cash.getNum100() + num100);
    }

    private void updateBalances(Customer c, int balance, int num1000, int num500, int num100, int withdrawAmount) {
        c.setAccountBalance(balance - withdrawAmount);
        cash.setTotalAmount(cash.getTotalAmount() - withdrawAmount);
        cash.setNum1000(cash.getNum1000() - num1000);
        cash.setNum500(cash.getNum500() - num500);
        cash.setNum100(cash.getNum100() - num100);

        System.out.println("Withdrawal successful! New balance: ₹" + c.getAccountBalance());

        int newBalance = c.getAccountBalance();
        c.addTransaction(new Transaction("Withdrawal", withdrawAmount, newBalance));
    }

    // Transfer money
    void transferMoney() {
        Customer sender = digitalBank.validateAccountAndPin();
        if (sender != null) {
            System.out.println("Account Balance: " + sender.getAccountBalance() + " ₹");
            transferProcess(sender);
            //digitalBank.start();
        }
    }

    // Transfer process
    private void transferProcess(Customer sender) {
    	int   transferAmount=0;
    	int receiverAccNo=0;
        System.out.println("Enter Receiver Account Number:");
        try {
        	
         receiverAccNo = in.nextInt();
         
        }catch(InputMismatchException e) {
        	System.out.println("Invalid input! Please enter a valid number.");
        	in.next();
        }


        Customer receiver = findCustomerByAccountNumber(receiverAccNo);
        if (receiver == null) {
            System.out.println("Receiver Account Not Found!!!!");
            return;
        }

        System.out.println("Enter the amount to transfer:");
        try {
        	
         transferAmount = in.nextInt();
        
        }catch(InputMismatchException e) {
        	System.out.println("Invalid input! Please enter a valid number.");
        	in.next();
        }


        if (transferAmount <= 10000 && transferAmount > 1000) {
            if (transferCheck(sender, transferAmount)) {
                sender.setAccountBalance(sender.getAccountBalance() - transferAmount);
                receiver.setAccountBalance(receiver.getAccountBalance() + transferAmount);

                sender.addTransaction(new Transaction("Transfer Sent", transferAmount, sender.getAccountBalance()));
                receiver.addTransaction(new Transaction("Transfer Received", transferAmount, receiver.getAccountBalance()));

                System.out.println("Transfer successful! Your new balance: ₹" + sender.getAccountBalance());
            } else {
                System.out.println("Insufficient funds. Please try a lower amount.");
            }
        } else {
            System.out.println("Enter a valid amount (more than ₹1000 and less than ₹10000).");
        }
    }
    private Customer findCustomerByAccountNumber(int accNo) {
        for (Customer c : digitalBank.getCustomers()) {
            if (c.getAccountNumber() == accNo) {
                return c;
            }
        }
        return null;
    }

    private boolean transferCheck(Customer sender, int transferAmount) {
        return sender.getAccountBalance() >= transferAmount;
    }

    // Mini statement
    void miniStatement() {
        Customer c = digitalBank.validateAccountAndPin();
        if (c != null) {
            LinkedList<Transaction> transactions = c.getTransactions();
            System.out.println("Last 5 transactions:");

            for (int i = Math.max(0, transactions.size() - 5); i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                System.out.println(t);
               // digitalBank.start();
            }
        }
    }

}