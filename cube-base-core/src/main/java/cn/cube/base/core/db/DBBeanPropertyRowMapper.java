package cn.cube.base.core.db;

import org.springframework.beans.BeanWrapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    private static final ConversionService conversionService;

    static {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        conversionService = defaultConversionService;
    }

    public DBBeanPropertyRowMapper(Class<T> mappedClass) {
        super(mappedClass);
        setPrimitivesDefaultedForNullValue(true);
    }

    protected void initBeanWrapper(BeanWrapper bw) {
        bw.setConversionService(conversionService);
    }

    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        if (pd.getPropertyType() == BigDecimal.class) {
            String value = rs.getString(index);
            if (value == null) {
                return null;
            }
            return new BigDecimal(value);
        }

        return super.getColumnValue(rs, index, pd);
    }

}
