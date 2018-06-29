package es.redmic.api.utils.sitemap.dto;

import java.util.ArrayList;

public class ModuleNamesDTO extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getParentName() {
		
		if (this.size() < 2)
			return null;
		return this.get(0);
	}
	
	public String getModuleName() {
		
		if (this.size() < 2)
			return null;
		return this.get(1);
	}
}
