package com.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TestWordCount {


    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Test word count");
        job.setJarByClass(TestWordCount.class);
        job.setMapperClass(map.class);
        job.setReducerClass(reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class map extends Mapper<Text, Text, Text, Text> {

        @Override
        protected void cleanup(Mapper<Text, Text, Text, Text>.Context context)
                throws IOException, InterruptedException {
            super.cleanup(context);
        }

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);
        }

        @Override
        protected void setup(Mapper<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

    }

    public static class reduce extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

    public static class partition extends Partitioner<Text, Text> {
        @Override
        public int getPartition(Text text, Text text2, int numPartitions) {
            return 0;
        }
    }

}
