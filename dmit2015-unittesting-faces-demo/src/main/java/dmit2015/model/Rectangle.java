package dmit2015.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class model a Rectangle shape.
 *
 * @author Sam Wu
 * @version 2024.02.06
 */
public class Rectangle {

    @Getter @Setter
    private double length;
    @Getter @Setter
    private double width;

    public double area() {
        return length * width;
    }

    public double perimeter() {
        return 2 * (length + width);
    }
}
