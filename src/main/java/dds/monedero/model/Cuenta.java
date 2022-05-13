package dds.monedero.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private int cantDepositosDiarios = 3;

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public Stream<Movimiento> filtrarDepositos() {
   return movimientos.stream().filter(movimiento -> movimiento.isDeposito());
  }
  public void validarMonto(double monto) {
    if (monto <= 0) 
      throw new RuntimeException(monto + ": el monto a ingresar debe ser un valor positivo");
  }

  public void realizarMovimiento(double monto) {
    validarMonto(monto);

    if (filtrarDepositos().count() >= 3) {
      throw new RuntimeException("Ya excedio los " + cantDepositosDiarios + " depositos diarios");
    }

    new Movimiento(LocalDate.now(), monto, true).agregateA(this);
  }
// Poner y sacar repiten lógica (y validaciones)
  public void sacar(double cuanto) {
    if (cuanto <= 0) {
      throw new RuntimeException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - cuanto < 0) {
      throw new RuntimeException("No puede sacar mas de " + getSaldo() + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new RuntimeException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento); // No es tu responsabilidad crear el movimiento, deberías recibirlo por param (Dependency Injection)
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum(); // No es tu responsabilidad, es del Movimiento.
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo; // Va al constructor.
  }


  //Posible god object? Muchos métodos
}
