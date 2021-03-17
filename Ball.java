import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ball
{
  Color color;
  public Vector2 position;
  public Vector2 velocity;
  public double radius;

  public Ball (Vector2 position, Vector2 velocity, int radius, Color color)
  {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  public Ball (Ball b)
  {
    this.position = b.position;
    this.velocity = b.velocity;
    this.radius = b.radius;
    this.color = b.color;
  }

  public void translate (Vector2 v)
  {
    Vector2 newPosition = this.position.add(v);
    if (this.checkBoundaryCollision(new Ball(this), new Vector2(0, 0), new Vector2(Container.WIDTH, Container.HEIGHT)))
    {
      this.position = this.position.add(v.subtract())
      this.velocity = this.velocity.multiply(-1);
    }
    this.position = this.position.add(v);
  }

  public boolean checkBoundaryCollision (Vector2 topLeft, Vector2 bottomRight)
  {
    this.checkBoundaryCollision(this, topLeft, bottomRight);
  }

  public static boolean checkBoundaryCollision (Ball b, Vector2 topLeft, Vector2 bottomRight)
  {
    if (!(bottomRight.y >= b.position.y+b.radius && b.position.y-b.radius >= topLeft.y))
    {
      System.out.print("Y");
      return true;
    }
    if (!(bottomRight.x >= b.position.x+b.radius && b.position.x-b.radius >= topLeft.x))
    {
      System.out.print("X");
      return true;
    }
    return false;
  }
}