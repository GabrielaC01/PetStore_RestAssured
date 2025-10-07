Feature: Consultar una orden

  @consultarOrden
  Scenario Outline: Consultar una orden por id
    Given estoy en la pagina del store
    And creo una orden con petId <petId>, quantity <quantity>, status "<status>"
    When consulto la orden por el id generado
    Then el codigo de respuesta de la orden es <statusCode>
    And la orden consultada tiene petId <petId>, quantity <quantity>, status "<status>"
    And el campo complete de la orden es true

    Examples:
      | petId | quantity | status   | statusCode |
      | 501   | 1        | placed   | 200        |
      | 777   | 2        | approved | 200        |

