package dds.monedero.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.ingresarDinero(1500);
  }

  @Test
  void PonerMontoNegativo() {
    assertThrows(RuntimeException.class, () -> cuenta.ingresarDinero(-1500));
  }

  @Test
  void MasDeTresDepositos() {
    assertThrows(RuntimeException.class, () -> {
          cuenta.ingresarDinero(1500);
          cuenta.ingresarDinero(456);
          cuenta.ingresarDinero(1900);
          cuenta.ingresarDinero(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(RuntimeException.class, () -> {
          cuenta.setSaldo(90); // OK Salvo lo de las excepciones que hacen super, o sea bleh
          cuenta.retirarDinero(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000() {
    assertThrows(RuntimeException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.retirarDinero(1001);
    });
  }

}