package Game.Enemy;

import java.util.Random;

public class EnemyFactory {

    private static Random rand = new Random();

    public static Enemy createRandomEnemy() {
        switch(rand.nextInt(2)) {
            case 0:
                return new Fly();

            case 1:
                return new Fly();

            default:
                throw new IllegalStateException();
        }
    }
}