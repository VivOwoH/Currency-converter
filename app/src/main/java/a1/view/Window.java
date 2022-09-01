package a1.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Window {
    private final int height;
    private final int width;
    private final Pane pane;
    private final Scene scene;
    
    public Window(int height, int width) {
        this.height = height;
        this.width = width;

        pane = new Pane();
        scene = new Scene(pane, width, height);
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public void run() {
        self.draw();
    }

    private void draw() {
        Rectangle tabBar = new Rectangle(10, 0, width, height);
    }
}
