package toan.zpx;

import java.io.*;
import java.util.HashSet;

public class Dataset {
    private String dir = "/home/lap12526-local/Downloads/blogs";
    private String nameDataset = "data.txt";

    public void processDataset() throws IOException {
        File folder = new File(this.dir);
        File[] listOfFiles = folder.listFiles();
        HashSet<String> hashSet = new HashSet<String>();

        BufferedReader buffer = null;
        System.out.println("Prcess start!");
        for (File file : listOfFiles) {
            buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] words = line.trim().split("[^a-zA-Z]+");
                for (String word : words) {
                    hashSet.add(word);
                }
            }
        }
        if (buffer != null) {
            buffer.close();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.nameDataset, true));
        for (Object object : hashSet) {
            bufferedWriter.write((String) object + "\n");
        }
        bufferedWriter.close();
        System.out.println("Process done!");
    }


}
