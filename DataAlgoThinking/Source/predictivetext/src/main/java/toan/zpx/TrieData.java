package toan.zpx;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TrieData {
    private Trie trie;

    public void loadData() {
        File file = new File("data.txt");
        this.trie = new Trie();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String line;
            while ((line = buffer.readLine()) != null) {
                this.trie.addWord(line);
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPredictiveText(String word) {
        ArrayList<String> prediction = (ArrayList) trie.getWords(word);
        Collections.sort(prediction, new Comparator<String>() {
            public int compare(String s1, String s2) {
                if (s1.length() > s2.length()) return 1;
                else if (s1.length() == s2.length()) return 0;
                else return -1;
            }
        });

        String result = "Predictive text: ";
        int numberPrediction = prediction.size() >= 5 ? 5 : prediction.size();

        for (int i = 0; i < numberPrediction; i++) {
            result += prediction.get(i) + " ";
        }

        return numberPrediction > 0 ? result : getPredictiveText(word.substring(0,word.length() - 1));
    }
}
