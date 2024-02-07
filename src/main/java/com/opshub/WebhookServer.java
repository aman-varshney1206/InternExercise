package com.opshub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Spark;

public class WebhookServer {

    private static final Logger logger = LogManager.getLogger(WebhookServer.class);

    public static void main(String[] args) {
        Spark.port(8080); // Choose a port of your preference

        Spark.post("/webhook-endpoint", WebhookServer::handleWebhookEvent);

        logger.info("Webhook server started on port 8080");
    }

    private static String handleWebhookEvent(Request request, Response response) {
        String payload = request.body();
        logger.info("Received Azure DevOps event: " + payload);

        // Add your custom logic here to process the Azure DevOps event

        response.status(200);
        return "Webhook received successfully!";
    }
}

