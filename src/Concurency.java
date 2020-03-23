import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class JThread extends Thread {
    int specialNumber;
    int[][] res;
    ReentrantLock locker;
    TimeUnit time;

    JThread(String name, int numb, int[][] array, ReentrantLock lock, TimeUnit time) {
        super(name);
        specialNumber = numb;
        this.res = array;
        locker = lock;
        this.time = time;
    }

    public void run() {
        locker.lock();
        try {
            for (int k = specialNumber - 1; k < specialNumber + 3; k++) {
                if (res[k][k] == 0)
                    res[k][k] = k + 66;
            }

            System.out.printf("%s \n", Thread.currentThread().getName());
            time.sleep(1 / 2);

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            for (int[] re : res) {
                for (int k = 0; k < res.length; k++) {
                    System.out.print(re[k] + "\t");
                }
                System.out.println();
            }
            locker.unlock();
        }

    }
}


public class Concurency {


    public static void main(String[] args) {

        ReentrantLock locker = new ReentrantLock();
        int n = 8;
        Scanner sc = null;
        TimeUnit time = TimeUnit.SECONDS;

        try {
            sc = new Scanner(new BufferedReader(new FileReader("src/Sample.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int[][] array = new int[n][n];
        assert sc != null;
        while (sc.hasNextLine()) {
            for (int i = 0; i < array.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    array[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                if (j == k) {
                    array[j][k] = 0;
                }
            }
        }
        System.out.println("filled Matrix:");
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                System.out.print(array[j][k] + "\t");
            }
            System.out.println();
        }


        for (int i = 1; i < n / 2 + 2; i++)
            new JThread("JThread " + i, i, array, locker, time).start();


    }
}
