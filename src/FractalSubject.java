import java.awt.*;
import java.util.ArrayList;

/**
 * Interface to convey required methods and signatures for FractalSubjects
 * This project uses an Observer design pattern where FractalGenerator is the FractalSubject
 * @author Maya Rao
 * @version 12-6-24
 */
public interface FractalSubject {
    /** Notify all Observers that there is updated information */
    public void notifyObservers();

    /**
     * Add Observers to an ArrayList of Observers
     *
     * @param obs   the Observer that needs to be added to the Subject
     */
    public void registerObserver(FractalObserver obs);

    /**
     * Remove Observers from an ArrayList of Observers
     *
     * @param obs   the Observer that needs to be removed from the Subject
     */
    public void unregisterObserver(FractalObserver obs);

    /**
     * Gets the ArrayList of FractalElements currently using the Subject
     * Only called if the Observers show interest in the notification
     *
     * @return  ArrayList of FractalElements using the Subject
     */
    public ArrayList<FractalElement> getFractalElements();

    /**
     * Collecting all the slider and button data from FractalGui
     *
     * @param sliderInfo    values of the sliders
     * @param colorInfo     colors of the trunk and leaves
     */
    public void setOptions(int[] sliderInfo, Color[] colorInfo);
}
