/*  ControlPanel.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: 
    This ControlPanel class extends JPanel to create the side control panel 
    of the simulation. This panel includes a player speed slider, 
    a radar zoom slider, a color chooser for the player's ship, and a 
    dialog box for purchasing and placing fishing traps. 
*/

import javax.swing.*; 
import java.awt.*;

public class ControlPanel extends JPanel{

    private SimulationModel model;

    private Button cast_line_button;
    private SpeedSlider speed_slider;
    private FishTrapDialogBox fish_trap_dialog;
    private Button fish_trap_button;
    private Button color_chooser_button;
    private JDialog color_dialog_box;

    private StatsDisplay stats;

    public ControlPanel(SimulationModel m) {
        model = m;

        // setLayout(new FlowLayout());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Making cast line button
        cast_line_button = new Button(model, "Cast Line",
                        "Cast Line button pressed.");
        cast_line_button.setEnabled(false);

        // Making speed label and slider
        JLabel speed_label = new JLabel("Adjust Speed:");
        speed_slider = new SpeedSlider(model);
        speed_slider.setEnabled(false);

        // Making zoom label and slider
        JLabel zoom_label = new JLabel("Adjust Radar Zoom:");
        ZoomSlider zoom_slider = new ZoomSlider(model);

        // Making color chooser button and dialog box
        color_chooser_button = new Button (model, "Change Color", "Player opened color chooser dialog");
        ColorChooser color_chooser = new ColorChooser(model);
        color_dialog_box = new JDialog();
        color_dialog_box.setSize(500, 300);
        color_dialog_box.add(color_chooser);

        // Making fish trap dialog box and button
        fish_trap_dialog = new FishTrapDialogBox(model, (JFrame) SwingUtilities.getWindowAncestor(this));
        fish_trap_button = new Button(model, "Buy Fish Trap", "Fish trap store opened");
        fish_trap_button.setEnabled(false);

        // Adding all buttons to same panel
        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new BoxLayout(buttons_panel, BoxLayout.Y_AXIS));
        buttons_panel.add(cast_line_button);
        cast_line_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons_panel.add(fish_trap_button);
        fish_trap_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons_panel.add(color_chooser_button);
        color_chooser_button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding all sliders to same panel
        JPanel sliders_panel = new JPanel();
        sliders_panel.setLayout(new BoxLayout(sliders_panel, BoxLayout.Y_AXIS));
        sliders_panel.add(speed_label);
        speed_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliders_panel.add(speed_slider);
        speed_slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliders_panel.add(zoom_label);
        zoom_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliders_panel.add(zoom_slider);
        zoom_slider.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding panels with widgets to control panel
        add(buttons_panel);
        add(Box.createVerticalStrut(10));
        add(sliders_panel);
        add(Box.createVerticalStrut(10));

        stats = new StatsDisplay(model.getStateHandler());
        add(stats);
    }

    public FishTrapDialogBox getFishTrapStore(){
        return fish_trap_dialog;
    }

    public void toggleFishTrapStoreButton(){
        // Sets fish trap button as enabled/disabled accordingly
        fish_trap_button.setEnabled(!fish_trap_button.isEnabled());
    }

    public void togglePausePlayControls(boolean play){
        // Toggles if the certain widgets can be used if pause/play
        speed_slider.setEnabled(play);
        cast_line_button.setEnabled(play);
    }

    public void toggleColorPopUp(){
        color_dialog_box.setVisible(!color_dialog_box.isVisible());
    }
}
