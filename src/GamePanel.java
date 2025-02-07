import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private static final int ROWS = 10;
    private static final int COLS = 15;
    private int tileSize; // Now dynamic

    private Player player;
    private List<Coffee> coffees;
    private Maze maze;
    private int score = 0;
    private int movesLeft = 50;
    private boolean gameOver = false;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600)); // Initial size
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.WHITE);

        initializeGame();
        startGameLoop();
    }

    private void initializeGame() {
        maze = new Maze(ROWS, COLS);
        player = new Player(1, 1);
        coffees = new ArrayList<>();
        coffees.add(new Coffee(5, 5));
        coffees.add(new Coffee(10, 2));
        coffees.add(new Coffee(7, 8));
    }

    private void calculateTileSize() {
        int width = getWidth();
        int height = getHeight();
        tileSize = Math.min(width / COLS, height / ROWS);
        tileSize = Math.max(tileSize, 20); // Minimum tile size
    }

    private void startGameLoop() {
        Timer timer = new Timer(100, e -> {
            if (!gameOver) {
                checkCoffeeCollision();
                checkGameStatus();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        calculateTileSize(); // Recalculate on each paint

        // Draw all elements using current tileSize
        maze.draw(g2d, tileSize);
        player.draw(g2d, tileSize);
        for (Coffee coffee : coffees) {
            if (!coffee.isCollected()) {
                coffee.draw(g2d, tileSize);
            }
        }
        drawUI(g2d);
        if (gameOver)
            drawGameOver(g2d);
    }

    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + score, 10, 20);
        g2d.drawString("Moves Left: " + movesLeft, 10, 40);
    }

    private void drawGameOver(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        String message = score >= coffees.size() * 100 ? "You Win!" : "Game Over!";
        g2d.drawString(message, getWidth() / 2 - 80, getHeight() / 2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            int key = e.getKeyCode();
            int newX = player.getGridX();
            int newY = player.getGridY();

            switch (key) {
                case KeyEvent.VK_UP -> newY--;
                case KeyEvent.VK_DOWN -> newY++;
                case KeyEvent.VK_LEFT -> newX--;
                case KeyEvent.VK_RIGHT -> newX++;
            }

            if (maze.isValidPosition(newX, newY)) {
                player.move(newX, newY);
                movesLeft--;
            }
        }
    }

    private void checkCoffeeCollision() {
        coffees.removeIf(coffee -> {
            if (!coffee.isCollected() && player.getGridX() == coffee.getGridX()
                    && player.getGridY() == coffee.getGridY()) {
                coffee.collect();
                score += 100;
                movesLeft += 5;
                return true;
            }
            return false;
        });
    }

    private void checkGameStatus() {
        if (movesLeft <= 0) {
            gameOver = true;
        } else if (coffees.stream().allMatch(Coffee::isCollected)) {
            gameOver = true;
        }
    }

    // Unused key listener methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}