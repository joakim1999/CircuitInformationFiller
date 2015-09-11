package com.cif.io;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.cif.tile.Tile;

public class Resistor {
	public float voltage = -1;
	public float power = -1;
	public float resistance = -1;
	public Rectangle bound;
	public String name;
	public boolean isInParallel;
	
	public Resistor(Tile startTile, Tile endTile){
		if(startTile == null && endTile == null){
			
		}else if(startTile.getX()<=endTile.getX() && startTile.getY()<=endTile.getY()){
			bound = new Rectangle(new Point(startTile.getX()*startTile.getWidth(), startTile.getY()*startTile.getHeight()));
			bound.add(new Point(endTile.getX() * endTile.getWidth() + endTile.getWidth(),endTile.getY()*endTile.getHeight()+endTile.getHeight()));
		}else{
			bound = new Rectangle(new Point(endTile.getX()*endTile.getWidth(), endTile.getY()*endTile.getHeight()));
			bound.add(new Point(startTile.getX() * startTile.getWidth() + startTile.getWidth(),startTile.getY()*startTile.getHeight()+startTile.getHeight()));
		}
	}
	
	public void render(Graphics g){
		g.drawRect(bound.x, bound.y, bound.width, bound.height);
		int y=bound.y + g.getFontMetrics().getHeight();
		g.drawString(name, bound.x + 3, y);
		y+=g.getFontMetrics().getHeight();
		g.drawString("U=" + (voltage==-1?"?":voltage + "V") , bound.x + 3, y);
		y+=g.getFontMetrics().getHeight();
		g.drawString("I=" + (power==-1?"?":power + "A") , bound.x + 3, y);
		y+=g.getFontMetrics().getHeight();
		g.drawString("R=" + (resistance==-1?"?":resistance + "\u2126") , bound.x + 3, y);
	}
	
	public void setName(String name){
		this.name = name;
	}
}
