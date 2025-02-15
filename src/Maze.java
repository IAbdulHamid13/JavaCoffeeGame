import java.awt.*;
import java.util.Random;

class Maze {
    private final boolean[][] grid;
    private final Random random;

    public Maze(int rows, int cols) {
        this(rows, cols, false);
    }

    public Maze(int rows, int cols, boolean simple) {
        grid = new boolean[rows][cols];
        random = new Random();
        if (simple)
            generateSimpleMaze();
        else
            generateMaze();
    }

    private void generateSimpleMaze() {
        // Simple maze with guaranteed path
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1;
    }

    private void generateMaze() {
        // Create borders
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1)
                    grid[i][j] = true;

        // Add more complex wall generation
        int wallDensity = grid.length * grid[0].length / 10;
        for (int k = 0; k < wallDensity; k++) {
            int attempts = 0;
            while (attempts < 100) {
                int x = random.nextInt(grid[0].length);
                int y = random.nextInt(grid.length);

                // Avoid walls on borders
                if (x == 0 || x == grid[0].length - 1 || y == 0 || y == grid.length - 1)
                    continue;

                // Create wall segments with some randomness
                if (random.nextBoolean()) {
                    // Vertical wall segment
                    int length = random.nextInt(3) + 2;
                    boolean canPlace = true;
                    for (int i = 0; i < length; i++) {
                        if (y + i >= grid.length || grid[y + i][x]) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (canPlace) {
                        for (int i = 0; i < length; i++)
                            grid[y + i][x] = true;
                        break;
                    }
                } else {
                    // Horizontal wall segment
                    int length = random.nextInt(3) + 2;
                    boolean canPlace = true;
                    for (int j = 0; j < length; j++) {
                        if (x + j >= grid[0].length || grid[y][x + j]) {
                            canPlace = false;
                            break;
                        }
                    }

                    if (canPlace) {
                        for (int j = 0; j < length; j++)
                            grid[y][x + j] = true;
                        break;
                    }
                }
                attempts++;
            }
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j])
                    g2d.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
        }
    }

    public boolean isValidPosition(int x, int y) {
        return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length && !grid[y][x];
    }
}