package com.test.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class JobMain extends Configured implements Tool {


    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "wordCount");
        job.setJarByClass(JobMain.class);
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path("hdfs://node1:8020/wordcount"));

        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //......
        job.setReducerClass(WordCountReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextOutputFormat.setOutputPath(job, new Path("hdfs://node1:8020/wordcount/wordcount_out"));
        boolean b = job.waitForCompletion(true);
        return b ? 0 : 1;

    }


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new JobMain(), args);
        System.exit(run);
    }
}
