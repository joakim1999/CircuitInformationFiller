package com.cif.calc;

import com.cif.io.Resistor;
import com.cif.main.Main;

public class Calculator {
	public Calculator(){
		
	}
	
	public void calculate(){
		tryMainWithOhm();
		
		tryResistorsWithOhm();
		
		if(Main.unknowns.isAllCleared()){
			System.out.println("Calculation completed. Every variable is solved");
			return;
		}
		
		if(tryToFindSharedPower()){
			tryMainWithOhm();
			tryResistorsWithOhm();
			if(Main.unknowns.isAllCleared()){
				System.out.println("Calculation completed. Every variable is solved");
				return;
			}
		}
		if(tryToFindTotalResistance()){
			if(tryMainWithOhm()){
				if(tryToFindSharedPower())tryResistorsWithOhm();
				if(Main.unknowns.isAllCleared()){
					System.out.println("Calculation completed. Every variable is solved");
					return;
				}
			}
		}
		else{
			if(tryToFindResistance()){
				if(tryResistorsWithOhm()){
					if(tryToFindSharedPower())tryMainWithOhm();
					if(Main.unknowns.isAllCleared()){
						System.out.println("Calculation completed. Every variable is solved");
						return;
					}
				}
			}
		}
		if(tryToFindTotalVoltage()){
			if(tryMainWithOhm()){
				if(tryToFindSharedPower())tryResistorsWithOhm();
				if(Main.unknowns.isAllCleared()){
					System.out.println("Calculation completed. Every variable is solved");
					return;
				}
			}
		}else{
			if(tryToFindVoltage()){
				if(tryToFindSharedPower())tryMainWithOhm();
				if(Main.unknowns.isAllCleared()){
					System.out.println("Calculation completed. Every variable is solved");
					return;
				}
			}
		}
		
		System.out.println("Calculation completed. Not all variables are solved");
	}
	
	public boolean tryMainWithOhm(){
		if(!Main.unknowns.isMainCleared()){
			//Try to solve Main's "?" with the law of Ohm
			if(Main.voltage == -1 && Main.power != -1 && Main.resistance != -1){
				Main.voltage = Main.power * Main.resistance;
			}else if(Main.power == -1 && Main.voltage != -1 && Main.resistance != -1){
				Main.power = Main.voltage/Main.resistance;
			}else if(Main.resistance == -1 && Main.voltage != -1 && Main.power != -1){
				Main.resistance = Main.voltage/Main.power;
			}
			return Main.unknowns.isMainCleared();
		}
		return true;
	}
	
	public boolean tryResistorsWithOhm(){
		//Try to solve Resistors' "?" with the law of Ohm. Returns true if at least one was solved
		boolean sucess = false;
		for(Resistor r : Main.resistors){
			if(tryResistorWithOhm(r)){
				sucess = true;
			}
		}
		return sucess;
	}
	
	public boolean tryResistorWithOhm(Resistor r){
		//Try to solve Resistor's "?" with the law of Ohm
		if(!Main.unknowns.isResistorCleared(r)){
			if(r.voltage == -1 && r.power != -1 && r.resistance != -1){
				r.voltage = r.power * r.resistance;
			}else if(r.power == -1 && r.voltage != -1 && r.resistance != -1){
				r.power = r.voltage/r.resistance;
				tryResistorWithOhm(r);
			}else if(r.resistance == -1 && r.voltage != -1 && r.power != -1){
				r.resistance = r.voltage/r.power;
				tryResistorWithOhm(r);
			}
			return Main.unknowns.isResistorCleared(r);
		}
		return true;
	}
	
	public boolean tryToFindSharedPower(){
		if(Main.power != -1){
			for(Resistor r : Main.resistors){
				if(!r.isInParallel){
					r.power = Main.power;
				}
			}
			return true;
		}
		
		for(Resistor r : Main.resistors){
			if(!r.isInParallel && r.power != -1){
				Main.power = r.power;
				for(Resistor res : Main.resistors){
					if(!res.isInParallel){
						res.power = r.power; 
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean tryToFindTotalResistance(){
		float totalResistance = 0;
		for(Resistor r : Main.resistors){
			if(r.resistance == -1){
				System.out.println("Can't find total resistance when not all resistances of resistors are present");
				return false;
			}else{
				totalResistance += r.resistance;
			}
		}
		Main.resistance = totalResistance;
		return true;
	}
	
	public boolean tryToFindTotalVoltage(){
		float totalVoltage = 0;
		for(Resistor r : Main.resistors){
			if(r.voltage == - 1){
				System.out.println("Can't find total voltage when not all resistances of resistors are present");
				return false;
			}else{
				totalVoltage += r.voltage;
			}
		}
		Main.voltage = totalVoltage;
		return true;
	}
	
	public boolean tryToFindResistance(){
		Resistor target = null;
		
		if(Main.resistance == -1){
			return false;
		}
		
		for(Resistor r : Main.resistors){
			if(r.resistance == -1){
				if(target == null){
					target = r;
				}else{
					return false;
				}
			}
		}
		
		if(target == null){
			System.out.println("All resistances are already found");
			return false;
		}
		
		float resistance = Main.resistance;
		for(Resistor r : Main.resistors){
			if(r != target){
				resistance -= r.resistance;
			}
		}
		target.resistance = resistance;
		return true;
	}
	
	public boolean tryToFindVoltage(){
		Resistor target = null;
		
		if(Main.voltage == -1){
			return false;
		}
		
		for(Resistor r : Main.resistors){
			if(r.voltage == -1){
				if(target == null){
					target = r;
				}else{
					return false;
				}
			}
		}
		
		if(target == null){
			System.out.println("All voltages are already found");
			return false;
		}
		
		float voltage = Main.voltage;
		for(Resistor r : Main.resistors){
			if(r != target){
				voltage -= r.voltage;
			}
		}
		target.voltage = voltage;
		return true;
	}
}
