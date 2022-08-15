package edu.upenn.cis120.finalprj;

import edu.upenn.cis120.finalprj.support.PBNModel;
import edu.upenn.cis120.finalprj.support.Pixel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static javax.imageio.ImageIO.read;
import static javax.swing.JOptionPane.*;

public class RunPBN implements Runnable{
    // game model stuff
    private PBNModel pbnModel;
    private String defaultImage;
    private int numRows;
    private int numCols;
    private boolean done;

    // graphics stuff
    private JPanel canvas;
    private JFrame frame;
    private JPanel sidebar;
    private JSpinner colorPicker;

    // stuff outside of the constructor
    // instructions - write more of this later.
    private String instructions = "Hi! Welcome to my paint by numbers game! \n" +
            "Just like any other paint by numbers game, click on the pixels with the correct \n" +
            "numbered pen, indicated by the number in the box on the right. You can change \n" +
            "the current pen by typing in the box or using the arrow buttons. But it won't \n" +
            "work if you set it to a number that's not on the canvas. \n" +
            "For your convenience, there is a toggle highlight option, which highlights all \n" +
            "pixels of the currently selected color, or unhighlights them if they're \n" +
            "already highlighted. \n" +
            "You can change the current image by two options: using a preset, or loading in \n" +
            "your own custom pixel art, which you can make at pixelartmaker.com. If you do so, \n" +
            "save it with '1 pixel per pixel' for use here. Also, make sure it's \n" +
            "not super huge (75 pixels on a side is really pushing it I'd say). \n" +
            "Also, sometimes it's a little weird and it takes a few clicks to color a pixel. \n" +
            "But if you have the right numbered pen, it'll color after a few clicks at most. \n" +
            "Have fun!";

    // default pictures array - for use with choose presets
    private String[][] defaultOptions = {{"Default", "orange.png"}, {"Target","testImage.png"},
            {"Amogus", "amogus.png"}, {"Baby Mode", "babyMode.png"}, {"Kazuha", "kazoo.png"}};

    public RunPBN() {
        defaultImage = "orange.png";
        // make the jframe
        frame = new JFrame("Paint by Numbers");
        frame.setResizable(false);
        canvas = new JPanel();
        // make the side panel JPanel and add it to the frame
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 0));
        frame.add(sidebar, BorderLayout.EAST);
        // initialize the colorPicker
        colorPicker = new JSpinner();
        reset(defaultImage);
    }

    // method for resetting the game image - this is where the I/O happens.
    // return true or false based on success
    // also will change your canvas for you and your framesize and everything
    public boolean reset(String pathname) {
        // reading in image (bad and scuffed)
        BufferedImage bufferedImage;
        try {
            File filepath = getFile(pathname);
            bufferedImage = read(filepath);
        } catch (IOException | NullPointerException | URISyntaxException | IllegalArgumentException e) {
            System.out.println(pathname);
            e.printStackTrace();
            return false;
        }
        pbnModel =  new PBNModel(bufferedImage);
        numRows = pbnModel.getNumRows();
        numCols = pbnModel.getNumCols();
        done = false;

        // remove the old canvas
        frame.remove(canvas);

        // make a new canvas
        JPanel newCanvas = new JPanel();

        // modify the new canvas
        newCanvas.setLayout(new GridLayout(numRows, numCols));
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                newCanvas.add(pbnModel.getPixel(i, j));
            }
        }

        // add canvas back, reset frame size accordingly
        canvas = newCanvas;
        frame.add(canvas, BorderLayout.CENTER);
        frame.revalidate();
        frame.setSize(new Dimension((int) (canvas.getPreferredSize().getWidth()
                + sidebar.getPreferredSize().getWidth()),
                (int) canvas.getPreferredSize().getHeight() + frame.getInsets().top));

        // add mouselistener to canvas
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    done = pbnModel.color(e.getY()/ Pixel.SIDELENGTH, e.getX()/Pixel.SIDELENGTH);
                    canvas.repaint();
                    if (done) {
                        showMessageDialog(frame, "You did it! You can continue with \n" +
                                "a new preset or custom picture.");
                    }
                } catch (IllegalArgumentException ie) {
                    return;
                }
            }
        });
        canvas.repaint();

        // change and reset the color picker
        colorPicker.setModel(new SpinnerNumberModel(0, 0, pbnModel.getNumColors() - 1, 1));

        return true;
    }

    // resolve filepath
    private File getFile(String pathname) throws URISyntaxException {
        File filepath = null;
        try {
            URL resource = getClass().getClassLoader().getResource(pathname); // class path (presets)
            if (resource == null) {
                throw new IllegalArgumentException("file not found!");
            }
            filepath = new File(resource.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
        filepath = new File(pathname); // for absolute path (custom picture)
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return filepath;
    }

    public void run() {
        // make the option popup menu for preset choices
        JPopupMenu choices = new JPopupMenu();
        for (int i = 0; i < defaultOptions.length; i++) {
            JButton temp = new JButton(defaultOptions[i][0]); // this is the name of the preset
            int finalI = i;
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reset(defaultOptions[finalI][1]); // file path for preset
                    choices.setVisible(false);
                }
            });
            choices.insert(temp,i);
        }

        // make the choose preset picture button
        JButton choosePreset = new JButton("preset picture");
        choosePreset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices.show(choosePreset, 0, 0);
            }
        });

        // make the type in custom picture button
        JButton chooseCustom = new JButton("custom picture");
        chooseCustom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newFile = showInputDialog("Type your path to your new file \n" +
                        "It should be an absolute path. Or I guess you could put " +
                        "it in this directory. \n" +
                        "Also don't make the picture really big. Don't say I didn't warn you!");
                boolean success = reset(newFile);
                if (success) {
                    System.out.println("Custom picture success!");
                } else {
                    showMessageDialog(frame, "Bad input :(");
                }
            }
        });

        // make the pull up instructions button
        JButton help = new JButton("help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessageDialog(frame, instructions);
            }
        });

        // add changelistener to spinner
        colorPicker.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                pbnModel.switchColorTag((Integer) colorPicker.getValue());
            }
        });

        // make the toggle highlight button
        JButton toggleHighlight = new JButton("toggle highlight");
        toggleHighlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbnModel.toggleHighlight();
                canvas.repaint();
            }
        });

        // add each thing to the side panel
        // final order: help, spinner, toggle highlight, preset, custom
        sidebar.add(help);
        sidebar.add(colorPicker);
        sidebar.add(toggleHighlight);
        sidebar.add(choosePreset);
        sidebar.add(chooseCustom);

        // go at the end to make the jframe visible etc.
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}