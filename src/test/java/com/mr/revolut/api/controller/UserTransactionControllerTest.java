package com.mr.revolut.api.controller;

import com.google.gson.Gson;
import com.mr.revolut.api.dto.UserTransactionDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserTransactionControllerTest {
    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/api");
        server.setHandler(context);
        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                UserAccountController.class.getCanonicalName() + "," + UserTransactionController.class.getCanonicalName());

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RestAssured.baseURI = "http://localhost/api/transaction";
        RestAssured.port = 8080;
    }

    @AfterAll
    static void afterAll() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferFundsStatusTest() {
        RestAssured.given().contentType(ContentType.JSON).body(new Gson().toJson(new UserTransactionDTO(2L, 1L, "100"))).put("/transfer")
                .then().statusCode(200);
    }

    @Test
    public void transferInsufficientFundsStatusTest() {
        RestAssured.given().contentType(ContentType.JSON).body(new Gson().toJson(new UserTransactionDTO(2L, 1L, "1000"))).put("/transfer")
                .then().statusCode(400);
    }

    @Test
    public void transferNegativeValueStatusTest() {
        RestAssured.given().contentType(ContentType.JSON).body(new Gson().toJson(new UserTransactionDTO(2L, 1L, "-1000"))).put("/transfer")
                .then().statusCode(400);
    }

}