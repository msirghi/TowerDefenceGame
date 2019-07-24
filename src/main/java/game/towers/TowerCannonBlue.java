package game.towers;

import game.projectiles.ProjectileCannon;
import game.enemies.Enemy;
import game.tiles.Tile;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower {

  public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
    super(type, startTile, enemies);
  }

  @Override
  public void shoot(Enemy target) {
    super.projectiles.add(new ProjectileCannon(super.type.type, super.getTarget(), super.getX(),
            super.getY(), 32, 32));
    super.target.reduceHealth(super.type.type.damage);
  }
}
