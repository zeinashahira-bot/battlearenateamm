import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Hunter extends Shooters {
    public Hunter(String name, int x, int y, Color color) {

        super(name, 120, x, y, color);
        Rectangle hat = new Rectangle(15, 0, 10, 5);
        hat.setFill(Color.DARKGREEN);
        hat.setStroke(Color.BLACK);
        ((Group)getSprite()).getChildren().add(hat);
    }
}