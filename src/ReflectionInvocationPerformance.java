import java.lang.*;

class TestClass {
    public int foo() {
        return 1;
    }
}

public class ReflectionInvocationPerformance {

    public static void main(String[] args) {
        System.out.println(
                "Reflection call: " + Utils.measurePerformance(ReflectionInvocationPerformance::reflectionInvocation) + "ms");
        System.out.println("Reflection call: " + Utils.measurePerformance(ReflectionInvocationPerformance::normalCall) + "ms");

    }

    public static void normalCall() {
        TestClass obj = new TestClass();
        obj.foo();
    }

    public static void reflectionInvocation() {
        try {
            Class<TestClass> cl = (Class<TestClass>) Class.forName("TestClass");
            TestClass obj = (TestClass) cl.getConstructors()[0].newInstance();
            cl.getMethod("foo").invoke(obj);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
