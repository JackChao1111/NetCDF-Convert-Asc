package tw.fondus.tool.thredds.xml;

import org.simpleframework.xml.Attribute;

/**
 * 
 * @author Chao
 *
 */
public class Url {
	@Attribute
	private String input;

	@Attribute
	private String output;

	public String getInput() {
		return input;
	}

	public void setInput( String input ) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput( String output ) {
		this.output = output;
	}
}
