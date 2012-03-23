package org.karatachi.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.karatachi.classloader.Reflection;
import org.karatachi.thread.AcceptInterruptable;
import org.karatachi.thread.InterruptableOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StatementWrapper implements Statement {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final Statement statement;
    private final InterruptableOperation interruptable;

    public StatementWrapper(Statement statement) {
        this.statement = statement;
        this.interruptable = new InterruptableOperation() {
            @Override
            public void interrupt() {
                try {
                    StatementWrapper.this.statement.cancel();
                } catch (SQLException e) {
                    logger.error("Error on interrupting statement", e);
                }
            }
        };
    }

    private void setInterruptable() throws SQLException {
        if (Thread.currentThread() instanceof AcceptInterruptable) {
            try {
                ((AcceptInterruptable) Thread.currentThread())
                        .setInterruptable(interruptable);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("Thread Interrupted", e);
            }
        }
    }

    private void clearInterruptable() {
        if (Thread.currentThread() instanceof AcceptInterruptable) {
            ((AcceptInterruptable) Thread.currentThread()).clearInterruptable();
        }
    }

    private void logExecute(String sql) throws SQLException {
        if (logger.isDebugEnabled()) {
            String logstr = "Execute SQL : sql = " + sql;
            if (logger.isTraceEnabled())
                logstr = logstr + " from : "
                        + Reflection.getAncestorMethodInfo(2);
            logger.debug(logstr);
        }
    }

    public void addBatch(String sql) throws SQLException {
        statement.addBatch(sql);
    }

    public void cancel() throws SQLException {
        statement.cancel();
    }

    public void clearBatch() throws SQLException {
        statement.clearBatch();
    }

    public void clearWarnings() throws SQLException {
        statement.clearWarnings();
    }

    public void close() throws SQLException {
        statement.close();
    }

    public boolean execute(String sql) throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.execute(sql);
        } finally {
            clearInterruptable();
        }
    }

    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.execute(sql, autoGeneratedKeys);
        } finally {
            clearInterruptable();
        }
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.execute(sql, columnIndexes);
        } finally {
            clearInterruptable();
        }
    }

    public boolean execute(String sql, String[] columnNames)
            throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.execute(sql, columnNames);
        } finally {
            clearInterruptable();
        }
    }

    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.executeQuery(sql);
        } finally {
            clearInterruptable();
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.executeUpdate(sql);
        } finally {
            clearInterruptable();
        }
    }

    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.executeUpdate(sql, autoGeneratedKeys);
        } finally {
            clearInterruptable();
        }
    }

    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.executeUpdate(sql, columnIndexes);
        } finally {
            clearInterruptable();
        }
    }

    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        try {
            setInterruptable();
            logExecute(sql);
            return statement.executeUpdate(sql, columnNames);
        } finally {
            clearInterruptable();
        }
    }

    public Connection getConnection() throws SQLException {
        return statement.getConnection();
    }

    public int getFetchDirection() throws SQLException {
        return statement.getFetchDirection();
    }

    public int getFetchSize() throws SQLException {
        return statement.getFetchSize();
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return statement.getGeneratedKeys();
    }

    public int getMaxFieldSize() throws SQLException {
        return statement.getMaxFieldSize();
    }

    public int getMaxRows() throws SQLException {
        return statement.getMaxRows();
    }

    public boolean getMoreResults() throws SQLException {
        return statement.getMoreResults();
    }

    public boolean getMoreResults(int current) throws SQLException {
        return statement.getMoreResults(current);
    }

    public int getQueryTimeout() throws SQLException {
        return statement.getQueryTimeout();
    }

    public ResultSet getResultSet() throws SQLException {
        return statement.getResultSet();
    }

    public int getResultSetConcurrency() throws SQLException {
        return statement.getResultSetConcurrency();
    }

    public int getResultSetHoldability() throws SQLException {
        return statement.getResultSetHoldability();
    }

    public int getResultSetType() throws SQLException {
        return statement.getResultSetType();
    }

    public int getUpdateCount() throws SQLException {
        return statement.getUpdateCount();
    }

    public SQLWarning getWarnings() throws SQLException {
        return statement.getWarnings();
    }

    public boolean isClosed() throws SQLException {
        return statement.isClosed();
    }

    public boolean isPoolable() throws SQLException {
        return statement.isPoolable();
    }

    public void setCursorName(String name) throws SQLException {
        statement.setCursorName(name);
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        statement.setEscapeProcessing(enable);
    }

    public void setFetchDirection(int direction) throws SQLException {
        statement.setFetchDirection(direction);
    }

    public void setFetchSize(int rows) throws SQLException {
        statement.setFetchSize(rows);
    }

    public void setMaxFieldSize(int max) throws SQLException {
        statement.setMaxFieldSize(max);
    }

    public void setMaxRows(int max) throws SQLException {
        statement.setMaxRows(max);
    }

    public void setPoolable(boolean poolable) throws SQLException {
        statement.setPoolable(poolable);
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        statement.setQueryTimeout(seconds);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return statement.isWrapperFor(iface);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return statement.unwrap(iface);
    }
}
