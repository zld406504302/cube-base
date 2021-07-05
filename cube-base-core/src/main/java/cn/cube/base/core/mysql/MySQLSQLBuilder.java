package cn.cube.base.core.mysql;


import cn.cube.base.core.db.GenericSQLBuilder;
import cn.cube.base.core.db.ISQLBuilder;

public class MySQLSQLBuilder extends GenericSQLBuilder implements ISQLBuilder {
	
	@Override
	public String getLastInsertIdSQL() {
		return "select last_insert_id()";
	}

}
