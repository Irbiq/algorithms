import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {

    public static void main(String[] args) {
        int a[] = new int[1];
        int b[] = new int[1];

        int n = 0;
        int m = 0;

        Main m1 = new Main();
        File f = new File("input.txt");

        try {
            FileReader r = new FileReader(f);
            Scanner sc = new Scanner(r);
            n = sc.nextInt();
            m = sc.nextInt();
            a = new int[n];
            b = new int[m];

            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }
            for (int i = 0; i < m; i++) {
                b[i] = sc.nextInt();
            }
            sc.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        List<Integer> supb = new ArrayList<>();
        Map<Integer, Integer> mp1 = new TreeMap<Integer, Integer>();

        for (int i = 0; i < m; i++) {
            mp1.put(b[i], i);
        }

        Integer val = 0;

        for (int i = 0; i < n; i++) {

            val = mp1.get(a[i]);
            if (val != null) {
                supb.add(val);
            }
        }

        List<Integer> d = new ArrayList<>(); // чило на кот оканчив послед
        d.add(-1000);

        for (int i = 1; i < supb.size() + 1; i++) {
            d.add(Integer.MAX_VALUE);
        }
        int j;
        int len = 0;
        for (int i = 0; i < supb.size(); i++) {


            j = Collections.binarySearch(d, supb.get(i));
            if (j < 0) {
                j = -j;
                j = j - 1;
            }
            if (d.get(j - 1) < supb.get(i) && supb.get(i) < d.get(j)) {
                d.set(j, supb.get(i));
                len = Math.max(len, j);
            }

        }

        try (FileWriter writer = new FileWriter("output.txt", false)) {
            writer.write(len + "\n");
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
