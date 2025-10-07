package com.ntt.steps;

import com.ntt.model.Orden;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class ConsultarOrden {

    private String URL_BASE = "https://petstore.swagger.io/v2/store/order/";

    public Orden orden;
    private Long createdOrderId;

    public void crearOrden(int petId, int quantity, String status){
        Response response = given()
                .baseUri(URL_BASE)
                .contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .log().all()
                .body("{\n" +
                        "  \"petId\": " + petId + ",\n" +
                        "  \"quantity\": " + quantity + ",\n" +
                        "  \"shipDate\": \"" + java.time.OffsetDateTime.now().toString() + "\",\n" +
                        "  \"status\": \"" + status + "\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post("");

        if (response.statusCode() == 200) {
            createdOrderId = response.jsonPath().getLong("id");
            System.out.println("## El POST creo orderId = " + createdOrderId);
            if (createdOrderId == null || createdOrderId <= 0L) {
                throw new AssertionError("El id generado es inválido: " + createdOrderId);
            }
        } else {
            System.out.println("Fallo creando orden. Status: " + response.statusCode());
            System.out.println(response.asString());
        }
    }

    public void consultarOrdenPorIdGenerado(){
        if (createdOrderId == null) {
            throw new AssertionError("No se generó orderId en el paso anterior.");
        }

        System.out.println("## El GET a: " + URL_BASE + createdOrderId);

        Response response = given()
                .baseUri(URL_BASE)
                .accept(ContentType.JSON)
                .relaxedHTTPSValidation()
                .log().all()
                .when()
                .get("" + createdOrderId);

        if (response.statusCode() == 200) {
            orden = response.as(Orden.class);
            if (orden.getId() == null || !orden.getId().equals(createdOrderId)) {
                throw new AssertionError("El id del GET no coincide con el del POST. GET=" +
                        orden.getId() + " POST=" + createdOrderId);
            }
            listarOrden(orden);
        } else if (response.statusCode() == 404) {
            System.out.println("Orden no encontrada: " + response.asString());
        }
    }

    public void listarOrden(Orden o){
        System.out.println("@@@@@@@@@@@ ORDEN @@@@@@@@@@@");
        System.out.println("id       : " + o.getId());
        System.out.println("petId    : " + o.getPetId());
        System.out.println("quantity : " + o.getQuantity());
        System.out.println("shipDate : " + o.getShipDate());
        System.out.println("status   : " + o.getStatus());
        System.out.println("complete : " + o.getComplete());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public void validarCodigoRespuesta(int statusCode){
        assertThat(lastResponse().statusCode(), is(statusCode));
    }

    public void validarCamposConsultados(int petId, int quantity, String status){
        assertThat("id debe ser > 0", orden.getId(), greaterThan(0L));
        assertThat(orden.getPetId(), is(petId));
        assertThat(orden.getQuantity(), is(quantity));
        assertThat(orden.getStatus(), is(status));
        assertThat("id no debe ser null", orden.getId(), notNullValue());
    }

    public void validarCompleteTrue(){
        assertThat(orden.getComplete(), is(true));
    }
}
