/*  SimulationModel.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: 
    The SimulationModel class is responsible for managing the state of the 
    simulator and acts as a "manager" between classes. It keeps track of the
    current color mode, zoom factor, vehicles on the radars, etc. It also helps 
    to paint the radars given the graphics from the RadarCanvas class.
*/

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.geom.*;

public class SimulationModel implements ActionListener{

    private int MAX_RADAR = 1600; // Max radar circle radius

    private JFrame window;
    private ControlPanel control_panel;
    private SimPanel sim_panel;
    private StateHandler state_handler;
    private RulesPopUp rules_popup;
    private GameOverPopUp game_over_popup;
    private FishTrapDialogBox fish_trap_store;
    private RadarCanvas fish_radar;
    private RadarCanvas ship_radar;
    private Ellipse2D.Double radar_boundary; // Boundary for vehicles
    private int color_mode_index; // 0 for dark mode, 1 for light mode
    private double zoom_factor; // Zoom factor for sizing 
    private ArrayList<Vehicle> fishes; // Vehicle array for fish radar
    private ArrayList<Vehicle> ships; // Vehicle array for ship radar
    private Timer timer;
    private Boolean sim_active; // Bool for if simulation is active or not
    private int player_speed = 8;

    public SimulationModel(JFrame window) {
        this.window = window;
        color_mode_index = 0; // Defaults to dark mode
        zoom_factor = 1.0; // Default zoom factor of 1
        sim_active = false;
        fishes = new ArrayList<Vehicle>();
        ships = new ArrayList<Vehicle>();
        state_handler = new StateHandler(this);
        rules_popup = new RulesPopUp(this, window);
        SwingUtilities.invokeLater(() -> {
            toggleRulesPopUp();
        });

        setupKeyBinding();
    }

    // Sets the necessary panels and radars
    public void setPanels(ControlPanel c, SimPanel s){
        control_panel = c;
        fish_trap_store = control_panel.getFishTrapStore();

        sim_panel = s;
        fish_radar = sim_panel.getFishRadar();
        ship_radar = sim_panel.getShipRadar();
    }


    // Action Performed
    public void actionPerformed(ActionEvent e) {
        for (Vehicle fish : fishes){
            fish.tick();
        }
        for (Vehicle ship : ships){
            ship.tick();
        }
        checkCollisions();
        repaintRadars();
    }





    /**** GETTERS ****/

    public StateHandler getStateHandler(){
        return state_handler;
    }

    public int getRadarWidth(){
        return fish_radar.getWidth(); // Radars are same size
    } 

    public int getRadarHeight(){
        return fish_radar.getHeight(); // Radars are same size
    }

    public int getColorMode(){
        return color_mode_index;
    }

    public double getZoomFactor(){
        return zoom_factor;
    }

    public int getPlayerSpeed(){
        return player_speed;
    }

    public boolean isSimActive(){
        return sim_active;
    }

    // Get the radar boundary aka the largest radar circle
    public Ellipse2D getBoundary(){
        int center_x = getRadarWidth()/2;
        int center_y = getRadarHeight()/2;
        int x = center_x + (-MAX_RADAR / 2);
        int y = center_y + (-MAX_RADAR / 2);
        radar_boundary = new Ellipse2D.Double(x, y, MAX_RADAR, MAX_RADAR);
        return radar_boundary;
    }





    /**** SETTERS ****/

    public void setZoomFactor(double zoom){
        zoom_factor = zoom;
        repaintRadars();
    }

    public void setPlayerSpeed(int speed){
        if(ships.size() > 0){
            ships.get(0).setSpeed(speed);
        }
        player_speed = speed;
    }

    // Changes the color mode and refreshes radar displays
    public void applyColorMode(int mode){
        color_mode_index = mode;
        repaintRadars();
    }





    /**** SIMULATION CONTROL METHODS ****/

