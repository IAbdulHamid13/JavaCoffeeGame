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

        // Cup body
        g2d.setColor(new Color(245, 245, 245)); // Off-white
        // I'm not really sure what colour to give it to make it make sense
        int cupWidth = tileSize / 2;
        int cupHeight = tileSize / 2;
        int[] xPoints = {
                x + tileSize / 4,
                x + tileSize / 4 + cupWidth,
                x + tileSize / 4 + cupWidth - 10,
                x + tileSize / 4 + 10
        };
        int[] yPoints = {
                y + tileSize / 2,
                y + tileSize / 2,
                y + tileSize / 2 + cupHeight,
                y + tileSize / 2 + cupHeight
        };
        g2d.fillPolygon(xPoints, yPoints, 4);

        // Coffee liquid (brown oval)
        g2d.setColor(new Color(139, 69, 19));
        g2d.fillOval(
                x + tileSize / 4 + 5,
                y + tileSize / 2 - 5,
                cupWidth - 10,
                cupHeight / 3);
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