package com.mr.revolut.api;

import com.mr.revolut.api.controller.UserAccountController;
import com.mr.revolut.api.database.DummyDB;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

@Log4j2
public class RevolutApp {
    public static void main(String[] args) {
        log.info("Initializing database");
        DummyDB initialDummyDB = new DummyDB();

        log.info("Starting Jetty server");
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                UserAccountController.class.getCanonicalName());

        try {
            server.start();
            log.info("Server started at port 8080");
            server.join();
        } catch (Exception e) {
            log.error(RevolutApp.class.getName() + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("Closing Jetty server");
            server.destroy();
        }
    }
}
