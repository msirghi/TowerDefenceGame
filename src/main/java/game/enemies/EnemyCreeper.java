package game.enemies;

import game.tiles.Map;
import game.tiles.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;

@Slf4j
@Getter
@Setter
public class EnemyCreeper extends Enemy {

  public EnemyCreeper(int tileX, int tileY, Map grid) {
    super(tileX, tileY, grid);
    this.setTexture("creeper");
//    this.setTexture("Efim");
  }

  public EnemyCreeper(Texture texture, Tile startTile, int width, int height, Map map, float speed, float health) {
    super(texture, startTile, width, height, map, speed, health);
  }
}
