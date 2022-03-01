package com.xunmall.example.java.byteCode.agent;

import java.lang.instrument.Instrumentation;

/**
 * @Author: wangyj03
 * @Description:
 * @Date: 2022/2/18 16:03
 */
public class SkyWalkingAgent {

    public static void premain(String args, Instrumentation instrumentation){
        System.out.println("Hello ,this is a SkyWalking Handbook");
    }


}
