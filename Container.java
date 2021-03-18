import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Container extends JPanel implements Runnable
{
  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;
  public static final Vector2 TOP_LEFT_VECTOR = new Vector2(0, HEIGHT);
  public static final Vector2 BOTTOM_RIGHT_VECTOR = new Vector2(WIDTH, 0);
  private final int FPS = 60;
  private final int DELAY = 1000/FPS;
  private Thread animator;
  private long timeDiff;

  public ArrayList<Ball> balls = new ArrayList();

  public Container ()
  {
    initContainer();
  }

  public void initContainer ()
  {
      setBackground(new Color(255, 153, 51));
      setPreferredSize(new Dimension(WIDTH, HEIGHT));

      balls.add(new Ball(new Vector2(256, 100), new Vector2(34, 500), 25, new Color(153, 204, 204)));
      balls.add(new Ball(new Vector2(54, 156), new Vector2(50, 550), 45, new Color(0, 102, 255)));
      balls.add(new Ball(new Vector2(400, 147), new Vector2(34, 40), 75, new Color(0, 102, 153)));
      balls.add(new Ball(new Vector2(34, 147), new Vector2(345, 578), 25, new Color(204, 153, 0)));
      balls.add(new Ball(new Vector2(430, 456), new Vector2(34, 45), 35, new Color(152, 51, 0)));
  }


  @Override
  public void addNotify ()
  {
    super.addNotify();

    animator = new Thread(this);
    animator.start();
  }

  @Override
  public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    drawThings(g);
  }

  public void drawCenteredCircle(Graphics g, int x, int y, int r) {
    x = x-(r/2);
    y = y-(r/2);
    g.fillOval(x,y,r,r);
  }

  private void drawThings(Graphics g)
  {
    for (Ball b : balls)
    {
      g.setColor(b.color);
      drawCenteredCircle(g, (int)b.position.x, (int)((HEIGHT - b.position.y)), (int)b.radius*2);
    }
    Toolkit.getDefaultToolkit().sync();
  }

  private void cycle ()
  {
    if (timeDiff == 0)
    {
      return;
    }
    for (Ball b : balls)
    {
      Vector2 v = b.velocity.multiply(timeDiff/1000.0);
      b.move(v);
    }
  }

@Override
  public void run ()
  {
    long beforeTime, sleep;

    beforeTime = System.currentTimeMillis();

    while (true)
    {
      cycle();
      repaint();


      sleep = DELAY - timeDiff;

      if (sleep < 0)
      {
        sleep = 2;
      }

      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e)
      {
        String msg = String.format("Thread interupted: %s", e.getMessage());

        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
      }

      timeDiff = System.currentTimeMillis() - beforeTime;

      beforeTime = System.currentTimeMillis();
    }
  }
}