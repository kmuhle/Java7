/*  MenuBar.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: 
    The MenuBar class extends JMenuBar to create a menu bar featuring a file 
    dropdown where the user can exit the window, and a Mode dropdown where 
    the user can choose the color mode (light mode vs dark mode).
*/

import javax.swing.*; 
import java.awt.event.*;

public class MenuBar extends JMenuBar implements ActionListener{

    private SimulationModel model;

    public MenuBar(SimulationModel m) {
        model = m;

        // Create File dropdown with exit option
        JMenu menu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        menu.add(exitItem);
        this.add(menu);

        // Create Mode drop down for either light mode or dark mode
        JMenu mode_menu = new JMenu("Mode");
        JMenuItem dark_mode_item = new JMenuItem("Dark Mode");
        JMenuItem light_mode_item = new JMenuItem("Light Mode");
        dark_mode_item.addActionListener(this);
        light_mode_item.addActionListener(this);
        mode_menu.add(dark_mode_item);
        mode_menu.add(light_mode_item);
        this.add(mode_menu);

        Button pause_play_sim = new Button(model, "PAUSE", 
                        "Pause/Play Simulation button pressed");
        Button restart_sim = new Button(model, "RESTART", 
                        "Restart Simulation button pressed.");
        Button rules = new Button(model, "RULES", 
                        "Rules button pressed.");

        this.add(pause_play_sim);
        this.add(restart_sim);
        this.add(rules);
    }

    // ActionListener method for all the pull-down menu items
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals ("Exit")) {
            System.out.println("Bye!");
            System.exit (0);
        } 
        else if (e.getActionCommand().equals ("Light Mode")){
            System.out.println("Switching to light mode");
            model.applyColorMode(1);
        }
        else if (e.getActionCommand().equals ("Dark Mode")){
            System.out.println("Switching to dark mode");
            model.applyColorMode(0);
        }
    }
}
