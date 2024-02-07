package lab14;

import lab14lib.Generator;
public class SawToothGenerator  implements Generator {

    private final int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    public double next() {
        state = (state + 1) % period;
        return normalize(state);
    }

    private double normalize(int x) {
        return 2.0 * x / period - 1.0;
    }
}
