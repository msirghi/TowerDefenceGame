package game.states;

import game.helpers.MainLoader;
import game.helpers.StateManager;
import game.ui.UI;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

@Slf4j
public class MainMenu {
  private Texture background;
  private UI menuUI;

  public MainMenu() {
    background = MainLoader.quickLoad("background");
    menuUI = new UI();
    menuUI.addButton("Play", "play", MainLoader.WIDTH / 2 - 128, (int) (MainLoader.HEIGHT * 0.45f));
    menuUI.addButton("Editor", "editor", MainLoader.WIDTH / 2 - 128, (int) (MainLoader.HEIGHT * 0.55f));
    menuUI.addButton("Quit", "quit", MainLoader.WIDTH / 2 - 128, (int) (MainLoader.HEIGHT * 0.65f));
  }

  private void updateButtons() {
    if (Mouse.isButtonDown(0)) {
      if (menuUI.isButtonClicked("Play")) {
        StateManager.setState(StateManager.gameState.GAME);
      }
      if (menuUI.isButtonClicked("Editor")) {
        StateManager.setState(StateManager.gameState.EDITOR);
      }
      if (menuUI.isButtonClicked("Quit")) {
        System.exit(0);
      }
    }
  }

  public void update() {
    MainLoader.drawQuadTex(background, 0, 0, 2048, 1024);
    menuUI.draw();
    updateButtons();
  }
}
