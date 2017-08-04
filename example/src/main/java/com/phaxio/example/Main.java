package com.phaxio.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static int main (String[] args) {
        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        handler.setContextPath("/");
        server.setHandler(handler);

        ServletHolder holder = handler.addServlet(ServletContainer.class, "/*");
        holder.setInitOrder(1);
        holder.setInitParameter("jersey.config.server.provider.packages", "com.phaxio.example.resources");
        holder.setInitParameter("jersey.config.server.provider.classnames", "com.phaxio.example.providers.UnrecognizedPropertyExceptionProvider,org.glassfish.jersey.media.multipart.MultiPartFeature");

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            server.destroy();
        }

        return 0;
    }
}
