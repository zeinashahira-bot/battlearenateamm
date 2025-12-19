import javafx.scene.paint.Color;
public class Pistol extends Weapons {
    public Pistol() {

        super("Pistol", 15, 600, 10.0);
    }
    @Override
    public Projectile shoot(double x, double y) {
        Projectile p = new Projectile(x, y, speed, damage);
        p.getSprite().setFill(Color.BLACK);
        p.getSprite().setRadius(7);
        return p;
    }
}