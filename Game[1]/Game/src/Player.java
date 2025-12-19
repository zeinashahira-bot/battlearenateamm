public class Player {
    private Shooters character;
    private Weapons weapon;
    private double x;
    private double y;
    private double speed =3;

    public Player(Shooters character, Weapons weapon, double x, double y, double speed) {
        this.character = character;
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    public void move(double dx,double dy){
        x+=dx;
        y+=dy;
    }
    public Projectile shoot(){

        return weapon.shoot(x,y);
    }
    public double getX() {

        return x;
    }
    public double getY() {

        return y;
    }
    public Shooters getCharacter() {

        return character;
    }
    public Weapons getWeapon() {

        return weapon;
    }
}