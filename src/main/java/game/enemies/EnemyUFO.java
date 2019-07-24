package game.enemies;

import game.tiles.Tile;
import game.tiles.Map;
import org.newdawn.slick.opengl.Texture;

public class EnemyUFO extends Enemy {
  public EnemyUFO(int tileX, int tileY, Map grid) {
    super(tileX, tileY, grid);
//    this.setTexture();
  }

  public EnemyUFO(Texture texture, Tile startTile, int width, int height, Map map, float speed, float health) {
    super(texture, startTile, width, height, map, speed, health);
  }
}
