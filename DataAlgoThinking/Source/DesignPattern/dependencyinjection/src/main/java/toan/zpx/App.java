package toan.zpx;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Drawing drawing = new Drawing(null);
        Drawing drawing1 = new Drawing(null);
        drawing.preparing();
        Shape shape = new Circle();
        Shape shape1 = new Square();
        drawing.setShape(shape);
        drawing1.setShape(shape1);
        drawing.draw();
        drawing1.draw();
    }
}
