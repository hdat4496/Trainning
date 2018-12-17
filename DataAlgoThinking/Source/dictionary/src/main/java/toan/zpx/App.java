package toan.zpx;

import java.util.Scanner;

public class App 
{
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args )
    {
//        DataStructure dataStructure = new Bloomfilter();
        DataStructure dataStructure = new Cuckoofilter();
//        DataStructure dataStructure = new Hashset();
        try {
            long startTime = System.nanoTime();
            dataStructure.loadData();
            String word = "";

            do {
                System.out.print("Please enter word for check or -1 to exit: ");
                word = scan.nextLine().toLowerCase();

                if (word.equals("-1")) {
                    System.out.println("Exit");
                    break;
                } else {
                    System.out.println("[" + word + "] " + (dataStructure.contains(word) ? " exists!" : "does not exist!"));
                }

            } while (true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
