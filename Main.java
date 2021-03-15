import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Main extends JFrame {

  public Main ()
  {
    initUI();
  }

    private void initUI() {
        
        add((JPanel) new Container());

        setResizable(false);
        pack();
        
        setTitle("Bouncing Balls");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame container = new Main();
            container.setVisible(true);
        });
    }
}