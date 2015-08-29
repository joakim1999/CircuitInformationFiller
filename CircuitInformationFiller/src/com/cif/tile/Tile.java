package com.cif.tile;

import java.awt.Graphics;

import com.cif.icons.CircuitIcons;
import com.cif.main.Main;

public class Tile {
	private int x;
	private int y;
	private int width;
	private int height;
	private CircuitIcons icon;
	
	public Tile(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setIcon(CircuitIcons icon){
		this.icon = icon;
	}
	
	public CircuitIcons getIcon(){
		return icon;
	}
	
	public void render(Graphics g){
		if(false){
			g.drawRect(x * width, y * height, width, height);
		}
		if(icon != null){
			icon.getAction().setArguments(new Object[]{g,x*width,y*height,width,height});
			icon.getAction().execute();
		}
	}
}
