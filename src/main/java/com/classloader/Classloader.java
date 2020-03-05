package jav;

import com.google.common.base.Stopwatch;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Classloader {
    public static void main(String[] args) {
        // 动态加载jar
//        try {
            // 包路径定义
//            URL urls = new URL("file:/F:\\maomao\\bigdata\\hadoop\\lib\\guava-18.0.jar");
////            URL urls = new URL("file:/"+System.getProperty("user.dir")+"\\lib\\guava-18.0.jar");
//            //GetPI.class
//            URLClassLoader urlLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//            Class<URLClassLoader> sysclass = URLClassLoader.class;
//            Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
//            method.setAccessible(true);
//            method.invoke(urlLoader, urls);
//            /*
//             * 实例化一个对象 ，这个类(yerasel.GetPI)可以是串行化传来的多个类的一个，运行时才知道需要哪个类
//             * 或者从xml 配置文件中获得字符串
//             * 例如
//             * String className = readfromXMlConfig;//从xml 配置文件中获得字符串
//             * class c = Class.forName(className);
//             * factory = (ExampleInterface)c.newInstance();
//             * 上面代码已经不存在类名称，它的优点是，无论Example类怎么变化，上述代码不变，
//             * 甚至可以更换Example的兄弟类Example2 , Example3 , Example4……，只要他们继承ExampleInterface就可以。
//             */
//            // 输入类名
//            String className = "com.google.common.base.Stopwatch";
//            Class<?> tidyClass = urlLoader.loadClass(className);
//            Object obj = tidyClass.newInstance();
//            System.out.println(obj.getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
////            // 被调用函数的参数
////            Class[] parameterTypes = {};
////            Method method2 = tidyClass.getDeclaredMethod("Output", parameterTypes);
////            method2.invoke(obj, null);
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//        }
loadJar("F:\\maomao\\bigdata\\hadoop\\lib");
        Stopwatch ss = new Stopwatch();
        System.out.println(ss.getClass().getProtectionDomain().getCodeSource().getLocation().getFile());

    }

    public static void loadJar(String path) {
        // 系统类库路径
        File libPath = new File(path);

        // 获取所有的.jar和.zip文件
        File[] jarFiles = libPath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar") || name.endsWith(".zip");
            }
        });

        if (jarFiles != null) {
            // 从URLClassLoader类中获取类所在文件夹的方法
            // 对于jar文件，可以理解为一个存放class文件的文件夹
            Method method = null;
            try {
                method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            boolean accessible = method.isAccessible();     // 获取方法的访问权限
            try {
                if (accessible == false) {
                    method.setAccessible(true);     // 设置方法的访问权限
                }
                // 获取系统类加载器
                URLClassLoader classLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();

                for (File file : jarFiles) {
                    try {
                        URL url = file.toURI().toURL();
                        method.invoke(classLoader, url);
                        System.out.println("读取jar文件成功" + file.getName());
                    } catch (Exception e) {
                        System.out.println("读取jar文件失败");
                    }
                }
            } finally {
                method.setAccessible(accessible);
            }
        }
    }
}

