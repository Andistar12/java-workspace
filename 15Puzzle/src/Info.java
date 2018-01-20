import javax.swing.*;
import java.awt.*;

//This class just displays info about how to play the game
//And some info about it. You don't have to do any of this.

public class Info extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

	public Info() {
		setLayout(null);
		setTitle("15 Puzzle Info");
		setSize(440,250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBackground(new Color(204, 191, 139));
		JLabel label1 = new JLabel("15 Puzzle (Implementation) v1.0 created by A. Nguyen");
		label1.setBounds(1, 0, 500, 50);
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label1);
		JLabel label2 = new JLabel("Original author information can be found online.");
		label2.setBounds(1, 20, 500, 50);
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label2);
		
		JLabel label3 = new JLabel("The classic 15 tile-sliding game that everyone loves!");
		label3.setBounds(1, 60, 500, 50);
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label3);
		JLabel label4 = new JLabel("Use W (Up), A (Left), S (Down), and D (Right)");
		label4.setBounds(1, 80, 500, 50);
		label4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label4);
		JLabel label5 = new JLabel("To move all the tiles back in order, 1 through 15.");
		label5.setBounds(1, 100, 500, 50);
		label5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label5);
		JLabel label6 = new JLabel("Try to use as little tile movements as possible!");
		label6.setBounds(1, 120, 500, 50);
		label6.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label6);
		
		JLabel label7 = new JLabel("Note: All positions only need 80 moves max to restore");
		label7.setBounds(1, 160, 500, 50);
		label7.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label7);
		JLabel label8 = new JLabel("the board to it's original solved positions. Good luck!");
		label8.setBounds(1, 180, 500, 50);
		label8.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		add(label8);
	}
}
