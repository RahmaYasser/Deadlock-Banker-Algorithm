import java.util.Scanner;

public class Process {
    private int m;
    private int[] need;
    private int[] max;
    private int[] allocated;

    public Process() {
    }

    public Process(int m) {
        this.m = m;
        need = new int[m];
        max = new int[m];
        allocated = new int[m];
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int[] getNeed() {
        return need;
    }

    public void setNeed() {
        for (int i = 0; i < m; i++) {
            need[i] = max[i]-allocated[i];
        }
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(Scanner scanner) {
        System.out.println("Enter the max number of instances for each resource the process will need");
        for (int i = 0; i < m; i++) {
            max[i] = scanner.nextInt();
        }
    }

    public int[] getAllocated() {
        return allocated;
    }

    public int[] setAllocated(Scanner scanner,int[]available) {
        System.out.println("The initial allocated instances for each resource");
        int tmp;
        for (int i = 0; i < m; i++) {
            tmp = scanner.nextInt();
            if(tmp>available[i]) {
                System.out.println("not enough resources available try again");
                i--;
                continue;
            }
            allocated[i] = tmp;
            available[i]-=tmp;
        }
        return available;
    }
}
