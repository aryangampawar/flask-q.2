package com.assignment;

import javax.jms.JMSException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

@Path("/publish")
public class MessageWebService {
    private MessagePublisher publisher = new MessagePublisher();

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response publishMessage(String message) {
        try {
            publisher.publishMessage(message);
            return Response.status(200).entity("Message published: " + message).build();
        } catch (JMSException e) {
            return Response.status(500).entity("Error publishing message: " + e.getMessage()).build();
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter(
            "jersey.config.server.provider.classnames",
            MessageWebService.class.getCanonicalName()
        );

        try {
            server.start();
            System.out.println("Web service started at http://localhost:8080/publish");
            server.join();
        } finally {
            server.destroy();
        }
    }
}