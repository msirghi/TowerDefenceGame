package game.ui;

import game.helpers.MainLoader;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

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

  public void draw() {
    menuButtons.forEach(button -> {
      MainLoader.drawQuadTex(button.getTexture(), button.getX(), button.getY(),
              button.getWidth(), button.getHeight());
    });
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
