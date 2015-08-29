package com.cif.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.cif.main.Main;

public class MetaDataReader {
	File metaDataFile;
	
	public MetaDataReader(File f){
		this.metaDataFile = f;
	}
	
	public void read() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(metaDataFile));
		String line;
		Resistor currentResistor = null;
		while((line = br.readLine()) != null){
			if(!line.contains("=")){
				if(line.contains("R")){
					currentResistor = Main.resistors.get(Integer.parseInt(line.substring(1)) - 1);
				}else if(line.contains("END")){
					currentResistor = null;
				}
			}else{
				if(line.startsWith("U")){
					if(line.split("=").length > 1){
						if(currentResistor == null){
							Main.voltage = Float.parseFloat(line.split("=")[1]);
						}else{
							currentResistor.voltage = Float.parseFloat(line.split("=")[1]);
						}
					}
					continue;
				}else if(line.startsWith("I")){
					if(line.split("=").length > 1){
						if(currentResistor == null){
							Main.power = Float.parseFloat(line.split("=")[1]);
						}else{
							currentResistor.power = Float.parseFloat(line.split("=")[1]);
						}
					}
				}else if(line.startsWith("R")){
					if(line.split("=").length > 1){
						if(currentResistor == null){
							Main.resistance = Float.parseFloat(line.split("=")[1]);
						}else{
							currentResistor.resistance = Float.parseFloat(line.split("=")[1]);
						}
					}
				}else{
					System.err.println("Couldn't recognize line:" + line);
				}
			}
		}
	}
}
