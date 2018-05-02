package tw.fondus.tool.thredds.command.main;

import org.junit.Test;

import tw.fondus.tool.thredds.command.main.ThreddsProcess;
import tw.fondus.tool.thredds.command.process.ProgramArguments;

public class ThreddsProcessTest {
	
	@Test
	public void test(){
		String[] args = new String[]{
			"-b",
			"src/test/resources/",
			"-c",
			"config.xml"
		};
		
		ProgramArguments arguments = new ProgramArguments();
		new ThreddsProcess().run( args, arguments );
	}
}
