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
        g2d.drawOval(centerX - headRadius, y + 5, headRadius * 2, headRadius * 2);

        // Glasses
        int glassY = y + 5 + headRadius / 2;
        int lensSize = headRadius;
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(centerX - headRadius * 2, glassY, lensSize, lensSize / 2);
        g2d.drawOval(centerX + headRadius, glassY, lensSize, lensSize / 2);
        g2d.drawLine(centerX - headRadius, glassY + lensSize / 4,
                centerX + headRadius, glassY + lensSize / 4);

        // Body
        g2d.setStroke(new BasicStroke());
        g2d.drawLine(centerX, y + tileSize / 4, centerX, y + tileSize / 2);

        // Arms
        g2d.drawLine(centerX - tileSize / 4, y + tileSize / 3,
                centerX + tileSize / 4, y + tileSize / 3);

        // Legs
        g2d.drawLine(centerX, y + tileSize / 2,
                centerX - tileSize / 4, y + tileSize / 2 + tileSize / 4);
        g2d.drawLine(centerX, y + tileSize / 2,
                centerX + tileSize / 4, y + tileSize / 2 + tileSize / 4);
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