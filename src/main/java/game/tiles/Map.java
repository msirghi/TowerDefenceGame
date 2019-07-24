package game.tiles;

import game.helpers.MainLoader;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Map {

  public Tile[][] map;
  private int tileWide;
  private int tileHeight;

  public Map() {
    this.tileWide = 20;
    this.tileHeight = 15;
    map = new Tile[20][15];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j] = new Tile(i * MainLoader.TILE_SIZE, j * MainLoader.TILE_SIZE, MainLoader.TILE_SIZE,
                MainLoader.TILE_SIZE, TileType.Grass);
      }
    }
  }

  public Map(int[][] newMap) {
    this.tileWide = newMap[0].length;
    this.tileHeight = newMap.length;
    map = new Tile[20][15];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        switch (newMap[j][i]) {
          case 0:
            map[i][j] = new Tile(i * MainLoader.TILE_SIZE, j * MainLoader.TILE_SIZE,
                    MainLoader.TILE_SIZE, MainLoader.TILE_SIZE, TileType.Grass);
            break;
          case 1:
            map[i][j] = new Tile(i * MainLoader.TILE_SIZE, j * MainLoader.TILE_SIZE, MainLoader.TILE_SIZE,
                    MainLoader.TILE_SIZE, TileType.Dirt);
            break;
          case 2:
            map[i][j] = new Tile(i * MainLoader.TILE_SIZE, j * MainLoader.TILE_SIZE, MainLoader.TILE_SIZE,
                    MainLoader.TILE_SIZE, TileType.Water);
            break;
        }
      }
    }
  }

  public void draw() {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j].draw();
      }
    }
  }

  public void setTile(int xCoord, int yCoord, TileType tileType) {
    map[xCoord][yCoord] = new Tile(xCoord * MainLoader.TILE_SIZE, yCoord * MainLoader.TILE_SIZE,
            MainLoader.TILE_SIZE, MainLoader.TILE_SIZE, tileType);
  }

  public Tile getTile(int xPlace, int yPlace) {
    if (xPlace < tileWide && yPlace < tileHeight && xPlace > -1 && yPlace > -1)
      return map[xPlace][yPlace];
    return new Tile(0, 0, 0, 0, TileType.NULL);
  }
}
