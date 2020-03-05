package com.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 查找包含指定字符串的句子
 *
 * @author hadoop
 */
public class Search {
    public static class Map extends Mapper<Object, Text, Text, Text> {
        private static final String word = "月";
        private FileSplit fileSplit;

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            fileSplit = (FileSplit) context.getInputSplit();
            String fileName = fileSplit.getPath().getName().toString();
            //按句号分割
            StringTokenizer st = new StringTokenizer(value.toString(), "。");
            while (st.hasMoreTokens()) {
                String line = st.nextToken().toString();
                System.out.println("+++++++++++++++++++++------------------" + line);
                if (line.indexOf(word) >= 0) {
                    context.write(new Text(fileName), new Text(line));
                    System.out.println("+++++++++++++++" + line);
                }
            }
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String lines = "";
            for (Text value : values) {
                lines += value.toString() + "---|---";
            }
            context.write(key, new Text(lines));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //检查运行命令  
        if (args.length != 2) {
            System.err.println("Usage search <int> <out>");
            System.exit(2);
        }
        //配置作业名  
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Search");
        //配置作业各个类  
        job.setJarByClass(Search.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}