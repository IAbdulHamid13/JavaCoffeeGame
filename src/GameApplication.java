import javax.swing.*;

public class GameApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame gameWindow = new GameWindow();
            gameWindow.setVisible(true);
        });
    }
}