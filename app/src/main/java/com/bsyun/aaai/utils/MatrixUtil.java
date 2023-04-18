package com.bsyun.aaai.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MatrixUtil {
    private static final String TAG = "MatrixUtil";
    public static final String gongwei[][] = {{"4", "9", "2"}, {"3", "5", "7"}, {"8", "1", "6"}};


    public static String[][] listToMatrix(List<String> list) {
        List<String> s1 = new ArrayList<>(3);
        List<String> s2 = new ArrayList<>(3);
        List<String> s3 = new ArrayList<>(3);

        for (int i = 0; i < list.size(); i++) {
            if (i < 3) {
                s1.add(CommmonUtil.getDefaultGan(list.get(i)));
            } else if (i < 6) {
                s2.add(CommmonUtil.getDefaultGan(list.get(i)));
            } else {
                s3.add(CommmonUtil.getDefaultGan(list.get(i)));
            }
        }
        List<String[]> ls = new ArrayList<String[]>();
        String[] str1 = s1.toArray(new String[3]);
        String[] str2 = s2.toArray(new String[3]);
        String[] str3 = s3.toArray(new String[3]);
        ls.add(0, str1);
        ls.add(1, str2);
        ls.add(2, str3);
        String[][] strings = ls.toArray(new String[3][3]);
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                Log.e(TAG, "bbbbb: " + strings[i][j]);
//            }
//        }
        return strings;
    }

    /**
     * @param matrix
     * @return 顺时针输出矩阵元素
     */
    public static ArrayList<String> clockwiseTheNum(String[][] matrix) {
        ArrayList<String> res = new ArrayList<String>(9);
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] flagArr = new boolean[rows][cols];
        int i = 0, j = -1;
        int dir = 1;

        while (res.size() < rows * cols) {
            if (dir == 1) {
                j++;
                if (j == cols || flagArr[i][j]) {
                    j--;
                    dir = 2;
                }
            }
            if (dir == 2) {
                i++;
                if (i == rows || flagArr[i][j]) {
                    i--;
                    dir = 3;
                }
            }
            if (dir == 3) {
                j--;
                if (j < 0 || flagArr[i][j]) {
                    j++;
                    dir = 4;
                }
            }
            if (dir == 4) {
                i--;
                if (i < 0 || flagArr[i][j]) {
                    i++;
                    dir = 1;
                }
            }
            if (!flagArr[i][j])
                res.add(matrix[i][j]);
            flagArr[i][j] = true;
        }
        for (int k = 0; k < res.size(); k++) {
//            Log.e(TAG, "printMatrix: " + res.get(k));
        }
        return res;
    }
    /**
     * @return 逆时针输出矩阵元素
     */
    public static ArrayList<String> counterclockwiseTheNum(String[][] matrix) {
        ArrayList<String> res = new ArrayList<String>(9);
        int len = matrix.length;
        int col = matrix[0].length;

        int mid = len < col ? len : col;//取行和列的最小值
        int hang = 0;//用于记录已打印的行数
        int lie = 0;//用于记录已打印的列数
        int flag1 = 0;//用于记录行标或列标
        int flag2 = 0;//用于记录行标或列标
        boolean[][] flagArr = new boolean[len][col];

        int i = 0;
        int sum = 0;
        for (int start = 0; start <= mid / 2; start++) {
            //1.上到下打印列
            if (lie < col) {
                for (i = start; i < len - start; i++) {
                    Log.e(TAG, matrix[i][start]);
                    res.add(sum, matrix[i][start]);
                    sum++;
                }
                flag1 = i - 1;
                lie++;
            }

            if (hang < len) {
                for (i = start + 1; i < col - start; i++) {
                    Log.e(TAG, matrix[flag1][i]);
                    res.add(sum, matrix[flag1][i]);
                    sum++;
                }
                flag2 = i - 1;
                hang++;

            }

            if (lie < col) {
                for (i = flag1 - 1; i >= start; i--) {
                    Log.e(TAG, matrix[i][flag2]);
                    res.add(sum, matrix[i][flag2]);
                    sum++;
                }
                flag1 = i;
                lie++;
            }

            if (hang < len) {
                for (i = flag2 - 1; i > start; i--) {
                    Log.e(TAG, matrix[start][i]);
                    res.add(sum, matrix[start][i]);
                    sum++;
                }
                hang++;
            }
        }
//        for (int j = 0; j < res.size(); j++) {
//            Log.e(TAG, "counterclockwiseTheNum: " + res.get(j));
//        }
        return res;
    }
}
