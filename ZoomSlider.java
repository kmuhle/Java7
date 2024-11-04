/*  ZoomSlider.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: This class is a slider component that is used to zoom in and
    out of the radars. The zoom factor is linked to the SimulationModel, 
    which updates the radar display accordingly.
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ZoomSlider extends JSlider implements ChangeListener {

    
    private SimulationModel model; // The simulation model
    private double zoom; // The current zoom factor

    public ZoomSlider(SimulationModel m) {
        super(JSlider.HORIZONTAL, 50, 100, 75);
        model = m;

        // Set initial zoom factor to 1.0 based on initial value (75) 
        zoom = getValue() / 75.0;

        setMajorTickSpacing(5);
        setSnapToTicks(true);
        setPaintTicks(true);

        setPreferredSize(new Dimension(50, 20));

        addChangeListener(this);
    }

    public void stateChanged(ChangeEvent e) {
        // Update zoom factor
        zoom = getValue() / 75.0; 
        System.out.println("Zoom now set to a factor of " + zoom);

        // Update zoom factor in simulation model
        model.setZoomFactor(zoom);
    }
}
