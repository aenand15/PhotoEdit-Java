package a7;

public class GrayPixel implements Pixel {

	private double intensity;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}	
	public Pixel blend(Pixel p, double weight){
		double newRed;
		double newGreen;
		double newBlue;
		double newIntensity;
		double ratio = 1 - weight;
		if(weight == 1){
			newRed = getRed();
			newGreen = getGreen();
			newBlue = getBlue();
			newIntensity = getIntensity();
		}else if(weight == 0){
			newRed = p.getRed();
			newGreen = p.getGreen();
			newBlue = p.getBlue();
			newIntensity = p.getIntensity();
		}else{
			newRed = (ratio * getRed()) + (weight * p.getRed());
			newGreen = (ratio * getGreen()) + (weight * p.getGreen());
			newBlue = (ratio * getBlue()) + (weight * p.getBlue());
			newIntensity = (.299 * newRed) + (.587 * newGreen) + (.114 * newBlue);
		}
		Pixel x = new GrayPixel(newIntensity);
		return x;
	}
	
	public Pixel lighten(double factor){
		Pixel white = new GrayPixel(1.0);
		Pixel original = new GrayPixel(getIntensity());
		if(factor == 1){
			return white;
		}else if(factor == 0){
			return original;
		}else{
			return blend(white, factor);
		}
	}
	public Pixel darken(double factor){
		Pixel dark = new GrayPixel(0.0);
		Pixel original = new GrayPixel(getIntensity());
		if(factor == 1){
			return dark;
		}else if(factor == 0){
			return original;
		}else{
			return blend(dark, factor);
		}
	}

}
