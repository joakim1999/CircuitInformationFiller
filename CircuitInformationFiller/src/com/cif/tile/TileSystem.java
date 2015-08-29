package com.cif.tile;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;

public class TileSystem{
	int tilesX;
	int tilesY;
	int width;
	int height;
	Tile[][] tileMap;
	
	public TileSystem(int tilesX, int tilesY, int width, int height){
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		this.width = width;
		this.height = height;
		this.tileMap = new Tile[tilesX][tilesY];
		initializeTiles();
	}
	
	public void initializeTiles(){
		for(int x = 0; x < tilesX;x++){
			for(int y = 0; y < tilesY;y++){
				tileMap[x][y] = new Tile(x,y,width,height);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		return tileMap[x][y];
	}
	
	public void render(Graphics g) {
		for(Tile[] tList : tileMap){
			for(Tile t : tList){
				g.setColor(Color.BLUE);
				t.render(g);
			}
		}
	}
}
