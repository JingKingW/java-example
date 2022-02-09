package com.xunmall.example.java.jmh;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 *
 * @author wangyanjing
 * @date 2020/2/3
 */
@State(Scope.Benchmark)
public class BenchmarkState {
    volatile double x = Math.PI;
}
