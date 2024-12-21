import java.awt.*;
import java.util.ArrayList;

/**
 * The concrete Subject in the Observer design pattern
 * Registers Observers to itself, notifies Observers of new settings, and recursively generates the tree fractal
 * @author Maya Rao
 * @version 12-6-24
 */
public class FractalGenerator implements FractalSubject {
    // constants
    private static final int START_DEPTH = 1;

    /** Starting x-coordinate for the tree fractal (halfway across the screen) */
    private static final int START_X = 500;

    /** Starting y-coordinate for the tree fractal (bottom of the screen) */
    private static final int START_Y = 775;

    /** Starting orientation for the tree fractal (90 degrees, straight vertical) */
    private static final int START_ANGLE = 90;


    // instance variables
    /** An ArrayList of FractalObservers that changes in size */
    private ArrayList<FractalObserver> observers;

    /** An int array of all slider values */
    private int[] sliderInfo;

    /** A Color array of all colors for the trunk and leaf */
    private Color[] colorInfo;


    // constructor
    /** Instantiate the ArrayList of FractalObserver with size = 0 */
    public FractalGenerator() {
        observers = new ArrayList<>();
    }


    // methods
    /** Notify all Observers that there is updated information */
    @Override
    public void notifyObservers() {
        // for each observer in observers ArrayList
        for (FractalObserver observer : observers) {
            // update each observer
            observer.update();
        }
    }

    /**
     * Add Observers to an ArrayList of Observers
     *
     * @param obs the Observer that needs to be added to the Subject
     */
    @Override
    public void registerObserver(FractalObserver obs) {
        observers.add(obs);
    }

    /**
     * Remove Observers from an ArrayList of Observers
     *
     * @param obs the Observer that needs to be removed from the Subject
     */
    @Override
    public void unregisterObserver(FractalObserver obs) {
        observers.remove(obs);
    }

    /**
     * Gets the ArrayList of FractalElements currently using the Subject
     * Only called if the Observers show interest in the notification
     *
     * @return  ArrayList of FractalElements using the Subject
     */
    @Override
    public ArrayList<FractalElement> getFractalElements() {
        // create ArrayList of FractalElements to return
        ArrayList<FractalElement> fractalElements = new ArrayList<>();

        // start recursion with trunk values: starting recursion depth, starting x, starting y, trunk length,
        //                                    trunk width, starting angle converted to radians, trunk color
        makeFractalElements(fractalElements, START_DEPTH, START_X, START_Y, sliderInfo[4], sliderInfo[5],
                convertToRadians(START_ANGLE), colorInfo[0]);

        // return updated ArrayList of FractalElements
        return fractalElements;
    }

    /**
     * Generates all Branches in the tree fractal with recursion:
     *  First calculates the ending coordinates of a Branch and adds the Branch to the ArrayList of FractalElements
     *  Then generates the information necessary for the left and right child Branches
     *
     * @param currentFractalElements    the ArrayList of FractalElements that will be updated during the recursion
     * @param currentDepth              current recursion depth, ensures the recursion will stop at the appropriate time
     * @param x1                        starting x-coordinate of the Branch
     * @param y1                        starting y-coordinate of the Branch
     * @param length                    length of the Branch
     * @param width                     width of the Branch
     * @param radians                   tilt of the Branch described with radians
     * @param color                     color of the Branch
     */
    private void makeFractalElements(ArrayList<FractalElement> currentFractalElements, int currentDepth, int x1, int y1,
                                     int length, int width, double radians, Color color) {
        // if current recursion depth is less than or equal to value from recursionDepthSlider
        if (currentDepth <= sliderInfo[0]) {
            // calculate ending x and y screen coordinates with unit circle trigonometry
            // larger x is further right, smaller x is further left
            int x2 = (int) (x1 + (length * Math.cos(radians)));

            // larger y is lower, smaller y is higher
            int y2 = (int) (y1 - (length * Math.sin(radians)));

            // add new Branch to ArrayList of FractalElements with calculated ending x and y coordinates
            currentFractalElements.add(new Branch(x1, y1, x2, y2, width, color));


            // generating new information for child branches
            // newLength = length * childParentRatio as %
            int newLength = (int) (length * (sliderInfo[1] / 100.0));

            // newWidth = width * childParentRatio as %
            int newWidth = (int) (width * (sliderInfo[1] / 100.0));

            // newColor is a Color in between trunkColor and leafColor
            Color newColor = makeGradient(color, colorInfo[1], currentDepth, sliderInfo[0]);

            // to tilt Branch by leftChildAngle, add leftChildAngle in radians to current radians
            // (larger radians are left in a unit circle)
            double leftRadians = radians + convertToRadians(sliderInfo[2]);

            // to tilt Branch by rightChildAngle, subtract rightChildAngle in radians to current radians
            // (smaller radians are right in a unit circle)
            double rightRadians = radians - convertToRadians(sliderInfo[3]);

            // recursively generate the left child Branch and necessary information for additional Branches
            makeFractalElements(currentFractalElements, currentDepth + 1, x2, y2, newLength, newWidth,
                    leftRadians, newColor);

            // recursively generate the right child Branch and necessary information for additional Branches
            makeFractalElements(currentFractalElements, currentDepth + 1, x2, y2, newLength, newWidth,
                    rightRadians, newColor);
        }
    }

    /**
     * Converts angles in degrees to radians; used in makeFractalElements
     *
     * @param angle angle in degrees
     * @return      radians equivalent of given angle
     */
    private double convertToRadians(int angle) {
        return Math.toRadians(angle);
    }

    /**
     * Creates a gradient from the trunk color and leaf color
     *
     * @param startColor    color of the parent Branch
     * @param endColor      color of the final Branch
     * @param currentDepth  current recursion depth
     * @param maxDepth      maximum recursion depth
     * @return              a Color somewhere in between the trunk color and leaf color
     */
    private Color makeGradient(Color startColor, Color endColor, int currentDepth, int maxDepth) {
        // calculate how much to adjust each color over the depth of the tree fractal (excluding the start)
        int changeRed = (endColor.getRed() - startColor.getRed()) / (maxDepth - 1);
        int changeGreen = (endColor.getGreen() - startColor.getGreen()) / (maxDepth - 1);
        int changeBlue = (endColor.getBlue() - startColor.getBlue()) / (maxDepth - 1);

        // calculate the new colors by adding the change value scaled by the current depth of the tree fractal
        int newRed = startColor.getRed() + (changeRed * currentDepth);
        int newGreen = startColor.getGreen() + (changeGreen * currentDepth);
        int newBlue = startColor.getBlue() + (changeBlue * currentDepth);

        return new Color(newRed, newGreen, newBlue);
    }

    /**
     * Collecting all the slider and button data from FractalGui
     *
     * @param sliderInfo values of the sliders
     * @param colorInfo  colors of the trunk and leaves
     */
    @Override
    public void setOptions(int[] sliderInfo, Color[] colorInfo) {
        // collecting int array of slider values to use in FractalSubject
        this.sliderInfo = sliderInfo;

        // collecting Color array of color values to use in FractalSubject
        this.colorInfo = colorInfo;

        // notify all observers of updated information from FractalGui
        notifyObservers();
    }
}
