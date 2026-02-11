package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * This class is used to create a single instance of LSystem.
 * 
 * @author Mihael Stočko
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	/**
	 * Holds commands mapped to keys
	 */
	private Dictionary commands = new Dictionary();
	
	/**
	 * Holds productions mapped to keys
	 */
	private Dictionary productions = new Dictionary();
	
	/**
	 * Lenght of one line unit
	 */
	private double unitLength = 0.1;
	
	/**
	 * Factor that is used to reduce the unitLength on deeper levels
	 */
	private double unitLengthDegreeScaler = 1;
	
	/**
	 * Original position of the turtle
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * Original angle of the turtle
	 */
	private double angle = 0;
	
	/**
	 * Starting string
	 */
	private String axiom = "";
	
	/**
	 * This method is used to set the unit length from outside.
	 * @return itself
	 */
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}
	
	/**
	 * This method is used to set the origin from outside.
	 * @return itself
	 */
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}
	
	/**
	 * This method is used to set the angle from outside.
	 * @return itself
	 */
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}
	
	/**
	 * This method is used to set the axiom from outside. Axiom cannot be null.
	 * @return itself
	 * @throws NullPointerException
	 */
	public LSystemBuilder setAxiom(String axiom) {
		if(axiom == null) {
			throw new NullPointerException("null is not a valid axiom.");
		}
		
		this.axiom = axiom;
		return this;
	}
	
	/**
	 * This method is used to set the unit length degree scaler from outside.
	 * @return itself
	 */
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	/**
	 * This method is used to register a production and map it to a symbol.
	 * The production cannot be null.
	 * @return itself
	 * @throws NullPointerException
	 */
	public LSystemBuilder registerProduction(char symbol, String production) {
		if(production == null) {
			throw new NullPointerException("null is not a valid production.");
		}
		
		productions.put(symbol, production);
		return this;
	}
	
	/**
	 * This method is used to register a command and map it to a symbol.
	 * @return itself
	 */
	public LSystemBuilder registerCommand(char symbol, String action) {
		String[] params = action.split("\\s+");
		String commandArgument = null;
		if(params.length == 2) {
			commandArgument = params[1];
		}
		Object act = createCommand(params[0], commandArgument);
		commands.put(symbol, act);
		return this;
	}
	
	/**
	 * Configures the whole class with a single array of strings.
	 * @return itself
	 * @throws IllegalArgumentException
	 */
	public LSystemBuilder configureFromText(String[] lines) {
		loop: for(int i = 0; i < lines.length; i++) {
			String[] params = lines[i].split("\\s+");
			switch(params[0]) {
			case "origin":
				try {
					origin = new Vector2D(Double.parseDouble(params[1]), Double.parseDouble(params[2]));
				} catch(Exception e) {
					throw new IllegalArgumentException(e);
				}
				break;
			case "angle":
				try {
					angle = Double.parseDouble(params[1]);
				} catch(Exception e) {
					throw new IllegalArgumentException(e);
				}
				break;
			case "unitLength":
				try {
					unitLength = Double.parseDouble(params[1]);
				} catch(Exception e) {
					throw new IllegalArgumentException(e);
				}
				break;
			case "unitLengthDegreeScaler":
				try {
					if(params.length == 2) {
						unitLengthDegreeScaler = Double.parseDouble(params[1]);
					} else if(params.length == 3) {
						if(params[1].charAt(params[1].length()-1) == '/') {
							params[1] = params[1].substring(0, params[1].length() - 1);
						} else if(params[2].charAt(0) == '/') {
							params[2] = params[2].substring(1);
						}
						unitLengthDegreeScaler = Double.parseDouble(params[1])/Double.parseDouble(params[2]);
					} else if(params.length == 4) {
						unitLengthDegreeScaler = Double.parseDouble(params[1])/Double.parseDouble(params[3]);
					}
				} catch(Exception e) {
					throw new IllegalArgumentException(e);
				}
				break;
			case "axiom":
				try {
					axiom = params[1];
				} catch(Exception e) {
					throw new IllegalArgumentException(e);
				}
				break;
			case "command":
				Command command = null;
				String commandArgument = null;
				if(params.length == 4) {
					commandArgument = params[3];
				}
				command = createCommand(params[2], commandArgument);
				commands.put(params[1].charAt(0), command);
				break;
			case "production":
				productions.put(params[1].charAt(0), params[2]);
				break;
			case "":
				continue loop;
			default:
				throw new IllegalArgumentException("Illegal expression.");
			}
		}
		return this;
	}
	
	/**
	 * Internally used method for creating an object for the required command.
	 * 
	 * @param arg1 command
	 * @param arg2 optional parameter
	 * @return Object that implements Command
	 * @throws IllegalArgumentException
	 */
	private Command createCommand(String arg1, String arg2) {
		Command command = null;
		
		switch(arg1) {
		case "draw":
			try {
				command = new DrawCommand(Double.parseDouble(arg2));
			} catch(Exception e) {
				throw new IllegalArgumentException(e);
			}
			break;
		case "skip":
			try {
				command = new SkipCommand(Double.parseDouble(arg2));
			} catch(Exception e) {
				throw new IllegalArgumentException(e);
			}
			break;
		case "rotate":
			try {
				double angle = Double.parseDouble(arg2);
				
				Vector2D vec = new Vector2D(Math.cos(angle/360*Math.PI*2), Math.sin(angle/360*Math.PI*2));
				
				command = new RotateCommand(vec);
			} catch(Exception e) {
				throw new IllegalArgumentException(e);
			}
			break;
		case "scale":
			try {
				command = new ScaleCommand(Double.parseDouble(arg2));
			} catch(Exception e) {
				throw new IllegalArgumentException(e);
			}
			break;
		case "push":
			command = new PushCommand();
			break;
		case "pop":
			command = new PopCommand();
			break;
		case "color":
			try {
				int r, g, b;
				r = arg2.charAt(0) + arg2.charAt(1);
				g = arg2.charAt(2) + arg2.charAt(3);
				b = arg2.charAt(4) + arg2.charAt(5);
				command = new ColorCommand(new Color(r, g, b));
			} catch(Exception e) {
				throw new IllegalArgumentException(e);
			}
			break;
		default:
			throw new IllegalArgumentException("Unsupported command.");
		}
		
		return command;
	}
	
	/**
	 * Builds an LSystem from the configuration
	 * @return Generated LSystem
	 */
	public LSystem build() {
		return new LSystem() {
			
			/**
			 * Length of the turtle's one step.
			 */
			private double effectiveLength;
			
			/**
			 * Generates a string from the axiom using productions.
			 * @param level Depth of the fractal
			 * @return Generated string
			 */
			@Override
			public String generate(int level) {
				String start = axiom;
				String result = "";
				for(int i = 0; i < level; i++) {
					for(int j = 0, len = start.length(); j < len; j++) {
						try {
							result += productions.get(start.charAt(j));
						} catch(Exception e) {
							result += start.charAt(j);
						}
					}
					start = result;
					result = "";
				}
				
				return start;
			}
			
			/**
			 * Draws the fractal on the screen to the desired level.
			 * @param level Depth of the fractal
			 * @param painter An instance of the Painter class
			 */
			@Override
			public void draw(int level, Painter painter) {
				effectiveLength = unitLength*Math.pow(unitLengthDegreeScaler, level);
				
				Context context = new Context();
				context.pushState(new TurtleState(origin, new Vector2D(Math.cos(angle), Math.sin(angle)), 
						Color.black, effectiveLength));
				
				String finalString = generate(level);
				for(int i = 0, len = finalString.length(); i < len; i++) {
					try {
						Command command = (Command)commands.get(finalString.charAt(i));

						command.execute(context, painter);
					} catch(Exception ignorable) {
						
					}
				}
			}
		};
	}
}
