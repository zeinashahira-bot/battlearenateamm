import javafx.scene.paint.Color;

public class AKM extends Weapons {

    public AKM() {
        super("AKM", 25, 1200, 13.0);
    }
    @Override
    public Projectile shoot(double startX, double startY) {
        Projectile p = new Projectile(startX, startY, speed, damage);
        p.getSprite().setFill(Color.MEDIUMSLATEBLUE);
        p.getSprite().setRadius(8);
        return p;
    }
}
