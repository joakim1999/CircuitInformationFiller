package com.cif.calc;

import com.cif.io.Parallel;
import com.cif.io.Resistor;
import com.cif.main.Main;

public class Unknowns {
	public boolean mainCleared = false;
	public Boolean[] resistorsCleared;
	public Boolean[][] parallelsCleared;
	
	public Unknowns(){
		resistorsCleared = new Boolean[Main.resistors.size()];
		parallelsCleared = new Boolean[Main.parallels.size()][];
		for(Parallel p : Main.parallels){
			parallelsCleared[Main.parallels.indexOf(p)] = new Boolean[p.resistors.size()];
		}
	}
	
	public boolean isMainCleared(){
		if(!mainCleared){
			return mainCleared = Main.voltage != -1 && Main.power != -1 && Main.resistance != -1;
		}
		return true;
	}
	
	public boolean isResistorCleared(Resistor r){
		int indexOf = Main.resistors.indexOf(r);
		if(resistorsCleared[indexOf] == null || resistorsCleared[indexOf] == false){
			return resistorsCleared[indexOf] = r.voltage != -1 && r.power != -1 && r.resistance != -1;
		}
		System.out.println(r.name + " cleared");
		return true;
	}
	
	public boolean isResistorsCleared(){
		for(Resistor r : Main.resistors){
			if(!this.isResistorCleared(r)){
				return false;
			}
		}
		return true;
	}
	
	public boolean isParallelCleared(Parallel p){
		for(Boolean b : parallelsCleared[Main.parallels.indexOf(p)]){
			if(b == null || !b){
				return false;
			}
		}
		return true;
	}
	
	public boolean isAllCleared(){
		if(mainCleared){
			for(Boolean b : resistorsCleared){
				if(b == null || !b){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
}
