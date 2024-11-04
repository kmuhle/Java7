/*  RulesPopUp.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: This class extends JDialog box, and acts as a popup for the 
    rules of the game
*/

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class RulesPopUp extends JDialog{
    private Button play_button;
    private Button exit_button;
    private JPanel start_buttons;
    private JPanel continue_buttons;
    private JLabel title;
    private JTextPane text;

    public RulesPopUp(SimulationModel m, JFrame frame) {
        super(frame, "Fishing Simulator", true);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(155, 224, 186));

        title = new JLabel("Welcome to the Fishing Simulator!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        text = new JTextPane();
        StyledDocument doc = text.getStyledDocument();

        // Create the title style
        SimpleAttributeSet titleStyle = new SimpleAttributeSet();
        StyleConstants.setBold(titleStyle, true);
        StyleConstants.setUnderline(titleStyle, true);
        StyleConstants.setFontSize(titleStyle, 14);

        // Create the subtitle style
        SimpleAttributeSet subtitleStyle = new SimpleAttributeSet();
        StyleConstants.setBold(subtitleStyle, true);
        StyleConstants.setFontSize(subtitleStyle, 12);

        // Create the normal text style
        SimpleAttributeSet normalStyle = new SimpleAttributeSet();
        StyleConstants.setFontSize(normalStyle, 12);

        // Create the italic style
        SimpleAttributeSet italicStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(italicStyle, true);
        StyleConstants.setFontSize(italicStyle, 12);

        try {
            doc.insertString(doc.getLength(), "Goal\n", titleStyle);
            doc.insertString(doc.getLength(), "Catch as many fish as you can without colliding with other ships. Earn money from each fish caught and use it to buy traps that will help you catch even more! How long can you last?\n\n", normalStyle);

            doc.insertString(doc.getLength(), "Choose & Edit Your Ship\n", titleStyle);
            doc.insertString(doc.getLength(), "Switch Ships: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Click any ship to control it. Move with ", normalStyle);
            doc.insertString(doc.getLength(), "Arrow Keys.\n", italicStyle);
            doc.insertString(doc.getLength(), "Speed: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Adjust your ship's speed with the speed slider in the control panel on the right.\n", normalStyle);
            doc.insertString(doc.getLength(), "Color: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Change your ship's color using the color chooser in the control panel.\n\n", normalStyle);

            doc.insertString(doc.getLength(), "Switch Between Radars\n", titleStyle);
            doc.insertString(doc.getLength(), "Switch Radars: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Press ", normalStyle);
            doc.insertString(doc.getLength(), "Space Bar", italicStyle);
            doc.insertString(doc.getLength(), ".\n", normalStyle);
            doc.insertString(doc.getLength(), "Fish Radar: ", subtitleStyle);
            doc.insertString(doc.getLength(), "The fish radar shows all of the fish swimming below you, your active ship, and any traps you place.\n", normalStyle);
            doc.insertString(doc.getLength(), "Ship Radar: ", subtitleStyle);
            doc.insertString(doc.getLength(), "The ship radar shows all of the ships in the water — avoid them!\n\n", normalStyle);

            doc.insertString(doc.getLength(), "Catch Fish & Earn Money\n", titleStyle);
            doc.insertString(doc.getLength(), "Catch Fish: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Collide with fish and either press the 'Cast Line' button or press ", normalStyle);
            doc.insertString(doc.getLength(), "C ", italicStyle);
            doc.insertString(doc.getLength(), "on your keyboard to cast a line and catch the fish. The price of each fish is dependant on its size ($100-$300).\n", normalStyle);
            doc.insertString(doc.getLength(), "Buy Traps: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Use your money to buy traps of various sizes and place them on the Fish Radar to catch fish. Fish will be caught in the traps when they collide with them\n\n", normalStyle);

            doc.insertString(doc.getLength(), "Avoid Enemy Ships\n", titleStyle);
            doc.insertString(doc.getLength(), "Lives: ", subtitleStyle);
            doc.insertString(doc.getLength(), "You have 5 lives. Colliding with a ship costs one life. Lose all 5, and it’s game over!\n\n", normalStyle);

            doc.insertString(doc.getLength(), "Gameplay Options\n", titleStyle);
            doc.insertString(doc.getLength(), "Zoom & Pan: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Use the zoom scrollbar to zoom in and out of the radar. Use the scroll bars on the side of the radars to pan around the radars.\n", normalStyle);
            doc.insertString(doc.getLength(), "Color Mode: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Change the color mode to light or dark using the Mode dropdown in the menu bar at the top of the screen.\n", normalStyle);
            doc.insertString(doc.getLength(), "Pause/Play: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Pause or resume the simulation at any time using the Pause/Play button in the menu bar.\n", normalStyle);
            doc.insertString(doc.getLength(), "Restart: ", subtitleStyle);
            doc.insertString(doc.getLength(), "Start fresh whenever you want using the Restart button in the menu bar.\n", normalStyle);
            doc.insertString(doc.getLength(), "Review Rules: ", subtitleStyle);
            doc.insertString(doc.getLength(), "See the rules by clicking the Rules button in the menu bar.\n", normalStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }


        Border textBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        text.setBorder(textBorder);
        text.setEditable(false);
        text.setFocusable(false);
        text.setBackground(new Color(176, 235, 213));
        

        start_buttons = new JPanel();
        start_buttons.setLayout(new FlowLayout());
        
        play_button = new Button(m, "Play", "Play button pressed!");
        start_buttons.add(play_button);

        exit_button = new Button(m, "Exit", "Exit button pressed");
        start_buttons.add(exit_button);
        start_buttons.setOpaque(false);

        continue_buttons = new JPanel();
        continue_buttons.setLayout(new FlowLayout());
        
        Button continue_button = new Button(m, "Continue", "Continue button pressed!");
        continue_buttons.add(continue_button);
        continue_buttons.setOpaque(false);

        JScrollPane scroll_pane = new JScrollPane(text);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 

        add(title, BorderLayout.NORTH);
        add(scroll_pane, BorderLayout.CENTER);
        add(start_buttons, BorderLayout.SOUTH);

        setLocationRelativeTo(frame);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setVisible(false);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = frame.getWidth() * 1 / 2; 
                int height = frame.getHeight() * 3 / 4; 
                setSize(Math.max(width, 200), Math.max(height, 200));
                setLocationRelativeTo(frame);
            }
            public void componentMoved(ComponentEvent e) {
                setLocationRelativeTo(frame);
            }
        });
    }

    public void changeButtons() {
        remove(start_buttons); 
        add(continue_buttons, BorderLayout.SOUTH);
    }
}
