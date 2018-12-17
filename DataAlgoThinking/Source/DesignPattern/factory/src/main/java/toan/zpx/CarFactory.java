package toan.zpx;

public class CarFactory {
    public Car getCar(CarType carType) {
        Car carCreated = null;
        switch (carType) {
            case FERARU:
                carCreated = new Ferari();
                break;
            case LAMBORGHINI:
                carCreated = new Lamborghini();
                break;
            case MERCEDER:
                carCreated = new Mercedes();
                break;
            case ROLLROYCE:
                carCreated = new RollRoyce();
                break;
        }
        return carCreated;
    }
}
