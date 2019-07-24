package game;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.helpers.StateManager;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.opengl.Display;

@Slf4j
public class Application {

  public Application() {
    MainLoader.beginGame();

    while (!Display.isCloseRequested()) {
      Metrics.update();
      StateManager.update();
      Display.update();
      Display.sync(60);
    }
    Display.destroy();
  }

  public static void main(String[] args) {
    new Application();
  }
}
