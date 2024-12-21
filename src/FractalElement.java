import java.awt.*;

/**
 * Interface to convey required methods and signatures for FractalElements
 * This project uses Branch as a FractalElement
 * FractalElements are responsible for drawing themselves
 * @author Maya Rao
 * @version 12-6-24
 */
public interface FractalElement {
    /**
     * Method for drawing a FractalElement
     *
     * @param g Graphics reference parameter
     */
    public void draw(Graphics g);
}
