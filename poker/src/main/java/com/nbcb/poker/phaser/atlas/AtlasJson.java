package com.nbcb.poker.phaser.atlas;

import java.util.ArrayList;
import java.util.List;

public class AtlasJson {

	private List<Item> frame = new ArrayList<Item>();
	
	public void  addItem(Item item){
		frame.add(item);
	}
	
	

	
	public List<Item> getFrame() {
		return frame;
	}




	public static class Item {
		private String filename;
		private boolean rotated = false;
		private boolean trimmed = true;
		private AtlasJsonXywh frame;
		private AtlasJsonXywh spriteSourceSize;
		private AtlasJsonXywh sourceSize;

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public boolean isRotated() {
			return rotated;
		}

		public void setRotated(boolean rotated) {
			this.rotated = rotated;
		}

		public boolean isTrimmed() {
			return trimmed;
		}

		public void setTrimmed(boolean trimmed) {
			this.trimmed = trimmed;
		}

		public AtlasJsonXywh getFrame() {
			return frame;
		}

		public void setFrame(AtlasJsonXywh frame) {
			this.frame = frame;
		}

		public AtlasJsonXywh getSpriteSourceSize() {
			return spriteSourceSize;
		}

		public void setSpriteSourceSize(AtlasJsonXywh spriteSourceSize) {
			this.spriteSourceSize = spriteSourceSize;
		}

		public AtlasJsonXywh getSourceSize() {
			return sourceSize;
		}

		public void setSourceSize(AtlasJsonXywh sourceSize) {
			this.sourceSize = sourceSize;
		}

	}

	public static class AtlasJsonXywh {
		private int x;
		private int y;
		private int w;
		private int h;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}
	}

}
