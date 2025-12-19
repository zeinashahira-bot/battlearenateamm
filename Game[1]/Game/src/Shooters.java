import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Shooters {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected Group sprite;
    protected Weapons currentWeapon;
    protected List<Weapons> inventory = new ArrayList<>();

    protected int directionX = 1;
    protected int directionY = 0;
    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
    public Shooters(String name, int maxHp, int x, int y, Color color) {

        this(name, maxHp, maxHp, x, y, color);
    }

    public Shooters(String name, int maxHp, int currentHp, int x, int y, Color color) {
        this.name = name;
        this.maxHealth = maxHp;
        this.currentHealth = currentHp;
        this.sprite = createRectangularShape(x, y, color);
        inventory.add(new Sword());
        inventory.add(new Pistol());
        inventory.add(new AKM());
    }

    private Group createRectangularShape(double x, double y, Color color) {
        Group Character = new Group();

        Rectangle body = new Rectangle(0, 0, 40, 60);
        body.setFill(color);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(2);

        Circle innerCircle = new Circle(20, 20, 10);
        innerCircle.setFill(color.brighter());
        innerCircle.setStroke(Color.BLACK);
        innerCircle.setStrokeWidth(1);

        Rectangle weaponIndicator = new Rectangle(30, 40, 5, 15);
        weaponIndicator.setFill(Color.GOLD);
        weaponIndicator.setStroke(Color.BLACK);
        weaponIndicator.setStrokeWidth(1);

        Character.getChildren().addAll(body, innerCircle, weaponIndicator);
        Character.setLayoutX(x);
        Character.setLayoutY(y);
        return Character;
    }

    public void move(double dx, double dy) {
        double newX = sprite.getLayoutX() + dx;
        double newY = sprite.getLayoutY() + dy;
        if (newX >= 0 && newX <= 760) sprite.setLayoutX(newX);
        if (newY >= 0 && newY <= 540) sprite.setLayoutY(newY);
        if (dx > 0) {
            directionX = 1;
            directionY = 0;
            sprite.setRotate(0);
        } else if (dx < 0) {
            directionX = -1;
            directionY = 0;
            sprite.setRotate(180);
        } else if (dy > 0) {
            directionX = 0;
            directionY = 1;
            sprite.setRotate(90);
        } else if (dy < 0) {
            directionX = 0;
            directionY = -1;
            sprite.setRotate(-90);
        }
    }
    public void equipWeapon(Weapons w) {

        this.currentWeapon = w;
        if (sprite.getChildren().size() > 2) {
            Rectangle weaponIndicator = (Rectangle) sprite.getChildren().get(2);
            if (w instanceof Sword) {
                weaponIndicator.setFill(Color.SILVER);
            } else if (w instanceof AKM) {
                weaponIndicator.setFill(Color.DARKSLATEGRAY);
            } else if (w instanceof Pistol) {
                weaponIndicator.setFill(Color.BLACK);
            }
        }
    }
    public void switchWeapon() {
        if (currentWeapon == null) return;
        int currentIndex = -1;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).getType().equals(currentWeapon.getType())) {
                currentIndex = i;
                break;
            }
        }
        int nextIndex = (currentIndex + 1) % inventory.size();
        this.currentWeapon = inventory.get(nextIndex);
        equipWeapon(currentWeapon);
    }
    public Weapons getWeapon() {
        return currentWeapon;
    }
    public Group getSprite() {
        return sprite;
    }
    public double getX() {
        return sprite.getLayoutX();
    }
    public double getY() {
        return sprite.getLayoutY();
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getMaxhealth() {
        return maxHealth;
    }
    public void takeDamage(int dmg) {
        currentHealth -= dmg;
        if(currentHealth < 0) currentHealth = 0;
        if (sprite.getChildren().size() > 5 && sprite.getChildren().get(5) instanceof Circle) {
            Circle innerCircle = (Circle) sprite.getChildren().get(1);
            Color originalColor = (Color) innerCircle.getFill();
            innerCircle.setFill(Color.RED);
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                javafx.application.Platform.runLater(() -> innerCircle.setFill(originalColor));
            }).start();
        }
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }
    public Projectile shootInDirection() {
        if (currentWeapon == null || !currentWeapon.canShoot()) {
            return null;
        }

        double startX = getX() + 20;
        double startY = getY() + 30;
        if (directionX == 1) {
            startX = getX() + 50;
        } else if (directionX == -1) {
            startX = getX() - 10;
        } else if (directionY == 1) {
            startY = getY() + 60;
        } else if (directionY == -1) {
            startY = getY() - 10;
        }
        Projectile p = currentWeapon.shoot(startX, startY);
        p.setDirection(directionX, directionY);
        return p;
    }
}