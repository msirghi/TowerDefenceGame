package game.projectiles;

import game.enemies.Enemy;
import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.others.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;

@Slf4j
@Getter
@Setter
public abstract class Projectile implements Entity {
  private float x;
  private float y;
  private int width;
  private int height;
  private float xVelocity;
  private float yVelocity;
  private float speed;
  private int damage;
  private boolean alive;
  private Enemy target;
  private Texture texture;

  public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
    this.texture = type.texture;
    this.target = target;
    this.width = width;
    this.height = height;
    this.alive = true;
    this.x = x;
    this.y = y;
    this.speed = type.speed;
    this.damage = type.damage;
    this.xVelocity = 0;
    this.yVelocity = 0;
    calculateDirection();
  }

  //how we need to move to find target
  private void calculateDirection() {
    //x & y - coord of projectiles
    //getX() && getY() - coord of the target
    float totalAllowedMovement = 1f;
    // bullet goes straight to the center of the tile, where is mob

    float xDistanceFromTarget = 0;
    float yDistanceFromTarget = 0;
    try {
      xDistanceFromTarget = Math.abs(target.getX() - x - MainLoader.TILE_SIZE / 4 + MainLoader.TILE_SIZE / 2);
      yDistanceFromTarget= Math.abs(target.getY() - y - MainLoader.TILE_SIZE / 4 + MainLoader.TILE_SIZE / 2);
    float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
    float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
    xVelocity = xPercentOfMovement;
    yVelocity = totalAllowedMovement - xPercentOfMovement;

    if (target.getX() < x) {
      xVelocity *= -1;
    }
    if (target.getY() < y) {
      yVelocity *= -1;
    }
    } catch (NullPointerException e) {
      log.info("NullPointerException was thrown!");
    }
  }

  public void damage() {
    target.damage(damage);
    alive = false;
  }

  public void update() {
    if (alive) {
      calculateDirection();
      x += xVelocity * Metrics.delta() * speed;
      y += yVelocity * Metrics.delta() * speed;
      try {
        if (MainLoader.CheckCollision(x, y, width, height, target.getX(), target.getY(),
                target.getWidth(), target.getHeight())) {
          //bullet is not visible when it hits the target
          damage();
        }
        draw();
      } catch (NullPointerException e) {

      }
    }
  }

  public void draw() {
    MainLoader.drawQuadTex(texture, x, y, 32, 32);
  }
}
