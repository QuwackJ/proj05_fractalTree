/**
 * Interface to convey required methods and signatures for FractalObservers
 * This project uses an Observer design pattern where FractalDrawing is the FractalObserver
 * @author Maya Rao
 * @version 12-6-24
 */
public interface FractalObserver {
     /** Updates the information each Observer has */
     public void update();
}
