package edu.upenn.cis120.finalprj.support;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

/**
 * Models game state
 */
public class PBNModel {
    private PBNColor[] palette; // array of PBNColors (the "palette")
    private Pixel[][] canvas; // 2d array storing the pixels in the canvas
    private int pixelsTotal; // total number of pixels to color

    private boolean done; // whether it's finished or not (whether all pixels are colored or not)
    private int pixelsColored; // number of pixels currently colored
    private int currentColorTag; // currently selected color

    /**
     * Constructor. Makes a PBNModel for the image represented by bufferedImage
     * @param bufferedImage: BufferedImage object representing the image to set
     *                     up the PBNModel for
     */
    public PBNModel(BufferedImage bufferedImage) {
        int rows = bufferedImage.getHeight();
        int cols = bufferedImage.getWidth();

        // initialize the basic info fields
        pixelsTotal = rows * cols;
        pixelsColored = 0;
        done = false;

        // initialize the canvas of pixels
        canvas = new Pixel[rows][cols];

        // temporary map and a counter to help populate the colors and colortags
        Map<Integer, PBNColor> tempMap = new TreeMap<>();
        int colorTagCounter = 0;
        // for loop to populate tempMap
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Color inputColor = new Color(bufferedImage.getRGB(col, row));
                Integer inputRGB = (Integer) bufferedImage.getRGB(col, row);
                if (tempMap.containsKey(inputRGB)) {
                    PBNColor pbnColor = tempMap.get(inputRGB);
                    // make new pixel
                    Pixel newPixel = new Pixel(pbnColor.getColorTag(), pbnColor.getColor());
                    // add the new pixel to the PBNColor's list
                    pbnColor.addPixel(newPixel);
                    // add the new pixel to the canvas here
                    canvas[row][col] = newPixel;
                } else {
                    // make new pixel based on the colortagcounter
                    Pixel firstPixel = new Pixel(colorTagCounter, inputColor);
                    // make new pbncolor and add the new pixel to it
                    PBNColor newColor = new PBNColor(inputColor, colorTagCounter, firstPixel);
                    // add the color to the map
                    tempMap.put(inputRGB, newColor);
                    // add pixel to the canvas here
                    canvas[row][col] = firstPixel;
                    // increment colortagcounter so we get different tags for every color
                    colorTagCounter++;
                }
            }
        }
        // after the for loop initialize the palette array
        palette = new PBNColor[tempMap.size()];
        // for loop to populate the palette array
        for (PBNColor c: tempMap.values()) {
            palette[c.getColorTag()] = c;
        }
    }

    /**
     * Another constructor, this one for testing purposes.
     * @param input: 2d array of Java Colors
     */
    public PBNModel(Color[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        // initialize the basic info fields
        pixelsTotal = rows * cols;
        pixelsColored = 0;
        done = false;

        // initialize the canvas of pixels
        canvas = new Pixel[rows][cols];

        // temporary map and a counter to help populate the colors and colortags
        Map<Integer, PBNColor> tempMap = new TreeMap<>();
        int colorTagCounter = 0;
        // for loop to populate tempMap
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Color inputColor = input[row][col];
                Integer inputRGB = (Integer) inputColor.getRGB();
                if (tempMap.containsKey(inputRGB)) {
                    PBNColor pbnColor = tempMap.get(inputRGB);
                    // make new pixel
                    Pixel newPixel = new Pixel(pbnColor.getColorTag(), pbnColor.getColor());
                    // add the new pixel to the PBNColor's list
                    pbnColor.addPixel(newPixel);
                    // add the new pixel to the canvas here
                    canvas[row][col] = newPixel;
                } else {
                    // make new pixel based on the colortagcounter
                    Pixel firstPixel = new Pixel(colorTagCounter, inputColor);
                    // make new pbncolor and add the new pixel to it
                    PBNColor newColor = new PBNColor(inputColor, colorTagCounter, firstPixel);
                    // add the color to the map
                    tempMap.put(inputRGB, newColor);
                    // add pixel to the canvas here
                    canvas[row][col] = firstPixel;
                    // increment colortagcounter so we get different tags for every color
                    colorTagCounter++;
                }
            }
        }
        // after the for loop initialize the palette array
        palette = new PBNColor[tempMap.size()];
        // for loop to populate the palette array
        for (PBNColor c: tempMap.values()) {
            palette[c.getColorTag()] = c;
        }
    }

    // Getters
    public int getNumRows() {
        return canvas.length;
    }

    public int getNumCols() {
        return canvas[0].length;
    }

    public int getPixelsTotal() {
        return pixelsTotal;
    }

    public int getPixelsColored() {
        return pixelsColored;
    }

    public boolean isDone() {
        return done;
    }

    public int getNumColors() {
        return palette.length;
    }

    public int getCurrentColorTag() {
        return currentColorTag;
    }

    public Color getCurrentColor() {
        return palette[currentColorTag].getColor();
    }

    public PBNColor getPBNColor(int colorTag) {
        return palette[colorTag];
    }

    /**
     * Getter method to extract a Pixel. Be careful not to modify the results from
     * this method!
     * @param row
     * @param col
     * @return the Pixel at the y position determiend by row and the x position
     *         Not well encapsulated.
     */
    public Pixel getPixel(int row, int col) {
        return canvas[row][col];
    }

    /**
     * Changes the current "pen" color
     * @param newColorTag: new integer color tag
     * @return the integer tag of the color that it's been changed to. Throws exception
     *         if the newColorTag is invalid.
     */
    public int switchColorTag(int newColorTag) {
        if (newColorTag >= palette.length || newColorTag < 0) {
            throw new IllegalArgumentException("new colortag out of bounds");
        }
        currentColorTag = newColorTag;
        return currentColorTag;
    }

    /**
     * Toggles highlight for the PBNColor associated with the colorTag parameter
     * @param colorTag
     */
    public void toggleHighlight(int colorTag) {
        if (colorTag >= palette.length || colorTag < 0) {
            throw new IllegalArgumentException("colortag out of bounds");
        }
        for (Pixel p: palette[colorTag].getPixels()) {
            p.toggleHighlight();
        }
    }

    /**
     * Toggles highlight for the currently selected PBNColor
     */
    public void toggleHighlight() {
        for (Pixel p: palette[currentColorTag].getPixels()) {
            p.toggleHighlight();
        }
    }

    /**
     * Colors the pixel at the position determined by input row and col
     * @param row
     * @param col
     * @return Whether the game is done or not (whether all pixels have
     *         been colored or not)
     */
    public boolean color(int row, int col) {
        if (row >= getNumRows() || row < 0) {
            throw new IllegalArgumentException("row out of bounds");
        }
        if (col > getNumCols() || col < 0) {
            throw new IllegalArgumentException("column out of bounds");
        }
        Pixel p = canvas[row][col];
        if (p.getColorTag() == currentColorTag) {
            boolean firstTimeColored = p.color();
            if (firstTimeColored) {
                pixelsColored++;
                done = pixelsColored == pixelsTotal;
            }
        }
        return done;
    }
}
