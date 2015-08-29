package com.cif.icons;

import java.awt.Graphics;

import com.cif.behavior.Action;

public enum CircuitIcons {
	STARTPOINT(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawRoundRect((int)arguments[1], (int)arguments[2], (int)arguments[3], (int)arguments[4], 300, 300);
		}
	}),
	ENDPOINT(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawRoundRect((int)arguments[1], (int)arguments[2], (int)arguments[3], (int)arguments[4], 300, 300);
		}
	}),
	LINE_HORIZONTAL(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1], (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + (int)arguments[3], (int)arguments[2] + ((int)arguments[4]/2));
		}
	}),
	LINE_VERTICAL(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2], 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + (int)arguments[4]);
		}
	}),
	MEETINGPOINT(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1], (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + (int)arguments[3], (int)arguments[2] + ((int)arguments[4]/2));
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2], 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + (int)arguments[4]);
		}
	}),
	LINE_LEFT_TOP(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1], (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2));
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2]);
		}
	}),
	LINE_BOTTOM_RIGHT(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + (int)arguments[4]);
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + (int)arguments[3], (int)arguments[2] + ((int)arguments[4]/2));
		}
	}),
	LINE_BOTTOM_LEFT(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1], (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2));
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + (int)arguments[4]);
		}
	}),
	LINE_RIGHT_TOP(new Action(""){
		@Override
		public void execute() {
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2], 
					(int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2));
			((Graphics)arguments[0]).drawLine((int)arguments[1] + ((int)arguments[3]/2), (int)arguments[2] + ((int)arguments[4]/2), 
					(int)arguments[1] + (int)arguments[3], (int)arguments[2] + ((int)arguments[4]/2));
		}
	});
	
	Action action;
	
	CircuitIcons(Action a){
		this.action = a;
	}
	
	public Action getAction(){
		return action;
	}
}
