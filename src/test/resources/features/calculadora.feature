Feature: Calculadora API

  Scenario: Sumar dos números
    Given la API está disponible
    When hago una suma con 5 y 3
    Then el resultado debe ser 8