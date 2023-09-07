package id.ic.vokta.util.property;

public class Property {

    private Property() {}

    // ENV PROPERTY
    // APP
    public static final String APP_CONTEXT_FQDN = "context.fqdn";
    public static final String APP_CONTEXT_PATH = "context.path";
    public static final String APP_SERVER_KEY = "app.server.key";
    public static final String APP_LOGIN_URL = "app.login.url";
    public static final String APP_CONTEXT_REGISTRATION_PATH = "context.registration.path";

    // DB
    public static final String DB_JDBC_URL = "db.jdbc.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool-size";
    public static final String DB_DRIVER_CLASSNAME = "db.driver.className";

    // AUDIT
    public static final String AUDIT_URL = "audit.url";
    public static final String AUDIT_ENABLED = "audit.enabled";

}
