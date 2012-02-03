package binky.reportrunner.service.misc;

import java.util.HashMap;
import java.util.Map;

public class JDBCDrivers {
	//private List<JDBCDriverDefinition> definitions = new LinkedList<JDBCDriverDefinition>();
	private Map<String, JDBCDriverDefinition> definitions = new HashMap<String, JDBCDriverDefinition>(); 
	
	
	public void addDefinition(JDBCDriverDefinition def) {
		this.definitions.put(def.getLabel(),def);
	}
	
	public  Map<String, JDBCDriverDefinition> getDefinitions() {
		return definitions;
	}
	

}