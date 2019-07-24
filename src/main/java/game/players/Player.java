package game.players;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.tiles.Map;
import game.towers.Tower;
import game.tiles.Tile;
import game.tiles.TileType;
import game.waves.WaveManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Player {
  public static int cash, lives;
  private Map grid;
  private TileType[] types;
  private WaveManager waveManager;
  private List<Tower> towerList;
  private boolean leftMouseButtonDown;
  private boolean rightMouseButtonDown;
  private boolean holdingTower;
  private Tower tempTower;

  public Player(Map grid, WaveManager waveManager) {
    this.grid = grid;
    this.waveManager = waveManager;
    this.leftMouseButtonDown = false;
    this.rightMouseButtonDown = false;
    this.holdingTower = false;
    this.tempTower = null;
    this.towerList = new ArrayList<>();
    cash = 0;
    lives = 0;
  }

  public static boolean modifyCash(int amount) {
    if (cash + amount >= 0) {
      log.info("Cash is: " + cash);
      cash += amount;
      return true;
    }
    log.info("Cash is: " + cash);
    return false;
  }

  public static void modifyLives(int amount) {
    lives += amount;
  }

  public void setup() {
    cash = 250;
    lives = 10;
  }

  private Tile getMouseTile() {
    return grid.getTile(Mouse.getX() / MainLoader.TILE_SIZE,
            (MainLoader.HEIGHT - Mouse.getY() - 1) / MainLoader.TILE_SIZE);
  }

  private void placeTower() {
    Tile currentTile = getMouseTile();
    if (holdingTower) {
      if (!currentTile.isOccupied() && modifyCash(-tempTower.getCost())) {
        currentTile.setOccupied(true);
        towerList.add(tempTower);
        holdingTower = false;
        tempTower = null;
      }
    }
  }

  public void pickTower(Tower tower) {
    tempTower = tower;
    holdingTower = true;
  }

  public void update() {
    if (holdingTower) {
      tempTower.setX(getMouseTile().getX());
      tempTower.setY(getMouseTile().getY());
      tempTower.draw();
    }

    for (Tower tower : towerList) {
      tower.update();
      tower.draw();
      tower.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
    }

    if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
      placeTower();
    }

    if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
      //right click
    }
    leftMouseButtonDown = Mouse.isButtonDown(0);
    rightMouseButtonDown = Mouse.isButtonDown(1);
    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        Metrics.changeMultiplier(0.2f);
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
        Metrics.changeMultiplier(-0.2f);
      }
    }
  }
}
