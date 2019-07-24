package game.projectiles;

import game.enemies.Enemy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ProjectileCannon extends Projectile {

  public ProjectileCannon(ProjectileType type, Enemy target, float x, float y,
                          int width, int height) {
    super(type, target, x, y, width, height);
  }

  @Override
  public void damage() {
//    super.getTarget().setSpeed(4f);
    //for damaging
    super.damage();
  }
}
