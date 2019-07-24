package game.projectiles;

import game.enemies.Enemy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ProjectileIce extends Projectile {

  public ProjectileIce(ProjectileType type, Enemy target, float x, float y, int width, int height) {
    super(type, target, x, y, width, height);
  }

  @Override
  public void damage() {
    super.getTarget().setSpeed(4f);
    //when shoots, projectile will no longer exist
//    super.setAlive(false);
    //for damaging
    super.damage();
  }
}
