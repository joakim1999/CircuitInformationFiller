package com.cif.main;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.cif.calc.Calculator;
import com.cif.calc.Unknowns;
import com.cif.io.Circuit;
import com.cif.io.MetaDataReader;
import com.cif.io.MetaDataWriter;
import com.cif.io.Resistor;
import com.cif.tile.Tile;
import com.cif.tile.TileSystem;

public class Main extends JPanel{
	public static String circuitFileName = "Circuit";
	
	public static Tile startTile;
	public static Tile endTile;
	
	public static ArrayList<Resistor> resistors;
	public static Tile startTileResistor = null;
	public static Tile endTileResistor = null;
	
	public static TileSystem tileSystem;
	
	static int tilesX = 20;
	static int tilesY = 20;
	
	public static float voltage = -1;
	public static float power = -1;
	public static float resistance = -1;
	
	static boolean initialized = false;
	
	public static Unknowns unknowns;
	
	public static int tilesUsedWidth = 0;
	public static int tilesUsedHeight = 0;
	
	public static void main(String[] args){
		JFrame jf = new JFrame();
		
		JPanel panel = new Main();
		jf.add(panel);
		
		jf.setSize(801, 801);
		tileSystem = new TileSystem(tilesX, tilesY, (jf.getWidth()-1)/tilesX, (jf.getHeight()-1)/tilesY);
		jf.setUndecorated(true);
		
		jf.setVisible(true);
		try {
			Circuit c = new Circuit(new File(circuitFileName));
			if(startTile == null){
				JOptionPane.showMessageDialog(jf, "L was not specified in the circuit. This is required!", "ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}else if(endTile == null){
				JOptionPane.showMessageDialog(jf, "N was not specified in the circuit. This is required!", "ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File f = new File(circuitFileName + "_meta.meta");
		if(f.exists()){
			MetaDataReader reader = new MetaDataReader(f);
			try {
				reader.read();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			unknowns = new Unknowns();
			Calculator c = new Calculator();
			c.calculate();
		}
		
		jf.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.showConfirmDialog(jf,"Do you want to make a metadata file for this circuit?(This will overwrite previous data)", 
						                            "Do you?", JOptionPane.YES_NO_OPTION)
				   == 0){
					MetaDataWriter writer = new MetaDataWriter(circuitFileName + "_meta.meta");
					try {
						writer.write();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		
		jf.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				jf.paint(jf.getGraphics());
			}
			
		});
		
		
		initialized = true;
		
		panel.paint(panel.getGraphics());
	}
	
	public Main(){
		resistors = new ArrayList<Resistor>();
	}
	
	public static void makeResistor(){
		if(startTileResistor != null && endTileResistor != null){
			Resistor resistor = new Resistor(startTileResistor, endTileResistor);
			resistor.setName("R" + (resistors.size() + 1));
			resistors.add(resistor);
		}else{
			System.err.println("makeResistor() was called even though start and/or end was not defined.");
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		if(!initialized)return;
		System.out.println("Painting");
		tileSystem.render(g);
		for(Resistor resistor : resistors){
			resistor.render(g);
		}
		
		int startX = startTile.getX()*startTile.getWidth();
		int startY = startTile.getY()*startTile.getHeight();
		g.drawString("L", startX + (startTile.getWidth()/2) - (g.getFontMetrics().stringWidth("L")/2), startY + (startTile.getHeight()/2)+3);
		
		int endX = endTile.getX()*endTile.getWidth();
		int endY = endTile.getY()*endTile.getHeight();
		g.drawString("N", endX + (endTile.getWidth()/2) - (g.getFontMetrics().stringWidth("N")/2), endY + (endTile.getHeight()/2)+3);
		
		int x = tilesUsedWidth * (startTile.getWidth()/2);
		int y = tilesUsedHeight * (startTile.getHeight()/2);
		g.drawString("U=" + (voltage==-1?"?":voltage + "V"), x, y);
		y += g.getFontMetrics().getHeight();
		g.drawString("I=" + (power==-1?"?":power + "A"), x, y);
		y += g.getFontMetrics().getHeight();
		g.drawString("R=" + (resistance==-1?"?":resistance + " Ohm"), x, y);
	}
}