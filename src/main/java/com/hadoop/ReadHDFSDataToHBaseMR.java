package com.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.junit.runner.Result;

/**
 * 需求：读取HDFS上的数据。插入到HBase库中
 * 这是MR的另一种写法
 */
public class ReadHDFSDataToHBaseMR extends Configured implements Tool {

    @Override
    public int run(String[] arg0) throws Exception {

        Configuration config = HBaseConfiguration.create();
        //告诉它我们的hbase在哪
        config.set("hbase.zookeeper.quorum", "hadoop02:2181,hadoop03:2181");
        config.set("fs.defaultFS", "hdfs://myha01/");
        //注意要把这两个配置文件下载下来，放到自己建的config包下面
        config.addResource("config/core-site.xml");
        config.addResource("config/hdfs-site.xml");

        System.setProperty("HADOOP_USER_NAME", "hadoop");
        Job job = Job.getInstance(config, "ReadHDFSDataToHBaseMR");
        job.setJarByClass(ReadHDFSDataToHBaseMR.class);

        job.setMapperClass(HBaseMR_Mapper.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 设置数据读取组件(因为是读取HDFS，相当于我们没改)
        job.setInputFormatClass(TextInputFormat.class);
        // 设置数据的输出组件，第一个参数就是表名,第二个参数就是ReduceTask,这个方法会做两个事
        // 1：改掉我们的数据输出组件 2：设置一个表名并获取到Htable对象 3：调用我们reduce阶段我们输出的所有put对象 4:自动往HBase中去插入
        TableMapReduceUtil.initTableReducerJob("student", HBaseMR_Reducer.class, job);
        //TableMapReduceUtil.initTableReducerJob("student", HBaseMR_Reducer.class, job, null, null, null, null, false);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Put.class);

//		FileInputFormat.addInputPath(job, new Path("E:\\bigdata\\hbase\\student\\input"));
        FileInputFormat.addInputPath(job, new Path("/student/input/"));

        boolean isDone = job.waitForCompletion(true);

        return isDone ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {

        int run = ToolRunner.run(new ReadHDFSDataToHBaseMR(), args);

        System.exit(run);
    }


    public static class HBaseMR_Mapper extends Mapper<LongWritable, Text, Text, NullWritable> {

        /**
         * 每次读取一行数据
         * <p>
         * Put  ： 构造一个put对象的时候，需要
         * put 'stduent','95001','cf:name','liyong'
         * <p>
         * <p>
         * name:huangbo
         * age:18
         * <p>
         * name:xuzheng
         */
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {

            context.write(value, NullWritable.get());

        }
    }

    //注意这里是有变动的,是 extends TableReducer，TableReducer<KEYIN,VALUEIN,KEYOUT>是抽象的继承了
    // Reducer<KEYIN,VALUEIN,KEYOUT,Mutation>,Mutation的实现类有四个分别是Append,Delete,Increment,Put，
    //因为我们是要插入到HBase，所以我们要写一堆的Put对象，就需要传入Put对象就行，别的不用管
    public static class HBaseMR_Reducer extends TableReducer<Text, NullWritable, NullWritable> {

        /**
         * key  =读取到的一行数据=  95011,包小柏,男,18,MA
         * <p>
         * 95001:  rowkey
         * 包小柏 : name
         * 18 : age
         * 男  ： sex
         * MA : department
         * <p>
         * column family :  cf
         */
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

            String[] split = key.toString().split(",");

            Put put = new Put(split[0].getBytes());

            put.addColumn("info".getBytes(), "name".getBytes(), split[1].getBytes());
            put.addColumn("info".getBytes(), "sex".getBytes(), split[2].getBytes());
            put.addColumn("info".getBytes(), "age".getBytes(), split[3].getBytes());
            put.addColumn("info".getBytes(), "department".getBytes(), split[4].getBytes());

            context.write(NullWritable.get(), put);
        }
    }
}