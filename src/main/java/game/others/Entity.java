package game.others;

public interface Entity {
  float getX();
  float getY();
  void update();
  void draw();
  int getWidth();
  int getHeight();
  void setWidth(int width);
  void setHeight(int height);
}
