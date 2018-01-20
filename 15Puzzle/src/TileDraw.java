import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//This class is what displays the thing 
class TileDraw extends JPanel{
	private static final long serialVersionUID = 1L;
	public static Info infoWindow;
	
	public TileDraw(){
		setOpaque(true);
		setLayout(null);
		setBackground(new Color(204, 191, 139));
		
		JButton restart = new JButton("New Puzzle");
		restart.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		restart.setBounds(20, 410, 185, 50);
		restart.setBackground(new Color(204, 191, 139));
		add(restart);
		
		JButton info = new JButton("Extra Info");
		info.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		info.setBounds(210, 410, 185, 50);
		info.setBackground(new Color(204, 191, 139));
		add(info);
		info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoWindow.setVisible(true);
			}
		
		});
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		info.setBorder(emptyBorder);
		restart.setBorder(emptyBorder);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TileSliders.shuffleArray(TileSliders.tileNums);
				repaint();
			}
		
		});
		
		info.setVisible(true);
		restart.setVisible(true);

		addKeyListener(new KeyListener() {
			 public void keyTyped(KeyEvent e) {}
			 public void keyPressed(KeyEvent e) {}
			 public void keyReleased(KeyEvent e) {
				 if (TileSliders.winFlag == false) TileSliders.manageKeyEvent(e.getKeyCode());
				 repaint();
			 }
		});
		
		info.addKeyListener(new KeyListener() {
			 public void keyTyped(KeyEvent e) {}
			 public void keyPressed(KeyEvent e) {}
			 public void keyReleased(KeyEvent e) {
				 if (TileSliders.winFlag == false) TileSliders.manageKeyEvent(e.getKeyCode());
				 repaint();
			 }
		});
		restart.addKeyListener(new KeyListener() {
			 public void keyTyped(KeyEvent e) {}
			 public void keyPressed(KeyEvent e) {}
			 public void keyReleased(KeyEvent e) {
				 if (TileSliders.winFlag == false) TileSliders.manageKeyEvent(e.getKeyCode());
				 repaint();
			 }
		});
		
		infoWindow = new Info();
		validate();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(new Color(204, 191, 139));
		if (TileSliders.checkWin() == true) {
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 72));
			g2d.drawString("You beat the", 10, 72);
			g2d.drawString("15 Puzzle!", 60, 150);
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 60));
			g2d.drawString("Tiles Moved: ", 30, 290);
			g2d.drawString(TileSliders.tilesMove + "", 150, 364);
		} else {
			int count = 0;
			for(int i = 20; i < 400; i += 100) {
				for(int j = 20; j < 400; j += 100) {
					if (TileSliders.tileNums[count] != 0) {
						g2d.setColor(new Color(237, 172, 102));
						g2d.fillRoundRect(j-1, i-1, 75, 75, 25, 25);
						g2d.setColor(new Color(0,0,0));
						g2d.drawRoundRect(j-1, i-1, 75, 75, 25, 25);
					}
					count++;
				}
			}
			
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 36));
			count = 0;
			for(int i = 70; i < 400; i += 100) {
				for(int j = 40; j < 400; j += 100) {
					if (TileSliders.tileNums[count] != 0) {
						if (TileSliders.tileNums[count] < 10) {
							g2d.drawString(" " + TileSliders.tileNums[count], j, i);
						} else {
							g2d.drawString(TileSliders.tileNums[count] + "", j, i);
						}
					}
					count++;
				}
			}
			g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			g2d.drawString("Tiles Moved: " + TileSliders.tilesMove, 125, 490);

		}
		
    }
}