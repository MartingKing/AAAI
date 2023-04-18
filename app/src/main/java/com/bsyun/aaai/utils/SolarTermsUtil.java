package com.bsyun.aaai.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主要用于把公历日期处理成24节气
 */
public class SolarTermsUtil {
    private static final String TAG = "SolarTermsUtil";
    private static final double D = 0.2422;
    private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<String, Integer[]>();//+1偏移
    private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<String, Integer[]>();//-1偏移
    /**
     * 用于保存24节气
     */
    private static String[] jieqi = {
            "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
            "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};
    private static String[] yuefen = {"02", "02", "03", "03", "04", "04", "05", "05", "06", "06", "07", "07", "08", "08", "09", "09", "10", "10", "11", "11", "12", "12", "01", "01"};
    private static String[] terms = {
            SolarTermsEnum.LICHUN.name(),
            SolarTermsEnum.YUSHUI.name(),
            SolarTermsEnum.JINGZHE.name(),
            SolarTermsEnum.CHUNFEN.name(),
            SolarTermsEnum.QINGMING.name(),
            SolarTermsEnum.GUYU.name(),
            SolarTermsEnum.LIXIA.name(),
            SolarTermsEnum.XIAOMAN.name(),
            SolarTermsEnum.MANGZHONG.name(),
            SolarTermsEnum.XIAZHI.name(),
            SolarTermsEnum.XIAOSHU.name(),
            SolarTermsEnum.DASHU.name(),
            SolarTermsEnum.LIQIU.name(),
            SolarTermsEnum.CHUSHU.name(),
            SolarTermsEnum.BAILU.name(),
            SolarTermsEnum.QIUFEN.name(),
            SolarTermsEnum.HANLU.name(),
            SolarTermsEnum.SHUANGJIANG.name(),
            SolarTermsEnum.LIDONG.name(),
            SolarTermsEnum.XIAOXUE.name(),
            SolarTermsEnum.DAXUE.name(),
            SolarTermsEnum.DONGZHI.name(),
            SolarTermsEnum.XIAOHAN.name(),
            SolarTermsEnum.DAHAN.name()
    };

    /**
     * 24节气
     **/
    private static enum SolarTermsEnum {
        LICHUN,//--立春
        YUSHUI,//--雨水
        JINGZHE,//--惊蛰
        CHUNFEN,//春分
        QINGMING,//清明
        GUYU,//谷雨
        LIXIA,//立夏
        XIAOMAN,//小满
        MANGZHONG,//芒种
        XIAZHI,//夏至
        XIAOSHU,//小暑
        DASHU,//大暑
        LIQIU,//立秋
        CHUSHU,//处暑
        BAILU,//白露
        QIUFEN,//秋分
        HANLU,//寒露
        SHUANGJIANG,//霜降
        LIDONG,//立冬
        XIAOXUE,//小雪
        DAXUE,//大雪
        DONGZHI,//冬至
        XIAOHAN,//小寒
        DAHAN;//大寒
    }

    static {
        DECREASE_OFFSETMAP.put(SolarTermsEnum.YUSHUI.name(), new Integer[]{2026});//雨水
        INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUNFEN.name(), new Integer[]{2084});//春分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOMAN.name(), new Integer[]{2008});//小满
        INCREASE_OFFSETMAP.put(SolarTermsEnum.MANGZHONG.name(), new Integer[]{1902});//芒种
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAZHI.name(), new Integer[]{1928});//夏至
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOSHU.name(), new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DASHU.name(), new Integer[]{1922});//大暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LIQIU.name(), new Integer[]{2002});//立秋
        INCREASE_OFFSETMAP.put(SolarTermsEnum.BAILU.name(), new Integer[]{1927});//白露
        INCREASE_OFFSETMAP.put(SolarTermsEnum.QIUFEN.name(), new Integer[]{1942});//秋分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANGJIANG.name(), new Integer[]{2089});//霜降
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LIDONG.name(), new Integer[]{2089});//立冬
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOXUE.name(), new Integer[]{1978});//小雪
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DAXUE.name(), new Integer[]{1954});//大雪
        DECREASE_OFFSETMAP.put(SolarTermsEnum.DONGZHI.name(), new Integer[]{1918, 2021});//冬至

        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[]{1982});//小寒
        DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAOHAN.name(), new Integer[]{2019});//小寒

        INCREASE_OFFSETMAP.put(SolarTermsEnum.DAHAN.name(), new Integer[]{2082});//大寒
    }

    //定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY =
            {{4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35,
                    23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84}
                    , {3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83,
                    7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12}};

    /**
     * @param year 年份
     * @param name 节气的名称
     * @return 返回节气是相应月份的第几天
     */
    public static String getSolarTermNum(int year, String name) {

        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        name = name.trim().toUpperCase();
        int ordinal = SolarTermsEnum.valueOf(name).ordinal();

        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
        int dateNum = 0;
        /**
         * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
         * 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
         */
        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            if (ordinal == SolarTermsEnum.XIAOHAN.ordinal() || ordinal == SolarTermsEnum.DAHAN.ordinal()
                    || ordinal == SolarTermsEnum.LICHUN.ordinal() || ordinal == SolarTermsEnum.YUSHUI.ordinal()) {
                //注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        dateNum = (int) (y * D + centuryValue) - (int) (y / 4);//步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, name);//步骤4，加上特殊的年分的节气偏移量
        return dateNum > 10 ? String.valueOf(dateNum) : "0" + dateNum;
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param name 节气名称
     * @return 返回其偏移量
     */
    public static int specialYearOffset(int year, String name) {
        int offset = 0;
        offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
        offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

        return offset;
    }

    public static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
        int off = 0;
        Integer[] years = map.get(name);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    /**
     * @param times 时间戳
     * @return 返回当前日期是什么节气
     */
    public static Map<String, String> solarTermToString(long times) {
        int year = DateUtil.getYearByTimeStamp(times);
        int month = DateUtil.getYearByTimeStamp(times);
        int day = DateUtil.getYearByTimeStamp(times);
        Map<String, String> jqMap = new HashMap<>(24);
        List<Integer> indexYue = new ArrayList<>();
        List<String> datas = new ArrayList<>();
        String curJieqi = "";
        for (int i = 0; i < 24; i++) {
            datas.add(i, getSolarTermNum(year, terms[i]));
        }
        //传进来年月日，先获取月份对应的index，每个月又两个index，取出来放到一个list里面待用，
        //在根据月对应的index来获取jieqiDay数组中对应的两个数据，如果传进来的day小于indexYue.get(0)则当日是属于上一个节气,
        // 如果indexYue.get(0)<day<indexYue.get(1) 则day属于indexYue.get(0)的节气，否则就是indexYue.get(1)的节气
        for (int i = 0; i < yuefen.length; i++) {
            Log.e("SolarTermsUtil", jieqi[i] + "：" + yuefen[i] + "月" + getSolarTermNum(year, terms[i]) + "日");
            jqMap.put(jieqi[i], year + "-" + yuefen[i] + "-" + getSolarTermNum(year, terms[i]));
            if (Integer.parseInt(yuefen[i]) == month) {
                indexYue.add(i);
            }
        }
//        if (month == 2) {
//            if (day < datas.get(indexYue.get(0))) {
//                curJieqi = jieqi[23];
//            } else if (day >= datas.get(indexYue.get(0)) && day < datas.get(indexYue.get(1))) {
//                curJieqi = jieqi[indexYue.get(0)];
//            } else if (day >= datas.get(indexYue.get(1))) {
//                curJieqi = jieqi[indexYue.get(1)];
//            }
//        } else {
//            if (day < datas.get(indexYue.get(0))) {
//                curJieqi = jieqi[indexYue.get(0) - 1];
//            } else if (day >= datas.get(indexYue.get(0)) && day < datas.get(indexYue.get(1))) {
//                curJieqi = jieqi[indexYue.get(0)];
//            } else if (day >= datas.get(indexYue.get(1))) {
//                curJieqi = jieqi[indexYue.get(1)];
//            }
//        }
//        Log.e(TAG, "curJieqi: " + curJieqi);
        return jqMap;
    }


}
