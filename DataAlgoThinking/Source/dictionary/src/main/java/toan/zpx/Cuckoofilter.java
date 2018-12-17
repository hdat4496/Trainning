package toan.zpx;
import com.github.mgunlogson.cuckoofilter4j.CuckooFilter;
import com.google.common.hash.Funnels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Cuckoofilter implements DataStructure {

    private CuckooFilter<String> cuckooFilter = new CuckooFilter.Builder<String>(
            Funnels.stringFunnel(StandardCharsets.UTF_8),
            1000000).build();;

    @Override
    public void loadData() {
        long startTime = System.nanoTime();
        File file = new File("data.txt");

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                this.cuckooFilter.put(line);
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
        boolean result = cuckooFilter.mightContain(word);
        long endTime = System.nanoTime();
        System.out.println("Time to check contains: "+(endTime - startTime) + " ns");
        return result;
    }
}
