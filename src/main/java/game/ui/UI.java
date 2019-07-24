package game.ui;

import game.helpers.MainLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UI {
  private List<Button> buttonList;
  private List<Menu> menuList;
  private TrueTypeFont font;
  private Font awtFont;

  public UI() {
    buttonList = new ArrayList<>();
    menuList = new ArrayList<>();
    awtFont = new Font("Impact", Font.BOLD, 30);
    font = new TrueTypeFont(awtFont, false);
  }

  public void drawString(int x, int y, String text) {
    font.drawString(x, y, text);
  }

  public void addButton(String name, String texture, int x, int y) {
    buttonList.add(new Button(name, MainLoader.quickLoad(texture), x, y));
  }

  public boolean isButtonClicked(String buttonName) {
    Button button = getButton(buttonName);
    float mouseY = MainLoader.HEIGHT - Mouse.getY() - 1;
    // mouse in the button area
    if (Mouse.getX() > button.getX() && Mouse.getX() < button.getX() + button.getWidth()
            && mouseY > button.getY() && mouseY < button.getY() + button.getHeight()) {
      return true;
    }
    return false;
  }

  private Button getButton(String buttonName) {
    for (Button button : buttonList) {
      if (button.getName().equals(buttonName)) {
        return button;
      }
    }
    return null;
  }

  public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
    menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
  }

  public Menu getMenu(String name) {
    for (Menu menu : menuList) {
      if (name.equals(menu.getName())) {
        return menu;
      }
    }
    return null;
  }

  public void draw() {
    for (Button button : buttonList) {
      MainLoader.drawQuadTex(button.getTexture(), button.getX(), button.getY(),
              button.getWidth(), button.getHeight());
    }
    for (Menu menu : menuList) {
      menu.draw();
    }
  }
}
