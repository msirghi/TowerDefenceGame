package game.helpers;

import game.states.Editor;
import game.states.Game;
import game.states.MainMenu;
import game.tiles.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StateManager {
  public static gameState gameState = StateManager.gameState.MAIN_MENU;
  public static MainMenu mainMenu;
  public static Game game;
  public static Editor editor;

  public static long nextSecond = System.currentTimeMillis() + 1000;
  public static int fps = 0;
  public static int framesInCurrentSecond = 0;

  static Map map = Leveler.loadMap("newMap1");

  public static void update() {
    switch (gameState) {
      case MAIN_MENU:
        if (mainMenu == null)
          mainMenu = new MainMenu();
        mainMenu.update();
        break;
      case GAME:
        if (game == null)
          game = new Game(map);
        game.update();
        break;
      case EDITOR:
        if (editor == null)
          editor = new Editor();
        editor.update();
        break;
    }

    long currentTime = System.currentTimeMillis();
    if (currentTime > nextSecond) {
      nextSecond += 1000;
      fps = framesInCurrentSecond;
      framesInCurrentSecond = 0;
    }

    framesInCurrentSecond++;
  }

  public static void setState(gameState newState) {
    gameState = newState;
  }

  public enum gameState {
    MAIN_MENU, GAME, EDITOR
  }
}
