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
  public int radius;

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

  public static Vector2 getCollisionVelocity (Ball other)
  {
    
  }
}