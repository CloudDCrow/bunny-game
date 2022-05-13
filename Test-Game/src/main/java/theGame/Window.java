package theGame;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
    private final int width, height;
    private final String title;
    private final Game game;
    private JFrame frame;

    public Window(int width, int height, String title, Game game) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.game = game;
    }

    public void start() {
        
        this.frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
