public class Main {
    public static void main(String[] args) {
        // create a FractalGenerator
        FractalGenerator generator = new FractalGenerator();

        // pass generator to FractalGui
        new FractalGui(generator);

        // pass generator to FractalDrawing
        new FractalDrawing(generator);
    }
}
