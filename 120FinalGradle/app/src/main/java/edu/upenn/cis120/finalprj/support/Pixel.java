package edu.upenn.cis120.finalprj.support;

import javax.swing.*;
import java.awt.*;
import java.awt.Color; // explicitly imported because IntelliJ complained if I didn't

/**
 * Class that represents a single pixel. Extends JComponent.
 */
public class Pixel extends JComponent {
    // instance variables
    private boolean isColored;
    private boolean isHighlighted;
    private int colorTag;
    private Color color;

    // class variables
    public static final int SIDELENGTH; // current side length in pixels.
    private static final Font FONT;
    private static final Color highlight;

    // initialize pixel size, number size, and the color for highlighting
    static {
        SIDELENGTH = 16;
        FONT = new Font("Dialog", 1, SIDELENGTH * 3 / 4);
        highlight = new Color(235, 64, 52);
    }

    // Constructor
    public Pixel(int colorTag, Color color) {
        isColored = false;
        isHighlighted = false;
        this.colorTag = colorTag;
        this.color = color;
    }

    // Getters
    public boolean isColored() {
        return isColored;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public int getColorTag() {
        return colorTag;
    }

    public Color getColor() {
        return color;
    }

    // returns whether its color was actually changed or not

    /**
     * Turns the isColored field of this Pixel to true.
     * @return Whether this is the first time this Pixel is being
     *         colored (used in determining whether the game is done).
     */
    public boolean color() {
        boolean firstTimeColored = !isColored;
        isColored = true;
        return firstTimeColored;
    }

    /**
     * Toggles highlighted state of this Pixel
     */
    public void toggleHighlight() {
        isHighlighted = !isHighlighted;
    }

    // JComponent things
    @Override
    public void paintComponent(Graphics g) {
        if (isColored) {
            g.setColor(color);
            g.fillRect(0, 0, SIDELENGTH, SIDELENGTH);
        } else {
            g.setColor(Color.black);
            g.drawRect(0, 0, SIDELENGTH, SIDELENGTH);
            g.setFont(FONT);
            g.drawString(Integer.toString(colorTag), 1, SIDELENGTH - 1);
        }
        if (isHighlighted) {
            g.drawRect(1, 1, SIDELENGTH - 1, SIDELENGTH - 1);
            g.setColor(highlight);
            g.drawRect(0, 0, SIDELENGTH, SIDELENGTH);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIDELENGTH, SIDELENGTH);
    }
}
