package com.example;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by omatikaya on 19/12/2016.
 */
public class Main {
    public static void main(String[] args) {
        int port = 8080;

        Server server = new Server(port);
        try {

            ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS );
            context.setContextPath( "/" );

            // http://localhost:8080/
            context.addServlet(new ServletHolder( new AllData() ),"/");


            HandlerList handlers = new HandlerList( );
            handlers.setHandlers( new Handler[] { context } );
            server.setHandler( handlers );

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
