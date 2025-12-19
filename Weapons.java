public abstract class Weapons {
    protected String type;
    protected int damage;
    protected long cooldown;
    protected double speed;
    protected long lastShot = 0;

    public Weapons(String type, int damage, long cooldown, double speed) {
        this.type = type;
        this.damage = damage;
        this.cooldown = cooldown;
        this.speed = speed;
    }

    public boolean canShoot() {
        if (System.currentTimeMillis() - lastShot >= cooldown) {
            lastShot = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public abstract Projectile shoot(double x, double y);
}