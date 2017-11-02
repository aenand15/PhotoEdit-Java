package a7;

import java.util.Iterator;

public class TileIterator implements Iterator<SubPicture>{

	private Picture pic;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public TileIterator(Picture p, int w, int h){
		pic = p;
		width = w;
		height = h;
	}
	
	public boolean hasNext(){
		if(x + width > pic.getWidth() && y + height > pic.getHeight()){
			return false;
		}
		else{
			return true;
		}
	}
	public SubPicture next(){
		if(!hasNext()){
			 throw new RuntimeException("No next picture in iteration.");
		}
		SubPicture cropped = pic.extract(x, y, width, height);
		x += width;
		if((x + width) >= pic.getWidth()){
			x = 0;
			y += height;
		}
		return cropped;
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}
}
