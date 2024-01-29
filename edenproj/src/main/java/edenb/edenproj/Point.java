package edenb.edenproj;

import org.springframework.data.mongodb.core.mapping.Document;

//import com.vaadin.flow.component.template.Id;

@Document(collection="draws")
public class Point 
{
    private double x;
    private double y;
    private String un;
    private String color;
    
    public Point(double x, double y, String un, String color)
    {
        this.x = x;
        this.y = y;
        this.un = un;
        this.color = color;
    }
    
     public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
    }
    
    public String getUn() {
        return un;
    }


    public void setUn(String un) {
        this.un = un;
    }

    public double getX() {
        return x;
    }


    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }


    public void setY(double y) {
        this.y = y;
    }


    @Override
    public String toString() {
        //return un + ": (" + x + "," + y + ")";
        return un + ": (" + x + "," + y + "), Color: " + color;
    }

}
