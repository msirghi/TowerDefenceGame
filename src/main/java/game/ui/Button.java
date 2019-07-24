package game.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Button {
  private Texture texture;
  private int x;
  private int y;
  private int width;
  private int height;
  private String name;

  public Button(String name, Texture texture, int x, int y, int width, int height) {
    this.texture = texture;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.name = name;
  }

  public Button(String name, Texture texture, int x, int y) {
    this.name = name;
    this.texture = texture;
    this.x = x;
    this.y = y;
    this.width = texture.getImageWidth();
    this.height = texture.getImageHeight();
  }
}
