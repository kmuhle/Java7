/*  Fish.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: The Fish class extends the abstract Vehicle class and 
    represents fish objects in the simulation. Each fish has a unique ID, 
    color schemes for different modes, and creates a unique shape to be drawn 
    by it's parent. 
*/

import java.awt.*;

public class Fish extends Vehicle{
    static int next_id = 0; // Next possible ID
    private int id;
    
    public Fish(SimulationModel m){
        super(m);
        makeShape();

        fill_colors = new Color[] {Color.green, new Color(235, 0, 196)};
        outline_colors = new Color[] {Color.black, new Color(138, 214, 131)};

        id = next_id;
        next_id++;
    }

    private void makeShape(){
        shape = new Polygon();
        int[][] points = {{2,0}, {1, 1}, {0, 1}, {-1, 0}, {-2, 1}, {-2, -1}, {-1,0}, {0, -1}, {1,-1}};

        for(int[] point : points){
            shape.addPoint(point[0], point[1]);
        }
    }

    public void drawID(Graphics2D g2d){
        Graphics2D temp_g2d = (Graphics2D) g2d.create();
        temp_g2d.translate(pos.x, pos.y);
        temp_g2d.rotate(Math.abs(Math.toRadians(direction)));

        temp_g2d.drawString("ID: " + id, -20, -15);
        temp_g2d.dispose();
    }

    public void resetIDs(){
        next_id = 0;
    }

    public void setActive(){}

    public void handleCollision(Vehicle v){
        v.handleFishCollision(this);
    }

    // Collisions with ships
    public void handleShipCollision(Vehicle v){
        if(((Ship)v).isActiveShip()){
            model.remove(this);
            model.caughtNewFish(size);
        }
    }

    // Collisions with other fish
    public void handleFishCollision(Vehicle v){
        direction -= 100;
    }

    // Collisions with fishtraps (to be handled by trap)
    public void handleFishTrapCollision(Vehicle v){
        v.handleFishCollision(this);
    }
}
