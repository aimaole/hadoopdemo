package com.hadoop;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ChildParent {
    public static int time = 0;

    /*
     * map将输出分割child和parent，然后正序输出一次作为右表， 反序输出一次作为左表，需要注意的是在输出的value中必须 加上左右表的区别标识。
     */
    public static class SingleRelationMap extends Mapper<LongWritable, Text, Text, Text> {
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String child = new String();// 孩子名字
            String parent = new String();// 父母名字
            String relation = new String();// 左右表标示

            // 输入的一行预处理文本
            StringTokenizer st = new StringTokenizer(value.toString());
            String[] values = new String[2];
            int i = 0;
            while (st.hasMoreTokens()) {
                values[i] = st.nextToken();
                i++;
            }

            if (values[0].compareTo("child") != 0) {
                child = values[0];
                parent = values[1];

                // 输出左表
                relation = "1";
                context.write(new Text(parent), new Text(relation + "+" + child + "+" + parent));
                // 输出右表
                relation = "2";
                context.write(new Text(child), new Text(relation + "+" + child + "+" + parent));
            }
        }

        ;
    }

    public static class SingleRelationReduce extends Reducer<Text, Text, Text, Text> {
        protected void reduce(Text key, Iterable<Text> value, Context context)
                throws IOException, InterruptedException {
            // 输出表头
            if (0 == time) {
                context.write(new Text("grandchild"), new Text("grandparent"));
                time++;
            }

            int grandchildnum = 0;
            String[] grandchild = new String[10];
            int grandparentnum = 0;
            String[] grandparent = new String[10];

            Iterator ite = value.iterator();
            while (ite.hasNext()) {
                String record = ite.next().toString();
                int len = record.length();
                int i = 2;
                if (0 == len) {
                    continue;
                }

                // 取得左右表标识
                char relation = record.charAt(0);
                // 定义孩子和父母变量
                String child = new String();
                String parent = new String();
                // 获取value-list中value的child
                while (record.charAt(i) != '+') {
                    child += record.charAt(i);
                    i++;
                }

                i = i + 1;

                while (i < len) {
                    parent += record.charAt(i);
                    i++;
                }

                // 左表，取出child放入grandchildren
                if ('1' == relation) {
                    grandchild[grandchildnum] = child;
                    grandchildnum++;
                }
                // 右表，取出parent放入grandparent
                if ('2' == relation) {
                    grandparent[grandparentnum] = parent;
                    grandparentnum++;
                }
                // grandchild和grandparent数组求笛卡尔儿积
            }

            if (0 != grandchildnum && 0 != grandparentnum) {
                for (int m = 0; m < grandchildnum; m++) {
                    for (int n = 0; n < grandparentnum; n++) {
                        // 输出结果
                        context.write(new Text(grandchild[m]), new Text(grandparent[n]));
                    }
                }
            }

        }

        ;
    }

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.set("mapred.job.tracker", "hadoop01:9001");

        try {
            Job job = new Job(conf);
            job.setJarByClass(ChildParent.class);
            // 设置Map和Reduce处理类
            job.setMapperClass(SingleRelationMap.class);
            job.setReducerClass(SingleRelationReduce.class);

            // 设置输出类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            // 设置输入和输出目录

            FileInputFormat.addInputPath(job, new Path("/usr/input/wc/Demo/single"));

            FileOutputFormat.setOutputPath(job, new Path("/usr/output/Demo/single"));

            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
