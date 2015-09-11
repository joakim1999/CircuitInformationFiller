package com.cif.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.cif.geom.Vector;
import com.cif.icons.CircuitIcons;
import com.cif.main.Main;

public class Circuit {
	public Circuit(File f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		int lineIndex = 0;
		while((line = br.readLine()) != null){
			char[] tokens = line.toCharArray();
			if(tokens.length > Main.tilesUsedWidth){
				Main.tilesUsedWidth = tokens.length;
			}
			for(int i = 0; i < tokens.length; i++){
				parse(i,lineIndex, i!=0?tokens[i-1]:'\0', tokens[i], i!=tokens.length-1?tokens[i+1]:'\0');
			}
			lineIndex++;
		}
		br.close();
		br = new BufferedReader(new FileReader(f));
		Main.tilesUsedHeight = (int) (br.lines().count()-1);
		br.close();
	}
	
	public void parse(int x, int y, char lastToken, char token, char peekToken){
		if(token == 'L'){
			System.out.println("Parsed L");
			Main.startTile = Main.tileSystem.getTile(x, y);
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.STARTPOINT);
		}else if(token == 'N'){
			System.out.println("Parsed N");
			Main.endTile = Main.tileSystem.getTile(x, y);
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.ENDPOINT);
		}else if(token == '-'){
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_HORIZONTAL);
		}else if(token == '|'){
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_VERTICAL);
		}else if(token == '/'){
			if(lastToken == '-' || lastToken == '+'){
				Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_LEFT_TOP);
			}else{
				Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_BOTTOM_RIGHT);
			}
		}else if(token == '\\'){
			if(lastToken == '-' || lastToken == '+'){
				Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_BOTTOM_LEFT);
			}else{
				Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.LINE_RIGHT_TOP);
			}
		}else if(token == '+'){
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.MEETINGPOINT);
			if(!Main.parallelStarted){
				Main.parallelStarted = true;
				Main.parallels.add(new Parallel());
				Main.parallels.get(Main.parallels.size()-1).name = "P" + Main.parallels.size();
			}else{
				Main.parallelStarted = false;
			}
		}else if(token == 'R'){
			Main.startTileResistor = Main.tileSystem.getTile(x, y);
		}else if(token == 'E'){
			if(Main.startTileResistor != null){
				Main.endTileResistor = Main.tileSystem.getTile(x, y);
				Main.makeResistor();
			}
		}else if(token == 'V'){
			Main.startTileResistor = Main.tileSystem.getTile(x, y);
			Main.endTileResistor = Main.tileSystem.getTile(x, y+1);
			Main.makeResistor();
		}else if(token == 'H'){
			Main.startTileResistor = Main.tileSystem.getTile(x, y);
			Main.endTileResistor = Main.tileSystem.getTile(x+1, y);
			Main.makeResistor();
		}else if(token == 'C'){
			Main.tileSystem.getTile(x, y).setIcon(CircuitIcons.MEETINGPOINT);
		}
	}
}
