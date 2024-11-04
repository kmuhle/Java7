/*  Ship.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: The Ship class extends the abstract Vehicle class and 
    represents ship objects in the simulation. Each ship has a unique ID, 
    color schemes for different modes, and creates a unique shape to be drawn 
    by it's parent. If a Ship is selected by the player, the movement becomes 
    controlled by the player with arrow keys
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;


public class Ship extends Vehicle{
    public enum ButtonAction {LEFT, RIGHT, UP, DOWN};
    static int next_id = 0; // Next possible ID
    private int id;

    // static int active_ship_id = 0;
    static Ship active_ship;
    static Color active_ship_color;

    private boolean just_hit = false;
    private boolean is_flash = false;

    private KeyEventDispatcher key_event_dispatcher;
    static KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    private Timer immunity_timer;
    private Timer flash_timer;

    public Ship(SimulationModel m) {
        super(m);
        makeShape();

        size = 1;

        fill_colors = new Color[] {Color.red, Color.red};
        outline_colors = new Color[] {new Color(2, 6, 115), new Color(0, 196, 235)};

        id = next_id;
        next_id++;

        // ID 0 will be the first selected player ship
        if(id == 0){
            active_ship = this;
            model.updatePlayerShip(null, this);
            direction = 90;
            speed = 8;
            pos.x = model.getRadarWidth()/2;
            pos.y = model.getRadarHeight()/2;
            setupKeyBinding();
        } else {
            while(checkCollision(active_ship)){
                calcVehiclePosition();
            }
        }
    }

    public void drawVehicle(Graphics2D g2d) {
        Shape transformed_shape = transformVehicle();

        // Draw the ID of the vehicle
        g2d.setColor(id_colors[model.getColorMode()]);
        drawID(g2d);

        // Draw the vehicle shape
        g2d.setColor(fill_colors[model.getColorMode()]);
        if(isActiveShip() && active_ship_color!=null){
            g2d.setColor(active_ship_color);
        }
        
        g2d.fill(transformed_shape);

        g2d.setColor(outline_colors[model.getColorMode()]);
        if(isActiveShip()){ 
            g2d.setColor(Color.white); 
            if(is_flash){
                g2d.setColor(new Color(110, 3, 1)); 
            }
        }
        g2d.draw(transformed_shape);
    }

    private void makeShape(){
        shape = new Polygon();
        int[][] points = {{2,0}, {1, 1}, {-1, 1}, {-2, 0}, {-1, -1}, {1, -1}};

        for(int[] point : points){
            shape.addPoint(point[0], point[1]);
        }
    }

    public void drawID(Graphics2D g2d){
        Graphics2D temp_g2d = (Graphics2D) g2d.create();
        temp_g2d.translate(pos.x, pos.y);
        // temp_g2d.rotate(Math.abs(Math.toRadians(direction)));
        temp_g2d.rotate(Math.toRadians(direction));

        temp_g2d.drawString("ID: " + id, -10, -15);
        temp_g2d.dispose();
    }

    public Ship getActive(){
        return active_ship;
    }

    // Set up new acive ship
    public void setActive(){
        if(id != active_ship.getID()){
            model.updatePlayerShip(active_ship, this);
            active_ship.setInactive();
            active_ship = this;
            speed = model.getPlayerSpeed();
            setupKeyBinding();
        }
    }

    // Clean up after old active ship
    public void setInactive(){
        Random random = new Random();
        direction = random.nextInt(0, 360);
        speed = random.nextInt(10, 20);
        manager.removeKeyEventDispatcher(key_event_dispatcher);
    }

    public void tick() {
        if(id != active_ship.getID()){
            super.tick();
        }
    }

    public int getID(){
        return id;
    }

    public boolean isActiveShip(){
        return (id == active_ship.getID());
    }

    public void resetIDs(){
        next_id = 0;
    }

    // Change player ship position 
    public void doAction (ButtonAction action) {
        double x_change = 0; 
        double y_change = 0;
        if(id == active_ship.getID() && model.isSimActive()){
            if (action == ButtonAction.UP) {
                direction = 90;
                y_change = speed * -Math.sin(Math.toRadians(direction));
            } else if (action == ButtonAction.DOWN) {
                direction = 90;
                y_change = speed * Math.sin(Math.toRadians(direction));
            } else if (action == ButtonAction.LEFT) {
                direction = 0;
                x_change = speed * -Math.cos(Math.toRadians(direction)); 
            } else if (action == ButtonAction.RIGHT){
                direction = 0;
                x_change = speed * Math.cos(Math.toRadians(direction)); 
            } 
            
            if (inBounds(pos.x + x_change, pos.y + y_change)) {
                pos.x += x_change;
                pos.y += y_change;
                model.repaintRadars();
            }
        }
    }

    // Setting key bindings for player movement (arrow keys)
    private void setupKeyBinding() {
        key_event_dispatcher = new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            doAction(ButtonAction.UP);
                            return true;
                        case KeyEvent.VK_DOWN:
                            doAction(ButtonAction.DOWN);
                            return true;
                        case KeyEvent.VK_LEFT:
                            doAction(ButtonAction.LEFT);
                            return true;
                        case KeyEvent.VK_RIGHT:
                            doAction(ButtonAction.RIGHT);
                            return true;
                    }
                }
                return false;
            }
        };
        manager.addKeyEventDispatcher(key_event_dispatcher);
    }

    public void handleCollision(Vehicle v){
        v.handleShipCollision(this);
    }

    // Collisions with other ships
    public void handleShipCollision(Vehicle v){
        if(isActiveShip() && !just_hit){
            model.hitShip();
            v.changeDirection();
            just_hit = true;
            startTimers();
        }
        else if(((Ship)v).isActiveShip()){
            v.handleShipCollision(this);
        }
        
    }

    // Collisions with fish
    public void handleFishCollision(Vehicle v){
        v.handleShipCollision(this);
    }

    // Collisions with fishtraps (to be handled by trap)
    public void handleFishTrapCollision(Vehicle v){
        v.handleShipCollision(this);
    }

    public void setColor(Color new_color){
        active_ship_color = new_color;
    }

    private void startTimers(){
        immunity_timer = new Timer(3000, e -> {
            just_hit = false;
            if (flash_timer != null && flash_timer.isRunning()) {
                flash_timer.stop();
            }
            is_flash = false;
        }); 

        flash_timer = new Timer(200, e -> {
            is_flash = !is_flash;
            model.repaintRadars();
        }); 
        flash_timer.setRepeats(true);

        immunity_timer.start();
        flash_timer.start();
    }
}
