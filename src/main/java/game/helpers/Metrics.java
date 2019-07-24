package game.helpers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.Sys;

@Slf4j
@Getter
@Setter
public class Metrics {
  private static boolean paused = false;
  public static long lastFrame;
  public static long totalTime;
  public static float d = 0;
  public static float multiplier = 1;

  public static Long getTime() {
    return Sys.getTime() * 1000 / Sys.getTimerResolution();
  }

  public static float getDelta() {
    long currentTime = getTime();
    int delta = (int) (currentTime - lastFrame);
    lastFrame = getTime();
    if (delta * 0.001f > 0.05f)
      return 0.05f;
    return delta * 0.001f;
  }

  public static float delta() {
    if (paused) {
      return 0;
    } else {
      return d * multiplier;
    }
  }

  public static float totalTime() {
    return totalTime;
  }

  public static float multiplier() {
    return multiplier;
  }

  public static void update() {
    d = getDelta();
    totalTime += d;
  }

  public static void changeMultiplier(float change) {
    if (multiplier + change < -1 && multiplier + change > 7) {

    } else {
      multiplier += change;
    }
  }

  public static void pause() {
    paused = !paused;
  }
}
