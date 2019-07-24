package game.tiles;

public enum TileType {

  Grass("cyan_stained_glass", true),
  Dirt("andesite", false),
  Water("water", false),
  NULL("water", false);

  String textureName;
  boolean buildable;

  TileType(String textureName, boolean buildable) {
    this.textureName = textureName;
    this.buildable = buildable;
  }
}
