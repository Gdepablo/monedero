package dds.monedero.exceptions;

public class MontoNegativoException extends RuntimeException {
  public MontoNegativoException(String message) {
    super(message); // DELEGAR EN SUPER ES SMELL
  }
}