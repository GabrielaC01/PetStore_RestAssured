package com.ntt.glue;

import com.ntt.steps.ConsultarOrden;
import io.cucumber.java.en.*;
import net.thucydides.core.annotations.Steps;

public class ConsultarOrdenStepDef {

    @Steps
    ConsultarOrden consultarOrden;

    @Given("estoy en la pagina del store")
    public void estoyEnLaPaginaDelStore() {
    }

    @And("creo una orden con petId {int}, quantity {int}, status {string}")
    public void creoUnaOrden(int petId, int quantity, String status) {
        consultarOrden.crearOrden(petId, quantity, status);
    }

    @When("consulto la orden por el id generado")
    public void consultoLaOrdenPorElIdGenerado() {
        consultarOrden.consultarOrdenPorIdGenerado();
    }

    @Then("el codigo de respuesta de la orden es {int}")
    public void elCodigoDeRespuestaDeLaOrdenEs(int statusCode) {
        consultarOrden.validarCodigoRespuesta(statusCode);
    }

    @And("la orden consultada tiene petId {int}, quantity {int}, status {string}")
    public void validarCamposConsultados(int petId, int quantity, String status) {
        consultarOrden.validarCamposConsultados(petId, quantity, status);
    }

    @And("el campo complete de la orden es true")
    public void validarCompleteTrue() {
        consultarOrden.validarCompleteTrue();
    }
}
