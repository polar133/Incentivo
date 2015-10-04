package com.monkeycoders.incentavo.incentivo.bus;

        import com.squareup.otto.Bus;

public class AndroidBus {
    private static final Bus BUS = new Bus();

    private AndroidBus() {
        // No instances.
    }

    public static Bus getInstance() {
        return BUS;
    }
}
