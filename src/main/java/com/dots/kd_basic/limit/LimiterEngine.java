package com.dots.kd_basic.limit;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class LimiterEngine {

    public static class LimiterEngineBuilder {
        private long id;

        public LimiterEngineBuilder id(long id) {
            this.id = id;
            return this;
        }

        public LimiterEngine ofMillis(long operPerMs) {
            return new LimiterEngine(this.id, operPerMs, Duration.ofMillis(1));
        }

        public LimiterEngine ofSec(long operPerSc) {
            return new LimiterEngine(this.id, operPerSc, Duration.ofSeconds(1));
        }

        public LimiterEngine ofMin(long operPerMn) {
            return new LimiterEngine(this.id, operPerMn, Duration.ofMinutes(1));
        }
    }

    @Getter
    private static class EngineParam {
        private final long cp;
        private final long ns;

        EngineParam(long permits, Duration period) {
            this.ns = period.toNanos() / permits;
            this.cp = permits;
        }
    }

    @Getter
    @Setter
    private static class EngineState {
        private long tk;
        private long rf;

        EngineState(EngineParam prm, long ns) {
            this.rf = ns;
            this.tk = prm.getCp();
        }

        EngineState(EngineState oth) {
            this.rf = oth.rf;
            this.tk = oth.tk;
        }

        void refill(EngineParam prm, long ns) {
            long rf = ns - this.rf;
            if (rf <= prm.getNs()) {
                return;
            }

            long tk = rf / prm.getNs();
            this.tk = Math.min(prm.getCp(), this.tk + tk);
            this.rf += tk * prm.getNs();
        }
    }

    public static LimiterEngineBuilder builder() {
        return new LimiterEngineBuilder();
    }

    private final AtomicReference<EngineState> bs;
    private final EngineParam bp;
    
    @Getter
    private final long id;

    private LimiterEngine(long id, long permits, Duration period) {
        this.id = id;
        this.bp = new EngineParam(permits, period);
        this.bs = new AtomicReference<>(new EngineState(this.bp, System.nanoTime()));
    }

    public boolean tryAcquire() {
        for (; ; ) {
            long cns = System.nanoTime();
            EngineState p = bs.get();
            EngineState n = new EngineState(p);

            n.refill(this.bp, cns);

            if (n.getTk() < 1L) {
                return false;
            } else {
                n.setTk(n.getTk() - 1L);
                if (bs.compareAndSet(p, n)) {
                    return true;
                }
            }
        }
    }
}
