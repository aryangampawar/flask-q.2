package com.assignment;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ESBRoute {
    public static void main(String[] args) throws Exception {
        // Load Spring application context with Camel configuration
        ClassPathXmlApplicationContext springContext =
                new ClassPathXmlApplicationContext("camel-context.xml");

        // Get CamelContext from Spring
        SpringCamelContext camelContext = springContext.getBean("camel", SpringCamelContext.class);

        // Start Camel context
        camelContext.start();

        // Keep the application running
        System.out.println("ESB Route is running. Press Ctrl+C to stop.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                camelContext.stop();
                springContext.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // Prevent the main thread from exiting
        Thread.currentThread().join();
    }
}