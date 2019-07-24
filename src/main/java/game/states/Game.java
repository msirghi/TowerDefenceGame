package game.states;

import game.enemies.Enemy;
import game.enemies.EnemyCreeper;
import game.enemies.EnemySkeleton;
import game.helpers.MainLoader;
import game.helpers.StateManager;
import game.towers.TowerType;
import game.players.*;
import game.tiles.Map;
import game.towers.TowerCannonBlue;
import game.towers.TowerCannonIce;
import game.ui.UI;
import game.waves.WaveManager;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

@Slf4j
@Getter
@Setter
public class Game {
  private Map map;
  private Player player;
  private WaveManager waveManager;
  private TowerCannonBlue blue;
  private UI gameUI;
  private UI.Menu towerPickerMenu;
  private Texture menuBackground;
  private UI healthUI;
  private UI.Menu healthMenu;
  private UI cashUI;
  private UI.Menu cashMenu;
  private UI waveUI;
  private UI.Menu waveMenu;
  private UI fpsUI;
  private UI.Menu fpsMenu;
  private Enemy[] enemyTypes;

  public Game(Map map) {
    this.map = map;
    this.menuBackground = MainLoader.quickLoad("towers1");
    enemyTypes = new Enemy[2];
    enemyTypes[0] = new EnemyCreeper(0, 11, this.map);
//    enemyTypes[1] = new EnemyUFO(0, 11, map);
    enemyTypes[1] = new EnemySkeleton(0, 11, this.map);
    waveManager = new WaveManager(enemyTypes, 3, 3);
    player = new Player(this.map, waveManager);
    player.setup();
    setupUI();
  }

  public void update() {
    MainLoader.drawQuadTex(menuBackground, 1280, 0, 192, 960);
    map.draw();
    waveManager.update();
    player.update();
    updateUI();
  }

  private void updateUI() {
    gameUI.draw();
    healthUI.draw();
    cashUI.draw();
    waveMenu.draw();
    fpsUI.draw();
    gameUI.drawString(1380, 600, "" + Player.lives);
    gameUI.drawString(1380, 650, "" + Player.cash);
    gameUI.drawString(1390, 700, "" + waveManager.getWaveNumber());
    gameUI.drawString(1390, 750, "" + StateManager.fps);

    if (Mouse.next()) {
      boolean mouseClicked = Mouse.isButtonDown(0);
      if (mouseClicked) {
        if (towerPickerMenu.isButtonClicked("BlueCannon")) {
          player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, map.getTile(0, 0),
                  waveManager.getCurrentWave().getEnemyList()));
        }
        if (towerPickerMenu.isButtonClicked("IceCannon")) {
          player.pickTower(new TowerCannonIce(TowerType.CannonIce, map.getTile(0, 0),
                  waveManager.getCurrentWave().getEnemyList()));
        }
      }
    }
  }

  private void setupUI() {
    gameUI = new UI();
    gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);
    towerPickerMenu = gameUI.getMenu("TowerPicker");
    towerPickerMenu.quickAdd("BlueCannon", "tower3");
    towerPickerMenu.quickAdd("IceCannon", "dalaranTower");

    healthUI = new UI();
    healthUI.createMenu("AdditionalUI", 1310, 600, 192, 960, 2, 0);
    healthMenu = healthUI.getMenu("AdditionalUI");
    healthMenu.quickAdd("Lives", "lives");

    cashUI = new UI();
    cashUI.createMenu("cashUI", 1310, 650, 192, 960, 2, 0);
    cashMenu = cashUI.getMenu("cashUI");
    cashMenu.quickAdd("Cash", "cash");

    waveUI = new UI();
    waveUI.createMenu("waveUI", 1310, 700, 192, 960, 2, 0);
    waveMenu = waveUI.getMenu("waveUI");
    waveMenu.quickAdd("Wave", "enemy70");

    fpsUI = new UI();
    fpsUI.createMenu("fpsUI", 1310, 750, 192, 960, 2, 0);
    fpsMenu = fpsUI.getMenu("fpsUI");
    fpsMenu.quickAdd("FPS", "fps");
  }
}
