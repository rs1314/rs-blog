package cn.rs.blog.commoms.utils;

public class PageSizeUtils {
    public static int pageSize(int counts, int size) {
        int temp = counts / size;
        int c = counts % size;
        if (c != 0) {
            temp += 1;
        }
        return temp;
    }

    public static int getPage(String pageTemp, int size,int defaultPageTemp) {
        int temp = 0;
        try {
            temp = Integer.parseInt(pageTemp);
        } catch (Exception e) {
            temp = defaultPageTemp;
        }
        temp =(temp - 1) * size;
        return temp;
    }
}
