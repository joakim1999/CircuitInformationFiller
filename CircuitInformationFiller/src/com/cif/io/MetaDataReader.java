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
		Parallel currentParallel = null;
		while((line = br.readLine()) != null){
			if(!line.contains("=")){
				if(line.contains("R")){
					currentResistor = Main.resistors.get(Integer.parseInt(line.substring(1)) - 1);
				}else if(line.contains("P")){
					currentParallel = Main.parallels.get(Integer.parseInt(line.substring(1)) - 1);
				}else if(line.contains("END")){
					if(currentResistor != null){
						currentResistor = null;
					}
					if(currentParallel != null){
						currentParallel = null;
					}
				}
			}else{
				if(line.startsWith("U")){
					if(line.split("=").length > 1){
						if(currentResistor == null && currentParallel == null){
							Main.voltage = Float.parseFloat(line.split("=")[1]);
						}else if(currentResistor != null){
							currentResistor.voltage = Float.parseFloat(line.split("=")[1]);
						}else if(currentParallel != null){
							currentParallel.voltage = Float.parseFloat(line.split("=")[1]);
						}
					}
				}else if(line.startsWith("I")){
					if(line.split("=").length > 1){
						if(currentResistor == null && currentParallel == null){
							Main.power = Float.parseFloat(line.split("=")[1]);
						}else if(currentResistor != null){
							currentResistor.power = Float.parseFloat(line.split("=")[1]);
						}else if(currentParallel != null){
							currentParallel.power = Float.parseFloat(line.split("=")[1]);
						}
					}
				}else if(line.startsWith("R")){
					if(line.split("=").length > 1){
						if(currentResistor == null && currentParallel == null){
							Main.resistance = Float.parseFloat(line.split("=")[1]);
						}else if(currentResistor != null){
							currentResistor.resistance = Float.parseFloat(line.split("=")[1]);
						}else if(currentParallel != null){
							currentParallel.resistance = Float.parseFloat(line.split("=")[1]);
						}
					}
				}else{
					System.err.println("Couldn't recognize line:" + line);
				}
			}
		}
		br.close();
	}
}
