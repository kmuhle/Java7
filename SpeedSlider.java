/*  SpeedSlider.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: This class is a slider component that is used to adjust the 
    player's movement speed. 
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SpeedSlider extends JSlider implements ChangeListener {

    
    private SimulationModel model; // The simulation model

    public SpeedSlider(SimulationModel m) {
        super(JSlider.HORIZONTAL, 3, 13, 8);
        model = m;

        setMajorTickSpacing(1);
        setSnapToTicks(true);
        setPaintTicks(true);

        setPreferredSize(new Dimension(120, 20));

        addChangeListener(this);
    }

    public void stateChanged(ChangeEvent e) {
        int speed = getValue();
        System.out.println("Speed is now " + speed);

        // Update zoom factor in simulation model
        model.setPlayerSpeed(speed);
    }
}
