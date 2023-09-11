package id.ic.vokta.manager;

import id.ic.vokta.job.PurgeBlacklistedTokenJob;
import id.ic.vokta.util.log.AppLogger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class ScheduleManager extends BaseManager {

    private static Scheduler scheduler;
    private static ScheduleManager instance;

    private static final String PURGE_BLACKLISTED_TOKEN_GROUP  = "purge-blacklisted-token-group";

    private ScheduleManager() {
        final String methodName = "Constructor";
        log = new AppLogger(ScheduleManager.class);
        log.debug(methodName, "Start");

        log.info(methodName, "Scheduler Started");

        JobDetail job;
        Trigger trigger;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            job = newJob(PurgeBlacklistedTokenJob.class).withIdentity("purge-blacklisted-token-job", PURGE_BLACKLISTED_TOKEN_GROUP).build();
            trigger = newTrigger().withIdentity("purge-blacklisted-token-job-every-1-day", PURGE_BLACKLISTED_TOKEN_GROUP).startNow().withSchedule(simpleSchedule().withIntervalInHours(24).repeatForever()).build();
            scheduler.scheduleJob(job, trigger);


        } catch (SchedulerException e) {
            log.error(methodName, "Exception caught: " + e);
        }
        log.info(methodName,"Scheduler Start Completed");
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }
}