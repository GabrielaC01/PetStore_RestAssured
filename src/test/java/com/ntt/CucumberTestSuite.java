package com.ntt;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"com.ntt"},
        features = "classpath:features",
        tags = "@consultarOrden"   //  ==> Definir el @tag  a ejecutar
)
public class CucumberTestSuite {
}

