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

  @Getter
  @Setter
  public class Menu {
    private List<Button> menuButtons;
    private int x;
    private int y;
    private int width;
    private int height;
    private int padding;
    private int buttonAmount;
    private int optionsWidth;
    private int optionsHeight;
    private String name;

    public Menu(String name, int x, int y, int width, int height,
                int optionsWidth, int optionsHeight) {
      this.name = name;
      this.width = width;
      this.padding = (width - (optionsWidth * MainLoader.TILE_SIZE)) / (optionsWidth + 1);
      this.height = height;
      this.optionsWidth = optionsWidth;
      this.optionsHeight = optionsHeight;
      this.x = x;
      this.y = y;
      this.buttonAmount = 0;
      this.menuButtons = new ArrayList<>();
    }

    public void addButton(Button button) {
      setButton(button);
    }

    public void draw() {
      for (Button button : menuButtons) {
        MainLoader.drawQuadTex(button.getTexture(), button.getX(), button.getY(),
                button.getWidth(), button.getHeight());
      }
    }

    public void quickAdd(String buttonName, String buttonTextureName) {
      Button button = new Button(buttonName, MainLoader.quickLoad(buttonTextureName), 0, 0);
      setButton(button);
    }

    private void setButton(Button button) {
      if (optionsWidth != 0) {
        button.setY(y + (buttonAmount / optionsWidth) * MainLoader.TILE_SIZE);
      }
      button.setX(x + (buttonAmount % 2) * (padding + MainLoader.TILE_SIZE) + padding);
      buttonAmount++;
      menuButtons.add(button);
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
      for (Button button : menuButtons) {
        if (button.getName().equals(buttonName)) {
          return button;
        }
      }
      return null;
    }
  }
}
