package com.miempresa.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class CalculadoraApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8082; // o el puerto que exponga tu contenedor
    }

    @Test
    public void testSuma() {
        RestAssured
            .given()
                .queryParam("a", 5)
                .queryParam("b", 3)
            .when()
                .get("/api/calculadora/sumar")
            .then()
                .statusCode(200)
                .body("resultado", equalTo(8));
    }
}