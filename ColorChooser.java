/*  
    ColorChooser.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: This class creates a JColorChooser. It listens for changes 
    in the selected color and sends the color to the SimulationModel to 
    update the players color.
*/

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class ColorChooser extends JColorChooser implements ChangeListener {
    private SimulationModel model;

    public ColorChooser(SimulationModel m) {
        model = m;
        
        // Gets rid of the default preview panel
        setPreviewPanel(new JPanel());

        getSelectionModel().addChangeListener(this);
    }

    // Changes the drawings current color to the selected color
    public void stateChanged(ChangeEvent e) {
        Color new_color = getColor(); 
        System.out.println ("Color changed to " + new_color);
        model.recolorPlayer(new_color);
    }
}

