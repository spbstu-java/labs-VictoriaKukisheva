package lab2;

import lab2.ExampleClass;
import lab2.Repeat;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Class<ExampleClass> clazz = ExampleClass.class;
            ExampleClass instance = clazz.getDeclaredConstructor().newInstance();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Repeat.class)) {
                    Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                    int timesToRepeat = repeatAnnotation.value();

                    method.setAccessible(true);

                    System.out.println("Calling method " + method.getName() + " " + timesToRepeat + " times:");

                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] parameters = createParameters(paramTypes);

                    for (int i = 0; i < timesToRepeat; i++) {
                        method.invoke(instance, parameters);
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Object[] createParameters(Class<?>[] paramTypes) {
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getDefaultValue(paramTypes[i]);
        }

        return params;
    }

    private static Object getDefaultValue(Class<?> type) {
        if (type == int.class) return 0;
        if (type == double.class) return 0.0;
        if (type == boolean.class) return false;
        if (type == String.class) return "default";
        if (type == long.class) return 0L;
        if (type == float.class) return 0.0f;
        if (type == char.class) return ' ';
        if (type == byte.class) return (byte)0;
        if (type == short.class) return (short)0;

        // For another return null
        return null;
    }
}