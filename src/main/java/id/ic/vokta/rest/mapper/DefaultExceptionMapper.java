package id.ic.vokta.rest.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import id.ic.vokta.util.log.AppLogger;
import id.ic.vokta.rest.model.ServiceResponse;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    private AppLogger log;

    public DefaultExceptionMapper() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public Response toResponse(Throwable ex) {
        log.error("toResponse", ex);
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ServiceResponse(Status.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON).build();
    }

}
