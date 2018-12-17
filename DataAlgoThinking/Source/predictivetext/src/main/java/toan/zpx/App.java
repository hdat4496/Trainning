package toan.zpx;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args ) {
        TrieData dataset = new TrieData();
        try {
            dataset.loadData();
            System.out.printf("Press -1 to exit.\n");
            String word = "";
            do {
                System.out.print("Please enter your word: ");
                word = scan.nextLine();
                if (word.equals("-1")) {
                    System.out.println("Exit");
                    break;
                } else if (word.matches(".*[^a-zA-Z]+.*")) {
                    System.out.println("Please enter word");
                } else {
                    System.out.println(dataset.getPredictiveText(word));
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
