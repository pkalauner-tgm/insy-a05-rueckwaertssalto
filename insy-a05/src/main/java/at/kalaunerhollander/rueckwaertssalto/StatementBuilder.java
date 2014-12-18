package at.kalaunerhollander.rueckwaertssalto;

import java.util.Arrays;

/**
 * Builds the statements
 *
 * @author Paul Kalauner 4AHIT
 * @version 20141210.1
 */
public class StatementBuilder {
    private String[] fields;
    private String condition;
    private String tableName;
    private String orderBy;
    private String orderDir;

    /**
     * initializes the StatementBuilder with the given table name
     *
     * @param tableName name of the table
     */
    private StatementBuilder(String tableName) {
        this.fields = new String[]{"*"};
        this.tableName = tableName;
        this.orderDir = "ASC";
    }

    /**
     * Creates a new {@code StatementBuilder}
     *
     * @param tableName name of table
     * @return a {@code StatementBuilder} instance
     */
    public static StatementBuilder createBuilder(String tableName) {
        return new StatementBuilder(tableName);
    }

    /**
     * Sets the fields which should be selected
     *
     * @param fields String array with the fields
     * @return the {@code StatementBuilder} instance
     */
    public StatementBuilder setFields(String[] fields) {
        this.fields = fields;
        return this;
    }

    /**
     * Sets the condition
     *
     * @param condition for the where clause
     * @return the {@code StatementBuilder} instance
     */
    public StatementBuilder setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Sets the order field
     *
     * @param orderBy order field
     * @return the {@code StatementBuilder} instance
     */
    public StatementBuilder setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    /**
     * Sets the order direction
     *
     * @param orderDir the order direction
     * @return the {@code StatementBuilder} instance
     */
    public StatementBuilder setOrderDir(String orderDir) {
        this.orderDir = orderDir;
        return this;
    }

    /**
     * Builds the statement
     *
     * @return statement as String
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        String fieldsStr = Arrays.toString(fields);
        sb.append("SELECT ").append(fieldsStr.substring(1, fieldsStr.length() - 1)).append(" FROM ").append(tableName);
        if (condition != null)
            sb.append(" WHERE ").append(condition);
        if (orderBy != null)
            sb.append(" ORDER BY ").append(orderBy).append(" ").append(orderDir);
        return sb.toString();
    }
}
