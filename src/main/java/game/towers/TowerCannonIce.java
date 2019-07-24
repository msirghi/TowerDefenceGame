package game.towers;

import game.projectiles.ProjectileIce;
import game.enemies.Enemy;
import game.tiles.Tile;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class TowerCannonIce extends Tower {
  public TowerCannonIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  public void shoot(Enemy target) {
    super.projectiles.add(new ProjectileIce(super.type.type, super.getTarget(), super.getX(),
            super.getY(), 32, 32));
    super.target.reduceHealth(super.type.type.damage);
  }

}
