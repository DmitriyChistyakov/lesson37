package ReflectionAndAnnotation;

public class TestClass {
//    private int prioritet;

    public TestClass(){

    }

    @Test (prioritet = "1")
    public void doTest1 (){
//        prioritet = 1;
        System.out.println("Начало теста №1:....... ");
    }

    @Test (prioritet = "2")
    public void doTest2 (){
//        prioritet = 2;
        System.out.println("Начало теста №2:......... ");

    }


    @Test(prioritet = "3")
    public void doTest3 (){
//        prioritet = 3;
        System.out.println("Начало теста №3:...... ");

    }



    @Test(prioritet = "4")
    public void doTest4 (){
//        prioritet = 4;
        System.out.println("Начало теста №4:...... ");

    }



    @Test(prioritet = "5")
    public void doTest5 (){
//        prioritet = 5;
        System.out.println("Начало теста №5:...... ");

    }

    @BeforeSuite (prioritet = "0")
    public void beforeSuite (){
        System.out.println("Начало первоначального теста до выполнения тестов....");
    }



    @AfterSuite(prioritet = "10000")
    public void afterSuite (){
        System.out.println("Начало конечного теста после выполнения ....");
    }




}
