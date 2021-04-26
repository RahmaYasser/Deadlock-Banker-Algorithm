import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SystemBanker banker = new SystemBanker();
        
        boolean flag = true;
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        while (flag) {
        	System.out.print("\n1- request resources\n2- release resources\n3- exit\nYour choice : ");
        	
        	choice = scanner.nextInt();
        	
        	switch (choice) {
        	case 1:
        		banker.requestResource();
        		break;
        	case 2:
        		banker.releaseResource();
        		break;
        	case 3:
        		System.out.println("Thank you!");
        		flag = false;
        		break;
        	default:
        		System.out.println("You entered invaild choice, please try again!");
        	}
        }
        
        scanner.close();
        /*banker.isSafeState(banker.getAvailable());
        System.out.println("nothing :)");*/
    }
}
