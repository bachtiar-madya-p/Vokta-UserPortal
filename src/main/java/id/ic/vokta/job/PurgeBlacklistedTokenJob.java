package id.ic.vokta.job;

import id.ic.vokta.util.helper.JWTHelper;
import id.ic.vokta.util.log.AppLogger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class PurgeBlacklistedTokenJob implements Job {

    public static final ReentrantLock lock = new ReentrantLock();
    private AppLogger log;

    public PurgeBlacklistedTokenJob() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        final String methodName = "execute";
        log.info("Purge BlacklistedToken Job execute Started");

        if (lock.tryLock()) {
            try {
                Set<String> blacklistedTokens = JWTHelper.getBlacklistedTokens();
                if (!blacklistedTokens.isEmpty()) {
                    JWTHelper.purgeBlacklistToken();
                }

            } catch (Exception e) {
                log.error(methodName, "Exception caught: " + e);
            } finally {
                lock.unlock();
            }

            log.info("Purge BlacklistedToken Job execute Completed");
        }
    }
}
