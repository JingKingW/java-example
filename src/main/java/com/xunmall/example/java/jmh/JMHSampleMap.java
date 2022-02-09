package com.xunmall.example.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyj03@zenmen.com
 * @description
 * @date 2021/8/10 9:56
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class JMHSampleMap {
    static Map hashMap = new HashMap();
    static Map syncHashMap = Collections.synchronizedMap(new HashMap<>());
    static Map concurrentHashMap = new ConcurrentHashMap();

    @Setup
    public void setUp() {
        for (int i = 1; i < 10000; i++) {
            hashMap.put(Integer.toString(i), Integer.toString(i));
            syncHashMap.put(Integer.toString(i), Integer.toString(i));
            concurrentHashMap.put(Integer.toString(i), Integer.toString(i));
        }
    }

    @Benchmark
    public void getHashMap() {
        hashMap.get(10);
    }

    @Benchmark
    public void getSyncHashMap() {
        syncHashMap.get(10);
    }

    @Benchmark
    public void getConcurrentHashMap() {
        concurrentHashMap.get(10);
    }

    @Benchmark
    public void hashMapSize(){
        hashMap.size();
    }

    @Benchmark
    public void syncHashMapSize(){
        syncHashMap.size();
    }

    @Benchmark
    public void concurrentHashMapSize(){
        concurrentHashMap.size();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(JMHSampleMap.class.getSimpleName()).forks(1).build();
        new Runner(opt).run();
    }
}
