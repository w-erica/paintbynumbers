package edu.upenn.cis120.finalprj.support;

import javax.swing.*;
import java.awt.*;
import java.awt.Color; // explicitly imported because IntelliJ complained if I didn't

/**
 * Class that represents a single pixel. Extends JComponent.
 */
public class Pixel extends JComponent {
    // instance variables -- backend stuff (but necessary to determine how it looks in frontend)
    private boolean isColored;
    private boolean isHighlighted;
    private int colorTag;

    // Constructor
    public Pixel(int colorTag, Color color) {
        isColored = false;
        isHighlighted = false;
        this.colorTag = colorTag;
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
}
