/*  SimPanel.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: 
    This SimPanel class extends JTabbedPane to create the tabbed pane that 
    holds the simulation radars. It also sets up keybindings to switch 
    between the tabs with space. 
*/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SimPanel extends JTabbedPane implements ChangeListener{
    private SimulationModel model;

    private RadarCanvas ship_radar;
    private RadarCanvas fish_radar;

    private JScrollPane fish_radar_scroll;
    private JScrollPane ship_radar_scroll;

    public SimPanel(SimulationModel m) {
        setPreferredSize(new Dimension(1600,1600));
        model = m;

        /* 
        Arrays of colors used in each mode. Array colors are in order of 
        Background color, text color, and radar lines
        */ 
        Color[][] fish_colors = {
                    {Color.black, 
                        Color.white, 
                        new Color(178, 250, 152)},  // Light Purple
                    {new Color(138, 214, 131),  // Lightish Green
                        Color.black, 
                        Color.black}};  // Dark Purple

        Color[][] ship_colors = {
                    {new Color(2, 6, 115), // Dark Blue
                        Color.white, 
                        Color.white}, // Light Purple
                    {new Color(0, 196, 235), // Light Blue
                        Color.black, 
                        Color.black}};  // Dark Purple

        // Create the ship radar and fish radar
        fish_radar = new RadarCanvas(model, fish_colors, true);
        ship_radar = new RadarCanvas(model, ship_colors, false);


        // Make scroll panes for each radar
        fish_radar_scroll = new JScrollPane(fish_radar, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        ship_radar_scroll = new JScrollPane(ship_radar, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Make the scroll bars scroll together
        fish_radar_scroll.getHorizontalScrollBar().setModel(ship_radar_scroll.getHorizontalScrollBar().getModel());
        fish_radar_scroll.getVerticalScrollBar().setModel(ship_radar_scroll.getVerticalScrollBar().getModel());


        addTab("Ship Radar", ship_radar_scroll);
        addTab("Fish Radar", fish_radar_scroll);

        centerScrolls(ship_radar_scroll);
        centerScrolls(fish_radar_scroll);

        setupKeyBinding();
        addChangeListener (this);
    }

    // Setting a key binding for the space bar
    private void setupKeyBinding() {
        KeyboardFocusManager manager = 
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED 
                            && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    switchTabs();
                    return true;
                }
                return false;
            }
        });
    }

    // Switches between the fish and ship radar tabs
    private void switchTabs() {
        int currentTab = this.getSelectedIndex();
        int totalTabs = this.getTabCount();
        int nextTab = (currentTab + 1) % totalTabs;
        this.setSelectedIndex(nextTab);
        System.out.println("Space bar pressed. Switching tabs.");
    }

    public RadarCanvas getFishRadar(){
        return fish_radar;
    }

    public RadarCanvas getShipRadar(){
        return ship_radar;
    }

    public void centerScrolls(JScrollPane radar_scroll){
        SwingUtilities.invokeLater(() -> {
            Rectangle bounds = radar_scroll.getViewport().getViewRect();

            JScrollBar horizontal = radar_scroll.getHorizontalScrollBar();
            horizontal.setValue((horizontal.getMaximum() - bounds.width) / 2);

            JScrollBar vertical = radar_scroll.getVerticalScrollBar();
            vertical.setValue((vertical.getMaximum() - bounds.height) / 2);
        });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        model.toggleFishTrapStoreButton();
    }
}
