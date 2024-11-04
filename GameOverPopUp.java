/*  GameOverPopUp.java
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

public class GameOverPopUp extends JDialog {
    private Button play_button;
    private Button exit_button;
    private JPanel start_buttons;
    private JLabel title;
    private JTextPane text;

    public GameOverPopUp(SimulationModel m, JFrame frame, int fish_caught, int money_made) {
        super(frame, "Game Over", true);
        getContentPane().setBackground(new Color(84, 9, 2));

        setLayout(new BorderLayout());

        title = new JLabel("Game Over!", SwingConstants.CENTER);
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

        SimpleAttributeSet centered = new SimpleAttributeSet();
        StyleConstants.setAlignment(centered, StyleConstants.ALIGN_CENTER);

        // Apply centered alignment to the entire document
        doc.setParagraphAttributes(0, doc.getLength(), centered, false);


        try {
            if(fish_caught <= 5){
                doc.insertString(doc.getLength(), "Fishing can be tough! It seems the fish had the upper hand this time. Don’t give up! \n\n", normalStyle);
            }
            else if(fish_caught <= 15){
                doc.insertString(doc.getLength(), "A modest catch! You managed to snag a few fish, but there's plenty more out there waiting for you!\n\n", normalStyle);
            }
            else if(fish_caught <= 25){
                doc.insertString(doc.getLength(), "Nice work! You managed to catch a good haul of fish. Keep honing those fishing skills!\n\n", normalStyle);
            }
            else if(fish_caught <= 40){
                doc.insertString(doc.getLength(), "Great job! You’re really racking up the catches! Keep fishing, and soon you'll be a legend in these waters!\n\n", normalStyle);
            }
            else{
                doc.insertString(doc.getLength(), "Incredible! You’ve mastered the art of fishing! The ocean is no match for your expertise!\n\n", normalStyle);
            }

            doc.insertString(doc.getLength(), "Your Stats:\n\n", titleStyle);
            doc.insertString(doc.getLength(), "Fish Caught: ", subtitleStyle);
            doc.insertString(doc.getLength(), Integer.toString(fish_caught) + "\n\n", normalStyle);
            doc.insertString(doc.getLength(), "Money Made: ", subtitleStyle);
            doc.insertString(doc.getLength(), "$" + Integer.toString(money_made), normalStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }


        Border textBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        text.setBorder(textBorder);
        text.setOpaque(false); 
        text.setEditable(false);
        text.setFocusable(false);
    
        start_buttons = new JPanel();
        start_buttons.setLayout(new FlowLayout());
        start_buttons.setOpaque(false); 
        
        play_button = new Button(m, "Play Again", "Play Again button pressed!");
        start_buttons.add(play_button);

        exit_button = new Button(m, "Exit", "Exit button pressed");
        start_buttons.add(exit_button);

        add(title, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);
        add(start_buttons, BorderLayout.SOUTH);

        setForegroundColors(new Component[] {title, text});

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setLocationRelativeTo(frame);
            }
            public void componentMoved(ComponentEvent e) {
                setLocationRelativeTo(frame);
            }
        });

        setSize(300, 300);
        setLocationRelativeTo(frame);
        setUndecorated(true);
        setAlwaysOnTop(true);
        // setVisible(true);
    }

    private void setForegroundColors(Component[] components){
        for(Component comp : components){
            comp.setForeground(Color.white);
        }
    }
}
