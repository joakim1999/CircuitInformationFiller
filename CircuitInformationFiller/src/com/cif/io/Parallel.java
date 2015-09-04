package com.cif.io;

import java.util.ArrayList;

import com.cif.calc.Calculator;
import com.cif.main.Main;

public class Parallel extends Calculator{
	public float voltage = -1;
	public float power = -1;
	public float resistance = -1;
	
	public String name;
	
	public Parallel(){
		this.resistors = new ArrayList<Resistor>();
	}
	
	public void addResistor(Resistor r){
		this.resistors.add(r);
	}
	
	public void solveParallel(){
		if(Main.unknowns.isParallelCleared(this)){
			return;
		}
		
		while(!Main.unknowns.isParallelCleared(this)){
			tryResistorsWithOhm();
			if(!Main.unknowns.isParallelCleared(this)){
				boolean progress = false;
				if(tryToFindSharedVoltage())progress = true;
				if(tryToFindTotalPower())progress = true;
				if(tryToFindTotalResistance())progress = true;
				if(tryMainWithOhm())progress = true;
				if(tryToFindPower())progress = true;
				if(tryToFindResistance())progress = true;
				if(progress == false)break;
			}
			
			else{
				if(!tryToFindSharedVoltage() || Main.unknowns.isParallelCleared(this)){
					break;
				}
			}
		}
	}
	
	public boolean tryMainWithOhm(){
		if(!Main.unknowns.isParallelCleared(this)){
			if(voltage == -1 && power != -1 && resistance != -1){
				voltage = power * resistance;
			}else if(power == -1 && voltage != -1 && resistance != -1){
				power = voltage/resistance;
			}else if(resistance == -1 && voltage != -1 && resistance != -1){
				resistance = voltage/resistance;
			}
			
			return Main.unknowns.isParallelCleared(this);
		}
		return true;
	}
	
	public boolean tryToFindSharedVoltage(){
		if(this.voltage != -1){
			for(Resistor r : resistors){
				if(r.voltage == -1){
					r.voltage = this.voltage;
					for(Resistor resis : resistors){
						resis.voltage = this.voltage;
					}
					return true;
				}
			}
		}
		for(Resistor r : resistors){
			if(r.voltage != -1){
				if(this.voltage == -1){
					this.voltage = r.voltage;
					for(Resistor res : resistors){
						res.voltage = r.voltage; 
					}
					return true;
				}
				for(Resistor res : resistors){
					if(res.voltage == -1){
						res.voltage = r.voltage; 
						for(Resistor resis : resistors){
							resis.voltage = this.voltage;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean tryToFindTotalPower(){
		if(this.power != -1)return false;
		float totalPower = 0;
		for(Resistor r : resistors){
			if(r.power == -1){
				System.out.println("Parallel: Can't find total power when not all the powers of the resistors are present");
				return false;
			}else{
				totalPower += r.power;
			}
		}
		this.power = totalPower;
		return true;
	}
	
	public boolean tryToFindTotalResistance(){
		if(this.resistance != -1)return false;
		float oneResistancePart = 0;
		for(Resistor r : resistors){
			if(r.resistance == -1){
				System.out.println("Parallel: Can't find total resistance when not all the resistances of the resistors are present");
				return false;
			}else{
				oneResistancePart += 1/r.resistance;
			}
		}
		this.resistance = (float)Math.pow(oneResistancePart, -1);
		return true;
	}
	
	public boolean tryToFindPower(){
		Resistor target = null;
		
		if(this.power == -1)return false;
		
		for(Resistor r : resistors){
			if(r.power == -1){
				if(target == null){
					target = r;
				}else{
					return false;
				}
				
			}
		}
		
		if(target == null){
			System.out.println("Parallel: All powers are already found");
			return false;
		}
		
		float power = this.power;
		
		for(Resistor r : resistors){
			if(r != target){
				power -= r.power;
			}
		}
		target.power = power;
		
		return true;
	}
	
	public boolean tryToFindResistance(){
		Resistor target = null;
		
		if(this.resistance == -1)return false;
		
		for(Resistor r : resistors){
			if(r.resistance == -1){
				if(target == null){
					target = r;
				}else{
					return false;
				}
				
			}
		}
		
		if(target == null){
			System.out.println("Parallel: All resistances are already found");
			return false;
		}
		
		float resistance = 1/this.resistance;
		
		for(Resistor r : resistors){
			if(r != target){
				resistance -= 1/r.resistance;
			}
		}
		
		target.resistance = (float) Math.pow(resistance, -1);
		return true;
	}
}
