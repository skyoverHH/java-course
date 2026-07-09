package HomeWork1;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {

    public static void main(String[] args) {
        runTests(TestedClass.class);
    }

    public static void runTests(Class<?> clazz) {
        Method beforeSuite = null;
        Method afterSuite = null;

        Map<Integer, List<Method>> testsByPriority = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuite = prepareSuiteMethod(beforeSuite, method, BeforeSuite.class);
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuite = prepareSuiteMethod(afterSuite, method, AfterSuite.class);
            }

            if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();

                if (priority < 1 || priority > 10) {
                    throw new RuntimeException(
                            "Приоритет у метода " + method.getName() + " должен быть от 1 до 10"
                    );
                }

                testsByPriority
                        .computeIfAbsent(priority, key -> new ArrayList<>())
                        .add(method);
            }
        }

        try {
            Object testObject = clazz.getDeclaredConstructor().newInstance();

            if (beforeSuite != null) {
                beforeSuite.invoke(null);
            }

            for (int priority = 10; priority >= 1; priority--) {
                List<Method> methods = testsByPriority.get(priority);

                if (methods == null) {
                    continue;
                }

                for (Method method : methods) {
                    method.invoke(testObject);
                }
            }

            if (afterSuite != null) {
                afterSuite.invoke(null);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка во время запуска тестов", e);
        }
    }

    private static Method prepareSuiteMethod(Method currentMethod,
                                             Method method,
                                             Class<?> annotationClass) {
        if (currentMethod != null) {
            throw new RuntimeException(
                    "Аннотация @" + annotationClass.getSimpleName() + " может быть только одна"
            );
        }

        if (!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException(
                    "Метод " + method.getName() + " должен быть static"
            );
        }

        return method;
    }
}