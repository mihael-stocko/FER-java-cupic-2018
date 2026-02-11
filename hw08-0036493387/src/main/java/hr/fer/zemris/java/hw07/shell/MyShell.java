package hr.fer.zemris.java.hw07.shell;

/**
 * This program runs the MyShell. Arguments are not used. The user can enter commands and
 * the shell will execute them.
 * 
 * @author Mihael Stočko
 *
 */
public class MyShell {
	
	/**
	 * Main method.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		Environment environment = new EnvironmentImpl();
		System.out.println("Welcome to MyShell v 1.0");
		
		while(true) {
			String l;
			try {
				l = environment.readLine();
			} catch(ShellIOException e) {
				System.out.println("Cannot read user input. Terminating.");
				return;
			}
			
			String[] line = l.split(" ", 2);
			String commandName = line[0];
			String arguments;
			if(line.length != 1) {
				arguments = line[1];
			} else {
				arguments = "";
			}
			
			ShellCommand command = environment.commands().get(commandName);
			try {
				if(command == null) {
					environment.writeln("Unsupported command.");
					continue;
				}
			} catch(ShellIOException e) {
				System.out.println("Cannot write to the console. Terminating.");
				return;
			}
			
			ShellStatus status;
			try {
				status = command.executeCommand(environment, arguments);
			} catch(ShellIOException e) {
				System.out.println("Cannot read user input or write to the console. Terminating.");
				return;
			}
			
			if(status == ShellStatus.TERMINATE) {
				break;
			}
		}
	}
}
