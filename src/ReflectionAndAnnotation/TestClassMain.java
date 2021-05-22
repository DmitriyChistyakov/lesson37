package ReflectionAndAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Создать класс, который может выполнять «тесты»,
 * в качестве тестов выступают классы с наборами методов с аннотациями @Test.
 * <p
 * Для этого у него должен быть статический метод start(),
 * которому в качестве параметра передается или объект типа Class, или имя класса.
 * <p
 * Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite,
 * если такой имеется, далее запущены методы с аннотациями @Test,
 * а по завершению всех тестов – метод с аннотацией @AfterSuite.
 * К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10),
 * в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый,
 * то порядок не имеет значения.
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре,
 * иначе необходимо бросить RuntimeException при запуске «тестирования».
 */


public class TestClassMain {



    public static void main(String[] args)  {

        String beforeSuite = "BeforeSuite";
        String afterSuite = "AfterSuite";
        String test = "Test";
        TestClass testClass = new TestClass();

        Method[] methods = testClass.getClass().getDeclaredMethods();
        int count = methods.length;
        String[] annotations = new String[count];
        String[] methodsSecuence = new String[count];

        String[] annotationsValue = new String[count];
        int[] annotationsValues = new int[count];


        for (int i = 0; i < count; i++) {
            Annotation[] tempAnnotation = methods[i].getAnnotations();
            annotations[i] = tempAnnotation[0].toString();
            int startPositionAnnotation = annotations[i].indexOf('.');
            int endPositionAnnotation = annotations[i].indexOf('(');
            annotations[i] = annotations[i].substring(startPositionAnnotation + 1, endPositionAnnotation);

            String tempMethodSecuence = String.format(String.valueOf(methods[i]));
            methodsSecuence[i] = tempMethodSecuence;
            int startPositionMethod = tempMethodSecuence.lastIndexOf('.');
            int endPositionMethod = tempMethodSecuence.lastIndexOf('(');
            methodsSecuence[i] = methodsSecuence[i].substring(startPositionMethod + 1, endPositionMethod);


            annotationsValue[i] = tempAnnotation[0].toString();
            int startPositionPrioritet = annotationsValue[i].indexOf('"');
            int endPositionPrioritet = annotationsValue[i].lastIndexOf('"');
            annotationsValue[i] = annotationsValue[i].substring(startPositionPrioritet + 1, endPositionPrioritet);
            annotationsValues[i] = Integer.parseInt(String.valueOf(annotationsValue[i]));

//            System.out.println(annotationsValues[i]);
//            System.out.println("Аннотация: " + annotations[i]);
//            System.out.println("Имя метода: " + methodsSecuence[i]);

        }


        if (doHaveAnnotationBeforeAndAfterSuiteInTestClass(annotations, beforeSuite) |
                doHaveAnnotationBeforeAndAfterSuiteInTestClass(annotations, afterSuite)) {
            throw new RuntimeException();
        } else {
            System.out.println("Условие по количеству beforeSuite и afterSuite выполнено");
        }


        /** Фильтрация методов в зависимости от приоритета */

        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (annotationsValues[i] > annotationsValues[j]) {

                    int tempannotationsValues = annotationsValues[i];
                    annotationsValues[i] = annotationsValues[j];
                    annotationsValues[j] = tempannotationsValues;

                    String temp = methodsSecuence[i];
                    methodsSecuence[i] = methodsSecuence[j];
                    methodsSecuence[j] = temp;

                    String tempAnn = annotations[i];
                    annotations[i] = annotations[j];
                    annotations[j] = tempAnn;
                }

            }
        }

/** Проверка фильтрации методов Вывод списка в консоль*/

        for (int i = 0; i < count; i++) {
            System.out.println();
            System.out.println(annotationsValues[i]);
            System.out.println("Аннотация: " + annotations[i]);
            System.out.println("Имя метода: " + methodsSecuence[i]);

        }
    }


    public static boolean doHaveAnnotationBeforeAndAfterSuiteInTestClass(String[] annotations, String findAnnotation) {
        int counter = 0;
        for (String annotation : annotations) {
            if (annotation.equals(findAnnotation)) {
                counter++;
            }
        }
        return (counter != 1);

    }


    public static void start(Object o, String methodsSecuence) {
        Method startedMethod;

        try {
            startedMethod = o.getClass().getDeclaredMethod(methodsSecuence);
            startedMethod.invoke(o);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
