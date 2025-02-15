import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Morning Joe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Changed to allow resizing

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
    }
}