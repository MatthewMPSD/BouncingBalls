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
  private final int WIDTH = 512;
  private final int HEIGHT = 512;
  private final int DELAY = 24;
  private Thread animator;
  private long timeDiff;

  public ArrayList<Ball> balls = new ArrayList();

  public Container ()
  {
    initContainer();
  }

  public void initContainer ()
  {
      setBackground(new Color(153, 204, 255));
      setPreferredSize(new Dimension(WIDTH, HEIGHT));

      balls.add(new Ball(new Vector2(256, 256), new Vector2(0, 5), 10, new Color(255, 153, 51)));
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

  private void drawThings(Graphics g)
  {
    for (Ball b : balls)
    {
      g.drawOval(b.position.x, b.position.y, b.radius*2, b.radius*2);
    }
    Toolkit.getDefaultToolkit().sync();
  }

  private void cycle ()
  {
    
  }

@Override
  public void run ()
  {
    long beforeTime, sleep;

    beforeTime = System.currentTimeMillis();

    while (true)
    {
      // cycle();
      repaint();

      timeDiff = System.currentTimeMillis() - beforeTime;
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

      beforeTime = System.currentTimeMillis();
    }
  }
}