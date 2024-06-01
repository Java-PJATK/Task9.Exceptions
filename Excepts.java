import java.util.Random;

class NotEnoughGas extends Exception {
    public NotEnoughGas(final String message) {
        super(message);
    }
}

class Explosion extends RuntimeException {
    public Explosion(final String message) {
        super(message);
    }
}

class Car {
    private int fuel;
    private int kilometersTravelled;

    public Car() {
        this.fuel = 35;
        this.kilometersTravelled = 0;
    }

    public void fill() {
        final Random rand = new Random();
        if (rand.nextInt(10) == 0) { // 10% probability of explosion
            throw new Explosion("BOOOOOOM");
        } else {
            final int additionalFuel = 15 + rand.nextInt(21); // Random number between 15 and 35
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

public class Excepts {
    public static void main(final String[] args) {
        final Car car = new Car();
        while (true) {
            try {
                car.drive100km();
            } catch (final NotEnoughGas e) {
                System.err.println(e.getMessage());
                car.fill();
            }
        }
    }
}
