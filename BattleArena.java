import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BattleArena extends Application {

    private Stage window;
    private Pane gameRoot;
    private Scene menuScene;
    private Scene gameScene;
    private Shooters player1;
    private Shooters player2;
    private int p1Direction = 1;
    private int p2Direction = -1;
    private List<Projectile> projectiles = new ArrayList<>();
    private Set<KeyCode> activeKeys = new HashSet<>();
    private AnimationTimer gameLoop;
    private Pane p1HealthHeart;
    private Pane p2HealthHeart;
    private Label winnerLabel;
    private Label p1WeaponLabel;
    private Label p2WeaponLabel;
    private ComboBox<String> p1ClassSelect;
    private ComboBox<String> p1WeaponSelect;
    private ComboBox<String> p2ClassSelect;
    private ComboBox<String> p2WeaponSelect;
    private final Modality ARENA_MODAL = Modality.APPLICATION_MODAL;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Battle Arena ");
        createMenu();
        window.setScene(menuScene);
        window.show();
    }

    private void createMenu() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 65%, #ffff99, #ffcc33, #ff6600, #cc3300);");
        Label title = new Label("BATTLE ARENA⚔");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setTextFill(Color.DARKBLUE);
        HBox p1Setup = new HBox(10);
        p1Setup.setAlignment(Pos.CENTER);
        p1ClassSelect = new ComboBox<>();
        p1ClassSelect.getItems().addAll("Prince", "Archer", "Hunter");
        p1ClassSelect.setValue("Archer");
        p1ClassSelect.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: white;");
        p1WeaponSelect = new ComboBox<>();
        p1WeaponSelect.getItems().addAll("AKM", "Sword", "Pistol");
        p1WeaponSelect.setValue("AKM");
        p1WeaponSelect.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: white;");
        Label p1Label = new Label("PLAYER 1:");
        p1Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        p1Label.setTextFill(Color.MEDIUMPURPLE);
        p1Setup.getChildren().addAll(p1Label, p1ClassSelect, p1WeaponSelect);
        HBox p2Setup = new HBox(10);
        p2Setup.setAlignment(Pos.CENTER);
        p2ClassSelect = new ComboBox<>();
        p2ClassSelect.getItems().addAll("Prince", "Archer", "Hunter");
        p2ClassSelect.setValue("Prince");
        p2ClassSelect.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: white;");
        p2WeaponSelect = new ComboBox<>();
        p2WeaponSelect.getItems().addAll("AKM", "Sword", "Pistol");
        p2WeaponSelect.setValue("Sword");
        p2WeaponSelect.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-text-fill: white;");
        Label p2Label = new Label("PLAYER 2:");
        p2Label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        p2Label.setTextFill(Color.RED);
        p2Setup.getChildren().addAll(p2Label, p2ClassSelect, p2WeaponSelect);
        Button startBtn = new Button("START MATCH");
        startBtn.setStyle("-fx-font-size: 24px; -fx-background-color: linear-gradient(to bottom, #e52d27, #b31217); " + "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 15px 30px;");
        startBtn.setOnAction(e -> startGame());

        Button instructionsBtn = new Button(" HOW TO PLAY 📖");
        instructionsBtn.setStyle("-fx-font-size: 18px; -fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); " + "-fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 25px;");
        instructionsBtn.setOnAction(e -> showInstructions());

        layout.getChildren().addAll(title, p1Setup, p2Setup, startBtn, instructionsBtn);
        menuScene = new Scene(layout, 800, 600);
    }
    private void showInstructions() {
        Stage instructionsStage = new Stage();
        instructionsStage.setTitle("Game Instructions");
        instructionsStage.initModality(ARENA_MODAL);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, lightblue, white); -fx-padding: 30;");

        Label title = new Label("🎮 GAME CONTROLS 🎮");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.DARKBLUE);
        VBox p1Controls = new VBox(10);
        p1Controls.setAlignment(Pos.CENTER_LEFT);
        Label p1Title = new Label("PLAYER 1 (BLUE):");
        p1Title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        p1Title.setTextFill(Color.BLUE);
        p1Controls.getChildren().addAll(
                p1Title,
                createInstructionRow("W", "Move Up"),
                createInstructionRow("A", "Move Left / Face Left"),
                createInstructionRow("S", "Move Down"),
                createInstructionRow("D", "Move Right / Face Right"),
                createInstructionRow("SPACE", "Shoot"),
                createInstructionRow("R", "Switch Weapon")
        );
        VBox p2Controls = new VBox(10);
        p2Controls.setAlignment(Pos.CENTER_LEFT);
        Label p2Title = new Label("PLAYER 2 (RED):");
        p2Title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        p2Title.setTextFill(Color.RED);
        p2Controls.getChildren().addAll(
                p2Title,
                createInstructionRow("UP ARROW", "Move Up"),
                createInstructionRow("LEFT ARROW", "Move Left / Face Left"),
                createInstructionRow("DOWN ARROW", "Move Down"),
                createInstructionRow("RIGHT ARROW", "Move Right / Face Right"),
                createInstructionRow("ENTER", "Shoot"),
                createInstructionRow("L", "Switch Weapon")
        );
        VBox rules = new VBox(10);
        rules.setAlignment(Pos.CENTER_LEFT);
        Label rulesTitle = new Label("GAME RULES:");
        rulesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rulesTitle.setTextFill(Color.DARKGREEN);
        rules.getChildren().addAll(
                rulesTitle,
                createRuleRow("• Each player starts with full health"),
                createRuleRow("• Shoot the opponent to reduce their health"),
                createRuleRow("• First player to lose all health loses"),
                createRuleRow("• Different weapons have different damage and cooldown"),
                createRuleRow("• Sword: Medium damage, medium speed"),
                createRuleRow("• Pistol: Fast shooting, lower damage"),
                createRuleRow("• AKM: High damage, slower shooting")
        );
        Button closeBtn = new Button("GOT IT!");
        closeBtn.setStyle("-fx-font-size: 18px; -fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30;");
        closeBtn.setOnAction(e -> instructionsStage.close());
        layout.getChildren().addAll(title, p1Controls, p2Controls, rules, closeBtn);
        Scene instructionsScene = new Scene(layout, 500, 700);
        instructionsStage.setScene(instructionsScene);
        instructionsStage.showAndWait();
    }

    private HBox createInstructionRow(String key, String action) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        Label keyLabel = new Label(key);
        keyLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 16));
        keyLabel.setTextFill(Color.WHITE);
        keyLabel.setStyle("-fx-background-color: #333; -fx-padding: 5 10; -fx-border-radius: 5;");
        Label actionLabel = new Label("→ " + action);
        actionLabel.setFont(Font.font("Arial", 16));
        row.getChildren().addAll(keyLabel, actionLabel);
        return row;
    }

    private Label createRuleRow(String text) {
        Label rule = new Label(text);
        rule.setFont(Font.font("Arial", 15));
        rule.setWrapText(true);
        rule.setMaxWidth(400);
        return rule;
    }

    private void startGame() {
        gameRoot = new Pane();
        gameRoot.setPrefSize(800, 600);
        gameRoot.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 70%, #ffff00, #ffcc00, #ff9900, #996600);");
        Rectangle divider = new Rectangle(398, 0, 4, 600);
        divider.setFill(Color.DARKGRAY);
        divider.setStroke(Color.GOLD);
        divider.setStrokeWidth(2);
        gameRoot.getChildren().add(divider);
        String p1Class = p1ClassSelect.getValue();
        if (p1Class.equals("Prince")) player1 = new Prince("P1", 100, 300, Color.BLUE);
        else if (p1Class.equals("Hunter")) player1 = new Hunter("PLAYER 1", 100, 300, Color.BLUE);
        else player1 = new Archer("PLAYER 1", 100, 300, Color.BLUE);
        player1.equipWeapon(createWeapon(p1WeaponSelect.getValue()));
        String p2Class = p2ClassSelect.getValue();
        if (p2Class.equals("Prince")) player2 = new Prince("P2", 650, 300, Color.RED);
        else if (p2Class.equals("Hunter")) player2 = new Hunter("PLAYER 2", 650, 300, Color.RED);
        else player2 = new Archer("PLAYER 2", 650, 300, Color.RED);
        player2.equipWeapon(createWeapon(p2WeaponSelect.getValue()));
        createGame();
        gameRoot.getChildren().addAll(player1.getSprite(), player2.getSprite());
        gameScene = new Scene(gameRoot, 800, 600);
        setupInput();
        window.setScene(gameScene);
        gameRoot.requestFocus();
        projectiles.clear();
        p1Direction = 1;
        p2Direction = -1;
        startGameLoop();
    }

    private Weapons createWeapon(String type) {
        if (type.contains("Pistol")) return new Pistol();
        if (type.contains("AKM")) return new AKM();
        return new Sword();
    }

    private void createGame() {
        Label p1HealthText = new Label("P1 Health:");
        p1HealthText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        p1HealthText.setTextFill(Color.WHITE);
        p1HealthText.setLayoutX(20);
        p1HealthText.setLayoutY(10);
        p1HealthHeart = createHealthBar(40, 35, Color.BLUE, 1.0);
        p1WeaponLabel = new Label("Weapon: " + player1.getWeapon().getType());
        p1WeaponLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        p1WeaponLabel.setTextFill(Color.DARKBLUE);
        p1WeaponLabel.setLayoutX(20);
        p1WeaponLabel.setLayoutY(45);
        Label p2HealthText = new Label("P2 Health:");
        p2HealthText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        p2HealthText.setTextFill(Color.WHITE);
        p2HealthText.setLayoutX(580);
        p2HealthText.setLayoutY(10);
        p2HealthHeart = createHealthBar(650, 35, Color.RED, 1.0);
        p2WeaponLabel = new Label("Weapon: " + player2.getWeapon().getType());
        p2WeaponLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        p2WeaponLabel.setTextFill(Color.DARKRED);
        p2WeaponLabel.setLayoutX(580);
        p2WeaponLabel.setLayoutY(45);
        gameRoot.getChildren().addAll(
                p1HealthText, p1HealthHeart, p1WeaponLabel,
                p2HealthText, p2HealthHeart, p2WeaponLabel
        );

        winnerLabel = new Label("");
        winnerLabel.setFont(Font.font("Impact", FontWeight.BOLD, 60));
        winnerLabel.setLayoutX(150);
        winnerLabel.setLayoutY(250);

        gameRoot.getChildren().add(winnerLabel);
    }

    private Pane createHealthBar(double x, double y, Color color, double healthPercentage) {
        Pane healthPane = new Pane();
        healthPane.setLayoutX(x);
        healthPane.setLayoutY(y);

        Rectangle background = new Rectangle(0, 0, 100, 20);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.BLACK);

        Rectangle healthFill = new Rectangle(0, 0, 100 * healthPercentage, 20);
        healthFill.setFill(color);

        Label healthLabel = new Label(String.format("%.0f%%", healthPercentage * 100));
        healthLabel.setLayoutX(35);
        healthLabel.setLayoutY(2);
        healthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        healthLabel.setTextFill(Color.WHITE);

        healthPane.getChildren().addAll(background, healthFill, healthLabel);

        return healthPane;
    }
    private void updateHealthHearts() {
        double p1HealthPercent = (double)player1.getCurrentHealth() / player1.getMaxHealth();
        double p2HealthPercent = (double)player2.getCurrentHealth() / player2.getMaxhealth();

        gameRoot.getChildren().remove(p1HealthHeart);
        gameRoot.getChildren().remove(p2HealthHeart);

        p1HealthHeart = createHealthBar(100, 10, Color.BLUE, p1HealthPercent);
        p2HealthHeart = createHealthBar(660, 10, Color.RED, p2HealthPercent);

        gameRoot.getChildren().addAll(p1HealthHeart, p2HealthHeart);
    }


    private void setupInput() {
        gameScene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            if (e.getCode() == KeyCode.R) {
                player1.switchWeapon();
                p1WeaponLabel.setText("Weapon: " + player1.getWeapon().getType());
            }
            if (e.getCode() == KeyCode.L) {
                player2.switchWeapon();
                p2WeaponLabel.setText("Weapon: " + player2.getWeapon().getType());
            }
        });
        gameScene.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();
    }

    private void update() {
        if (player1.isDead() || player2.isDead()) return;
        if (activeKeys.contains(KeyCode.W)) player1.move(0, -5);
        if (activeKeys.contains(KeyCode.S)) player1.move(0, 5);
        if (activeKeys.contains(KeyCode.A)) {
            if (player1.getX() > 0) player1.move(-5, 0);
        }
        if (activeKeys.contains(KeyCode.D)) {
            if (player1.getX() < 760) player1.move(5, 0);
        }
        if (activeKeys.contains(KeyCode.D)) {
            if (player1.getX() < 760) player1.move(5, 0);
        }
        if (activeKeys.contains(KeyCode.SPACE)) shoot(player1,p1Direction);
        if (activeKeys.contains(KeyCode.UP)) player2.move(0, -5);
        if (activeKeys.contains(KeyCode.DOWN)) player2.move(0, 5);
        if (activeKeys.contains(KeyCode.LEFT)) {
            if (player2.getX() > 0) player2.move(-5, 0);
        }
        if (activeKeys.contains(KeyCode.RIGHT)) {
            if (player2.getX() < 760) player2.move(5, 0);
        }
        if (activeKeys.contains(KeyCode.ENTER)) shoot(player2,p2Direction);

        updateProjectiles();
        updateHealthHearts();
    }
    private void shoot(Shooters shooter, int direction) {
        Projectile p = shooter.shootInDirection();
        if (p != null) {
            projectiles.add(p);
            gameRoot.getChildren().add(p.getSprite());
        }
    }

    private void updateProjectiles() {
        List<Projectile> toRemove = new ArrayList<>();
        for (Projectile p : projectiles) {
            p.move();
            if (p.getSprite().getBoundsInParent().intersects(player2.getSprite().getBoundsInParent())) {
                player2.takeDamage(p.getDamage());
                toRemove.add(p);
            }
            else if (p.getSprite().getBoundsInParent().intersects(player1.getSprite().getBoundsInParent())) {
                player1.takeDamage(p.getDamage());
                toRemove.add(p);
            }
            if (p.getX() < 0 || p.getX() > 800) toRemove.add(p);
        }
        for (Projectile p : toRemove) {
            gameRoot.getChildren().remove(p.getSprite());
            projectiles.remove(p);
        }
        checkWinner();
    }
    private void checkWinner() {
        if (player1.isDead() || player2.isDead()) {
            gameLoop.stop();

            if (player1.isDead()) {
                winnerLabel.setText("    PLAYER 2 WINS\uD83C\uDFC6");
                winnerLabel.setTextFill(Color.RED);
            } else {
                winnerLabel.setText("    PLAYER 1 WINS\uD83C\uDFC6");
                winnerLabel.setTextFill(Color.BLUE);
            }

            HBox buttonBox = new HBox(20);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setLayoutX(250);
            buttonBox.setLayoutY(320);

            Button playAgainBtn = new Button("Play Again");
            playAgainBtn.setStyle("-fx-font-size: 18px; -fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold;");
            playAgainBtn.setOnAction(e -> {
                startGame();
            });

            Button mainMenuBtn = new Button("Main Menu");
            mainMenuBtn.setStyle("-fx-font-size: 18px; -fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            mainMenuBtn.setOnAction(e -> window.setScene(menuScene));

            buttonBox.getChildren().addAll(playAgainBtn, mainMenuBtn);
            gameRoot.getChildren().removeIf(node -> node instanceof Button || node instanceof HBox);
            gameRoot.getChildren().add(buttonBox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}