import javax.swing.JFrame;
import javax.swing.JLabel;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TestWin
{
    public static void main(String[] args) { 
    JFrame win = new JFrame("my win");
    win.setSize(800, 600);
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //win.getContentPane().setBackground(Color.BLACK);
    win.getContentPane().setBackground(java.awt.Color.BLUE);
    win.setVisible(true);

    JLabel lbl = new JLabel("eden barlev");
    //lbl.setFont(new Font("Arial", Font.BOLD,50));
    win.add(lbl);
    }
}