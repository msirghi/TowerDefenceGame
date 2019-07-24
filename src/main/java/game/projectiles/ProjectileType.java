package game.projectiles;

import game.helpers.MainLoader;
import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {
  CannonBall(MainLoader.quickLoad("bullet"), 10, 500),
  IceBall(MainLoader.quickLoad("iceProjectile"), 6, 450);

  public Texture texture;
  public int damage;
  public float speed;

  ProjectileType(Texture textures, int damage, float speed) {
    this.texture = textures;
    this.damage = damage;
    this.speed = speed;
  }
}
