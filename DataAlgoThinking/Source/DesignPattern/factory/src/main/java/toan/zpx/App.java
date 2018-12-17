package toan.zpx;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CarFactory carFactory = new CarFactory();
        Car car = carFactory.getCar(CarType.FERARU);
        car.showInfo();
    }
}
