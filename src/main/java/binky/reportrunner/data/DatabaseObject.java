package binky.reportrunner.data;

import java.io.Serializable;

public abstract class DatabaseObject<ID extends Serializable  > implements Serializable  {

	public abstract ID getId();
	
}
