import java.awt.*;

class Coffee {
    private final int gridX;
    private final int gridY;
    private boolean collected = false;

    public Coffee(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }

    public void draw(Graphics2D g2d, int tileSize) {
        if (collected)
            return;
        int x = gridX * tileSize;
        int y = gridY * tileSize;
        g2d.setColor(new Color(139, 69, 19)); // Brown color
        g2d.fillOval(x + tileSize / 4, y + tileSize / 4, tileSize / 2, tileSize / 2);
    }

    public void collect() {
        collected = true;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean isCollected() {
        return collected;
    }
}