package tw.fondus.tool.thredds.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class Urls {
	@ElementList( inline = true, entry = "url" )
	private List<Url> list;

	public List<Url> getList() {
		return list;
	}

	public void setList( List<Url> list ) {
		this.list = list;
	}

}
