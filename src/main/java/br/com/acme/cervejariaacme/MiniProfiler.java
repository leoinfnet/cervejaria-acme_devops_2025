package br.com.acme.cervejariaacme;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class MiniProfiler {
    private long startTime;
    private long startCpuTime;
    private final OperatingSystemMXBean osBean;
    public MiniProfiler() {
        this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }
    public void start() {
        startTime = System.nanoTime();
        startCpuTime = osBean.getProcessCpuTime();
    }

    public void stop(String label) {
        long endTime = System.nanoTime();
        long endCpuTime = osBean.getProcessCpuTime();

        long wallTimeMs = (endTime - startTime) / 1_000_000;
        long cpuTimeMs = (endCpuTime - startCpuTime) / 1_000_000;

        System.out.println("==== PROFILER: " + label + " ====");
        System.out.println("Tempo total (wall-clock): " + wallTimeMs + " ms");
        System.out.println("Tempo de CPU do processo: " + cpuTimeMs + " ms");
        System.out.println("Uso de CPU relativo: " + percent(cpuTimeMs, wallTimeMs) + " %");
        System.out.println("==============================");
    }

    private double percent(long cpuMs, long wallMs) {
        if (wallMs == 0) return 0;
        return (cpuMs * 100.0) / wallMs;
    }
    public static void main(String[] args) {
        MiniProfiler p = new MiniProfiler();
        p.start();

        for (int i = 0; i < 50_000_000; i++) {
            Math.sqrt(i); // alguma coisa só pra gastar CPU
        }

        p.stop("Loop pesado");
    }
}
