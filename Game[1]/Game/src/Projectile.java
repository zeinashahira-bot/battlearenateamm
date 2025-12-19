import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Projectile {
    private Circle sprite;
    private double speed;
    private int damage;
    private int directionX;
    private int directionY;
    public Projectile(double x, double y, double speed, int damage) {
        this.speed = speed;
        this.damage = damage;
        this.sprite = new Circle(x, y, 5, Color.BLACK);
        this.directionX = 1;
        this.directionY = 0;
    }

    public void move() {
        sprite.setCenterX(sprite.getCenterX() + (speed * directionX));
        sprite.setCenterY(sprite.getCenterY() + (speed * directionY));
    }
    public void setDirection(int dirX, int dirY) {
        this.directionX = dirX;
        this.directionY = dirY;
    }
    public void setDirectionX(int dirX) {
        this.directionX = dirX;
    }

    public void setDirectionY(int dirY) {
        this.directionY = dirY;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public Circle getSprite() {

        return sprite;
    }
    public double getX() {

        return sprite.getCenterX();
    }
    public double getY() {
        return sprite.getCenterY();
    }
    public int getDamage() {
        return damage;
    }
}