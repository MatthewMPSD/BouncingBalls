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
  
  private Vector2 lastPosition;

  public Ball (Vector2 position, Vector2 velocity, int radius, Color color)
  {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
  }

  public boolean checkBallCollision (Ball other)
  {
    double dist = Vector2.distance(this.position, other.position);
    return dist > this.radius + other.radius;
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
    this.lastPosition = this.position;
    this.position = this.position.add(v);
  }
  
  public Vector2 getLastPosition ()
  {
    return this.lastPosition;
  }

  public void move (Vector2 v)
  {
    Vector2 finalVectorHorizontal = checkHorizontalBoundaryCollisionContinuous(this, v, Container.TOP_LEFT_VECTOR, Container.BOTTOM_RIGHT_VECTOR);
    if (finalVectorHorizontal != null)
    {
      this.velocity.y *= -1;
      this.position = finalVectorHorizontal;
    }
    Vector2 finalVectorVertical = checkVerticalBoundaryCollisionContinuous(this, v, Container.TOP_LEFT_VECTOR, Container.BOTTOM_RIGHT_VECTOR);
    if (finalVectorVertical != null)
    {
      this.velocity.x *= -1;
      this.position = finalVectorVertical;
    }
    this.translate(v);
  }

  public static Vector2 checkHorizontalBoundaryCollisionContinuous (Ball b, Vector2 amount, Vector2 topLeft, Vector2 bottomRight)
  {
    Vector2 current = b.position;
    Vector2 next = current.add(amount);
    double slope = (next.y - current.y) / (next.x - current.x);
    double collisionY = next.y < current.y ? bottomRight.y + b.radius : topLeft.y - b.radius;
    double collisionX = Double.isInfinite(slope) ? current.x : (collisionY - (current.y - slope * current.x)) / slope;

    Vector2 collisionVector = new Vector2(collisionX, collisionY);
    if ((current.y >= collisionVector.y && collisionVector.y >= next.y) || (current.y <= collisionVector.y && collisionVector.y <= next.y))
    {
      return collisionVector;
    }
    else
    {
      return null;
    }
  }

  public static Vector2 checkVerticalBoundaryCollisionContinuous (Ball b, Vector2 amount, Vector2 topLeft, Vector2 bottomRight)
  {
    Vector2 current = b.position;
    Vector2 next = current.add(amount);
    double slope = (next.y - current.y) / (next.x - current.x);
    double collisionX = next.x > current.x ? bottomRight.x - b.radius : topLeft.x + b.radius;
    double collisionY = collisionX*slope + (current.y - slope*current.x);
    Vector2 collisionVector = new Vector2(collisionX, collisionY);
    if ((current.x >= collisionVector.x && collisionVector.x >= next.x) || (current.x <= collisionVector.x && collisionVector.x <= next.x))
    {
      return collisionVector;
    }
    else
    {
      return null;
    }
  }

  public boolean checkBoundaryCollisionDiscrete (Vector2 topLeft, Vector2 bottomRight)
  {
    return this.checkBoundaryCollisionDiscrete(this, topLeft, bottomRight);
  }

  public static boolean checkBoundaryCollisionDiscrete (Ball b, Vector2 topLeft, Vector2 bottomRight)
  {
    if (!(bottomRight.y <= b.position.y+b.radius && b.position.y-b.radius <= topLeft.y))
    {
      return true;
    }
    if (!(bottomRight.x >= b.position.x+b.radius && b.position.x-b.radius >= topLeft.x))
    {
      return true;
    }
    return false;
  }
}