# Parking App

Esta aplicación simula un parking con coches (hilos) en Java.

## Clases

### 1. **`Parking`**

La clase principal: el parking. Contiene una lista de plazas y coches.

#### Métodos importantes:

- **`inicializarPlazas()`**: Inicializa las plazas del parking.

- **`inicializarCoches()`**: Inicializa los coches como hilos que extienden de Thread.

- **`comprobarPlazas()`**: Comprueba si hay plazas disponibles en el estacionamiento con la lista de plazas. (Synchronized)

### 2. **`Coche`**

Clase que imita un coche que intenta estacionarse en el parking. Extiende de la superclase Thread.

#### Métodos importantes:

- **`entradaParking()`**: Intenta estacionarse en el parking. Lanza una excepción si no hay plazas disponibles. (Synchronized)

- **`salidaParking()`**: Sale del parking y deja la plaza libre. (Synchronized)

- **`run()`**: El método principal que simula el comportamiento del coche.

### 3. **`Plaza`**

Representa una plaza en el parking.

#### Métodos importantes:

- **`isOcupada()`**: Verifica si la plaza está ocupada.

- **`setOcupada(boolean ocupada)`**: Establece el estado de ocupación de la plaza.

- **`getNumPlaza()`**: Obtiene el número que identifica la plaza.

## Cómo ejecutar el programa

1. Descargate el proyecto

2. Abre el proyecto en tu IDE. (En mi caso IntelliJ)

3. Ejecuta la clase principal **`Main`**

Los coches se inicializarán, y tras esperar 2 segundos, entrarán en un bucle infinito en el que entran y salen del Parking.
Se visualizarán con colores:
    - Azul para inicializar los hilos (coches).
    - Verde cuando entra o sale de una plaza correctamente.
    - Amarillo cuando está intenando entrar.
    - Rojo cuando no encuentra una plaza libre aún.
    