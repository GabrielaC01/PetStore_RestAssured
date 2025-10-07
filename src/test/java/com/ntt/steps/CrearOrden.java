package com.ntt.steps;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class CrearOrden {

    private static final String CREATE_ORDER = "https://petstore.swagger.io/v2/store/order";

    @Step("Crear orden petId={0}, quantity={1}, status={2} en PetStore")
    public void crearOrden(int petId, int quantity, String status){
        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .body("{\n" +
                        "  \"petId\": " + petId + ",\n" +
                        "  \"quantity\": " + quantity + ",\n" +
                        "  \"status\": \"" + status + "\",\n" +
                        "  \"complete\": true,\n" +
                        "  \"shipDate\": \"" + java.time.OffsetDateTime.now().toString() + "\"\n" +
                        "}")
                .log().all()
                .post(CREATE_ORDER)
                .then()
                .log().all();
    }

    @Step("Validar código de respuesta {0}")
    public void validarCodigoRespuesta(int statusCode) {
        restAssuredThat(resp -> resp.statusCode(statusCode));
    }

    @Step("Validar que la orden creada tiene id generado")
    public void validarIdGenerado() {
        restAssuredThat(resp -> resp.body("id", notNullValue()));
        restAssuredThat(resp -> resp.body("id", greaterThan(0L)));
        Long id = SerenityRest.lastResponse().jsonPath().getLong("id");
        System.out.println("Order ID generado: " + id);
    }

    @Step("Validar campos petId, quantity, status")
    public void validarCampos(int petId, int quantity, String status) {
        restAssuredThat(resp -> resp.body("petId", equalTo(petId)));
        restAssuredThat(resp -> resp.body("quantity", equalTo(quantity)));
        restAssuredThat(resp -> resp.body("status", equalTo(status)));
    }

    @Step("Validar complete=true")
    public void validarCompleteTrue() {
        restAssuredThat(resp -> resp.body("complete", equalTo(true)));
    }
}
