package toan.zpx;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        String key = "toan";
        HashTable hashTable = new HashTable();

        hashTable.insert(key, "toanhda");

        String value = hashTable.search(key);
        if (value == null) {
            System.out.println("No key is: " +key);
        }
        else {
            System.out.println("Value = " + value);
        }

        hashTable.insert(key, "huynhduyanhtoan");

        value = hashTable.search(key);
        if (value == null) {
            System.out.println("No key is: " +key);
        }
        else {
            System.out.println("Value = " + value);
        }

        hashTable.delete(key);
        value = hashTable.search(key);
        if (value == null) {
            System.out.println("No key is: " +key);
        }
        else {
            System.out.println("Value = " + value);
        }
    }
}
