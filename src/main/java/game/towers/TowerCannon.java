package game.towers;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.enemies.Enemy;
import game.projectiles.Projectile;
import game.tiles.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Getter
@Setter
public class TowerCannon {
  private float x;
  private float y;
  private float angle;
  private int width;
  private int height;
  private int damage;
  private int range;
  private float timeSinceLastShot;
  private float firingSpeed;
  private boolean targeted;
  private Tile startTile;
  private Texture baseTexture;
  private Texture cannonTexture;
  private List<Projectile> projectiles;
  private CopyOnWriteArrayList<Enemy> enemies;
  private Enemy target;

  private Enemy getTarget() {
    Enemy closestEnemy = null;
    float closestDistance = 10000;
    for (Enemy enemy : enemies) {
      if (isInRange(enemy) && findDistance(enemy) < closestDistance) {
        closestDistance = findDistance(enemy);
        closestEnemy = enemy;
      }
    }
    if (closestEnemy != null) {
      targeted = true;
    }
    return closestEnemy;
  }

  private float findDistance(Enemy enemy) {
    float xDistance = Math.abs(enemy.getX() - x);
    float yDistance = Math.abs(enemy.getY() - y);
    return xDistance + yDistance;
  }

  private boolean isInRange(Enemy enemy) {
    // x & y - position of the tower
    float xDistance = Math.abs(enemy.getX() - x);
    float yDistance = Math.abs(enemy.getY() - y);
    return xDistance < range && yDistance < range;
  }

  private float calculateAngle() {
    double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
    return (float) Math.toDegrees(angleTemp) - 90;
  }

  public void update() {
    if (!targeted) {
      target = getTarget();
    }

    if (target == null || !target.isAlive()) {
      targeted = false;
    }

    timeSinceLastShot += Metrics.delta();
    if (timeSinceLastShot > firingSpeed) {
      shoot();
    }

    projectiles.forEach(Projectile::update);
    angle = calculateAngle();
    draw();
  }

  private void shoot() {
    timeSinceLastShot = 0;
  }

  public void draw() {
    MainLoader.drawQuadTex(baseTexture, x, y, width, height);
    MainLoader.drawQuadTexRot(cannonTexture, x, y, width, height, angle);
  }
}
