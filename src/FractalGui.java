import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * A GUI that has sliders and buttons to control how the tree fractal generates and appears in FractalDrawing
 * @author Maya Rao
 * @version 12-6-24
 */
public class FractalGui extends JFrame {
    // constants - makes rearranging widgets easier
    /** x-coordinate of a slider or button */
    private static final int WIDGET_X = 25;

    /** y-coordinate of a slider or button */
    private static final int WIDGET_Y = 100;

    /** Width of a slider or button */
    private static final int WIDGET_WIDTH = 350;

    /** Height of a slider or button */
    private static final int WIDGET_HEIGHT = 40;


    // instance variables
    /** Reference to the Subject interface */
    private FractalSubject subject;

    /** Variable to store information from the Recursion Depth slider */
    private JSlider recursionDepthSlider;

    /** Variable to store information from the Child to Parent Ratio slider */
    private JSlider childParentRatioSlider;

    /** Variable to store information from the Left Child Angle slider */
    private JSlider leftChildAngleSlider;

    /** Variable to store information from the Right Child Angle slider */
    private JSlider rightChildAngleSlider;

    /** Variable to store information from the Trunk Length slider */
    private JSlider trunkLengthSlider;

    /** Variable to store information from the Trunk Width slider */
    private JSlider trunkWidthSlider;

    /** Initial Trunk color */
    private Color trunkColor = new Color(139, 69, 19); // brown

    /** Initial Leaf color (color at the very end of the tree fractal) */
    private Color leafColor = new Color(0, 255, 51); // lime green


