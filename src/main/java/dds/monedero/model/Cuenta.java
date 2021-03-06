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
  return movimientos.stream().filter(movimiento -> movimiento.getOperacion());
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

public void retirarDinero(double monto) {
  validarMonto(monto);

  if (monto > saldo) {
  throw new RuntimeException("No puede sacar mas de " + saldo + " $");}

  double limite = limiteDiario - getMontoExtraidoA(LocalDate.now());
  alcanzoLimiteDiario(monto, limite);
  new Movimiento(LocalDate.now(), monto, esDeposito(monto)).agregateA(this);
  }

  private void alcanzoLimiteDiario(double monto, double limite) {
    if (monto > limite) {
      throw new RuntimeException("No puede extraer mas de $ " + limiteDiario
          + " diarios, límite: " + limite);
    }
  }

  public void ingresarDinero(double monto) {
    validarMonto(monto);
    excedioCantDepositosDiarios();
    new Movimiento(LocalDate.now(), monto, esDeposito(monto)).agregateA(this);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return buscarMovimientoPorFecha(fecha)
    .mapToDouble(Movimiento::getMonto)
    .sum();
  }
  
  private Stream<Movimiento> buscarMovimientoPorFecha(LocalDate fecha) {
    return getMovimientos().stream()
    .filter(movimiento -> !movimiento.getOperacion() && movimiento.getFecha().equals(fecha));
  }
  
  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
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