    // Pause or play simulation depending on current state
    public boolean pausePlaySimulation(){
        if(!sim_active){ 
            timer = new Timer(500, this); 
            timer.start(); // Starts the timer
            sim_active = true; // Marks the simulation as active
        } else {
            timer.stop(); // Stops the timer
            sim_active = false; // Marks the simulation as inactive
        }
        control_panel.togglePausePlayControls(sim_active);
        return sim_active;
    }

    // Pause or play simulation depending on given bool
    public boolean pausePlaySimulation(boolean pause){
        if(!sim_active && !pause){ 
            timer = new Timer(500, this); 
            timer.start(); // Starts the timer
            sim_active = true; // Marks the simulation as active
        } else if (sim_active && pause){
            timer.stop(); // Stops the timer
            sim_active = false; // Marks the simulation as inactive
        }
        control_panel.togglePausePlayControls(sim_active);
        return sim_active;
    }

    // Restart the simulation over
    public void restartSimulation(){
        // dispose of old game over popup if necessary
        if(game_over_popup != null){
            game_over_popup.setVisible(false);
            game_over_popup.dispose();
        }

        // stop of old timer if necessary
        if(timer != null && timer.isRunning()){
            timer.stop();
        }

        // Resets the simulation stats, arrays, and adds new vehicles
        state_handler.resetStats();

        Ship temp_ship = new Ship(this);
        temp_ship = temp_ship.getActive();
        temp_ship.resetIDs(); // Resets the IDs to start from 0
        temp_ship.setInactive(); // Sets the active ship inactive

        Fish temp_fish = new Fish(this);
        temp_fish.resetIDs(); // Resets the IDs to start from 0

        // Clears current vehicle arrays
        fishes.clear();
        ships.clear();

        // Adds lots of fishes / ships to each array
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));
        fishes.add(new Fish(this));

        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));
        ships.add(new Ship(this));

        sim_active = false;
        pausePlaySimulation();
    }

    // Stops simulation
    public void stopSimulation(){
        timer.stop();
        restartSimulation();
    }





    /**** PAINTING THE RADARS ****/
    
    public void repaintRadars(){
        fish_radar.repaint();
        ship_radar.repaint();
    }

    public void paintRadars(Graphics2D g2d, Color[][] colors, Boolean is_fish_radar){
        drawRadarCircles(g2d, colors); // Draw radar circles
        drawRadialLines(g2d, colors); // Draw radar radial lines

        if (is_fish_radar){
            for (Vehicle fish : fishes){
                fish.drawVehicle(g2d);
            }
        } else {
            for (Vehicle ship : ships){
                ship.drawVehicle(g2d);
            }
        }
    }

    private void drawRadarCircles(Graphics2D g2d, Color[][] colors){
        int center_x = getRadarWidth()/2;
        int center_y = getRadarHeight()/2;

        for (int i = MAX_RADAR; i >= 100; i -= 100){
            g2d.setColor(colors[color_mode_index][2]);
            int x = center_x + (-i / 2);
            int y = center_y + (-i / 2);

            if (i == MAX_RADAR) { g2d.setColor(Color.red); }
            
            g2d.drawOval(x, y, i, i);
            getBoundary();
        }
    }
    
    private void drawRadialLines(Graphics2D g2d, Color[][] colors){
        g2d.setColor(colors[color_mode_index][2]);
        int center_x = getRadarWidth()/2;
        int center_y = getRadarHeight()/2;

        // Calculating the radius given the zoom factor
        double radius = (radar_boundary.getWidth() / 2) + 30;

        // Draw lines at 30-degree intervals
        for (int degree = 0; degree < 360; degree += 30) {
            // Math to get radians and center point
            double radian = Math.toRadians(degree);
            int endX = center_x + (int)(radius * Math.cos(radian));
            int endY = center_y + (int)(radius * Math.sin(radian)); 

            g2d.drawLine(center_x, center_y, endX, endY);
        }
    }

    public void recolorPlayer(Color new_color){
        Ship player_ship = new Ship(this);
        player_ship = player_ship.getActive();
        player_ship.setColor(new_color);
        repaintRadars();
    }

    



    /**** COLLISION CHECKING METHODS ****/

    public void checkCastLineFishCollisions(){
        for(int i = 0; i < fishes.size(); i++){
            for(int j = i + 1; j < fishes.size(); j++){
                if(fishes.get(i).checkCollision(fishes.get(j))){
                    fishes.get(i).handleCollision(fishes.get(j));
                }
            }
        }
    }

    public void checkCollisions(){
        Ship player_ship = new Ship(this);
        player_ship = player_ship.getActive();

        for(int i = 0; i < ships.size(); i++){
            for(int j = i + 1; j < ships.size(); j++){
                if(ships.get(i).checkCollision(ships.get(j))){
                    ships.get(i).handleCollision(ships.get(j));
                }
            }
        }
    }

    public void remove(Vehicle v){
        fishes.remove(v);
        ships.remove(v);
    }

    public void caughtNewFish(int fish_size){
        state_handler.updateFishCaught(1);
        state_handler.updateMoneyMade(fish_size * 100);

        if(fishes.size() <= 3){
            fishes.add(new Fish(this));
        } else if(fishes.size() <= 15){
            if(new Random().nextBoolean()){
                fishes.add(new Fish(this));
            }
        }
    }

    public void hitShip(){
        state_handler.updateLivesLeft(-1);
    }





    /**** USER SHIP SELECTION METHODS ****/

     public void radarClicked(Point point){
        if(fish_trap_store.isFishTrapStoreOpen()){
            FishTrap curr_trap = fish_trap_store.getCurrTrap();
            curr_trap.setPos(point);
        }
        for (Vehicle ship : ships){
            if(ship.isClicked(point)){
                ship.setActive();
            }
        }
    }
    
    public void updatePlayerShip(Vehicle old_player, Vehicle new_player){
        fishes.remove(old_player);
        fishes.add(new_player);
    }





    /**** GAMESTATE POPUP METHODS ****/

    public void toggleRulesPopUp(){
        rules_popup.setVisible(!rules_popup.isVisible());
    }

    // public void toggleGameOverPopUp(){
    //     game_over_popup.setVisible(!game_over_popup.isVisible());
    // }

    public void updateRulesPopUp(){
        rules_popup.changeButtons();
        toggleRulesPopUp();
    }

    public void gameOver(int fish_caught, int money_made){
        pausePlaySimulation(true);
        game_over_popup = new GameOverPopUp(this, window, fish_caught, money_made);
        game_over_popup.setVisible(true);
    }







    /**** FISHTRAP STORE & OTHER CONTROL PANEL METHODS ****/

    public void toggleFishTrapStore(){
        fish_trap_store.toggleFishTrapStore();
        if(fish_trap_store.isFishTrapStoreOpen()){
            FishTrap new_trap = fish_trap_store.makeFishTrap();
            fishes.add(new_trap);
        } else {
            fishes.remove(fish_trap_store.getCurrTrap());
        }
    }

    public void toggleFishTrapStoreButton(){
        control_panel.toggleFishTrapStoreButton();
    }

    public void purchaseConfirmed(){
        FishTrap new_trap = fish_trap_store.getCurrTrap();
        new_trap.makePurchase();

        state_handler.updateMoneyMade(-(new_trap.getSize() * 100));
        fish_trap_store.toggleFishTrapStore();
    }

    public void toggleColorPopUp(){
        control_panel.toggleColorPopUp();
    }





    /**** KEYBINDING ****/

    private void setupKeyBinding() {
        KeyboardFocusManager manager = 
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED 
                            && e.getKeyCode() == KeyEvent.VK_C) {
                    checkCastLineFishCollisions();
                    return true;
                }
                return false;
            }
        });
    }
}
