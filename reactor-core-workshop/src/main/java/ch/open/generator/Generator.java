package ch.open.generator;

import java.util.Random;

public class Generator {

    private Random random = new Random();
    private int nrOfCalls;

    public Integer generate() {
        nrOfCalls++;
        return random.nextInt(10);
    }

    public int getNrOfCalls() {
        return nrOfCalls;
    }
}
