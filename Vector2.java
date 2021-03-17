public class Vector2
{
  public double x;
  public double y;

  public Vector2 (double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public Vector2 subtract (Vector2 other)
  {
    return new Vector2(this.x-other.x, this.y-other.y);
  }

  public Vector2 add (Vector2 other)
  {
    return new Vector2(this.x+other.x, this.y+other.y);
  }

  public static double distance (Vector2 v1, Vector2)
  {
    return Math.sqrt(Math.pow((v2.x - v1.x), 2), Math.pow(v2.y - v2.y, 2))
  }
}