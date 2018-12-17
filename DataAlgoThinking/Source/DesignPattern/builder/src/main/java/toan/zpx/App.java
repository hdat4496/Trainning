package toan.zpx;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Person person = new Person.Builder()
                .name("ToanHDA")
                .age(22)
                .languages(Arrays.asList("Vietnam", "English"))
                .build();
        System.out.print(person.toString());
    }
}
