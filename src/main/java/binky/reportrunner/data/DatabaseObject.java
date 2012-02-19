package binky.reportrunner.data;

import java.io.Serializable;

public abstract class DatabaseObject<ID extends Serializable  > implements Serializable  {
	private static final long serialVersionUID = 4326796900014950571L;

	public abstract ID getId();

	@Override
	public abstract int hashCode() ;

	@Override
	public abstract boolean equals(Object obj);
	
}
