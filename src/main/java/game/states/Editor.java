package game.states;

import game.helpers.MainLoader;
import game.tiles.Map;
import game.tiles.TileType;
import game.ui.UI;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static game.helpers.Leveler.loadMap;
import static game.helpers.Leveler.saveMap;

@Slf4j
@Getter
public class Editor {
  private Map grid;
  private int index;
  private TileType[] types;
  private UI editorUI;
  private UI.Menu tilePickerMenu;
  private Texture menuBackground;


  public Editor() {
    this.grid = loadMap("newMap1");
    this.index = 0;
    this.menuBackground = MainLoader.quickLoad("towers");


    this.types = new TileType[4];
    this.types[0] = TileType.Grass;
    this.types[1] = TileType.Dirt;
    this.types[2] = TileType.Water;
    setupUI();
  }

  private void setupUI() {
    editorUI = new UI();
    editorUI.createMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
    tilePickerMenu = editorUI.getMenu("TilePicker");
    tilePickerMenu.quickAdd("grass", "cyan_stained_glass");
    tilePickerMenu.quickAdd("dirt", "andesite");
    tilePickerMenu.quickAdd("Water", "water");
  }

  private void draw() {
    MainLoader.drawQuadTex(menuBackground, 1280, 0, 192, 960);
    grid.draw();
    editorUI.draw();
  }

  public void update() {
    draw();
    if (Mouse.next()) {
      boolean mouseClicked = Mouse.isButtonDown(0);
      if (mouseClicked) {
        if (tilePickerMenu.isButtonClicked("grass")) {
          index = 0;
        } else if (tilePickerMenu.isButtonClicked("dirt")) {
          index = 1;
        } else if (tilePickerMenu.isButtonClicked("Water")) {
          index = 2;
        } else {
          setTile();
        }
      }
    }

    while (Keyboard.next()) {
      if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
        moveIndex();
      }
      if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
        saveMap("newMap1", grid);
      }
    }
  }

  private void setTile() {
    grid.setTile((int) Math.floor(Mouse.getX() / MainLoader.TILE_SIZE),
            (int) Math.floor((MainLoader.HEIGHT - Mouse.getY() - 1) / MainLoader.TILE_SIZE), types[index]);
  }

  private void moveIndex() {
    index++;
    if (index > types.length - 1) {
      index = 0;
    }
  }
}
