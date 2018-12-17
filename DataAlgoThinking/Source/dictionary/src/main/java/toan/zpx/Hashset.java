package toan.zpx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;

public class Hashset implements DataStructure {
    private HashSet<String> hashSet;

    @Override
    public void loadData() {
        long startTime = System.nanoTime();
        File file = new File("data.txt");
        this.hashSet = new HashSet<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                this.hashSet.add(line);
            }

            buffer.close();
            long endTime = System.nanoTime();
            System.out.println("Time to load:  "+(endTime - startTime) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public  boolean contains(String word) {
        long startTime = System.nanoTime();
        boolean result = hashSet.contains(word);
        long endTime = System.nanoTime();
        System.out.println("Time to check contains: "+(endTime - startTime) + " ns");
        return result;
    }
}
