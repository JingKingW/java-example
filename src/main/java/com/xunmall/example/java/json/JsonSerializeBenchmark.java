package com.xunmall.example.java.json;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: WangYanjing
 * @Date: 2019/6/17 17:11
 * @Description:
 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class JsonSerializeBenchmark {

    /**
     * 序列化次数参数
     */

    @Param({"1000", "10000", "100000"})
    private int count;

    private Person p;

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(JsonSerializeBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(0)
                .build();
        Collection<RunResult> results =  new Runner(opt).run();

    }

    @Benchmark
    public void JsonLib() {
        for (int i = 0; i < count; i++) {
            JsonLibUtil.bean2Json(p);
        }
    }

    @Benchmark
    public void Gson() {
        for (int i = 0; i < count; i++) {
            GsonUtil.parseToJson(p);
        }

    }

    @Benchmark
    public void FastJson() {
        for (int i = 0; i < count; i++) {
            FastJsonUtil.convertObjectToJSON(p);
        }
    }

    @Benchmark
    public void Jackson() throws IOException {
        for (int i = 0; i < count; i++) {
            JacksonUtil.BeanToJson(p);
        }
    }


    @Setup
    public void prepare() throws ParseException {
        List<Person> friends = new ArrayList<Person>();
        friends.add(createAPerson("小明", null));
        friends.add(createAPerson("Tony", null));
        friends.add(createAPerson("陈小二", null));
        p = createAPerson("邵同学", friends);
    }

    @TearDown
    public void shutdown() {
    }


    private Person createAPerson(String name, List<Person> friends) throws ParseException {
        Person newPerson = new Person();
        newPerson.setName(name);
        Student student = new Student();
        student.setName("curry");
        student.setAge(30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        student.setDate(dateFormat.parse("1988-9-21"));
        student.setSex("M");
        newPerson.setMan(student);
        newPerson.setAge(24);
        List<String> hobbies = new ArrayList<String>();
        hobbies.add("篮球");
        hobbies.add("游泳");
        hobbies.add("coding");
        Map<String, String> clothes = new HashMap<String, String>();
        clothes.put("coat", "Nike");
        clothes.put("trousers", "adidas");
        clothes.put("shoes", "安踏");
        return newPerson;
    }

}
