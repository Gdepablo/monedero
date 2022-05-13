package dds.monedero.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Cuenta {
  
  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private int cantDepositosDiarios = 3;
  int limiteDiario = 1000;

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

public boolean esDeposito(double monto) {
  if(monto > 0)
  return true; 
  return false;
  };

  public void excedioCantDepositosDiarios() {
  if (filtrarDepositos().count() >= 3) {
  throw new RuntimeException("Ya excedio los " + cantDepositosDiarios + " depositos diarios");}
  }

public void realizarMovimiento(double monto) {
  excedioCantDepositosDiarios();

  if (monto < 0 && (getSaldo() - monto) < 0) {
  throw new RuntimeException("No puede sacar mas de " + saldo + " $");}
  double limite = limiteDiario - getMontoExtraidoA(LocalDate.now());
  alcanzoLimiteDiario(monto, limite);
  saldo+=monto;
  new Movimiento(LocalDate.now(), monto, esDeposito(monto)).agregateA(this);
  }

  private void alcanzoLimiteDiario(double monto, double limite) {
    if (-1 * monto > limite) {
      throw new RuntimeException("No puede extraer mas de $ " + limiteDiario
          + " diarios, límite: " + limite);
    }
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
    this.saldo = saldo; 
  }
}
