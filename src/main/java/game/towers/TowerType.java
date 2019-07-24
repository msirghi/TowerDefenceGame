package game.towers;

import game.helpers.MainLoader;
import game.projectiles.ProjectileType;
import org.newdawn.slick.opengl.Texture;

public enum TowerType {

  CannonRed(new Texture[]{
          MainLoader.quickLoad("tower"),
          MainLoader.quickLoad("cannon")}, ProjectileType.CannonBall, 10, 1000, 3, 0),
  CannonBlue(new Texture[] {
          MainLoader.quickLoad("tower3"),
          MainLoader.quickLoad("cannonGun")}, ProjectileType.CannonBall, 10, 1000, 3, 15),
  CannonIce(new Texture[] {
          MainLoader.quickLoad("dalaranTower"),
          MainLoader.quickLoad("cannonGun")}, ProjectileType.IceBall, 10, 1000, 3, 20);

  public Texture[] textures;
  public ProjectileType type;
  public int damage;
  public int range;
  public int cost;
  public float firingSpeed;

  TowerType(Texture[] textures, ProjectileType type, int damage, int range,
            float firingSpeed, int cost) {
    this.textures = textures;
    this.cost = cost;
    this.type = type;
    this.damage = damage;
    this.range = range;
    this.firingSpeed = firingSpeed;
  }
}
