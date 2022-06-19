package theGame;

import java.awt.Dimension; 
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame{
	
    private final int width, height;
    private final String title;
    private final Game game;
    
    private JFrame frame;
    private ImageIcon icon;

    public Window(int width, int height, String title, Game game) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.game = game;
        this.icon = new ImageIcon("res/bunny-icon.png");
    }

    public void start() {
        
        frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());
    }
}