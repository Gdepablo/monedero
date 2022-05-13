package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private double monto;
  private boolean esDeposito;

  public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha); // ??? Ni siquiera valida, y no sólo es poco declarativo sino que poco intuitivo
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha); // ^ x2
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha); //Debería validar, esto no valida un choto
  }

  public boolean isDeposito() {
    return esDeposito;
  }

  public boolean isExtraccion() {
    return !esDeposito;
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, esDeposito); // Como dije antes, es responsabilidad de esta class y no de cuenta...creo.
  }

  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else { // Lógica repetida, se podría crear una función llamada modificarAhorros o algo así y , de querer restar, le paso un número negativo
      return cuenta.getSaldo() - getMonto();
    }
  }
}
