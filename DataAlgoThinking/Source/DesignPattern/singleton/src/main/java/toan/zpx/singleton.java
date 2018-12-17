package toan.zpx;

public class singleton {
    private static singleton instance = new singleton();

    private singleton() {}

    public static singleton getInstance() {
        if(instance == null){
            instance = new singleton();
        }
        return instance;
    }

    public void connect() {
        System.out.println("This is singleton!");
    }
}
