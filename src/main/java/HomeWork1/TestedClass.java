package HomeWork1;

public class TestedClass {

    @BeforeSuite
    public static void before() {
        System.out.println("before");
    }

    @Test(priority = 3)
    public void testA() {
        System.out.println("test A priority 3");
    }

    @Test(priority = 8)
    public void testB() {
        System.out.println("test B priority 8");
    }

    @Test
    public void testC() {
        System.out.println("test C default priority 5");
    }

    @Test(priority = 1)
    public void testD() {
        System.out.println("test D priority 1");
    }

    @AfterSuite
    public static void after() {
        System.out.println("after");
    }
}