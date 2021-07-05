package cn.cube.base.core.db;

import cn.cube.base.core.db.EntityClassWrapper.ColumnField;
import cn.cube.base.core.exception.NoDataUpdatedException;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class SQLExecutor<T> {

    private final JdbcTemplate jdbcTemplate;
    private final EntityClassWrapper entityClassWrapper;

    public SQLExecutor(JdbcTemplate jdbcTemplate, Class<T> klass) {
        this.jdbcTemplate = jdbcTemplate;
        entityClassWrapper = EntityClassWrapper.wrap(klass);
    }

    private String buildColumns(Object entity, boolean includeId, boolean ignoreNull) {
        List<String> list = Lists.newArrayList();

        for (ColumnField field : entityClassWrapper.getColumnFields()) {
            if (field.isId() && !includeId) {
                continue;
            }

            if (ignoreNull && isNull(entity, field)) {
                continue;
            }

            list.add(field.getColumnName());
        }

        return Joiner.on(",").join(list);
    }

    private String buildColumnPlaceholders(Object entity, boolean includeId, boolean ignoreNull) {
        List<String> list = Lists.newArrayList();

        for (ColumnField field : entityClassWrapper.getColumnFields()) {
            if (field.isId() && !includeId) {
                continue;
            }

            if (ignoreNull && isNull(entity, field)) {
                continue;
            }

            list.add("?");
        }

        return Joiner.on(",").join(list);
    }

    private List<Object> buildParameters(Object entity, boolean includeId, boolean versionCheck, boolean ignoreNull) {
        List<Object> list = Lists.newArrayList();
        for (ColumnField field : entityClassWrapper.getColumnFields()) {
            if (field.isId() && !includeId) {
                continue;
            }

            if (field.isVersion() && versionCheck) {
                continue;
            }
            if (ignoreNull && isNull(entity, field)) {
                continue;
            }

            Object value = field.getJdbcValue(entity);
            list.add(value);
        }

        return list;
    }

    private boolean isNull(Object entity, ColumnField f) {
        return f.get(entity) == null;
    }


    public int batchInsert(List<T> entities, boolean replace, boolean ignore) {
        if (entities.isEmpty()) {
            return 0;
        }

        T firstEntity = entities.get(0);

        boolean includeId = entityClassWrapper.isIdPresent(firstEntity);

        final StringBuilder sql = new StringBuilder();
        final List<Object> parameters = new ArrayList<>();

        if (replace) {
            sql.append("replace into ");
        } else {
            if (ignore) {
                sql.append("insert ignore into ");
            } else {
                sql.append("insert into ");
            }

        }
        sql.append(entityClassWrapper.getTableName())
                .append("(");
        sql.append(buildColumns(firstEntity, includeId, false)).append(")");
        sql.append(" values");

        int size = entities.size();
        for (int i = 0; i < size; i++) {
            T entity = entities.get(i);

            parameters.addAll(buildParameters(entity, includeId, false, false));

            sql.append("(");
            sql.append(buildColumnPlaceholders(entity, includeId, false));
            sql.append(")");
            if (i < size - 1) {
                sql.append(",");
            }
        }

        return jdbcTemplate.update(sql.toString(), parameters.toArray());
    }

    public int insert(T entity, boolean replace) {

        boolean includeId = entityClassWrapper.isIdPresent(entity);

        final StringBuilder sql = new StringBuilder();
        final List<Object> parameters = buildParameters(entity, includeId, false, true);

        if (replace) {
            sql.append("replace into ");
        } else {
            sql.append("insert into ");
        }
        sql.append(entityClassWrapper.getTableName())
                .append("(");
        sql.append(buildColumns(entity, includeId, true)).append(")");
        sql.append(" values(");
        sql.append(buildColumnPlaceholders(entity, includeId, true));
        sql.append(")");

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql.toString(),
                        Statement.RETURN_GENERATED_KEYS);
                int idx = 1;
                for (Object param : parameters) {
                    StatementCreatorUtils.setParameterValue(ps, idx++,
                            SqlTypeValue.TYPE_UNKNOWN, param);
                }
                return ps;
            }
        }, generatedKeyHolder);

        if (replace) {
            //返回的数量会翻倍, 原因未知
            return result / 2;
        }

        Number key = generatedKeyHolder.getKey();
        if (key != null) {
            ColumnField idField = entityClassWrapper.getIdColumnField();
            if (idField == null) {
                throw new IllegalArgumentException("id column is required!");
            }
            Class<?> keyClass = idField.getField().getType();
            if (keyClass == Long.class || keyClass == Long.TYPE) {
                idField.set(entity, key.longValue());
            } else {
                idField.set(entity, key.intValue());
            }
        }

        return result;
    }

    private String buildSetsForUpdate(Object entity, boolean includeId, boolean versionCheck) {
        return buildSetsForUpdate(entity, includeId, versionCheck, false);
    }

    private String buildSetsForUpdate(Object entity, boolean includeId, boolean versionCheck,boolean ignoreNull) {
        List<String> list = Lists.newArrayList();
        for (ColumnField field : entityClassWrapper.getColumnFields()) {
            if ((field.isId() && !includeId)) {
                continue;
            }
            if (field.isVersion() && versionCheck) {
                continue;
            }

            if (ignoreNull && isNull(entity, field)) {
                continue;
            }

            list.add(field.getColumnName() + " = ?");
        }

        return Joiner.on(",").join(list);
    }

    private String buildIdCondition(boolean versionCheck) {
        ColumnField idField = entityClassWrapper.getIdColumnField();
        if (idField == null) {
            throw new IllegalStateException("id column is required");
        }
        StringBuffer buffer = new StringBuffer(idField.getColumnName() + "=?");
        return buffer.toString();
    }


    public int updateWithCheckVersion(T entity) throws NoDataUpdatedException {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(entityClassWrapper.getTableName())
                .append(" set ");
        sql.append(buildSetsForUpdate(entity, false, true));
        sql.append(" where ").append(buildIdCondition(true));

        List<Object> parameters = buildParameters(entity, false, true, true);
        Object id = entityClassWrapper.getIdColumnField().get(entity);
        parameters.add(id);
        int rowsEffect = jdbcTemplate.update(sql.toString(), parameters.toArray());
        if (rowsEffect == 0) {
            String msg = "no data updated. entity: ";
            throw new NoDataUpdatedException(msg + entity);
        }
        return rowsEffect;
    }


    public int update(T entity, boolean ignoreNull) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(entityClassWrapper.getTableName())
                .append(" set ");
        sql.append(buildSetsForUpdate(entity, false, false, ignoreNull));
        sql.append(" where ").append(buildIdCondition(false));

        List<Object> parameters = buildParameters(entity, false, false, ignoreNull);
        Object id = entityClassWrapper.getIdColumnField().get(entity);
        parameters.add(id);
        return jdbcTemplate.update(sql.toString(), parameters.toArray());
    }

    public int update(T entity) {
        return update(entity, true);
    }

    public int update(T entity, String... properties) {
        if (properties == null || properties.length == 0) {
            throw new IllegalArgumentException("properties can't be empty");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(entityClassWrapper.getTableName())
                .append(" set ");

        List<Object> parameters = new ArrayList<>();
        for (String field : properties) {
            if (field.equalsIgnoreCase("version")) {
                throw new IllegalArgumentException("version field can't be modified.");
            }
            ColumnField columnField = entityClassWrapper.getColumnField(field);
            sql.append(columnField.getColumnName()).append(" = ?,");
            parameters.add(columnField.getJdbcValue(entity));
        }
        sql.setLength(sql.length() - 1);

        sql.append(" where ").append(buildIdCondition(false));

        Object id = entityClassWrapper.getIdColumnField().get(entity);
        parameters.add(id);
        return jdbcTemplate.update(sql.toString(), parameters.toArray());
    }

    public int delete(T entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(entityClassWrapper.getTableName());
        sql.append(" where ").append(buildIdCondition(false));

        Object id = entityClassWrapper.getIdColumnField().get(entity);
        return jdbcTemplate.update(sql.toString(), id);
    }

}
