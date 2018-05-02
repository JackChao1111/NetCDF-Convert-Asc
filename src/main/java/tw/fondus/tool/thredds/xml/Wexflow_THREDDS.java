package tw.fondus.tool.thredds.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 
 * @author Chao
 *
 */
@Root( name = "Wexflow_THREDDS" )
public class Wexflow_THREDDS {
	@Element
	private VariableNames variableNames;

	@Element
	private Urls urls;

	public VariableNames getVariableNames() {
		return variableNames;
	}

	public void setVariableNames( VariableNames variableNames ) {
		this.variableNames = variableNames;
	}

	public Urls getUrls() {
		return urls;
	}

	public void setUrls( Urls urls ) {
		this.urls = urls;
	}

}
