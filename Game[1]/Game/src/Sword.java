import javafx.scene.paint.Color;

public class Sword extends Weapons {
    public Sword() {
        super("Sword", 10, 400, 15.0);
    }
    @Override
    public Projectile shoot(double x, double y) {
        Projectile p = new Projectile(x, y, speed, damage);
        p.getSprite().setFill(Color.BLUE);
        return p;
    }
}