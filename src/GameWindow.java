import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Coffee Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Changed to allow resizing

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
    }
}