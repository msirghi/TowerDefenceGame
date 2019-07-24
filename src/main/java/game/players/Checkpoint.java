package game.players;

import game.tiles.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

// each enemy's turn
@Slf4j
@Getter
@Setter
public class Checkpoint {
  private Tile tile;
  private int xDirection;
  private int yDirection;

  public Checkpoint(Tile tile, int xDirection, int yDirection) {
    this.tile = tile;
    this.xDirection = xDirection;
    this.yDirection = yDirection;
  }
}
