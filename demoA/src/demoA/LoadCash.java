package demoA;

public class LoadCash {
	private int totalCash = 0;
    private int loadAmount;
    private int num1000;
    private int num500;
    private int num100;

    public LoadCash(int loadAmount) {
        this.loadAmount = loadAmount;
        this.totalCash = totalCash + loadAmount;
       
            calculateDenominations();
        
    }
    
   

    public void load() {
        if (validateLoadAmount()) {
            calculateDenominations();
            printDenominations();
        }
        
    }

    private boolean validateLoadAmount() {
        if (loadAmount < 100000) {
            System.out.println("Load valid Amount!!");
            return false;
        }
        if (loadAmount % 100000 != 0) {
            System.out.println("Load the Amount in exact multiples of 1,00,000");
            return false;
        }
        return true;
    }

    private void calculateDenominations() {
    	
    	int baseAmound=100000;
        int n = loadAmount / baseAmound;

        int quantity1 = 20;
        int quantity2 = 100;
        int quantity3 = 300;

        num1000 = quantity1 * n;
        num500 = quantity2 * n;
        num100 = quantity3 * n;
    }

    private void printDenominations() {
        int amount1 = 1000 * num1000;
        int amount2 = 500 * num500;
        int amount3 = 100 * num100;
        int totalAmount = amount1 + amount2 + amount3;

        System.out.println("1000 X " + num1000 + " = " + amount1 + " ₹");
        System.out.println("500 X " + num500 + " = " + amount2 + " ₹");
        System.out.println("100 X " + num100 + " = " + amount3 + " ₹");
        System.out.println("Total Amount: " + totalAmount + " ₹");
    }

    public int getTotalAmount() {
        return 1000 * num1000 + 500 * num500 + 100 * num100;
    }

    public int getNum1000() {
        return num1000;
    }

    public void setNum1000(int num1000) {
        this.num1000 = num1000;
    }

    public int getNum500() {
        return num500;
    }

    public void setNum500(int num500) {
        this.num500 = num500;
    }

    public int getNum100() {
        return num100;
    }

    public void setNum100(int num100) {
        this.num100 = num100;
    }

    public void setTotalAmount(int loadAmount) {
        this.loadAmount = loadAmount;
       
        if (validateLoadAmount()) {
            calculateDenominations();
        }
    }
}