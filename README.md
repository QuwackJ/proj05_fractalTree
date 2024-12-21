This is a project I did in college to learn about creating fractals with recursion, GUIs, and the Observer design pattern.

The goal of the project is to create a GUI that allows a user to generate a tree fractal of a specified recursion depth, child-to-parent ratio, left child angle, right child angle, trunk length, trunk width, trunk color, and leaf color. Any changes made to the tree fractal are displayed live. There is also a button to randomize the tree fractal settings.

Using the Observer design pattern, the GUI sends the tree fractal settings to the Subject (in this case, the concrete Subject is FractalGenerator) so it can recursively generate the fractal. The Subject also has a list of Observers it keeps track of and notifies. The Observer (in this case, the concrete Observer is FractalDrawing) receives these updates and draws itself accordingly.
