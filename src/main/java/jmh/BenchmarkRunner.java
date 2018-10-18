package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BenchmarkRunner {


    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @Warmup(time = 1, iterations = 3)
//    @Fork(value = 1)
//    @Measurement(time = 2, iterations = 2)
//    public void noOptional() {
//        Object1 obj1 = new Object1();
//        obj1.setObject2(new Object2());
//        obj1.getObject2().setName("name");
//
//        for (int i = 0; i < 10000; i++) {
//            // doSmth does nothing. Just using it in order to prevent compilation/JIT dead code optimisation.
//            doSmth(obj1 != null && obj1.getObject2() != null && obj1.getObject2().getName() != null ? obj1.getObject2().getName() : "");
//        }
//    }
//
//    @Benchmark
//    @Warmup(time = 1, iterations = 3)
//    @Fork(value = 1)
//    @Measurement(time = 2, iterations = 2)
//    @BenchmarkMode(Mode.Throughput)
//    public void withOptional() {
//        Object1 obj1 = new Object1();
//        obj1.setObject2(new Object2());
//        obj1.getObject2().setName("name");
//
//        for (int i = 0; i < 10000; i++) {
//            // doSmth does nothing. Just using it in order to prevent compilation/JIT dead code optimisation.
//            doSmth(Optional.ofNullable(obj1.getObject2().getName()).orElse(""));
//        }
//    }

    @State(Scope.Benchmark)
    public static class MyState {

        private List<Long> input = new ArrayList<>();

        {
            for (int i = 0; i < 100; i++) {
                input.add((long) i);
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(time = 1, iterations = 2)
    @Fork(value = 1)
    @Measurement(time = 2, iterations = 2)
    public void loop(MyState state) {
        long result = 0;
        for (int i = 0; i < 100; i++) {
            result += state.input.get(i);
        }

        doSmth(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(time = 1, iterations = 2)
    @Fork(value = 1)
    @Measurement(time = 2, iterations = 2)
    public void loopForEach(MyState state) {
        long result = 0;

        for (Long x : state.input){
            result += x;
        }

        doSmth(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(time = 1, iterations = 2)
    @Fork(value = 1)
    @Measurement(time = 2, iterations = 2)
    public void stream(MyState state) {

        long result = state.input.stream().reduce((x, y) -> x + y).get();

        doSmth(result);
    }

    public void doSmth(long smth) {
        if (smth > 0) {
            // do nothing
        } else {
            // do nothing
        }
    }

    public void doSmth(String name) {
        if (name.equals("")) {
            // do nothing
        } else {
            // do nothing
        }
    }

}

class Object1 {
    private Object2 object2;

    public Object2 getObject2() {
        return object2;
    }

    public void setObject2(Object2 object2) {
        this.object2 = object2;
    }
}

class Object2 {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
