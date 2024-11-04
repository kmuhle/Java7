/*  FishTrapDialogBox.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: This class extends JDialog box, and acts as a popup for the 
    player to purchase a fish trap. They can choose the type from a Combobox 
    and and set the coordinates by clicking in the radar;
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FishTrapDialogBox extends JDialog implements WindowListener {
    private SimulationModel model;
    private JComboBox<String> trap_type_combobox;
    private Button confirm_button;
    private Button cancel_button;
    private FishTrap curr_trap;

    public FishTrapDialogBox(SimulationModel m, JFrame frame) {
        super(frame, "Purchase Fish Trap", false);
        model = m;

        setLayout(new GridLayout(5, 2));
        
        add(new JLabel("Select Trap Type:"));
        String[] trapTypes = {"Small Trap ($100)", "Medium Trap ($200)", "Large Trap ($300)"};
        trap_type_combobox = new JComboBox<>(trapTypes);
        add(trap_type_combobox);

        trap_type_combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curr_trap.changeTrapSize(trap_type_combobox.getSelectedIndex() + 1); // Update the current trap size based on selection
                checkFunds();
            }
        });

        add(new JLabel("Click in radar to set position of trap"));

        confirm_button = new Button(model, "Confirm", "Fish trap purchase confirmed");
        add(confirm_button);

        cancel_button = new Button(model, "Cancel", "Fish trap purchase canceled");
        add(cancel_button);

        setSize(300, 200);
        setLocationRelativeTo(frame);
        setAlwaysOnTop(true);
        addWindowListener(this);
    }

    public void checkFunds(){
        int money = model.getStateHandler().getMoneyMade();
        int curr_cost = (trap_type_combobox.getSelectedIndex() + 1) * 100;
        confirm_button.setEnabled(money >= curr_cost);
    }

    public FishTrap makeFishTrap(){
        curr_trap = new FishTrap(model, trap_type_combobox.getSelectedIndex() + 1);
        return curr_trap;
    }

    public FishTrap getCurrTrap(){
        return curr_trap;
    }

    public void toggleFishTrapStore(){
        if(isVisible()){
            setVisible(false);
            if(!curr_trap.isPurchased()){
                model.remove(curr_trap);
            }
            curr_trap = null;
        } else {
            setVisible(true);
        }
        checkFunds();
    }

    public boolean isFishTrapStoreOpen(){
        return isVisible();
    }

    
    @Override
    public void windowClosing(WindowEvent e) {
        if (curr_trap != null && !curr_trap.isPurchased()) {
            model.remove(curr_trap);
        }
        curr_trap = null;
    }

    public void windowOpened(WindowEvent e) { }
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
