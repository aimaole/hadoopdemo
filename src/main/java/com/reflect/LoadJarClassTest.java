package reflect;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 动态载入指定类
 */
public class LoadJarClassTest {
	
	@Test
	public void testLoadClass() throws Exception{
		/*动态载入指定类*/
		File file=new File("D:/test");//类路径(包文件上一层)
		URL url=file.toURI().toURL();
		ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类载入器
		//import com.sun.org.apache.bcel.internal.util.ClassLoader;
		//ClassLoader classLoader = new ClassLoader(new String[]{""});//类路径
		Class<?> cls=loader.loadClass("loadjarclass.TestTest");//载入指定类。注意一定要带上类的包名
		Object obj=cls.newInstance();//初始化一个实例
		Method method=cls.getMethod("printString",String.class,String.class);//方法名和相应的參数类型
		Object o=method.invoke(obj,"chen","leixing");//调用得到的上边的方法method
		System.out.println(String.valueOf(o));//输出"chenleixing"
		
		/*动态载入指定jar包调用当中某个类的方法*/
		file=new File("D:/test/commons-lang3.jar");//jar包的路径
		url=file.toURI().toURL();
		loader=new URLClassLoader(new URL[]{url});//创建类载入器
		cls=loader.loadClass("org.apache.commons.lang3.StringUtils");//载入指定类，注意一定要带上类的包名
		method=cls.getMethod("center",String.class,int.class,String.class);//方法名和相应的各个參数的类型
		o=method.invoke(null,"chen",Integer.valueOf(10),"0");//调用得到的上边的方法method(静态方法，第一个參数能够为null)
		System.out.println(String.valueOf(o));//输出"000chen000","chen"字符串两边各加3个"0"字符串
	}

}