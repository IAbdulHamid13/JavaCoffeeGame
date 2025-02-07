import java.awt.*;

class Maze {
    private final boolean[][] grid;

    public Maze(int rows, int cols) {
        grid = new boolean[rows][cols];
        initializeWalls();
    }

    private void initializeWalls() {
        // Create borders
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1) {
                    grid[i][j] = true;
                }
            }
        }

        // Add some internal walls
        grid[5][3] = true;
        grid[5][4] = true;
        grid[5][5] = true;
        grid[3][7] = true;
        grid[4][7] = true;
    }

    public void draw(Graphics2D g2d, int tileSize) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j]) {
                    g2d.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }
        }
    }

    public boolean isValidPosition(int x, int y) {
        return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length && !grid[y][x];
    }
}