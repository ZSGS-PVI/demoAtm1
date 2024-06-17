package demoA;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



public class DigitalBank extends ATM{
	
	
	
	 Scanner in = new Scanner(System.in);
	    ArrayList<Customer> customer = new ArrayList<>();
	    private LoadCash cash;
	    private AtmProcess atmProcess;
	   
	    

	    public DigitalBank() {
	        addCustomerInfo();
	        //atmProcess = new AtmProcess(this);
	        this.cash = new LoadCash(0);
	    }
	    

	    public Customer[] getCustomers1() {
	        return customer.toArray(new Customer[0]);
	    }

	    
	    public void loadCashToAtm(int amount) {
	        cash.setTotalAmount(cash.getTotalAmount() + amount);
	    }
	    
	    public static void main(String[] args) {
	        DigitalBank digitalbank = new DigitalBank();
	        digitalbank.start();
	    }

	    public void start() {
	    
	    	while(true) {
	        System.out.println("------WELCOME TO DIGITALBANK-------\n");
	        System.out.println(" 1.Load cash to atm \n 2.customer info \n 3.ATM process \n 4.Exit");
	        System.out.println("Enter the number");
	        int choice=0;
	    
	        try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                in.next(); // Clear the invalid input
                continue; // Skip the rest of the loop and start over
            }


	        
	        switch (choice) {
	            case 1:
	            	LoadCashToAtm();
	                break;
	            case 2:
	            	ShowCustomerDetails();
	                break;
	            case 3:
	            	ShowAtmProcess();
	                break;
	            case 4:
	                System.out.println("Thank you! Have a nice day!");
	                //System.exit(0);
	                return;
	            default:
	                System.out.println("Invalid choice! Please try again.");
	        }
	       
	    }
       
	         }
	    
	   
//
//	     int getValidatedChoice() {
//	        int choice = -1;
//	        while (true) {
//	            try {
//	                choice = in.nextInt();
//	                break;
//	            } catch (InputMismatchException e) {
//	                System.out.println("Invalid input. Please enter a valid number.");
//	                in.next(); // Clear the invalid input
//	            }
//	        }
//	        return choice;
//	    }

	    // Load amount to the ATM
	   

		@Override
		void LoadCashToAtm()  {
			 int loadAmount = 0;
		        System.out.println("Enter the Amount to load the ATM: (Atleast 1000000)");
		        try {
		        loadAmount =in.nextInt();
		        }catch(InputMismatchException e) {
		        	System.out.println("Invalid input! Please enter a valid number.");
		        	in.next();
		        }
		        cash = new LoadCash(loadAmount);
		        cash.load();
		        atmProcess = new AtmProcess(this);
		        
		        
			
		}

		

		@Override
		void  ShowCustomerDetails() {
			 if (customer.isEmpty()) {
		            addCustomerInfo();
		        }
		        System.out.println("---------------------------------------------------");
		        System.out.printf("%-10s %-15s %-10s %-15s\n", "Acc No", "Account Holder", "Pin Number", "AccountBalance");
		        System.out.println("---------------------------------------------------");

		        for (Customer customers : customer) {
		            System.out.printf("%-10d %-15s %-10d %-10s₹\n", customers.getAccountNumber(), customers.getAccountHolder(), customers.getPinNumber(), customers.getAccountBalance());
		        }
		        System.out.println("---------------------------------------------------\n");
		      
		    }

		    private void addCustomerInfo() {
		        customer.add(new Customer(101, "Suresh", 2343, 25243));
		        customer.add(new Customer(102, "Ganesh", 5432, 34123));
		        customer.add(new Customer(103, "Magesh", 7854, 26100));
		        customer.add(new Customer(104, "Naresh", 2345, 80000));
		        customer.add(new Customer(105, "Harish", 1907, 103400));
		    }

		
		    

			@Override
			void ShowAtmProcess() {
				int option=0;
				if (atmProcess == null) {
			        atmProcess = new AtmProcess(this); // Initialize if not already initialized
			    }
			    
				
				  System.out.println(" 1.Check Balance \n 2.Withdraw Money \n 3.Transfer Money \n 4.Mini Statement \n 5.Exit");
			        System.out.println("Enter the number:");
			        try {
			         option = in.nextInt();
			        }catch(InputMismatchException e) {
			        	System.out.println("Invalid input! Please enter a valid number.");
			        	in.next();
			        }

			        switch (option) {
			            case 1:
			                atmProcess.checkBalance();
			                break;
			            case 2:
			            	atmProcess.withdrawMoney();
			                break;
			            case 3:
			            	atmProcess.transferMoney();
			                break;
			            case 4:
			            	atmProcess.miniStatement();
			                break;
			            case 5:
			                return;
			            default:
			                System.out.println("Invalid choice! Please try again.");
			        }
			    }
	   

	       

	  
	 
	      

		public	Customer validateAccountAndPin() {
			
			while(true) {
				int accNo=0;
				boolean validInput=false;
				
			while(!validInput) {
			    try {
			        System.out.println("Enter Your Account Number:");
			         accNo = in.nextInt();
			         validInput=true;
			    }catch(InputMismatchException e) {
			    	System.out.println("Invalid input! Please enter a valid number.");
			    	in.next();
			    }
			}
		

			        for (Customer c : customer) {
			            if (c.getAccountNumber() == accNo) {
			            	int pinNo=0;
			            	validInput=false;
			            	
			           while(!validInput) {
			            	try {
			                System.out.println("Enter Your Pin Number:");
			                 pinNo = in.nextInt();
			                 validInput=true;
			            	}catch(InputMismatchException e) {
			            		System.out.println("Invalid input! Please enter a valid number.");
			            		in.next();
			            	}
			            }

			                if (c.getPinNumber() == pinNo) {
			                    return c;
			                }
			                
			              else {
			                    System.out.println("wrong pin! Please enter a valid pin number.");
			                    
			            }
			            }
			        }
			        
			        System.out.println("Account Not Found! enter 1 to continue");
			        try {
	                    in.nextInt();
	                }catch(InputMismatchException e) {
	                	System.out.println("Invalid input. Please enter a valid account number");
	                	in.next();
	                }
			    } 
		}

		public Customer[] getCustomers() {
			// TODO Auto-generated method stub
			
			return customer.toArray(new Customer[0]);
		}

		public LoadCash getCash() {
			// TODO Auto-generated method stub
			cash.getTotalAmount();
			return cash;
			
		}

		




}
