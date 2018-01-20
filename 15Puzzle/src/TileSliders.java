import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


//This class sets up the physical window for the game. It also holds most of the variables
//Used in the program (note that they all are marked "static")
public class TileSliders extends JFrame{
	private static final long serialVersionUID = 1L;
	public static TileSliders tileSliderFrame;
	public static int[] tileNums = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	public static boolean winFlag = false;
	public static int tilesMove = 0;
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
				tileSliderFrame = new TileSliders();
				tileSliderFrame.setVisible(true);		    
			}
		});

	}
	
	public TileSliders() {
		setContentPane(new TileDraw());
		setTitle("15 Puzzle");
		setSize(427,525);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(204, 191, 139));
		//setIconImage(new ImageIcon(getClass().getClassLoader().getResource("s.png")).getImage());

		addKeyListener(new KeyListener() {
			 public void keyTyped(KeyEvent e) {}
			 public void keyPressed(KeyEvent e) {}
			 public void keyReleased(KeyEvent e) {
				 manageKeyEvent(e.getKeyCode());
				 repaint();
			 }
		});
		shuffleArray(TileSliders.tileNums);
	}
	
	// Implementing Fisher–Yates shuffle
	static void shuffleArray(int[] ar) {
		do {
			Random rnd = new Random();
			for (int i = ar.length - 1; i > 0; i--) {
				int index = rnd.nextInt(i + 1);
				int a = ar[index];
				ar[index] = ar[i];
				ar[i] = a;
			}	
		} while (checkSolubility(tileNums) == false);
		//for (int i = 0; i < 16; i++) System.out.print(tileNums[i] + " ");
		 tilesMove = 0;
	}
	 
	public static int locateZero (int[] a){
		int loc0 = -1;
		for (int i = 0; i < 16; i++) {
			if (TileSliders.tileNums[i] == 0) {
				loc0 = i;
				break;
			}
		}
		return loc0;
	}
	
	public static boolean checkSolubility(int[] a){
		 int sum = 0;
		 for (int count = 0; count < 15; count++){
			 for (int count2 = count + 1; count2 < 16; count2++){
				 if (tileNums[count] > tileNums[count2] && tileNums[count] != 0 && tileNums[count2] != 0) {
					 sum++;
				 }
			 }
		 }
			
		 switch (locateZero(tileNums)){
		 case 0:
		 case 1:
		 case 2:
		 case 3:
			 sum += 1;
			 break;
		 case 4:
		 case 5:
		 case 6:
		 case 7:
			 sum += 2;
			 break;
		 case 8:
		 case 9:
		 case 10:
		 case 11:
			 sum += 3;
			 break;
		 case 12:
		 case 13:
		 case 14:
		 case 15:
			 sum += 4;
			 break;
		 }
		 if (sum % 2 == 0) return true;
		 return false;
	}
	
	 public static void manageKeyEvent(int e){
		 int loc0 = -1;
		 for (int i = 0; i < 16; i++) {
			 if (TileSliders.tileNums[i] == 0) {
				 loc0 = i;
				 break;
			 }
		 }
		// System.out.print(loc0);
		 switch (e){
		 case KeyEvent.VK_NUMPAD8:
		 case KeyEvent.VK_KP_UP:
		 case KeyEvent.VK_W:
		 case KeyEvent.VK_UP:
			 if (loc0 + 4 <= 15) {
				 TileSliders.tileNums[loc0] = TileSliders.tileNums[loc0 + 4];
				 TileSliders.tileNums[loc0 + 4] = 0;
				 tilesMove++;
			 }
			 break;
		 case KeyEvent.VK_NUMPAD4:
		 case KeyEvent.VK_KP_LEFT:
		 case KeyEvent.VK_A:
		 case KeyEvent.VK_LEFT:
			 if (loc0 != 3 && loc0 != 7 && loc0 != 11 && loc0 != 15) {
				 TileSliders.tileNums[loc0] = TileSliders.tileNums[loc0 + 1];
				 TileSliders.tileNums[loc0 + 1] = 0;
				 tilesMove++;
			 }
			 break;
		 case KeyEvent.VK_NUMPAD6:
		 case KeyEvent.VK_KP_RIGHT:
		 case KeyEvent.VK_D:
		 case KeyEvent.VK_RIGHT:
			 if (loc0 != 0 && loc0 != 4 && loc0 != 8 && loc0 != 12) {
				 TileSliders.tileNums[loc0] = TileSliders.tileNums[loc0 - 1];
				 TileSliders.tileNums[loc0 - 1] = 0;
				 tilesMove++;
			 }
			 break;
		 case KeyEvent.VK_NUMPAD2:
		 case KeyEvent.VK_KP_DOWN:
		 case KeyEvent.VK_S:
		 case KeyEvent.VK_DOWN:
			 if (loc0 - 4 >= 0) {
				 TileSliders.tileNums[loc0] = TileSliders.tileNums[loc0 - 4];
				 TileSliders.tileNums[loc0 - 4] = 0;
				 tilesMove++;
			 }
			 break;
		 }
	 }
	 
	 public static boolean checkWin() {
		 int[] ansArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		 if (tileNums != null && tileNums.length == 16) {
			 for (int i = 0; i < 15; i++){
				 if (tileNums[i] != ansArray[i]) {
					 winFlag = false;
					 return false;
				 }
			 }
		 } else {
			 winFlag = false;
			 return false;
		 }
		 winFlag = true;
		 return true;
	 }
}