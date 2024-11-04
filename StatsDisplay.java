/*  StatsDisplay.java
	Name: Kalyn Muhlenberg
	Email: Kalyn.muhlenberg@tufts.edu
	Description: 
    This class is responsible for displaying player statistics. It shows the
    number of fish caught, the amount of money earned, and the remaining lives 
    of the player. The stats are updated based on the values from the 
    StateHandler
*/

import javax.swing.*;
import java.awt.*;

public class StatsDisplay extends JPanel {

    private StateHandler state_handler;

    // Labels for displaying statistics
    private JLabel num_fish_label;
    private JLabel money_label;
    private JLabel lives_label;

    public StatsDisplay(StateHandler sh) {
        state_handler = sh;
        state_handler.setStatDisplay(this);
        setLayout(new GridLayout(10, 1));

        // Create stats labels with initial values 
        num_fish_label = new JLabel("Fish Caught: 0");
        money_label = new JLabel("Money Earned: $0");
        lives_label = new JLabel("Remaining Lives: 5");

        // Add labels to Stats 
        add(num_fish_label);
        add(money_label);
        add(lives_label);

        setBorder(BorderFactory.createTitledBorder("Simulation Stats"));
    }

    // Update the stats based new values
    public void updateStats(int fish_caught, int money_made, int lives_left) {
        num_fish_label.setText("Fish Caught: " + fish_caught);
        money_label.setText("Money Earned: $" + money_made);
        lives_label.setText("Remaining Lives: " + lives_left);
    }
}
