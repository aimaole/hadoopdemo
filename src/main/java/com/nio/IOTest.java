package nio;

import java.io.*;

public class IOTest {
    public static void main(String[] args) throws IOException {
        InputStream fileInputStream = new FileInputStream("F://software/系统/CentOS-7-x86_64-DVD-1708.iso");
        OutputStream fileOutputStream = new FileOutputStream("E://CentOS-7-x86_64-DVD-1708.iso");

//        int length  = fileInputStream.available();
        byte[] bytes = new byte[10240];
        while (true) {
            int read = fileInputStream.read(bytes);
            if (read == -1) {
                break;
            }
            fileOutputStream.write(bytes);

        }

        fileOutputStream.close();
        fileInputStream.close();


    }
}
