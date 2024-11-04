/*  Button.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: 
    This Button class extends JButton to create buttons with different 
    actions within the simulation. Each button has a label and a 
    corresponding print string that is printed when the button is clicked. 
    The different buttons start the simulation, stop it, and cast a fishing
    line.
*/

import javax.swing.*;
import java.awt.event.*;

public class Button extends JButton implements ActionListener {
    private SimulationModel model;
    private String print_string;
    private String type;

    public Button (SimulationModel m, String type, String print_string) {
        model = m; // The simulation model
        this.type = type;
        this.print_string = print_string;

        // Adds button label (ie it's type)
	    setText (type);
	    addActionListener (this);
    }

    public void actionPerformed (ActionEvent e) {
        System.out.println (this.print_string);

        if (type.equals("PAUSE")){
            // Pauses/Plays the Simulation
            boolean pause_or_play = model.pausePlaySimulation();
            resetPausePlayLabel(pause_or_play);
        }
        else if (type.equals("RESTART")){
            // Restarts Simulation
            model.restartSimulation(); 
        }
        else if (type.equals("Cast Line")){
            // Cast Line and check if fish is caught
            model.checkCastLineFishCollisions(); 
        }
        else if (type.equals("Buy Fish Trap") || type.equals("Cancel")){
            // Toggles visibility of the fish trap pop up
            model.toggleFishTrapStore();
        }
        else if (type.equals("Confirm")){
            // Confirms purchase and placement of fish trap
            model.purchaseConfirmed();
        }
        else if (type.equals("Change Color")){
            // Toggles visibility of the color chooser pop up
            model.toggleColorPopUp();
        }
        else if (type.equals("Play")){
            // Confirms purchase and placement of fish trap
            model.updateRulesPopUp();
            model.restartSimulation();
        }
        else if (type.equals("Exit")){
            // Toggles visibility of the color chooser pop up
            // model.toggleRulesPopUp();
            System.exit (0);
        }
        else if (type.equals("RULES")){
            // Opens rules
            model.pausePlaySimulation(true);
            model.toggleRulesPopUp();
            
        }
        else if (type.equals("Continue")){
            model.pausePlaySimulation(false);            
            model.toggleRulesPopUp();
            
        }
        else if (type.equals("Play Again")){
            // Confirms purchase and placement of fish trap
            model.restartSimulation();
        }
    }

    private void resetPausePlayLabel(boolean pause_or_play){
        if(pause_or_play){
            setText ("PAUSE");
        } else {
            setText ("PLAY");
        }
    }
}
