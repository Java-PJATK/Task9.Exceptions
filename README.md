# Task9.Exceptions

June 1, 2024
Deadline: Jun 13 (inclusive)

# Task 9

Create two custom exception classes:

1. `NotEnoughGas` — checked;
2. `Explosion` — unchecked.

Then create a class `Car` with:

1. Fields of type `int` corresponding to the amount of fuel in the tank and the number of kilometers travelled. The fields are initialized with the values `35` and `0`, respectively.
2. Method `public void fill()`, which:
   * with a probability of 10%, throws an exception of type `Explosion` (which we do _not_ handle).
   * if there was no explosion, fills the tank with an additional random number of litres of gasoline (from the range [15, 35]) and prints information on the current fuel level.

3. Method `public void drive100km() throws NotEnoughGas`, which:
   * throws an exception of type `NotEnoughGas`, if the fuel level is below 10 litres.
   * if there is enough fuel, consumes 10 litres of gasoline and increments the number of kilometers travelled by 100 km.
   * prints information on the total number of kilometers travelled and the current fuel level.

**Note**: Messages about exceptions printed by the JVM go to the error stream. To see all messages issued by both the JVM and our program in the correct order, the program should print not to the standard output but rather to the error stream; therefore, use `System.err.println` instead of `System.out.println`.

For example, the following program:

```java
public class Excepts {
    public static void main(String[] args) {
        Car car = new Car();
        while (true) {
            try {
                car.drive100km();
            } catch (NotEnoughGas e) {
                System.err.println(e.getMessage());
                car.fill();
            }
        }
    }
}
```

should print something like

```
100km driven, 25 liters left
200km driven, 15 liters left
300km driven, 5 liters left
Only 5 liters. Must fill the tank
After filling 35
400km driven, 25 liters left
500km driven, 15 liters left
600km driven, 5 liters left
Only 5 liters. Must fill the tank
After filling 23
700km driven, 13 liters left
800km driven, 3 liters left
Only 3 liters. Must fill the tank
After filling 20
900km driven, 10 liters left
1000km driven, 0 liters left
Only 0 liters. Must fill the tank
After filling 29
1100km driven, 19 liters left
1200km driven, 9 liters left
Only 9 liters. Must fill the tank
After filling 34
1300km driven, 24 liters left
1400km driven, 14 liters left
1500km driven, 4 liters left
Only 4 liters. Must fill the tank
After filling 19
1600km driven, 9 liters left
Only 9 liters. Must fill the tank
Exception in thread "main" Explosion: BOOOOOOM
    at Car.fill(Excepts.java:21)
    at Excepts.main(Excepts.java:10)
```

## Solution

```java
import java.util.Random;

// Custom checked exception
class NotEnoughGas extends Exception {
    public NotEnoughGas(String message) {
        super(message);
    }
}

// Custom unchecked exception
class Explosion extends RuntimeException {
    public Explosion(String message) {
        super(message);
    }
}

// Car class with required fields and methods
class Car {
    private int fuel;
    private int kilometersTravelled;

    public Car() {
        this.fuel = 35;
        this.kilometersTravelled = 0;
    }

    public void fill() {
        Random rand = new Random();
        if (rand.nextInt(10) == 0) {  // 10% probability of explosion
            throw new Explosion("BOOOOOOM");
        } else {
            int additionalFuel = 15 + rand.nextInt(21);  // Random number between 15 and 35
            this.fuel += additionalFuel;
            System.err.println("After filling " + this.fuel);
        }
    }

    public void drive100km() throws NotEnoughGas {
        if (this.fuel < 10) {
            throw new NotEnoughGas("Only " + this.fuel + " liters. Must fill the tank");
        } else {
            this.fuel -= 10;
            this.kilometersTravelled += 100;
            System.err.println(this.kilometersTravelled + "km driven, " + this.fuel + " liters left");
        }
    }
}

// Main class to test the functionality
public class Excepts {
    public static void main(String[] args) {
        Car car = new Car();
        while (true) {
            try {
                car.drive100km();
            } catch (NotEnoughGas e) {
                System.err.println(e.getMessage());
                car.fill();
            }
        }
    }
}

```
