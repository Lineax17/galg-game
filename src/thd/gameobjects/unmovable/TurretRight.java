package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.ShotLeftwards;
import thd.gameobjects.movable.XWingShot;
import thd.gameobjects.movable.XWing;

import java.util.Random;

/**
 * Describing a static gameobject that looks like a turret.
 */
public class TurretRight extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject<XWing> {
    private String[] namesOfAssets;
    private Random random;
    private String imageName;
    private int randomIndex;


    /**
     * Initializes a new big turret.
     *
     * @param gameView        Instance of {@link GameView}.
     * @param gamePlayManager Instance of {@link GamePlayManager}.
     * @see GameView
     * @see GamePlayManager
     */
    public TurretRight(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        random = new Random();
        super.size = 30;
        super.rotation = 0;
        super.width = 150;
        super.height = 33;
        super.speedInPixel = 2;
        distanceToBackground = 5;
        namesOfAssets = new String[]{"turret_right_big.png", "turret_right_dual.png", "turret_right_single.png", "turret_right_small.png", "turret_right_weird.png"};
        randomIndex = random.nextInt(namesOfAssets.length);
        imageName = namesOfAssets[randomIndex];
        hitBoxOffsets(0, 0, -120, 0);

    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof XWingShot) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public String toString() {
        return "TurretRight: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.getY() > 720) {
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public void updateStatus() {
        shoot();
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(imageName, position.getX(), position.getY(), 2.0, rotation);
    }

    private void shoot() {
        if (gameView.timer(3000, this)) {
            gamePlayManager.spawnGameObject(new ShotLeftwards(gameView, gamePlayManager, this));
            gameView.playSound("laser.wav", false);
        }
    }

    @Override
    public boolean tryToActivate(XWing xWing) {
        return position.getY() > - 100;
    }
}
