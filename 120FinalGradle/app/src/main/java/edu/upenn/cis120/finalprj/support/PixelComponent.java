package edu.upenn.cis120.finalprj.support;

import javax.swing.*;
import java.awt.*;

public class PixelComponent extends JComponent {
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

    // instance variables (dependent on the pbnModel)
    private int row;
    private int col;
    private Pixel mirror;

    public PixelComponent(int i, int j, PBNModel pbnModel) {
        if (i >= pbnModel.getNumRows() || i < 0) {
            System.out.println("PixelComponent row out of bounds");
        }
        if (j >= pbnModel.getNumCols() || j < 0) {
            System.out.println("PixelComponent col out of bounds");
        }
        row = i;
        col = j;
        mirror = pbnModel.getPixel(i, j);
    }

    // JComponent things
    @Override
    public void paintComponent(Graphics g) {
        if (mirror.isColored()) {
            g.setColor(mirror.getColor());
            g.fillRect(0, 0, SIDELENGTH, SIDELENGTH);
        } else {
            g.setColor(Color.black);
            g.drawRect(0, 0, SIDELENGTH, SIDELENGTH);
            g.setFont(FONT);
            g.drawString(Integer.toString(mirror.getColorTag()), 1, SIDELENGTH - 1);
        }
        if (mirror.isHighlighted()) {
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
