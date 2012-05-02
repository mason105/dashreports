package binky.reportrunner.util;

import java.sql.Types;
import org.hibernate.dialect.DerbyTenSevenDialect;

public class DerbyDialect extends DerbyTenSevenDialect {

	//http://stackoverflow.com/questions/10113122/hibernate-derby-comparisons-between-boolean-and-integer-are-not-supported
	
    public DerbyDialect() {
        // fix Derby dialect boolean data type mapping error
        registerColumnType(Types.BOOLEAN, "INTEGER");
    }
}