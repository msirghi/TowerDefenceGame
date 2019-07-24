package game.tiles;

import game.helpers.MainLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
@Getter
@Setter
public class Tile {
  private float x;
  private float y;
  private int width;
  private int height;
  private Texture texture;
  private TileType tileType;
  private boolean occupied;

  public Tile(float x, float y, int width, int height, TileType tileType) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.tileType = tileType;
      this.texture = MainLoader.quickLoad(tileType.textureName);
    occupied = !tileType.buildable;
  }

  public void draw() {
    MainLoader.drawQuadTex(texture, x, y, width, height);
  }

  public int getXPlace() {
    return (int) x / MainLoader.TILE_SIZE;
  }

  public int getYPlace() {
    return (int) y / MainLoader.TILE_SIZE;
  }
}
