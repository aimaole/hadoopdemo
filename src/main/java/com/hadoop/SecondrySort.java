package com.hadoop;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecondrySort extends Configured implements Tool {

    static class Fruit implements WritableComparable<Fruit>{
        private static final Logger logger = LoggerFactory.getLogger(Fruit.class);
        //日期
        private String date;
        //名字
        private String name;
        //数量
        private Integer sales;

        public Fruit(){
        }
        public Fruit(String date,String name,Integer sales){
            this.date = date;
            this.name = name;
            this.sales = sales;
        }

        public String getDate(){
            return this.date;
        }

        public String getName(){
            return this.name;
        }

        public Integer getSales(){
            return this.sales;
        }

        @Override
        public void readFields(DataInput in) throws IOException{
            this.date = in.readUTF();
            this.name = in.readUTF();
            this.sales = in.readInt();
        }

        @Override
        public void write(DataOutput out) throws IOException{
            out.writeUTF(this.date);
            out.writeUTF(this.name);
            out.writeInt(sales);
        }

        @Override
        public int compareTo(Fruit other) {
            int result1 = this.date.compareTo(other.getDate());
            if(result1 == 0) {
                int result2 = this.sales - other.getSales();
                if (result2 == 0) {
                    double result3 = this.name.compareTo(other.getName());
                    if(result3 > 0) return -1;
                    else if(result3 < 0) return 1;
                    else return 0;
                }else if(result2 >0){
                    return -1;
                }else if(result2 < 0){
                    return 1;
                }
            }else if(result1 > 0){
                return -1;
            }else{
                return 1;
            }
            return 0;
        }

        @Override
        public int hashCode(){
            return this.date.hashCode() * 157 + this.sales + this.name.hashCode();
        }

        @Override
        public boolean equals(Object object){
            if (object == null)
                return false;
            if (this == object)
                return true;
            if (object instanceof Fruit){
                Fruit r = (Fruit) object;
//                if(r.getDate().toString().equals(this.getDate().toString())){
                return r.getDate().equals(this.getDate()) && r.getName().equals(this.getName())
                        && this.getSales() == r.getSales();
            }else{
                return false;
            }
        }

        public String toString() {
            return this.date + " " + this.name + " " + this.sales;
        }

    }

    static class FruitPartition extends Partitioner<Fruit, NullWritable>{
        @Override
        public int getPartition(Fruit key, NullWritable value,int numPartitions){
            return Math.abs(Integer.parseInt(key.getDate()) * 127) % numPartitions;
        }
    }

    public static class GroupingComparator extends WritableComparator{
        protected GroupingComparator(){
            super(Fruit.class, true);
        }

        @Override
        public int compare(WritableComparable w1, WritableComparable w2){
            Fruit f1 = (Fruit) w1;
            Fruit f2 = (Fruit) w2;

            if(!f1.getDate().equals(f2.getDate())){
                return f1.getDate().compareTo(f2.getDate());
            }else{
                return f1.getSales().compareTo(f2.getSales());
            }
        }
    }

    public static class Map extends Mapper<LongWritable, Text, Fruit, NullWritable> {

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String str[] = line.split(" ");
            Fruit fruit = new Fruit(str[0],str[1],new Integer(str[2]));
            //Fruit fruit = new Fruit();
            //fruit.set(str[0],str[1],new Integer(str[2]));
            context.write(fruit, NullWritable.get());
        }
    }

    public static class Reduce extends Reducer<Fruit, NullWritable, Text, NullWritable> {

        public void reduce(Fruit key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            String str = key.getDate() + " " + key.getName() + " " + key.getSales();
            context.write(new Text(str), NullWritable.get());
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        // 判断路径是否存在，如果存在，则删除
        Path mypath = new Path(args[1]);
        FileSystem hdfs = mypath.getFileSystem(conf);
        if (hdfs.isDirectory(mypath)) {
            hdfs.delete(mypath, true);
        }

        Job job = Job.getInstance(conf, "Secondry Sort app");
        // 设置主类
        job.setJarByClass(SecondrySort.class);

        // 输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        // 输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Mapper
        job.setMapperClass(Map.class);
        // Reducer
        job.setReducerClass(Reduce.class);

        // 分区函数
        job.setPartitionerClass(FruitPartition.class);

        // 分组函数
        job.setGroupingComparatorClass(GroupingComparator.class);

        // map输出key类型
        job.setMapOutputKeyClass(Fruit.class);
        // map输出value类型
        job.setMapOutputValueClass(NullWritable.class);

        // reduce输出key类型
        job.setOutputKeyClass(Text.class);
        // reduce输出value类型
        job.setOutputValueClass(NullWritable.class);

        // 输入格式
        job.setInputFormatClass(TextInputFormat.class);
        // 输出格式
        job.setOutputFormatClass(TextOutputFormat.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        int exitCode = ToolRunner.run(new SecondrySort(), args);
        System.exit(exitCode);
    }
}