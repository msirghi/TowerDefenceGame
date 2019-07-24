package game.helpers;

import game.tiles.Map;
import game.tiles.Tile;
import game.tiles.TileType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class Leveler {
  public static void saveMap(String mapName, Map grid) {
    String mapData = "";
    for (int i = 0; i < grid.getTileWide(); i++) {
      for (int j = 0; j < grid.getTileHeight(); j++) {
        mapData += getTileID(grid.getTile(i, j));
      }
    }
    File file = new File(mapName);
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write(mapData);
      bufferedWriter.close();
    } catch (IOException e) {
      log.info("Error saving file.");
    }
  }

  public static Map loadMap(String mapName) {
    Map grid = new Map();

    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(mapName));
      String data = bufferedReader.readLine();
      for (int i = 0; i < grid.getTileWide(); i++) {
        for (int j = 0; j < grid.getTileHeight(); j++) {
          grid.setTile(i, j, getTileType(data.substring(i * grid.getTileHeight() + j,
                  i * grid.getTileHeight() + j + 1)));
        }
      }
      bufferedReader.close();
    } catch (FileNotFoundException e) {
      log.info("Error loading file.");
    } catch (IOException e) {
      log.info("Error reading character in file.");
    }
    return grid;
  }

  public static TileType getTileType(String id) {
    TileType type = TileType.NULL;

    switch (id) {
      case "0":
        type = TileType.Grass;
        break;
      case "1":
        type = TileType.Dirt;
        break;
      case "2":
        type = TileType.Water;
        break;
      case "3":
        type = TileType.NULL;
        break;
    }
    return type;
  }

  public static String getTileID(Tile tile) {
    String id = "E";

    switch (tile.getTileType()) {
      case Grass:
        id = "0";
        break;
      case Dirt:
        id = "1";
        break;
      case Water:
        id = "2";
        break;
      case NULL:
        id = "3";
        break;
    }
    return id;
  }
}
