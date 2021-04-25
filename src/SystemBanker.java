import java.util.ArrayList;
import java.util.Scanner;

public class SystemBanker {

    private int [] available;
    private int [][] max;
    private int [][] allocation;
    private int [][] need;
    private String state[];
    private int n,m;
    private ArrayList<Integer> processesOrder; // if the state is safe, you can print the order of processes
    public SystemBanker() {
        Scanner scanner = new Scanner(System.in);
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


    public boolean isSafeState(int[] tmpAvailable){
        // you have to change the allocation as it's difficult to send it in parameters,
        // based on this method result, change allocation later in your method
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


    public int[] getAvailable() {
        return available;
    }
}
