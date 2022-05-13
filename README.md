## Monedero

### CODE SMELLS
https://github.com/Gdepablo/monedero/tree/f48ed18567a99fe6b3392bc33bdecc97b3b9e22a

+ Carpeta Exceptions se fue; antes que delegar en super uso RuntimeException que recibe un mensaje y listo (además era la clase padre de todas)
+ Poner y sacar repiten validaciones, además poner utiliza mucha composición a.b().c().d() en vez de delegar en otros métodos/clases/funciones
+ Los valores hardcodeados como 1000 etc los cambié por si en un futuro llegan a cambiar (sé que probablemente es sobrediseñar, cosa que no es la idea, pero creo que el cambio no afecta la performance del ejercicio)
  public Cuenta() {
    saldo = 0;
  }
+ Lo de arriba es inútil si per se el saldo ya está inicializado con 0 y sería una particularización del setter de saldo
+ En el new Movimiento en vez de hardcodear el booleano creo una funcion llamada esDeposito que me dice si algo es un deposito basado en si saldo es >0 o menor.
+ fueExtraido() y fueDepositado() repiten lógica y en esencia no me sirven (tampoco se testean)
+ corrijo el agregarMovimiento para que en vez de crear un movimiento y agregarlo, lo reciba por parámetro y lo agregue (Dependency Injection)
+ calcularValor tiene lógica repetida, se podría simplificar
+ TresDepositos era un test incompleto, y no sabía bien cómo testearlo así que le saqué el @test nomás porque abajo había uno que testea lo mismo

En el commit, hay algunos code smells donde yo me equivoqué, ejemplos son
https://github.com/Gdepablo/monedero/blob/f48ed18567a99fe6b3392bc33bdecc97b3b9e22a/src/main/java/dds/monedero/model/Movimiento.java 
Linea 36, 32 y 28; 32 y 28 repiten lógica sí, pero lo de "no valida" no tiene sentido.


  


