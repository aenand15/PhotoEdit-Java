package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.io.IOException;



import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspector extends JPanel implements MouseListener{
	
	private PictureView picView;
	private JLabel pixelInfo;
	
	PixelInspector(Picture p){
		setLayout(new BorderLayout());
		picView = new PictureView(p.createObservable());
		picView.addMouseListener(this);
		add(picView, BorderLayout.CENTER);
		this.pixelInfo = new JLabel();
		add(pixelInfo, BorderLayout.WEST);

		
	}

	public static void main(String[] args) throws IOException{

		Picture p = Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp.jpg");
		PixelInspector widget = new PixelInspector(p);
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		this.pixelInfo.setText(
				"<html>X: " + e.getX() + "<br>Y: "+ e.getY() +
				"<br>Red: "+ round(picView.getPicture().getPixel(e.getX(), e.getY()).getRed(), 2) +
				"<br>Green: " + round(picView.getPicture().getPixel(e.getX(), e.getY()).getGreen(), 2) +
				"<br> Blue: " + round(picView.getPicture().getPixel(e.getX(), e.getY()).getBlue(), 2) +
				"<br>Brightness: " + round(picView.getPicture().getPixel(e.getX(), e.getY()).getIntensity(), 2)
				);

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	
}
