import java.awt.*;

/**
 * Contains all necessary information to draw Branches of a tree fractal
 * @author Maya Rao
 * @version 12-6-24
 *
 * @param x1    starting x-coordinate
 * @param y1    starting y-coordinate
 * @param x2    final x-coordinate
 * @param y2    final y-coordinate
 * @param width thickness of Branch
 * @param color color of Branch
 */
public record Branch(
        int x1,
        int y1,
        int x2,
        int y2,
        int width,
        Color color
) implements FractalElement {
    /**
     * Method for drawing a FractalElement
     *
     * @param g Graphics reference parameter
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        // drawing a Branch is like drawing a line with a width, color, starting x, starting y, ending x, and ending y
        g2D.setStroke(new BasicStroke(width));
        g2D.setColor(color);
        g2D.drawLine(x1, y1, x2, y2);
    }
}
