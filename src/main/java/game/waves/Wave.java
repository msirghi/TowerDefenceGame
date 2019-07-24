package game.waves;

import game.helpers.MainLoader;
import game.helpers.Metrics;
import game.enemies.Enemy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@Slf4j
public class Wave {
  private float timeSinceLastSpawn;
  private float spawnTime;
  private Enemy[] enemyType;
  private CopyOnWriteArrayList<Enemy> enemyList;
  private int enemiesPerWave;
  private int enemiesSpawn;
  private boolean waveCompleted;

  public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
    this.spawnTime = spawnTime;
    this.enemyType = enemyTypes;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastSpawn = 0;
    this.enemiesSpawn = 0;
    waveCompleted = false;
    enemyList = new CopyOnWriteArrayList<>();
    spawn();
  }

  public void update() {
    boolean enemiesDead = true;
    if (enemiesSpawn < enemiesPerWave) {
      timeSinceLastSpawn += Metrics.delta();
      if (timeSinceLastSpawn > spawnTime) {
        spawn();
        timeSinceLastSpawn = 0;
      }
    }

    for (Enemy enemy : enemyList) {
      if (enemy.isAlive()) {
        enemiesDead = false;
        enemy.update();
        enemy.draw();
      } else {
        enemyList.remove(enemy);
      }
    }
    if (enemiesDead) {
      waveCompleted = true;
    }
  }

  public void spawn() {
    int enemyChosen;
    Random random = new Random();
    enemyChosen = random.nextInt(enemyType.length);
    enemyList.add(new Enemy(enemyType[enemyChosen].getTexture(),
            enemyType[enemyChosen].getStartTile(), MainLoader.TILE_SIZE,
            MainLoader.TILE_SIZE, enemyType[enemyChosen].getMap(),
            enemyType[enemyChosen].getSpeed(), enemyType[enemyChosen].getHealth()));
    enemiesSpawn++;
  }
}
