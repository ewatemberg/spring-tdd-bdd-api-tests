package com.miempresa.tests;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalculadoraStepDefs {

    private Response response;

    @Given("la API está disponible")
    public void la_api_esta_disponible() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
    }

    @When("hago una suma con {int} y {int}")
    public void hago_una_suma_con_y(Integer a, Integer b) {
        response = RestAssured
                .given()
                .queryParam("a", a)
                .queryParam("b", b)
                .when()
                .get("/api/calculadora/sumar");
    }

    @Then("el resultado debe ser {int}")
    public void el_resultado_debe_ser(Integer esperado) {
        int resultado = Integer.parseInt(response.getBody().asString());
        assertThat(resultado, equalTo(esperado));
    }
}