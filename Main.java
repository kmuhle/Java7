/* 	Main.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: Main class of the Fishing Simulator. This class calls the
    MenuBar, SimPanel, and ControlPanel classes to set up the contents of the 
    window. 
*/

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ComponentListener{

    private Container content;
    private JDialog controls_popup;
    private ControlPanel controls;
    
    public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
    
    public Main () { 
		// Window setup
		setSize (1600, 800); 
		setDefaultCloseOperation (EXIT_ON_CLOSE);
        setBackground(Color.black);

        // Set up content pane
        content = getContentPane();
        content.setLayout(new BorderLayout());
        // content.setBackground(new Color(211, 211, 211)); 
        content.setBackground(new Color(244, 232, 252)); 
        

        // Create the simulation model
        SimulationModel sim_model = new SimulationModel(this);

        // Create and add title
        JLabel title = 
                new JLabel("FISHING SIMULATOR", SwingConstants.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        // Creating and setting up menu bar
        MenuBar menu_bar = new MenuBar(sim_model);
        setJMenuBar(menu_bar);

        // Make a SimPanel to hold the radars
        SimPanel tab_radar_pane = new SimPanel(sim_model);
        content.add(tab_radar_pane, BorderLayout.CENTER);
        

        // Set up and add control panel
        controls = new ControlPanel(sim_model);
        content.add(controls, BorderLayout.EAST);

        controls_popup = new JDialog(this, false);
        controls_popup.setSize(new Dimension(150, 350));
        controls_popup.setAlwaysOnTop(true);
        controls_popup.setResizable(false);
        
        sim_model.setPanels(controls, tab_radar_pane);
        
        // Set visibility
        setVisible (true); 
        addComponentListener(this);
	}

    @Override
    public void componentResized(ComponentEvent e) {
        Dimension size = getSize();
        if (size.width < 500 || size.height < 400) {
            controls_popup.setVisible(true);
            content.remove(controls);
            controls_popup.add(controls);

        }
        else {
            content.add(controls, BorderLayout.EAST);
            controls_popup.setVisible(false);
        }
        
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}

    


}
