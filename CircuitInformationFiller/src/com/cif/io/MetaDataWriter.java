package com.cif.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.cif.main.Main;

public class MetaDataWriter{
	String fileName;
	
	public MetaDataWriter(String outputName){
		this.fileName = outputName;
	}
	
	public void write() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
		bw.write("U=" + (Main.voltage==-1?"":Main.voltage));
		bw.newLine();
		bw.write("I=" + (Main.power==-1?"":Main.power));
		bw.newLine();
		bw.write("R=" + (Main.resistance==-1?"":Main.resistance));
		bw.newLine();
		bw.write("END");
		bw.newLine();
		for(Resistor res : Main.resistors){
			bw.write(res.name);
			bw.newLine();
			bw.write("U=" + (res.voltage==-1?"":res.voltage));
			bw.newLine();
			bw.write("I=" + (res.power==-1?"":res.power));
			bw.newLine();
			bw.write("R=" + (res.resistance==-1?"":res.resistance));
			bw.newLine();
			bw.write("END");
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
