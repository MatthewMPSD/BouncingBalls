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

  public Vector2 multiply (Vector2 other) {
    return new Vector2(this.x*other.x, this.y*other.y);
  }

  public Vector2 multiply (double other)
  {
    return new Vector2(this.x * other, this.y * other);
  }


  @Override
  public String toString() {
    return "(" + x + ", " + y + ')';
  }
}