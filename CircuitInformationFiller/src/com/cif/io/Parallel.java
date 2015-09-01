package com.cif.io;

import java.util.ArrayList;

public class Parallel {
	public float voltage = -1;
	public float power = -1;
	public float resistance = -1;
	
	public ArrayList<Resistor> resistors;
	
	public Parallel(){
		this.resistors = new ArrayList<Resistor>();
	}
	
	public void addResistor(Resistor r){
		this.resistors.add(r);
	}
}
