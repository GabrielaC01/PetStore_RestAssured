Feature: Crear una orden PetStore

  @crearOrden
  Scenario: Crear una orden
    When creo la orden con petId 501, quantity 1, status "placed"
    Then el codigo de respuesta es 200
    And la orden creada tiene id generado
    And la orden creada tiene petId 501, quantity 1, status "placed"
    And el campo complete es true
