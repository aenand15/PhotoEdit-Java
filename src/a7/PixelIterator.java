package a7;
import java.util.Iterator;

public class PixelIterator implements Iterator<Pixel>{

	private Picture pic;
	private int x;
	private int y;
	
	public PixelIterator(Picture p){
		pic = p;
		x = 0;
		y = 0;
	}
	
	public boolean hasNext(){
		if(x >= pic.getWidth() || y >= pic.getHeight()){
			return false;
		}
		else{
			return true;
		}
	}
	public Pixel next(){
		if(!hasNext()){
			 throw new RuntimeException("No next song in iteration.");
		}
		Pixel copy;
		copy = pic.getPixel(x, y);
		x += 1;
		if(x == pic.getWidth()){
			x = 0;
			y += 1;
		}
		return copy;
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}


}
