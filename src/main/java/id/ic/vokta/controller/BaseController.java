package id.ic.vokta.controller;

import id.ic.vokta.manager.ConnectionManager;
import id.ic.vokta.util.log.AppLogger;
import id.ic.vokta.manager.PropertyManager;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;

import javax.inject.Inject;
import java.sql.SQLException;

public class BaseController {

    protected Jdbi jdbi;

    protected AppLogger log;

    public BaseController() {
        // Empty Constructor
    }

    protected <T> AppLogger getLogger(Class<T> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected int getIntProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    protected boolean getBoolProperty(String key) {
        return PropertyManager.getInstance().getBooleanProperty(key);
    }

    protected Handle getHandle() throws SQLException {
        return Jdbi.open(ConnectionManager.getInstance().getDataSource());
    }

    protected boolean executeUpdate(Update update) {
        return update.execute() > 0;
    }

    protected int execute(Update update) {
        return update.execute();
    }
}
