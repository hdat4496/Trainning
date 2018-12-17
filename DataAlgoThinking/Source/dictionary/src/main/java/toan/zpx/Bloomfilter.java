package toan.zpx;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Bloomfilter implements DataStructure{
    private BloomFilter<String> bloomfilter;

    @Override
    public void loadData() {
        long startTime = System.nanoTime();
        File file = new File("data.txt");
        this.bloomfilter = BloomFilter.create(Funnels.unencodedCharsFunnel(), 1000000, 0.1);

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                this.bloomfilter.put(line);
            }

            buffer.close();
            long endTime = System.nanoTime();
            System.out.println("Time to load: "+(endTime - startTime) + " ns");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean contains(String word) {
        long startTime = System.nanoTime();
        boolean result = bloomfilter.mightContain(word);
        long endTime = System.nanoTime();
        System.out.println("Time to check contains: "+(endTime - startTime) + " ns");
        return result;
    }

}
