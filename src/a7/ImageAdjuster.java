package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ImageAdjuster extends JPanel implements ChangeListener{

	private PictureView picView;
	private JSlider blur;
	private JSlider saturation;
	private JSlider brightness;
	private int f;
	private double f2, f3;
	private ObservablePicture original;
	private Picture picture;
	
	ImageAdjuster(Picture p){
		setLayout(new BorderLayout());
		picView = new PictureView(p.createObservable());
		add(picView, BorderLayout.CENTER);
		
		this.picture = p;
		
		JPanel sliders = new JPanel();
		sliders.setLayout(new GridLayout(3,1));
		JLabel b = new JLabel();
		b.setText("Blur: ");
		JLabel s = new JLabel();
		s.setText("Saturation: ");
		JLabel bright = new JLabel();
		bright.setText("Brightness: ");
		blur = new JSlider(0, 5, 0);
		blur.setMajorTickSpacing(1);
		blur.setPaintTicks(true);
		blur.setPaintLabels(true);
		blur.setSnapToTicks(true);
		saturation = new JSlider(-100, 100, 0);
		saturation.setMajorTickSpacing(25);
		saturation.setPaintTicks(true);
		saturation.setPaintLabels(true);
		brightness = new JSlider(-100, 100, 0);
		brightness.setMajorTickSpacing(25);
		brightness.setPaintTicks(true);
		brightness.setPaintLabels(true);
		JPanel blurs = new JPanel();
		blurs.setLayout(new BorderLayout());
		JPanel sats = new JPanel();
		sats.setLayout(new BorderLayout());
		JPanel brights = new JPanel();
		brights.setLayout(new BorderLayout());
		blurs.add(b, BorderLayout.WEST);
		blurs.add(blur, BorderLayout.CENTER);
		sats.add(s, BorderLayout.WEST);
		sats.add(saturation, BorderLayout.CENTER);
		brights.add(bright, BorderLayout.WEST);
		brights.add(brightness, BorderLayout.CENTER);
		sliders.add(blurs);
		sliders.add(sats);
		sliders.add(brights);
		add(sliders, BorderLayout.SOUTH);
		
		blur.addChangeListener(this);
		brightness.addChangeListener(this);
		saturation.addChangeListener(this);
		
		original = picView.getPicture();

		f = 0;
		f2 = 0;
		f3 = 0;
		
	}
	public static void main(String[] args) throws IOException{
		Picture p = Helper.readFromURL("https://lh6.ggpht.com/xjqcoIvOn-r4OSlMa-qhoAp7NWJX15uYIm1Dkm-y2-g0dqdmc0ja4cRzS5Mo6UQqfA=h900");
		ImageAdjuster widget = new ImageAdjuster(p);
		
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
	public ObservablePicture blurry(Picture p, int factor){
		for(int i = 0; i < p.getWidth(); i++){
			for(int j = 0; j < p.getHeight(); j++){
				if(factor > 0){
					int a = Math.max(0,  i - factor);
					int b = Math.max(0, j- factor);
					int c = Math.min(p.getWidth() - a, i + factor - a + 1);
					int d = Math.min(p.getHeight() - b, j + factor - b + 1);
					SubPicture pic = new SubPictureImpl(p, a, b, c, d);
					Pixel pix = avg(pic);
					p.setPixel(i, j, pix);
				}else{
					Pixel pix = p.getPixel(i, j);
					p.setPixel(i, j, pix);
				}
			}
		}
		return p.createObservable();
	}
	public Pixel avg(SubPicture pic){
		double sumRed = 0;
		double sumGreen = 0;
		double sumBlue = 0;
		double avgRed = 0;
		double avgGreen = 0;
		double avgBlue = 0;
		int counter = 0;
		Iterator<Pixel> i = new PixelIterator(pic);
		while(i.hasNext()){
			Pixel tempPixel = i.next();
			sumRed += tempPixel.getRed();
			sumGreen += tempPixel.getGreen();
			sumBlue += tempPixel.getBlue();
			counter++;
		}
		avgRed = sumRed / counter;
		avgGreen = sumGreen / counter;
		avgBlue = sumBlue / counter;
		return new ColorPixel(avgRed, avgGreen, avgBlue);
	}

	
	public ObservablePicture brighten(Picture p, double factor){	
		Picture y = p;
		double newFactor = Math.abs(factor / 100);
		for(int i = 0; i < y.getWidth(); i++){
			for(int j = 0; j < y.getHeight(); j++){
			if(factor > 0){
				y.setPixel(i, j, original.getPixel(i, j).lighten(newFactor));
			}else if(factor < 0){
				y.setPixel(i, j, original.getPixel(i, j).darken(newFactor));
			}else{
				y.setPixel(i, j, original.getPixel(i, j));
			}
		}
		
		}
		return y.createObservable();
	}
	
	public ObservablePicture saturate(Picture p, double f){
		for(int i = 0; i < p.getWidth(); i++){
			for(int j = 0; j < p.getHeight(); j++){
		try{
		if(f < 0){

					double b = p.getPixel(i, j).getIntensity();
					double red, green, blue;
					red = picView.getPicture().getPixel(i, j).getRed() * (1 + (f / 100))
						- (b * f / 100);	
					green = picView.getPicture().getPixel(i, j).getGreen() * (1 + (f / 100))
							- (b * f / 100);
					blue = picView.getPicture().getPixel(i, j).getBlue() * (1 + (f / 100))
							- (b * f / 100);
					Pixel x = new ColorPixel(red, green, blue);
					p.setPixel(i, j, x);	
		}
		if(f > 0){
					double a;
					double blue = p.getPixel(i, j).getBlue();
					double red = p.getPixel(i, j).getRed();
					double green = p.getPixel(i, j).getGreen();
					if(red > blue && red > green){
						a = red;
					}else if(blue > red && blue > green){
						a = blue;
					}else{
						a = green;
					}
					red = red * ((a + ((1 - a) * (f / 100))) / a);
					blue = blue * ((a + ((1 - a) * (f / 100))) / a);
					green = green * ((a + ((1 - a) * (f / 100))) / a);
					Pixel x = new ColorPixel(red, green, blue);
					p.setPixel(i, j, x);
	}
		}catch(IllegalArgumentException e){
		Pixel z = new ColorPixel(0,0,0);
		p.setPixel(i, j, z);
	}
			}
		}
		return p.createObservable();
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		original = new ObservablePictureImpl(picture);
		int newf = blur.getValue();
		double newf2 = brightness.getValue();
		double newf3 = saturation.getValue();
		if(f != newf || f2 != newf2 || f3 != newf3){
			original =  new ObservablePictureImpl(picture).copy(picture);
			picView.setPicture(saturate(brighten(blurry(original, newf), newf2), newf3));
			f = newf;
			f2 = newf2;
			f3 = newf3;
		}
	}
	
}
