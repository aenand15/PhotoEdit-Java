package a7;
/*
 * I don't know if it's my computer's runtime but there is a small lag in the 
 * mouse clicking causing it to sometime not display it but if you click somewhere
 * else then you'll see that it was there all along. If you expand the display
 * it'll run better
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.io.IOException;
import java.util.Iterator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class FramePuzzle extends JPanel implements MouseListener, KeyListener{
	
	private Picture p;
	private PictureView[][] pictures;
	private PictureView block;
	private int tileWidth, tileHeight;
	private boolean xCheck;
	private boolean yCheck;
	private int xBlockLocation;
	private int yBlockLocation;
	
	public FramePuzzle(Picture p){
		setLayout(new GridLayout(5,5));
		int width = p.getWidth();
		int height = p.getHeight();
		int tileWidth = width / 5;
		int tileHeight = height / 5;
		this.pictures = new PictureView[5][5];
		xCheck = false;
		yCheck = false;
		//iterator of tiles while loop and .next
		Iterator<SubPicture> i = new TileIterator(p, tileWidth, tileHeight);

			for(int a = 0; a < 5; a++){
				for(int b = 0; b < 5; b++){
					ObservablePicture y = new ObservablePictureImpl(i.next());
					pictures[a][b] = new PictureView(y);
				}
			}
		
		Picture x = pictures[4][4].getPicture();

		for(int a = 0; a < pictures[4][4].getWidth(); a++){
			for(int b = 0; b < pictures[4][4].getHeight(); b++){
				Pixel l = new ColorPixel(0,0,1);
				x.setPixel(a, b, l);
			}
		}
		PictureView block = new PictureView(x.createObservable());
		xBlockLocation = 4;
		yBlockLocation = 4;
		pictures[xBlockLocation][yBlockLocation] = block;
	
		for(int a = 1; a < 6; a++){
		for(int b = 1; b < 6; b++){
			add(pictures[a - 1][b - 1]);
			}
		}
		for(int f = 0; f < 5; f++){
			for(int j = 0; j < 5; j++){
				pictures[f][j].addMouseListener(this);
			}
		}
		this.setFocusable(true);
		this.grabFocus();
		this.addKeyListener(this);
		
	}
	
	
	public static void main(String[] args) throws IOException{

		Picture p = Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		FramePuzzle widget = new FramePuzzle(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 7 Pixel Inspector ");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(widget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
	
	public void swapHorizontally(int leftX, int leftY, int rightX, int rightY){
		PictureView temp = pictures[rightY][rightX];
		pictures[rightY][rightX] = pictures[leftY][leftX];
		pictures[leftY][leftX] = temp;

	}
	
	public void swapVertically(int bottomX, int bottomY, int topX, int topY){
		PictureView temp = pictures[topY][topX];
		pictures[topY][topX] = pictures [bottomY][bottomX];
		pictures[bottomY][bottomX] = temp;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		outerloop:
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
					if(e.getSource() == pictures[y][x] && y == yBlockLocation){
						if(x < xBlockLocation){
							while(x != xBlockLocation){
								swapHorizontally((xBlockLocation - 1), yBlockLocation, xBlockLocation, yBlockLocation);
								this.xBlockLocation = xBlockLocation - 1;
							}
							break outerloop;
						}if(x > xBlockLocation){
							while(x != xBlockLocation){
								swapHorizontally(xBlockLocation, yBlockLocation, (xBlockLocation + 1), yBlockLocation);
								this.xBlockLocation = xBlockLocation + 1;
							}
							break outerloop;
						}
					}
					if(e.getSource() == pictures[y][x] && x == xBlockLocation){
						if(e.getSource() == pictures[y][x] && x == xBlockLocation){
							if (y < yBlockLocation){
								while(y != yBlockLocation){
									swapVertically(xBlockLocation, (yBlockLocation - 1), xBlockLocation, yBlockLocation);
									this.yBlockLocation--;
								}
								break outerloop;
							}
							if(y > yBlockLocation){
								while(y != yBlockLocation){
									swapVertically(xBlockLocation, yBlockLocation, xBlockLocation, (yBlockLocation + 1));
									this.yBlockLocation++;
								}
								break outerloop;
							}
						}
					}
			}
			}
		
		removeAll();
		for(int a = 1; a < 6; a++){
			for(int b = 1; b < 6; b++){
					add(pictures[a - 1][b - 1]);
				}
			}
		repaint();
		revalidate();
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			swapHorizontally((xBlockLocation - 1), yBlockLocation, xBlockLocation, yBlockLocation);
			this.xBlockLocation = xBlockLocation - 1;

		}
		if (key == KeyEvent.VK_RIGHT) {
			swapHorizontally(xBlockLocation, yBlockLocation, (xBlockLocation + 1), yBlockLocation);
			this.xBlockLocation = xBlockLocation + 1;

	    }
		if (key == KeyEvent.VK_UP) {
			swapVertically(xBlockLocation, (yBlockLocation - 1), xBlockLocation, yBlockLocation);
			this.yBlockLocation--;
		    }

		if (key == KeyEvent.VK_DOWN) {
			swapVertically(xBlockLocation, yBlockLocation, xBlockLocation, (yBlockLocation + 1));
			this.yBlockLocation++;
		    }
		removeAll();
		for(int a = 1; a < 6; a++){
			for(int b = 1; b < 6; b++){
					add(pictures[a - 1][b - 1]);
				}
			}
		repaint();
		revalidate();
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
