package com.cif.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.cif.calc.CommandedInfo;
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
			if(line.startsWith("!")){
				String command = line.substring(1);
				String[] tokens = command.split("&");
				Resistor res1 = null;
				Resistor res2 = null;
				tokens[1] = tokens[1].split("=")[0];
				System.out.println("Token0:" + tokens[0]);
				System.out.println("Token1:" + tokens[1]);
				for(Resistor r : Main.resistors){
					if(r.name.equals(tokens[0])){
						res1 = r;
						System.out.println("Found " + tokens[0]);
					}else if(r.name.equals(tokens[1])){
						res2 = r;
						System.out.println("Found " + tokens[1]);
					}
				}
				CommandedInfo.add(res1.name + "+" + res2.name, (int)Math.pow(1/res1.resistance + 1/res2.resistance,-1));
			}
			else if(!line.contains("=")){
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
