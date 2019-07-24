package game.helpers;

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

@Slf4j
public class MainLoader {
  public static final int WIDTH = 1472;
  public static final int HEIGHT = 960;
  public static final int TILE_SIZE = 64;

  public static void beginGame() {
    Display.setTitle("Tower Defence");
    try {
      Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
      Display.create();
    } catch (LWJGLException e) {
      log.info("Error creating the window.");
    }

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  public static boolean CheckCollision(float x1, float y1, float width1, float height1,
                                       float x2, float y2, float width2, float height2) {
    if (x1 + width1 > x2 && x1 < x2 + width2
            && y1 + height1 > y2 && y1 < y2 + height2) {
      return true;
    }
    return false;
  }

  public static void drawQuadTex(Texture texture, float x, float y, float width, float height) {
    texture.bind();
    glTranslatef(x, y, 0);
    glBegin(GL_QUADS);

    glTexCoord2f(0, 0);
    glVertex2f(0, 0);

    glTexCoord2f(1, 0);
    glVertex2f(width, 0);

    glTexCoord2f(1, 1);
    glVertex2f(width, height);

    glTexCoord2f(0, 1);
    glVertex2f(0, height);

    glEnd();
    glLoadIdentity();
  }

  public static void drawQuadTexRot(Texture texture, float x, float y,
                                    float width, float height, float angle) {
    texture.bind();
    glTranslatef(x + width / 2, y + height / 2, 0);
    glRotatef(angle, 0, 0, 1);
    glTranslatef(-width / 2, -height / 2, 0);
    glBegin(GL_QUADS);

    glTexCoord2f(0, 0);
    glVertex2f(0, 0);

    glTexCoord2f(1, 0);
    glVertex2f(width, 0);

    glTexCoord2f(1, 1);
    glVertex2f(width, height);

    glTexCoord2f(0, 1);
    glVertex2f(0, height);

    glEnd();
    glLoadIdentity();
  }

  public static Texture loadTexture(String path, String fileType) {
    Texture texture = null;

    InputStream in = ResourceLoader.getResourceAsStream(path);
    try {
      texture = TextureLoader.getTexture(fileType, in);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return texture;
  }

  public static Texture quickLoad(String name) {
    if (name == "water1") {
      return quickLoadGif(name);
    }
    Texture texture;
    texture = loadTexture(name + ".png", "PNG");
    return texture;
  }

  public static Texture quickLoadGif(String name) {
    Texture texture;
    texture = loadTexture(name + ".gif", "GIF");
    return texture;
  }

  public Texture getGIF(String name) {
    Texture texture = null;
    try {
      BufferedImage bufferedImage = ImageIO.read(getClass().getResource("name"));
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "gif", os);
      InputStream is = new ByteArrayInputStream(os.toByteArray());
      texture = TextureLoader.getTexture("GIF", is);
    } catch (Exception e) {
      log.info("logging");
    }
    return texture;
  }
}
