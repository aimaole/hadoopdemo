package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NewIOTest {
    public static void main(String[] args) throws IOException {
        //第一种
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream= null;
        FileChannel channelin= null;
        FileChannel channelout= null;
        try {
            fileInputStream = new FileInputStream("F://software/系统/CentOS-7-x86_64-DVD-1708.iso");
            fileOutputStream = new FileOutputStream("E://CentOS-7-x86_64-DVD-1708.iso");
            channelin = fileInputStream.getChannel();
            channelout = fileOutputStream.getChannel();
            //第一种
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            while (true) {
//                int eof = channelin.read(byteBuffer);
//                if (-1 == eof) {
//                    break;
//                }
//                byteBuffer.flip();
//                channelout.write(byteBuffer);
//                byteBuffer.clear();
//
//            }
            //第二种
            channelout.transferFrom(channelin,0,channelin.size());


        } finally {
            channelin.close();
            channelout.close();
            fileInputStream.close();
            fileOutputStream.close();
        }



    }

}
