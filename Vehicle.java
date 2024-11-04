
/*  Vehicle.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: The Vehicle class is an abstract class for creating vehicles 
    in the simulation. It handles random position generation and size 
    calculation, as well as drawing the vehicles using the shapes defined in 
    the subclasses.
*/

import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public abstract class Vehicle{

    protected SimulationModel model; // The simulation model
    protected Polygon shape; // Shape of vehicle
    protected Color[] fill_colors; // Colors to fill the vehicle
    protected Color[] outline_colors; // Colors to outline the vehicle
    protected Color[] id_colors = {Color.white, Color.black}; // Colors for id
    private Random random = new Random(); // Random number generator
    protected int size; 
    // private double rand_x_val;
    // private double rand_y_val;
    protected double direction;
    protected double speed;
    protected Point2D.Double pos; // Vehicle position

    public Vehicle(SimulationModel m) {
        model = m;

        size = (random.nextInt(3) + 1);
        // rand_x_val = random.nextDouble();
        // rand_y_val = random.nextDouble();
        direction = random.nextInt(0, 360);
        speed = random.nextInt(10, 20);
        pos = new Point2D.Double();

        calcVehiclePosition();
    }

    public void setSpeed(int new_speed){
        speed = new_speed;
    }

    public Point2D.Double getPos(){
        return pos;
    }

    public void tick() {
        // Update position based on speed and direction
        pos.x += speed * Math.cos(Math.toRadians(direction)); 
        pos.y += speed * Math.sin(Math.toRadians(direction));

        if (!inBounds(pos.x, pos.y)) {
            changeDirection();
        }
    }

    // Calculates the position of the vehicle from the random doubles provided
    public void calcVehiclePosition(){
        double diameter = model.getBoundary().getWidth();
        int center_x = model.getRadarWidth()/2;
        int center_y = model.getRadarHeight()/2;

        double rand_x_val = random.nextDouble();
        double rand_y_val = random.nextDouble();
        
        double radar_radius = (diameter / 2);
        double angle = rand_x_val * 2 * Math.PI;
        double radius = rand_y_val * radar_radius;

        pos.setLocation(center_x + (radius * Math.cos(angle)), center_y + (radius * Math.sin(angle)));
    }

    public void drawVehicle(Graphics2D g2d) {
        Shape transformed_shape = transformVehicle();

        // Draw the ID of the vehicle
        g2d.setColor(id_colors[model.getColorMode()]);
        drawID(g2d);

        // Draw the vehicle shape
        g2d.setColor(fill_colors[model.getColorMode()]);
        g2d.fill(transformed_shape);
        g2d.setColor(outline_colors[model.getColorMode()]);
        g2d.draw(transformed_shape);
    }

    // Transforms the shape of the vehicle for size, position, and direction
    protected Shape transformVehicle(){
        double vehicle_size = ((Math.min(model.getRadarWidth(), model.getRadarHeight()) * 0.005) + size / 2);

        AffineTransform at = new AffineTransform();
        at.translate(pos.x, pos.y);
        at.scale(vehicle_size, vehicle_size);
        at.rotate(Math.toRadians(direction));
        return at.createTransformedShape(shape);
    }

    protected boolean inBounds(double x, double y){
        return model.getBoundary().contains(x, y);
    }

    public int getSize(){
        return size;
    }

    public void changeDirection(){
        direction = direction - 150;
    }

    public Polygon getShape(){
        return shape;
    }

    public boolean checkCollision(Vehicle v) {
        Shape this_shape = transformVehicle();
        Shape other_shape = v.transformVehicle();
        return this_shape.getBounds2D().intersects(other_shape.getBounds2D());
    }

    public boolean isClicked(Point point) {
        // Transforms the shape in order to account for the initial zoom scaling
        AffineTransform at = new AffineTransform();
        at.translate(model.getRadarWidth()/2, model.getRadarHeight()/2);
        at.scale(model.getZoomFactor(), model.getZoomFactor());
        at.translate(-model.getRadarWidth()/2, -model.getRadarHeight()/2);
        Shape transformed_vehicle = at.createTransformedShape(transformVehicle());

        return transformed_vehicle.contains(point);
    }
    

    // Abstract methods, to be implemented by subclasses
    abstract public void drawID(Graphics2D g2d);
    abstract public void setActive();
    abstract public void resetIDs();

    abstract public void handleCollision(Vehicle v);
    abstract public void handleShipCollision(Vehicle v);
    abstract public void handleFishCollision(Vehicle v);
    abstract public void handleFishTrapCollision(Vehicle v);
}
