package id.ic.up.manager;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@Singleton
@ApplicationPath("rest")
public class ServiceManager extends ResourceConfig {
	public ServiceManager() {

		// Packages to Auto Discover
		packages("id.ic.up.rest");
	}
}
