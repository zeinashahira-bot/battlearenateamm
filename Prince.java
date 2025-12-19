import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Prince extends Shooters {
    public Prince(String name, int x, int y, Color color) {

        super(name, 150, x, y, color);
        Circle crownPart1 = new Circle(20, 10, 3);
        crownPart1.setFill(Color.GOLD);
        crownPart1.setStroke(Color.BLACK);

        Circle crownPart2 = new Circle(15, 12, 3);
        crownPart2.setFill(Color.GOLD);
        crownPart2.setStroke(Color.BLACK);

        Circle crownPart3 = new Circle(25, 12, 3);
        crownPart3.setFill(Color.GOLD);
        crownPart3.setStroke(Color.BLACK);

        ((Group)getSprite()).getChildren().addAll(crownPart1, crownPart2, crownPart3);
    }
}