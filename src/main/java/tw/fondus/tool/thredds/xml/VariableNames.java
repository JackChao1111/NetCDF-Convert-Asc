package tw.fondus.tool.thredds.xml;

import org.simpleframework.xml.Element;

/**
 * 
 * @author Chao
 *
 */
public class VariableNames {
	@Element( name = "x")
	private String x;
	
	@Element( name = "y")
	private String y;
	
	@Element( name = "value" )
	private String value;
	
	@Element(name = "missingValue")
	private String missingValue;

	public String getX() {
		return x;
	}

	public void setX( String x ) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY( String y ) {
		this.y = y;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	public String getMissingValue() {
		return missingValue;
	}

	public void setMissingValue( String missingValue ) {
		this.missingValue = missingValue;
	}
		
}
