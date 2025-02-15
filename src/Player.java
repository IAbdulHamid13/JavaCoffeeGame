import java.awt.*;

class Player {
    private int gridX, gridY;

    public Player(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }

    public void draw(Graphics2D g2d, int tileSize) {
        int x = gridX * tileSize;
        int y = gridY * tileSize;
        g2d.setColor(Color.BLACK);
        int centerX = x + tileSize / 2;

        // Head
        int headRadius = tileSize / 8;
        int headY = y + 5;
        g2d.drawOval(centerX - headRadius, headY, headRadius * 2, headRadius * 2);

        // Glasses (Inside Head)
        int glassY = headY + headRadius / 2;
        int lensSize = headRadius - 2;
        g2d.drawOval(centerX - lensSize - 2, glassY, lensSize, lensSize / 2); // Left lens
        g2d.drawOval(centerX + 2, glassY, lensSize, lensSize / 2); // Right lens
        g2d.drawLine(centerX - lensSize / 2, glassY + lensSize / 4, centerX + lensSize / 2, glassY + lensSize / 4); // Bridge

        // Body
        g2d.setStroke(new BasicStroke());
        int bodyY = y + tileSize / 4;
        g2d.drawLine(centerX, bodyY, centerX, y + tileSize / 2);

        // Arms
        g2d.drawLine(centerX - tileSize / 4, y + tileSize / 3, centerX + tileSize / 4, y + tileSize / 3);

        // Legs
        int legY = y + tileSize / 2;
        g2d.drawLine(centerX, legY, centerX - tileSize / 4, legY + tileSize / 4);
        g2d.drawLine(centerX, legY, centerX + tileSize / 4, legY + tileSize / 4);
    }

    public void move(int newX, int newY) {
        gridX = newX;
        gridY = newY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
}
