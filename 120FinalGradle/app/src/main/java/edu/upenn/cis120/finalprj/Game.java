package edu.upenn.cis120.finalprj;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        Runnable game = new RunPBN();
        SwingUtilities.invokeLater(game);
    }
}
