import java.util.ArrayList;
import java.util.Scanner;

public class SystemBanker {

    Scanner scanner;

    private int [] available;
    private int [][] max;
    private int [][] allocation;
    private int [][] need;
    private String state[];
    private int n,m;
    private ArrayList<Integer> processesOrder; // if the state is safe, you can print the order of processes
    public SystemBanker() {
        scanner = new Scanner(System.in);
        System.out.println("Enter number of resources");
        m = scanner.nextInt();
        System.out.println("Enter number of processes");
        n = scanner.nextInt();

        available = new int[m];
        max = new int[n][m];
        allocation = new int[n][m];
        need = new int[n][m];
        state = new String[n];
        processesOrder = new ArrayList<>();

        System.out.println("Enter available instances number for each resource");
        for (int i = 0; i < m; i++) available[i] = scanner.nextInt();

        System.out.println("Now you are going to enter each process info:");
        for (int i = 0; i < n; i++) {
            Process process = new Process(m);
            process.setMax(scanner);
            available = process.setAllocated(scanner,available);//updated available
            process.setNeed(); // calculates need
            max[i] = process.getMax();
            allocation[i] = process.getAllocated();
            need[i] = process.getNeed();
        }
        System.out.println("we done !");
    }

    private void copyArray(int [] a, int[] b){
        for (int i = 0; i < m; i++) {
            a[i] = b[i];
        }
    }
    public boolean isSafeState(int[] available){
        // you have to change the allocation as it's difficult to send it in parameters,
        // based on this method result, change allocation later in your method
        int [] tmpAvailable = new int[m];
        copyArray(tmpAvailable,available);
        boolean res = false;
        boolean[] finished = new boolean[n];
        while (true){
            boolean cycleChanged = false;
            int i;
            for ( i = 0; i < n; i++) {
                if(!finished[i] && vectorLessOrEqual(need[i],tmpAvailable)){
                    finished[i] = true;
                    vectorSum(tmpAvailable,allocation[i]);
                    cycleChanged = true;
                    processesOrder.add(i);
                }
            }
            if(!cycleChanged){
                processesOrder.clear();
                return false;
            }
            boolean allFinished = true;
            for (int j = 0; j < n; j++) {
                if(!finished[j]){
                    allFinished = false ;
                    break;
                }
            }
            if(allFinished)return true;
        }

    }

    boolean vectorLessOrEqual(int[] a,int[] b){
        boolean res = true;
        for (int i = 0; i < m; i++) {
            if(a[i]>b[i]) return false;
        }
        return res;
    }
    public void vectorSum(int[]a, int[]b){
        for (int i = 0; i < m; i++) {
            a[i] = a[i]+b[i];
        }
    }

    //TODO request and release methods


    public void requestResource() {
        int[] request = new int[m];
        int[] tmpAvailable = available; // if the request is not granted, the original available doen't affect
        boolean flag = true; // if true check system state (safe or not)

        System.out.print("Which process you want to request : ");
        int processNumber = scanner.nextInt();

        System.out.println("Enter the process request for each rescource");
        for (int i = 0 ; i < m ; i++) {
            request[i] = scanner.nextInt();

            // checking if there are enough available resources and need resources to be requested
            if (request[i] > tmpAvailable[i] || request[i] > need[processNumber][i])
                flag = false;
        }

        if (flag) {
            for (int i = 0 ; i < m ; i++) {
                tmpAvailable[i] -= request[i];
                need[processNumber][i] -= request[i];
                allocation[processNumber][i] += request[i];
            }
            if (isSafeState(tmpAvailable)) {
                System.out.println("The request is granted!\nNow available resources are : ");

                // subtract the original available
                for (int i = 0 ; i < m ; i++) {
                    available[i] -= request[i];
                    System.out.print(available[i] + " ");
                }
                System.out.println();

                return;
            }
        }

        if (flag) {
            // return to the previous state
            for (int i = 0 ; i < m ; i++) {
                need[processNumber][i] += request[i];
                allocation[processNumber][i] -= request[i];
            }
        }

        System.out.println("The request is not granted!");
    }

    public void releaseResource() {
        int[] release = new int[m];
        boolean flag = true; // if true process can release the resources

        System.out.print("Which process you want to release : ");
        int processNumber = scanner.nextInt();

        System.out.println("Enter the resources you want to release");
        for (int i = 0 ; i < m ; i++) {
            release[i] = scanner.nextInt();

            // checking if there are enough allocated resources to release some or all
            if (release[i] > allocation[processNumber][i])
                flag = false;
        }

        if (flag) {
            System.out.println("Process released the given resources!\nNow available resources are : ");

            // releasing the resources from allocated one and adding them to available
            for (int i = 0 ; i < m ; i++) {
                available[i] += release[i];
                allocation[processNumber][i] -= release[i];
                System.out.print(available[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("Process can't release the given resources!");
        }
    }

    public int[] getAvailable() {
        return available;
    }
}
