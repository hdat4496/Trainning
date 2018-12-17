package toan.zpx;
public class App 
{
    public static void main( String[] args )
    {
        singleton instance = singleton.getInstance();
        instance.connect();
    }
}
