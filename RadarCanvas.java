/*  RadarCanvas.java
    Name: Kalyn Muhlenberg
    Email: Kalyn.muhlenberg@tufts.edu
    Description: RadarCanvas is a class that extends JPanel and provides the 
    canvas for displaying the radar in the simulation. 
*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class RadarCanvas extends JPanel implements MouseListener{

    private SimulationModel model; // The simulation model
    private Color[][] colors; // Colors for radar
    private Boolean is_fish_radar; // true if fish radar, false if ship radar

    public RadarCanvas(SimulationModel m, Color[][] c, Boolean is_fish_radar) {
        model = m;
        colors = c;
        this.is_fish_radar = is_fish_radar;

        setPreferredSize(new Dimension(2200,2200));
        addMouseListener (this);
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double scale = model.getZoomFactor();
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(scale, scale);
        g2d.translate(-getWidth() / 2, -getHeight() / 2);

        this.setBackground(colors[model.getColorMode()][0]);

        model.paintRadars(g2d, colors, is_fish_radar);
	}

    // Mouse pressed listener
    public void mousePressed (MouseEvent event) {
        System.out.println ("Mouse down at " + event.getPoint().x + ", " + event.getPoint().y);
        model.radarClicked(event.getPoint());
    }

    public void mouseReleased (MouseEvent event) {}
    public void mouseClicked (MouseEvent event) {}
    public void mouseEntered (MouseEvent event) {}
    public void mouseExited (MouseEvent event) {}
}

