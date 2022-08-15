package edu.upenn.cis120.finalprj.support;

import java.util.LinkedList;
import java.util.List;
import java.awt.Color;

/**
 * Not to be confused with the Java Color class, this is a color class specifically
 * for Paint By Numbers.
 */
public class PBNColor {
    private int colorTag;
    private Color color;
    private boolean isHighlighted;
    private List<Pixel> pixels;

    // Constructor
    public PBNColor(Color color, int colorTag, Pixel firstPixel) {
        isHighlighted = false;
        this.color = color;
        this.colorTag = colorTag;
        pixels = new LinkedList<Pixel>();
        pixels.add(firstPixel);
    }

    // Getters
    public int getColorTag() {
        return colorTag;
    }

    public Color getColor() {
        return color;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public List<Pixel> getPixels() {
        return new LinkedList<Pixel>(pixels);
    }

    /**
     * Adds p to the color's list of pixels.
     * @param p: pixel to add
     */
    public void addPixel(Pixel p) {
        pixels.add(p);
    }

    /**
     * Toggles highlighted state of all pixels in this PBNColor's list of pixels
     */
    public void toggleHighlight() {
        for (Pixel p: pixels) {
            p.toggleHighlight();
        }
    }
}
