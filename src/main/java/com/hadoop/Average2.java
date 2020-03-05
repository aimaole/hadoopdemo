package com.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * 计算每个学生的平均成绩
 *
 * @author hadoop
 */
public class Average2 {

    public static class Map extends Mapper<Object, Text, Text, IntWritable> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // 按行分割数据
            StringTokenizer st = new StringTokenizer(value.toString(), "\n");
            while (st.hasMoreTokens()) {
                // 按空格分割每行数据
                StringTokenizer stl = new StringTokenizer(st.nextToken());
                String name = stl.nextToken();
                String score = stl.nextToken();
                // 名字 分数
                context.write(new Text(name), new IntWritable(Integer.parseInt(score)));
            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int count = 0; // 数量
            int sum = 0; // 总和
            for (IntWritable val : values) {
                count++;
                sum += val.get();
            }
            int average = (int) sum / count; // 计算平均数
            System.out.println(sum + "--" + count + "--" + average);
            context.write(key, new IntWritable(average));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO Auto-generated method stub
        Configuration conf = new Configuration();
        // conf.set("mapred.job.tracker", "localhost:9001");
        conf.addResource("config.xml");
        args = new String[]{"hdfs://localhost:9000/user/hadoop/input/average2_in",
                "hdfs://localhost:9000/user/hadoop/output/average2_out"};
        // 检查运行命令
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage WordCount <int> <out>");
            System.exit(2);
        }
        // 配置作业名
        Job job = new Job(conf, "average1 ");
        // 配置作业各个类
        job.setJarByClass(Average2.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        // Mapper的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}
