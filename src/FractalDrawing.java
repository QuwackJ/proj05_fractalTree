import javax.swing.*;
import java.awt.*;

/**
 * The concrete Observer in the Observer design pattern
 * Registers itself to Subject, receives updates from Subject, and collects information from Subject to draw
 * @author Maya Rao
 * @version 12-6-24
 */
public class FractalDrawing extends JFrame implements FractalObserver {
    // instance variable
    /** Reference to the Subject interface */
    private FractalSubject subject;


    // constructor
    /**
     * Constructs the display window for the tree fractal
     *
     * @param subject   FractalSubject interface (FractalDrawing can't directly interact with concrete Subject)
     */
    public FractalDrawing(FractalSubject subject) {
        // setting subject reference to passed in FractalSubject interface
        this.subject = subject;

        // registering this class (an Observer) to FractalSubject
        this.subject.registerObserver(this);


        // adding a title to the window
        setTitle("Fractal Display");

        // setting window size to 1000 pixels (width) x 775 pixels (height)
        // always width x height
        setSize(1000, 775);

        // setting window location on my screen at (515, 0)
        setLocation(515, 0);

        // don't allow user to resize the window by clicking and dragging
        setResizable(false);

        // quits program when clicking the red button
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // now only using JPanel/DrawArea, done using JFrame
        JPanel panel = new DrawArea();

        // null says no layout manager, set absolute positioning
        panel.setLayout(null);

        // make DrawArea background black
        panel.setBackground(Color.BLACK);

        // attaching DrawArea to JFrame (getContentPane() is a JFrame method)
        getContentPane().add(panel);

        // able to make windows that are present but hidden; we want this to be visible
        setVisible(true);
    }


    // method
    /** Updates the information each Observer has */
    @Override
    public void update() {
        // FractalObserver requests data from FractalSubject to eventually draw
        subject.getFractalElements();

        // resets DrawArea and redraws tree fractal
        repaint();
    }


    /** Creates a custom JPanel (DrawArea) to render the tree fractal */
    private class DrawArea extends JPanel {
        /**
         * Overridden paintComponent to draw each FractalElement retrieved from the Subject
         *
         * @param g the Graphics object to protect
         */
        @Override
        protected void paintComponent(Graphics g) {
            // calling the original paintComponent method
            super.paintComponent(g);

            // for each Branch in the ArrayList of FractalElements
            for (FractalElement branch : subject.getFractalElements()) {
                // make the Branch draw itself
                branch.draw(g);
            }
        }
    }
}
