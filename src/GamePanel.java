import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    private static final int ROWS = 10;
    private static final int COLS = 15;
    private int tileSize;

    private Player player;
    private List<Coffee> coffees;
    private Maze maze;
    private int score = 0;
    private int movesLeft = 50;
    private boolean gameOver = false;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.WHITE);

        initializeGame();
        startGameLoop();
    }

    private void initializeGame() {
        boolean validMaze = false;
        int maxAttempts = 100;
        int attempts = 0;

        while (!validMaze && attempts < maxAttempts) {
            maze = new Maze(ROWS, COLS);
            
            // Find player starting position
            int playerX = 1, playerY = 1;
            for (int y = 1; y < ROWS - 1; y++) {
                for (int x = 1; x < COLS - 1; x++) {
                    if (maze.isValidPosition(x, y)) {
                        playerX = x;
                        playerY = y;
                    }
                }
            }
            player = new Player(playerX, playerY);

            // Place coffees
            coffees = new ArrayList<>();
            Random random = new Random();
            int coffeesToSpawn = 3;
            int coffeeAttempts = 0;
            
            while (coffees.size() < coffeesToSpawn && coffeeAttempts < 100) {
                int x = random.nextInt(COLS);
                int y = random.nextInt(ROWS);
                if (maze.isValidPosition(x, y)) {
                    coffees.add(new Coffee(x, y));
                }
                coffeeAttempts++;
            }

            // Validate maze connectivity
            validMaze = isMazeValid(playerX, playerY);
            attempts++;
        }

        if (!validMaze) {
            // Fallback to simple maze if validation fails
            maze = new Maze(ROWS, COLS, true);
        }
    }

    private boolean isMazeValid(int startX, int startY) {
        int[][] distances = new int[ROWS][COLS];
        for (int[] row : distances) Arrays.fill(row, Integer.MAX_VALUE);
        distances[startY][startX] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> distances[a[1]][a[0]]));
        queue.add(new int[]{startX, startY});

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < COLS && newY >= 0 && newY < ROWS) {
                    if (maze.isValidPosition(newX, newY) && distances[newY][newX] > distances[y][x] + 1) {
                        distances[newY][newX] = distances[y][x] + 1;
                        queue.add(new int[]{newX, newY});
                    }
                }
            }
        }

        // Check all coffee positions
        for (Coffee coffee : coffees) {
            if (distances[coffee.getGridY()][coffee.getGridX()] == Integer.MAX_VALUE) {
                return false;
            }
        }
        return true;
    }

    
    private void calculateTileSize() {
        int width = getWidth();
        int height = getHeight();
        tileSize = Math.min(width / COLS, height / ROWS);
        tileSize = Math.max(tileSize, 20);
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
        calculateTileSize();

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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}