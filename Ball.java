import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ball implements Collider
{
  public Color color;
  public Vector2 position;
  public Vector2 velocity;
  public double radius;

  private static final double DENSITY = 1;

  private double mass;
  public double getMass()
  {
    return this.mass;
  }

  public Ball (Vector2 position, Vector2 velocity, int radius, Color color)
  {
    this.position = position;
    this.velocity = velocity;
    this.radius = radius;
    this.color = color;
    this.mass = Math.PI * (this.radius * this.radius) * DENSITY;
  }

  public double getWidth ()
  {
    return this.radius*2;
  }

  public double getHeight()
  {
    return this.radius*2;
  }

  public boolean checkBallCollisionDiscrete (Ball other)
  {
    double dist = Vector2.distance(this.position, other.position);
    return dist > this.radius + other.radius;
  }

  public Vector2 checkBallCollisionContinuous (Ball b, Vector2 v)
  {
    return Ball.checkBallCollisionContinuous(this, b, v);
  }

  public static Vector2 checkBallCollisionContinuous (Ball b1, Ball b2, Vector2 v)
  {
      Vector2 current = b1.position;
      Vector2 next = b1.position.add(v);
      double dist = Vector2.distance(b1.position, b2.position);
      double targetDist = b1.radius + b2.radius;
      double aActual = b1.position.x - b2.position.x;
      double bActual = b1.position.y - b2.position.y;
      double aTarget = (targetDist / dist) * aActual;
      double bTarget = (targetDist / dist) * bActual;

      Vector2 collisionVector = new Vector2(b2.position.x + (b1.position.x < b2.position.x ? aTarget : -aTarget), b2.position.y + (b1.position.y < b2.position.y ? bTarget : -bTarget));
      if ((current.y >= collisionVector.y && collisionVector.y >= next.y) || (current.y <= collisionVector.y && collisionVector.y <= next.y) && (current.x >= collisionVector.x && collisionVector.x >= next.x) || (current.x <= collisionVector.x && collisionVector.x <= next.x))
      {
        return collisionVector;
      }
      else
      {
        return null;
      }
  }

  public Ball (Ball b)
  {
    this.position = b.position;
    this.velocity = b.velocity;
    this.radius = b.radius;
    this.color = b.color;
    this.mass = b.getMass();
  }

  public void translate (Vector2 v)
  {
    this.position = this.position.add(v);
  }

  public void move (Vector2 v)
  {
    Vector2 dir = v;
    Vector2 finalVectorHorizontal = checkHorizontalBoundaryCollisionContinuous(this, dir, Container.TOP_LEFT_VECTOR, Container.BOTTOM_RIGHT_VECTOR);
    if (finalVectorHorizontal != null)
    {
      this.velocity.y *= -1;
      dir = this.position.subtract(finalVectorHorizontal);
    }
    Vector2 finalVectorVertical = checkVerticalBoundaryCollisionContinuous(this, dir, Container.TOP_LEFT_VECTOR, Container.BOTTOM_RIGHT_VECTOR);
    if (finalVectorVertical != null)
    {
      this.velocity.x *= -1;
      dir = this.position.subtract(finalVectorVertical);
    }
    this.translate(dir);
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

  public static Pair<Vector2, Vector2> getCollisionResponseVelocity (Ball b1, Ball b2)
  {
      double m1 = b1.mass;
      double m2 = b2.mass;
      Vector2 c1 = b1.position;
      Vector2 c2 = b2.position;
      Vector2 v1 = b1.velocity;
      Vector2 v2 = b2.velocity;
      Vector2 top1 = v1.subtract(v2) c1.subtract(c2);
      Vector2 vHat1 = v1.subtract(((2*m1)/(m1+m2)) * (top1));
  }
}