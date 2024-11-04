/*  FishTrap.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: The FishTrap class extends the abstract Vehicle class and 
    represents fishing trap objects in the simulation. Each fishing trap will 
    be able to "catch" a fish when it is collided with. 
*/

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class FishTrap extends Vehicle{
    // private int caught_fish;
    private int max_capacity;
    private boolean purchased;
    private ArrayList<Fish> caught_fish;
    
    public FishTrap(SimulationModel m, int size){
        super(m);
        makeShape();

        purchased = false;

        this.size = size;
        caught_fish = new ArrayList<Fish>();
        max_capacity = size;
        direction = 0;
        
        fill_colors = new Color[] {Color.orange, Color.orange};
        outline_colors = new Color[] {Color.orange, Color.orange};
    }

    public void setPos(Point point){
        // Create AffineTransform to account for zoom
        AffineTransform at = new AffineTransform();
        at.translate(model.getRadarWidth()/2, model.getRadarWidth()/2);
        at.scale(1/model.getZoomFactor(), 1/model.getZoomFactor()); 
        at.translate(-model.getRadarWidth()/2, -model.getRadarWidth()/2);

        Point transformed_point = new Point();
        at.transform(point, transformed_point);

        // Set the transformed position
        pos.x = transformed_point.x;
        pos.y = transformed_point.y;
    }

    public boolean isPurchased(){
        return purchased;
    }

    public void makePurchase(){
        purchased = true;
    }

    public void changeTrapSize(int new_size){
        size = new_size;
        max_capacity = new_size;
    }

    public void tick(){}

    private void makeShape(){
        shape = new Polygon();
        int[][] points = {{0,0}, {0, 5}, {5, 5}, {5, 0}};

        for(int[] point : points){
            shape.addPoint(point[0], point[1]);
        }
    }

    public void drawVehicle(Graphics2D g2d) {
        Shape transformed_shape = transformVehicle();

        // Draw the ID of the vehicle
        g2d.setColor(id_colors[model.getColorMode()]);
        drawID(g2d);

        // Draw the vehicle shape
        if(purchased){
            g2d.setColor(fill_colors[model.getColorMode()]);
            g2d.fill(transformed_shape);
        }
        g2d.setColor(outline_colors[model.getColorMode()]);
        g2d.draw(transformed_shape);
    }

    public void drawID(Graphics2D g2d){
        Graphics2D temp_g2d = (Graphics2D) g2d.create();
        temp_g2d.translate(pos.x, pos.y);
        temp_g2d.rotate(Math.abs(Math.toRadians(direction)));

        temp_g2d.drawString("Fish: " + caught_fish.size() + "/" + max_capacity, 0, 0);
        temp_g2d.dispose();
    }

    public void resetIDs(){}

    public void setActive(){}

    public void handleCollision(Vehicle v){
        v.handleFishTrapCollision(this);
    }

    // Collisions with ships
    public void handleShipCollision(Vehicle v){
        if(((Ship)v).isActiveShip()){
            for(Fish fish : caught_fish){
                model.caughtNewFish(fish.getSize());
            }
            caught_fish.clear();
        }
    }

    // Collisions with fish 
    public void handleFishCollision(Vehicle v){
        if(purchased && caught_fish.size() < max_capacity){
            caught_fish.add((Fish)v);
            model.remove(v);
        }
    }

    // Collisions with fishtraps (no effect)
    public void handleFishTrapCollision(Vehicle v){
    }
}
