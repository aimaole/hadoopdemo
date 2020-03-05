package com.hadoop;

import org.apache.hadoop.util.ProgramDriver;

public class HadoopAppDriver {

    public static int exec(String[] args) {
        int exitCode = -1;
        ProgramDriver pgd = new ProgramDriver();
        try {
            pgd.addClass("wordCount", WordCount.class, "wordCount");
            pgd.driver(args);
            exitCode = 0;
        } catch (Throwable e) {
            // TODO Auto-generated catch blockl
            e.printStackTrace();
        }

        return exitCode;

    }

    public static void main(String[] args) {
        int exitCode = exec(args);
        System.exit(exitCode);
    }

}
