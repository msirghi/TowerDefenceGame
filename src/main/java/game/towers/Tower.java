package game.towers;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.enemies.Enemy;
import game.others.Entity;
import game.projectiles.Projectile;
import game.tiles.Tile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newdawn.slick.opengl.Texture;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@NoArgsConstructor
public abstract class Tower implements Entity {
  private float x;
  private float y;
  private float timeSinceLastShot;
  private float firingSpeed;
  private float angle;
  private int width;
  private int height;
  private int damage;
  private int range;
  private int cost;
  private boolean targeted;
  public Enemy target;
  private Texture[] textures;
  private CopyOnWriteArrayList<Enemy> enemies;
  public List<Projectile> projectiles;
  public TowerType type;

  public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    this.textures = type.textures;
    this.type = type;
    this.cost = type.cost;
    this.damage = type.damage;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.range = type.range;
    this.width = startTile.getWidth();
    this.height = startTile.getHeight();
    this.enemies = enemies;
    this.targeted = false;
    this.firingSpeed = type.firingSpeed;
    this.timeSinceLastShot = 0f;
    this.angle = 0f;
    this.projectiles = new ArrayList<>();
  }

  @Override
  public void update() {
    if (!targeted || !target.isAlive()) {
      target = getTarget();
    } else {
      angle = calculateAngle();
      if (timeSinceLastShot > firingSpeed) {
        shoot(target);
        try {
          URL url = this.getClass().getClassLoader().getResource("fire.wav");
          AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
          Clip clip = AudioSystem.getClip();
          clip.open(audioIn);
          clip.start();
        } catch (Exception e) {

        }
        timeSinceLastShot = 0;
      }
    }

    if (target == null || !target.isAlive()) {
      targeted = false;
    }
    timeSinceLastShot += Metrics.delta();

    for (Projectile projectile : projectiles) {
      projectile.update();
    }
    draw();
  }

  @Override
  public void draw() {
    MainLoader.drawQuadTex(textures[0], x, y, width, height);
    if (textures.length > 1) {
      for (int i = 1; i < textures.length; i++) {
        MainLoader.drawQuadTexRot(textures[i], x, y, width, height, angle);
      }
    }
  }

  private boolean isInRange(Enemy enemy) {
    // x & y - position of the tower
    float xDistance = Math.abs(enemy.getX() - x);
    float yDistance = Math.abs(enemy.getY() - y);

    if (xDistance < range && yDistance < range) {
      return true;
    }
    return false;
  }

  public Enemy getTarget() {
    Enemy closestEnemy = null;
    float closestDistance = 10000;
    for (Enemy enemy : enemies) {
      //closest one
      if (isInRange(enemy) && findDistance(enemy) < closestDistance &&
               enemy.isAlive()) {
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

  private float calculateAngle() {
    double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
    return (float) Math.toDegrees(angleTemp) - 90;
  }

  public abstract void shoot(Enemy target);

  public void updateEnemyList(CopyOnWriteArrayList<Enemy> enemyList) {
    enemies = enemyList;
  }
}
