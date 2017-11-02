package a7;


public class ColorPixel implements Pixel {

	private double red;
	private double green;
	private double blue;
	
	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' '};
	
	public ColorPixel(double r, double g, double b) {
		if (r > 1.0 || r < 0.0) {
			throw new IllegalArgumentException("Red out of bounds");
		}
		if (g > 1.0 || g < 0.0) {
			throw new IllegalArgumentException("Green out of bounds");
		}
		if (b > 1.0 || b < 0.0) {
			throw new IllegalArgumentException("Blue out of bounds");
		}
		red = r;
		green = g;
		blue = b;
	}
	
	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getGreen() {
		return green;
	}

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR*getRed() + 
				GREEN_INTENSITY_FACTOR*getGreen() + 
				BLUE_INTENSITY_FACTOR*getBlue();
	}
	
	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity()*10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}
	public Pixel blend(Pixel p, double weight){
		double newRed;
		double newGreen;
		double newBlue;
		double ratio = 1 - weight;
		if(weight == 1){
			newRed = getRed();
			newGreen = getGreen();
			newBlue = getBlue();
		}else if(weight == 0){
			newRed = p.getRed();
			newGreen = p.getGreen();
			newBlue = p.getBlue();
		}else{
			newRed = (ratio * getRed()) + (weight * p.getRed());
			newGreen = (ratio * getGreen()) + (weight * p.getGreen());
			newBlue = (ratio * getBlue()) + (weight * p.getBlue());
		}
		Pixel x = new ColorPixel(newRed, newGreen, newBlue);
		return x;
	}
	
	public Pixel lighten(double factor){
		Pixel white = new ColorPixel(1.0, 1.0, 1.0);
		Pixel original = new ColorPixel(red, green, blue);
		if(factor == 1){
			return white;
		}else if(factor == 0){
			return original;
		}else{
			return blend(white, factor);
		}
	}
	public Pixel darken(double factor){
		Pixel dark = new ColorPixel(0.0, 0.0, 0.0);
		Pixel original = new ColorPixel(red, green, blue);
		if(factor == 1){
			return dark;
		}else if(factor == 0){
			return original;
		}else{
			return blend(dark, factor);
		}
	}
}
