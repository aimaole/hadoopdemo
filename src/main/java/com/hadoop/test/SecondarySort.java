package com.hadoop.test;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SecondarySort {
    public static class StringPair implements WritableComparable<StringPair> {
        private String first;
        private String second;
        private String third;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }


        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StringPair) {
                StringPair sp = (StringPair) obj;
                if (this.first.equals(sp.getFirst())
                        && this.second.equals(sp.getSecond())
                        && this.third.equals(sp.third)) {
                    return true;
                }else return false;
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(StringPair o) {
            return 0;
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeUTF(first);
        }

        @Override
        public void readFields(DataInput in) throws IOException {

        }
    }
}