    // constructor
    /**
     * Constructs the GUI window with all the sliders and buttons needed for the user to interact with it
     * GUI also collects all the data from sliders and buttons and sends it to FractalSubject
     *
     * @param subject   FractalSubject interface (GUI can't directly interact with the concrete Subject)
     */
    public FractalGui(FractalSubject subject) {
        // setting subject reference to passed in FractalSubject interface
        this.subject = subject;

        // adding a title to the window
        setTitle("Fractal Settings");

        // setting window size to 400 pixels (width) x 775 pixels (height)
        // always width x height
        setSize(400, 775);

        // don't allow user to resize the window by clicking and dragging
        setResizable(false);

        // quits program when clicking the red button
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // now only using JPanel, done using JFrame
        // null says no layout manager, set absolute positioning
        JPanel panel = new JPanel(null);

        // attaching JPanel to JFrame (getContentPane() is a JFrame method)
        getContentPane().add(panel);

        // adding recursion depth slider
        recursionDepthSlider = makeSliderWithLabel(panel, "Recursion Depth:", WIDGET_Y - 30, 4,
                20, 12, WIDGET_Y, 2, 1);
        panel.add(recursionDepthSlider);

        // adding child to parent ratio slider
        childParentRatioSlider = makeSliderWithLabel(panel, "Child to Parent Ratio:", WIDGET_Y + 45,
                40, 80, 70, WIDGET_Y + 75, 10, 5);
        panel.add(childParentRatioSlider);

        // adding left child angle slider
        leftChildAngleSlider = makeSliderWithLabel(panel, "Left Child Angle:", WIDGET_Y + 120, 0,
                90, 20, WIDGET_Y + 150, 10, 5);
        panel.add(leftChildAngleSlider);

        // adding right child angle slider
        rightChildAngleSlider = makeSliderWithLabel(panel, "Right Child Angle:", WIDGET_Y + 195, 0,
                90, 30, WIDGET_Y + 225, 10, 5);
        panel.add(rightChildAngleSlider);

        // adding trunk length slider
        trunkLengthSlider = makeSliderWithLabel(panel, "Trunk Length:", WIDGET_Y + 270, 100,
                400, 200, WIDGET_Y + 300, 100, 25);
        panel.add(trunkLengthSlider);

        // adding trunk width slider
        trunkWidthSlider = makeSliderWithLabel(panel, "Trunk Width:", WIDGET_Y + 345, 10, 50,
                15, WIDGET_Y + 375, 10, 5);
        panel.add(trunkWidthSlider);

        // adding trunk color button and color picker
        JButton trunkColorButton = new JButton("Trunk Color");
        trunkColorButton.setBounds(WIDGET_X, WIDGET_Y + 420, WIDGET_WIDTH, WIDGET_HEIGHT);
        trunkColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Choose Start Color", trunkColor);
                if (chosenColor != null) {
                    // make trunkColor the chosenColor from the JColorChooser
                    trunkColor = chosenColor;

                    // collect updated trunkColor to send to FractalSubject
                    collectOptions();
                }
            }
        });
        panel.add(trunkColorButton);

        // adding leaf color button and color picker
        JButton leafColorButton = new JButton("Leaf Color");
        leafColorButton.setBounds(WIDGET_X, WIDGET_Y + 465, WIDGET_WIDTH, WIDGET_HEIGHT);
        leafColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Choose End Color", leafColor);
                if (chosenColor != null) {
                    // make leafColor the chosenColor from the JColorChooser
                    leafColor = chosenColor;

                    // collect updated leafColor to send to FractalSubject
                    collectOptions();
                }
            }
        });
        panel.add(leafColorButton);

        // adding randomize button
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.setBounds(WIDGET_X, WIDGET_Y + 510, WIDGET_WIDTH, WIDGET_HEIGHT);
        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set sliders to a random int between min and max value
                Random random = new Random();
                recursionDepthSlider.setValue(random.nextInt(((20 - 4) + 1) + 4));
                childParentRatioSlider.setValue(random.nextInt(((80 - 40) + 1) + 40));
                leftChildAngleSlider.setValue(random.nextInt(((90 - 0) + 1) + 0));
                rightChildAngleSlider.setValue(random.nextInt(((90 - 0) + 1) + 0));
                trunkLengthSlider.setValue(random.nextInt(((400 - 100) + 1) + 100));
                trunkWidthSlider.setValue(random.nextInt(((50 - 10) + 1) + 10));

                // randomizing trunkColor and leafColor
                trunkColor = generateRandomColor(random);
                leafColor = generateRandomColor(random);

                // collect randomized settings to send to FractalSubject
                collectOptions();
            }
        });
        panel.add(randomizeButton);

        // collect updated settings to send to FractalSubject
        collectOptions();

        // able to make windows that are present but hidden; we want this to be visible
        setVisible(true);
    }


    // methods
    /**
     * Creates a slider with an appropriate label
     *
     * @param panel         the JPanel where the slider should exist
     * @param labelText     what the slider should be called
     * @param labelY        y-coordinate of the label on the panel
     * @param min           minimum value of the slider
     * @param max           maximum value of the slider
     * @param startValue    where the slider should start when the GUI opens
     * @param sliderY       y-coordinate of the slider on the panel
     * @param majorTick     how far apart the big ticks should be placed
     * @param minorTick     how far apart the small ticks should be placed
     * @return              the complete slider for users to interact with
     */
    private JSlider makeSliderWithLabel(JPanel panel, String labelText, int labelY, int min, int max, int startValue,
                                        int sliderY, int majorTick, int minorTick) {
        // create and add label with given labelText and labelY
        JLabel label = new JLabel(labelText);
        label.setBounds(WIDGET_X, labelY, WIDGET_WIDTH, WIDGET_HEIGHT);
        panel.add(label);

        // create slider with min, max, startValue, sliderY, majorTick, minorTick
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, startValue);
        slider.setBounds(WIDGET_X, sliderY, WIDGET_WIDTH, WIDGET_HEIGHT);
        slider.setMajorTickSpacing(majorTick);
        slider.setMinorTickSpacing(minorTick);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // if user drags the slider, collect settings to send to FractalSubject
                collectOptions();
            }
        });
        return slider;
    }

    /** Collect all settings from sliders and buttons and send to FractalSubject */
    private void collectOptions() {
        // make int array of all slider values
        int[] sliderInfo = {
                recursionDepthSlider.getValue(),
                childParentRatioSlider.getValue(),
                leftChildAngleSlider.getValue(),
                rightChildAngleSlider.getValue(),
                trunkLengthSlider.getValue(),
                trunkWidthSlider.getValue()
        };

        // make Color array of trunkColor and leafColor
        Color[] colorInfo = {trunkColor, leafColor};

        // send slider and color values to FractalSubject
        subject.setOptions(sliderInfo, colorInfo);
    }

    /**
     * Generate a random color for the trunk and leaf; used in randomizeButton
     *
     * @param r Random object
     * @return  a Color with random red, green, blue values
     */
    private Color generateRandomColor(Random r) {
        // generate a random number from 0 to 255 for red, green, blue
        int red = r.nextInt(256);
        int green = r.nextInt(256);
        int blue = r.nextInt(256);

        // return a new Color with randomized values
        return new Color(red, green, blue);
    }
}
