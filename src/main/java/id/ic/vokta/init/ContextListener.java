package id.ic.vokta.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import id.ic.vokta.manager.ScheduleManager;
import id.ic.vokta.util.log.AppLogger;
import id.ic.vokta.manager.ConnectionManager;
import id.ic.vokta.manager.EncryptionManager;
import id.ic.vokta.manager.PropertyManager;

@WebListener
public class ContextListener implements ServletContextListener {

    public final AppLogger log;

    public ContextListener() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("Application Init Started");
        PropertyManager.getInstance();
        EncryptionManager.getInstance();
        ConnectionManager.getInstance();
        ScheduleManager.getInstance();
        log.info("Application Init Completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        log.info("Application Shutdown Started");
        PropertyManager.getInstance().shutdown();
        EncryptionManager.getInstance().shutdown();
        ConnectionManager.getInstance().shutdown();
        log.info("Application Shutdown Completed");
    }
}
