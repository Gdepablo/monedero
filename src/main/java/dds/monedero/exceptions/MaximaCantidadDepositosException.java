package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException(String message) {
    super(message); // Según palabras del mismismo DODAIN, declarar algo y hacer super es estúpido e innecesario
  }

}