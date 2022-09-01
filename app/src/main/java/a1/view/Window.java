package a1.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
//        System.out.println("window initiated");
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public void run() {
        this.draw();
    }

    private void draw() {
        Rectangle tabBar = new Rectangle(10, 0, width, height);
        tabBar.setFill(Paint.valueOf("ORANGE"));
        tabBar.setViewOrder(10.0);
        pane.getChildren().add(tabBar);
    }
}
