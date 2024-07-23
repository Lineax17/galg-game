package thd.game.managers;

import thd.game.utilities.GameView;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

class GameWorldManager extends GamePlayManager {
    private final List<CollidingGameObject> wallsForPathDecision;
    private final List<GameObject> activatableGameObjects;


    protected GameWorldManager(GameView gameView) {
        super(gameView);
        activatableGameObjects = new LinkedList<>();
        wallsForPathDecision = new LinkedList<>();
        score = new Score(gameView, this);
        xwing = new XWing(gameView, this, wallsForPathDecision);
        overlay = new Overlay(gameView, this);
        background = new Background(gameView, this);
        liveCounter = new LiveCounter(gameView, this);
    }

    private void spawnGameObjects() {
        spawnGameObject(score);
        spawnGameObject(xwing);
        spawnGameObject(overlay);
        spawnGameObject(background);
        spawnGameObject(liveCounter);
    }

    private void spawnGameObjectsFromWorldString() {
        String[] lines = super.level.world.split("\\R");

        int[][] walls = new int[lines.length][40];

        for (int line = 0; line < lines.length; line++) {
            for (int column = 0; column < lines[line].length() - 1; column++) {
                char character = lines[line].charAt(column);
                if (character == 'W') {
                    walls[line][column] = 1;
                } else {
                    walls[line][column] = 0;
                }
            }
        }


        for (int line = 0; line < lines.length; line++) {
            for (int column = 0; column < lines[line].length(); column++) {
                char character = lines[line].charAt(column);
                if (character == 'W') {
                    Wall wall = new Wall(gameView, this, walls, line, column);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    wall.getPosition().updateCoordinates(x, y);
                    wallsForPathDecision.add(wall);
                    addActivatableGameObject(wall);
                }
            }
        }


        for (int line = 0; line < lines.length; line++) {
            for (int column = 0; column < lines[line].length(); column++) {
                char character = lines[line].charAt(column);

                if (character == 'A') {
                    Alien alien = new Alien(gameView, this, xwing);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    alien.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(alien);
                } else if (character == 'a') {
                    SuperAlien superAlien = new SuperAlien(gameView, this, xwing);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    superAlien.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(superAlien);
                } else if (character == 'G') {
                    Gem gem = new Gem(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    gem.getPosition().updateCoordinates(x, y);
                    gem.addWallsToCollisionList(wallsForPathDecision);
                    addActivatableGameObject(gem);
                    //spawnGameObject(gem);
                } else if (character == 'F') {
                    SpaceFrog spaceFrog = new SpaceFrog(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    spaceFrog.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(spaceFrog);
                    //activateGameObjects();
                } else if (character == 'f') {
                    SuperFrog superFrog = new SuperFrog(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    superFrog.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(superFrog);
                    //activateGameObjects();
                } else if (character == 'H') {
                    Head head = new Head(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    head.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(head);
                } else if (character == 'h') {
                    BlueHead head = new BlueHead(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    head.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(head);
                } else if (character == '+') {
                    Bonus bonus = new Bonus(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    bonus.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(bonus);
                } else if (character == '-') {
                    SuperBonus superBonus = new SuperBonus(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    superBonus.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(superBonus);
                } else if (character == '1') {
                    ObstacleType1 obstacle = new ObstacleType1(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    obstacle.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(obstacle);
                    //activateGameObjects();
                } else if (character == '2') {
                    ObstacleType2 obstacle = new ObstacleType2(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    obstacle.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(obstacle);
                    //activateGameObjects();
                } else if (character == '3') {
                    ObstacleType3 obstacle = new ObstacleType3(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    obstacle.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(obstacle);
                    //activateGameObjects();
                } else if (character == '4') {
                    ObstacleType4 obstacle = new ObstacleType4(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    obstacle.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(obstacle);
                    //activateGameObjects();
                } else if (character == 'R') {
                    TurretRight turretBig = new TurretRight(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    turretBig.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(turretBig);
                    //activateGameObjects();
                } else if (character == 'L') {
                    TurretLeft turretSmall = new TurretLeft(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    turretSmall.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(turretSmall);
                    //activateGameObjects();
                } else if (character == 'U') {
                    Ufo ufo = new Ufo(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    ufo.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(ufo);
                    //activateGameObjects();
                } else if (character == 'S') {
                    Worm worm = new Worm(gameView, this, wallsForPathDecision);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    worm.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(worm);
                    //activateGameObjects();
                } else if (character == 's') {
                    SuperWorm superWorm = new SuperWorm(gameView, this, wallsForPathDecision);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    superWorm.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(superWorm);
                    //activateGameObjects();
                } else if (character == 'B') {
                    Bat bat = new Bat(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    bat.getPosition().updateCoordinates(x, y);
                    bat.addWallsToCollisionList(wallsForPathDecision);
                    addActivatableGameObject(bat);
                    //activateGameObjects();
                } else if (character == 'b') {
                    SuperBat superBat = new SuperBat(gameView, this);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    superBat.getPosition().updateCoordinates(x, y);
                    superBat.addWallsToCollisionList(wallsForPathDecision);
                    addActivatableGameObject(superBat);
                    //activateGameObjects();
                } else if (character == '#') {
                    Booster booster = new Booster(gameView, this, xwing);
                    double x = (column - super.level.worldOffsetColumns) * 32;
                    double y = (line - super.level.worldOffsetLines) * 32;
                    booster.getPosition().updateCoordinates(x, y);
                    addActivatableGameObject(booster);
                }
            }
        }
    }

    protected void initializeLevel() {
        activatableGameObjects.clear();
        destroyAllGameObjects();
        spawnGameObjects();
        spawnGameObjectsFromWorldString();
        clearListsForPathDecisionsInGameObjects();
    }

    private void clearListsForPathDecisionsInGameObjects() {
        wallsForPathDecision.clear();
    }

    private void addActivatableGameObject(GameObject gameObject) {
        activatableGameObjects.add(gameObject);
        addToShiftableGameObjectsIfShiftable(gameObject);
    }

    @Override
    protected void gameLoopUpdate() {
        super.gameLoopUpdate();
        activateGameObjects();
    }

    private void activateGameObjects() {
        ListIterator<GameObject> iterator = activatableGameObjects.listIterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            /*
            if (gameObject instanceof ActivatableGameObject<?> activatableGameObject) {
                if (activatableGameObject instanceof ActivatableGameObject<XWing>)
                if(activatableGameObject.tryToActivate(xwing)) {
                    spawnGameObject(gameObject);
                    iterator.remove();

                }
            }
             */

            if (gameObject instanceof Wall wall) {
                if (wall.tryToActivate(xwing)) {
                    spawnGameObject(wall);
                    iterator.remove();
                }
            } else if (gameObject instanceof Head head) {
                if (head.tryToActivate(xwing)) {
                    spawnGameObject(head);
                    iterator.remove();
                }
            } else if (gameObject instanceof BlueHead head) {
                if (head.tryToActivate(xwing)) {
                    spawnGameObject(head);
                    iterator.remove();
                }
            }else if (gameObject instanceof SpaceFrog spaceFrog) {
                if (spaceFrog.tryToActivate(xwing)) {
                    spawnGameObject(spaceFrog);
                    iterator.remove();
                }
            } else if (gameObject instanceof SuperFrog superFrog) {
                if (superFrog.tryToActivate(xwing)) {
                    spawnGameObject(superFrog);
                    iterator.remove();
                }
            } else if (gameObject instanceof ObstacleType1 obstacle) {
                if (obstacle.tryToActivate(xwing)) {
                    spawnGameObject(obstacle);
                    iterator.remove();
                }
            } else if (gameObject instanceof ObstacleType2 obstacle) {
                if (obstacle.tryToActivate(xwing)) {
                    spawnGameObject(obstacle);
                    iterator.remove();
                }
            } else if (gameObject instanceof ObstacleType3 obstacle) {
                if (obstacle.tryToActivate(xwing)) {
                    spawnGameObject(obstacle);
                    iterator.remove();
                }
            } else if (gameObject instanceof ObstacleType4 obstacle) {
                if (obstacle.tryToActivate(xwing)) {
                    spawnGameObject(obstacle);
                    iterator.remove();
                }
            } else if (gameObject instanceof TurretRight turretBig) {
                if (turretBig.tryToActivate(xwing)) {
                    spawnGameObject(turretBig);
                    iterator.remove();
                }
            } else if (gameObject instanceof TurretLeft turretSmall) {
                if (turretSmall.tryToActivate(xwing)) {
                    spawnGameObject(turretSmall);
                    iterator.remove();
                }
            } else if (gameObject instanceof Alien alien) {
                if (alien.tryToActivate(xwing)) {
                    spawnGameObject(alien);
                    iterator.remove();
                }
            } else if (gameObject instanceof SuperAlien superAlien) {
                if (superAlien.tryToActivate(xwing)) {
                    spawnGameObject(superAlien);
                    iterator.remove();
                }
            } else if (gameObject instanceof Gem gem) {
                if (gem.tryToActivate(xwing)) {
                    spawnGameObject(gem);
                    iterator.remove();
                }
            } else if (gameObject instanceof Ufo ufo) {
                if (ufo.tryToActivate(xwing)) {
                    spawnGameObject(ufo);
                    iterator.remove();
                }
            } else if (gameObject instanceof Worm worm) {
                if (worm.tryToActivate(xwing)) {
                    spawnGameObject(worm);
                    iterator.remove();
                }
            } else if (gameObject instanceof SuperWorm superWorm) {
                if (superWorm.tryToActivate(xwing)) {
                    spawnGameObject(superWorm);
                    iterator.remove();
                }
            } else if (gameObject instanceof Bat bat) {
                if (bat.tryToActivate(xwing)) {
                    spawnGameObject(bat);
                    iterator.remove();
                }
            } else if (gameObject instanceof SuperBat superBat) {
                if (superBat.tryToActivate(xwing)) {
                    spawnGameObject(superBat);
                    iterator.remove();
                }
            } else if (gameObject instanceof Bonus bonus) {
                if (bonus.tryToActivate(xwing)) {
                    spawnGameObject(bonus);
                    iterator.remove();
                }
            } else if (gameObject instanceof SuperBonus superBonus) {
                if (superBonus.tryToActivate(xwing)) {
                    spawnGameObject(superBonus);
                    iterator.remove();
                }
            } else if (gameObject instanceof Booster booster) {
                if (booster.tryToActivate(xwing)) {
                    spawnGameObject(booster);
                    iterator.remove();
                }
            }
        }
    }
}
