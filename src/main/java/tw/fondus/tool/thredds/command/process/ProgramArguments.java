package tw.fondus.tool.thredds.command.process;

import com.beust.jcommander.Parameter;

public class ProgramArguments {
	@Parameter(names = { "--base", "-b" }, required = true, description = "The base of working directory.")
	protected String basePath;
	
	@Parameter(names = { "--config", "-c" }, required = true, description = "The input of THREDDS of XML configuration.")
	protected String config;
	
	@Parameter(names = { "--help", "-h" }, description = "Show how to usage.", help = true)
	protected boolean help = false;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath( String basePath ) {
		this.basePath = basePath;
	}
	
	public String getConfig() {
		return config;
	}

	public void setConfig( String config ) {
		this.config = config;
	}

	public boolean isHelp() {
		return help;
	}

	public void setHelp( boolean help ) {
		this.help = help;
	}
	
	
}
