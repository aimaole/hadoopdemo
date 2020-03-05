package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//https://blog.csdn.net/suifeng3051/article/details/48160753
public class NewIO {
    /**
     * 用java NIO api拷贝文件
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copyFileUseNIO(String src,String dst) throws IOException {
        //声明源文件和目标文件
        FileInputStream fi=new FileInputStream(new File(src));
        FileOutputStream fo=new FileOutputStream(new File(dst));
        //获得传输通道channel
        FileChannel inChannel=fi.getChannel();
        FileChannel outChannel=fo.getChannel();
        //获得容器buffer
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        while(true){
            //判断是否读完文件
            //当没有更多的数据时，拷贝就算完成，此时 read() 方法会返回 -1 ，我们可以根据这个方法判断是否读完。
            int eof =inChannel.read(buffer);
            if(eof==-1){
                break;
            }
            //重设一下buffer的position=0，limit=position
            buffer.flip();
            //开始写
            outChannel.write(buffer);
            //写完要重置buffer，重设position=0,limit=capacity
            buffer.clear();
        }
        inChannel.close();
        outChannel.close();
        fi.close();
        fo.close();
    }

}
