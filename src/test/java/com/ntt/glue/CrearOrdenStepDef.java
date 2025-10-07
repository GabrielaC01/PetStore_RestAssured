package com.ntt.glue;

import com.ntt.steps.CrearOrden;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class CrearOrdenStepDef {

    @Steps
    CrearOrden crearOrden;

    @When("creo la orden con petId {int}, quantity {int}, status {string}")
    public void crearOrden(int petId, int quantity, String status){
        crearOrden.crearOrden(petId, quantity, status);
    }

    @Then("el codigo de respuesta es {int}")
    public void elCodigoDeRespuestaEs(int statusCode) {
        crearOrden.validarCodigoRespuesta(statusCode);
    }

    @And("la orden creada tiene id generado")
    public void validarIdGenerado() {
        crearOrden.validarIdGenerado();
    }

    @And("la orden creada tiene petId {int}, quantity {int}, status {string}")
    public void validarCampos(int petId, int quantity, String status) {
        crearOrden.validarCampos(petId, quantity, status);
    }

    @And("el campo complete es true")
    public void validarCompleteTrue() {
        crearOrden.validarCompleteTrue();
    }
}
