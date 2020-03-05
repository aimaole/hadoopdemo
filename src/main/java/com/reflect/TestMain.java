package reflect;

public class TestMain {
    public static void main(String[] args) {
        System.out.println(XYZ.name);

        // 不会初始化静态块
        Class clazz1 = XYZ.class;
        System.out.println("------");
        // 会初始化
        try {
            Class clazz2 = Class.forName("reflect.XYZ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String path = XYZ.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
    }
}

class XYZ {
    public static String name = "luoxn28";

    static {
        System.out.println("xyz静态块");
    }

    public XYZ() {
        System.out.println("xyz构造了");
    }
}