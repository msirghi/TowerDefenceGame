package game.waves;

import game.enemies.Enemy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class WaveManager {
  private float timeSinceLastWave;
  private float timeBetweenEnemies;
  private int waveNumber;
  private int enemiesPerWave;
  private Enemy[] enemyTypes;
  private Wave currentWave;

  public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave) {
    this.enemyTypes = enemyTypes;
    this.timeBetweenEnemies = timeBetweenEnemies;
    this.enemiesPerWave = enemiesPerWave;
    this.timeSinceLastWave = 0;
    this.waveNumber = 0;

    this.currentWave = null;
    newWave();
  }

  public void update() {
    if (!currentWave.isWaveCompleted()) {
      currentWave.update();
    } else {
      newWave();
    }
  }

  private void newWave() {
    currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave);
    waveNumber++;
    log.info("Wave number: " + waveNumber);
  }
}
