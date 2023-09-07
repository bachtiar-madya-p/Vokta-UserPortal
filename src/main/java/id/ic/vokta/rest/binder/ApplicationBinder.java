package id.ic.vokta.rest.binder;

import id.ic.vokta.rest.validator.Validator;
import id.ic.vokta.util.log.AppLogger;
import org.atteo.classindex.ClassIndex;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import id.ic.vokta.controller.Controller;

public class ApplicationBinder extends AbstractBinder {
    private AppLogger log;

    public ApplicationBinder() {
        log = new AppLogger(this.getClass());
    }

    @Override
    protected void configure() {
        final String methodName = "configure";

        log.debug(methodName, "start");

        // Controllers
        ClassIndex.getAnnotated(Controller.class).forEach(this::bindAsContract);
        // Validators
        ClassIndex.getAnnotated(Validator.class).forEach(this::bindAsContract);
        log.debug(methodName, "completed");
    }

}
