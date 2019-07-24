package game.enemies;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.others.Entity;
import game.players.Checkpoint;
import game.players.Player;
import game.tiles.Map;
import game.tiles.Tile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Getter
public class Enemy implements Entity {
  private float x;
  private float y;
  private float speed;
  private float health;
  private float startHealth;
  private float hiddenHealth;
  private int width;
  private int height;
  private int currentCheckpoint;
  private Texture texture;
  private Texture healthBackground;
  private Texture healthForeground;
  private Texture healthBorder;
  private Tile startTile;
  private boolean first;
  private Map map;
  private List<Checkpoint> checkpoints;
  private int[] directions;
  private boolean alive;

  public Enemy(int tileX, int tileY, Map grid) {
    this.healthBackground = MainLoader.quickLoad("healthBackground");
    this.healthForeground = MainLoader.quickLoad("healthForeground");
    this.healthBorder = MainLoader.quickLoad("healthBorder");
    this.startTile = grid.getTile(tileX, tileY);
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.width = MainLoader.TILE_SIZE;
    this.height = MainLoader.TILE_SIZE;
    this.startHealth = health;
    this.health = 50;
    this.speed = 50;
    this.map = grid;

    checkpoints = new ArrayList<>();
    this.healthBackground = MainLoader.quickLoad("healthBackground");
    this.healthForeground = MainLoader.quickLoad("healthForeground");
    this.healthBorder = MainLoader.quickLoad("healthBorder");

    // x & y
    directions = new int[2];
    this.directions[0] = 0;
    this.directions[1] = 0;

    directions = findNextDirection(startTile);
    this.currentCheckpoint = 0;
    populateCheckpointList();
  }

  public void setTexture(String textureName) {
    this.texture = MainLoader.quickLoad(textureName);
  }

  public Enemy(Texture texture, Tile startTile, int width, int height, Map map,
               float speed, float health) {
    this.texture = texture;
    this.first = true;
    this.alive = true;
    this.x = startTile.getX();
    this.y = startTile.getY();
    this.startTile = startTile;
    this.width = width;
    this.map = map;
    this.height = height;
    this.startHealth = health;
    this.health = health;
    this.speed = speed;
    this.hiddenHealth = health;
    checkpoints = new ArrayList<>();
    this.healthBackground = MainLoader.quickLoad("healthBackground");
    this.healthForeground = MainLoader.quickLoad("healthForeground");
    this.healthBorder = MainLoader.quickLoad("healthBorder");

    // x & y
    directions = new int[2];
    this.directions[0] = 0;
    this.directions[1] = 0;

    directions = findNextDirection(startTile);
    this.currentCheckpoint = 0;
    populateCheckpointList();
  }

  private void populateCheckpointList() {
    checkpoints.add(findNextCheckpoint(startTile, directions = findNextDirection(startTile)));
    int counter = 0;
    boolean continuing = true;

    while (continuing) {
      int[] currentDirection = findNextDirection(checkpoints.get(counter).getTile());
      // next direction or checkpoint exists?
      if (currentDirection[0] == 2 || counter == 20) {
        continuing = false;
      } else {
        checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(),
                directions = findNextDirection(checkpoints.get(counter).getTile())));
      }
      counter++;
    }
  }

  public void draw() {
    float healthPercentage = health / startHealth;
    MainLoader.drawQuadTex(texture, x, y, width, height);
    MainLoader.drawQuadTex(healthBackground, x, y - 16, width, 8);
    MainLoader.drawQuadTex(healthForeground, x, y - 16, MainLoader.TILE_SIZE * healthPercentage, 8);
    MainLoader.drawQuadTex(healthBorder, x, y - 16, width, 8);
  }

  public void reduceHealth(float amount) {
    hiddenHealth -= amount;
  }

  private Checkpoint findNextCheckpoint(Tile s, int[] dir) {
    Tile next = null;
    Checkpoint checkpoint;

    boolean found = false;
    int counter = 1;

    while (!found) {
      // loops over the tiles where mobs are headed (for x & y axes).
      // it goes exactly the path, finds block that cannot be passed
      // and sets checkpoint to block - 1 in direction
      if (s.getXPlace() + dir[0] * counter == map.getTileWide() ||
              s.getYPlace() + dir[1] * counter == map.getTileHeight()
              || s.getTileType() != map.getTile(s.getXPlace() + dir[0] * counter,
              s.getYPlace() + dir[1] * counter).getTileType()) {
        found = true;
        counter -= 1; //moving back to find tile (block - 1)
        next = map.getTile(s.getXPlace() + dir[0] * counter,
                s.getYPlace() + dir[1] * counter); // the one previous (block - 1)
      }
      counter++;
    }
    checkpoint = new Checkpoint(next, dir[0], dir[1]);
    return checkpoint;
  }

  public void damage(int amountOfDamage) {
    health -= amountOfDamage;
    if (health <= 0) {
      enemyDie();
      Player.modifyCash(5);
    }
  }

  private void enemyDie() {
    alive = false;
  }

  //what direction to go next
  private int[] findNextDirection(Tile startTile) {
    int[] dir = new int[2];
    Tile up = map.getTile(startTile.getXPlace(), startTile.getYPlace() - 1);
    Tile right = map.getTile(startTile.getXPlace() + 1, startTile.getYPlace());
    Tile down = map.getTile(startTile.getXPlace(), startTile.getYPlace() + 1);
    Tile left = map.getTile(startTile.getXPlace() - 1, startTile.getYPlace());

    if (startTile.getTileType() == up.getTileType() && directions[1] != 1) {
      dir[0] = 0;
      dir[1] = -1;
    } else if (startTile.getTileType() == right.getTileType() && directions[0] != -1) {
      dir[0] = 1;
      dir[1] = 0;
    } else if (startTile.getTileType() == down.getTileType() && directions[1] != -1) {
      dir[0] = 0;
      dir[1] = 1;
    } else if (startTile.getTileType() == left.getTileType() && directions[0] != 1) {
      dir[0] = -1;
      dir[1] = 0;
    } else {
      dir[0] = 2;
      dir[1] = 2;
//      log.info("No direction to go!");
    }
    return dir;
  }

  private void endOfMapReached() {
    Player.modifyLives(-1);
    enemyDie();
  }

  public void update() {
    if (first) {
      first = false;
    } else {
      //if reached, go to the next checkpoint
      if (checkpointReached()) {
        if (currentCheckpoint + 1 == checkpoints.size()) {
          endOfMapReached();
        } else {
          currentCheckpoint++;
        }
      } else {
        //based on current position of the enemy
        //if not in the checkpoint, continue in current direction
        x += Metrics.delta() * checkpoints.get(currentCheckpoint).getXDirection() * speed;
        y += Metrics.delta() * checkpoints.get(currentCheckpoint).getYDirection() * speed;
      }
    }
  }

  private boolean checkpointReached() {
    boolean reached = false;
    Tile tile = null;
    tile = checkpoints.get(currentCheckpoint).getTile();

    //inside tile area
    if (x > tile.getX() - 3 && x < tile.getX() + 3
            && y > tile.getY() - 3 && y < tile.getY() + 3) {
      reached = true;
      x = tile.getX();
      y = tile.getY();
    }
    return reached;
  }
}
