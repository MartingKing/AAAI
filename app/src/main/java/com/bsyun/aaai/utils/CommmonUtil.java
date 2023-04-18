package com.bsyun.aaai.utils;

import android.text.TextUtils;
import android.util.Log;

import com.bsyun.aaai.Jieqi;
import com.bsyun.aaai.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommmonUtil {

    private static final String TAG = "CommmonUtil";
    public static final String[] pro_names = new String[]{"奇门遁甲", "八字", "风水"};
    public static final String[] pro_desc = new String[]{"是中国古代术数著作，也是奇门、六壬、太乙三大秘宝中的第一大秘术，为三式之首", "即生辰八字，是一个人出生时的干支历日期", "风水是自然界的力量，是宇宙的大磁场能量"};
    public static final int[] iconGongwei = {R.mipmap.tri4, R.mipmap.tri9, R.mipmap.tri2, R.mipmap.tri3, R.mipmap.bg_center, R.mipmap.tri7, R.mipmap.tri8, R.mipmap.tri1, R.mipmap.tri6};
    public static final String[] item_detail_name = new String[]{"五行", "八卦", "八门", "八神", "九星", "十天干", "十二地支", "十二长生", "奇门遁甲常用术语", "奇门用神", "宫位数", "解盘信息", "拆补法排盘", "八门盘快速排盘"};
    public static final String[] aroundJiuXing = {"天辅", "天英", "天芮", "天柱", "天心", "天蓬", "天任", "天冲", "天禽"};
    public static final String[] yuanJiuXing = {"天辅", "天英", "天芮", "天冲", "天禽", "天柱", "天任", "天蓬", "天心"};
    public static final String[] yuanBamen = {"杜门", "景门", "死门", "伤门", "中门", "惊门", "生门", "休门", "开门"};
    public static final String POSITION = "position_detail";
    public static final String TIME = "time_long";
    public static final String[] bashen = {"值符", "腾蛇", "太阴", "六合", "白虎", "玄武", "九地", "九天"};
    public static String[] tianpangan = {"戊", "己", "庚", "辛", "壬", "癸", "丁", "丙", "乙", ""};

    public static String[] xunkong = {"戌亥", "申酉", "午未", "辰巳", "寅卯", "子丑"};
    //旬空对应recycle的position位置
    public static String[] xunkongGongwei = {"8", "25", "12", "0", "63", "76"};
    public static String[] jijieGongwei = {""};
    /**
     * 用于保存24节气
     */
    public static final String[] jieqi = {
            "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑",
            "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};
    public static final int[] yuefen = {2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 1, 1};
    /**
     * 子午卯酉 为上元
     * <p>
     * 寅申巳亥 为中元
     * <p>
     * 辰戌丑未 为下元
     */
    private static String[] yuan_fu = {"上元", "中元", "下元", "上元", "中元", "下元", "上元", "中元", "下元", "上元", "中元", "下元"};
    private static String[] yuanfu_header = {"甲子", "己巳", "甲戌", "己卯", "甲申", "己丑", "甲午", "己亥", "甲辰", "己酉", "甲寅", "己未"};
    private static String[] xs = {"甲子", "甲戌", "甲申", "甲午", "甲辰", "甲寅"};
    public static String[] liuxunshou = {"甲子戊", "甲戌己", "甲申庚", "甲午辛", "甲辰壬", "甲寅癸"};

    public static String[] sixty_jiazi = {
            "甲子", "乙丑", "丙寅", "丁卯", "戊辰",
            "己巳", "庚午", "辛未", "壬申", "癸酉",
            "甲戌", "乙亥", "丙子", "丁丑", "戊寅",
            "己卯", "庚辰", "辛巳", "壬午", "癸未",
            "甲申", "乙酉", "丙戌", "丁亥", "戊子",
            "己丑", "庚寅", "辛卯", "壬辰", "癸巳",
            "甲午", "乙未", "丙申", "丁酉", "戊戌",
            "己亥", "庚子", "辛丑", "壬寅", "癸卯",
            "甲辰", "乙巳", "丙午", "丁未", "戊申",
            "己酉", "庚戌", "辛亥", "壬子", "癸丑",
            "甲寅", "乙卯", "丙辰", "丁巳", "戊午",
            "己未", "庚申", "辛酉", "壬戌", "癸亥"};

    // TODO: 2020/11/12 此处时间替换，当精确到时分秒的时候可能有误差
    public static Map<String, String> replaceMapValue(Map<String, String> mapPaiyue, Map<String, String> mapSolar, long time) {
        Map<String, String> newMap = new HashMap<>(24);
        for (Entry<String, String> entry : mapPaiyue.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            int year = Integer.parseInt(value.substring(1, 5).trim());
            String exceptYearDate = value.substring(11);
            //如果mapPaiyue中value的年份不等于当前年份，把对应的年月日换成mapSolar的  2020-12-21 18:02:21
            if (year != DateUtil.getYearByTimeStamp(time)) {
                newMap.put(key, mapSolar.get(key) + exceptYearDate);
            } else {
                newMap.put(key, value);
            }
        }
        return newMap;
    }

    /**
     * @param rgz     日的干支
     * @param curtime 时间
     * @return 返回阳盾或者阴盾几局
     */
    public static List<Object> getPanInfo(long curtime, String rgz) {
        int index = 0;
        String curJieqi = "";
        boolean isYangdun = true;//是否是阳盾
        //夏至和冬至时间 夏至以后为阴盾局 冬至以后为阳盾局
        int curYear = DateUtil.getYearByTimeStamp(curtime);
        int curMonth = DateUtil.getMonthByTimeStamp(curtime);
        Jieqi mj = new Jieqi();
        Map<String, String> map = replaceMapValue(mj.paiYue(curYear), SolarTermsUtil.solarTermToString(curtime), curtime);
        for (Entry<String, String> entry : map.entrySet()) {
            String jq = entry.getKey();
            String date = entry.getValue();
            long dd = DateUtil.getStringToDate(date);
        }
        //冬至后用阳盾2020-12-21 18:02:21，夏至后用阴盾2020-06-21 05:43:37
        String xzDate = map.get("夏至");
        String dzDate = map.get("冬至");
        long xztime = DateUtil.getStringToDate(xzDate);
        long dztime = DateUtil.getStringToDate(dzDate);
        //如果当前月份为夏至后，冬至前则用阴盾
        for (int i = 0; i < sixty_jiazi.length; i++) {
            if (sixty_jiazi[i].equals(rgz)) {
                index = i;
            }
        }
        if (curtime > xztime && curtime < dztime) {
            isYangdun = false;
        }
        int line = index / 5;
        String yuan = yuan_fu[line];
        //首先判断月份是否匹配，每个月两个节气，再根据两个节气的时间做判断，是属于什么节气
        //存储根据正常节气顺序获取的时间戳
        List<Long> datas = new ArrayList<>(24);
        List<String> jq = new ArrayList<>(24);
        List<Integer> indexYue = new ArrayList<>(24);
        for (int i = 0; i < jieqi.length; i++) {
            String time = map.get(jieqi[i]);
            datas.add(i, DateUtil.getStringToDate(time));
            jq.add(i, jieqi[i]);
        }
        for (int i = 0; i < yuefen.length; i++) {
            if (yuefen[i] == curMonth) {
                indexYue.add(i);
            }
        }
        if (curMonth == 2) {
            if (curtime < datas.get(indexYue.get(0))) {
                curJieqi = jieqi[23];
            } else if (curtime >= datas.get(indexYue.get(0)) && curtime < datas.get(indexYue.get(1))) {
                curJieqi = jieqi[indexYue.get(0)];
            } else if (curtime >= datas.get(indexYue.get(1))) {
                curJieqi = jieqi[indexYue.get(1)];
            }
        } else {
            if (curtime < datas.get(indexYue.get(0))) {
                curJieqi = jieqi[indexYue.get(0) - 1];
            } else if (curtime >= datas.get(indexYue.get(0)) && curtime < datas.get(indexYue.get(1))) {
                curJieqi = jieqi[indexYue.get(0)];
            } else if (curtime >= datas.get(indexYue.get(1))) {
                curJieqi = jieqi[indexYue.get(1)];
            }
        }

        Map<String, String> dunMap = new HashMap<>(24);
        dunMap.put("冬至", "174");
        dunMap.put("小寒", "285");
        dunMap.put("大寒", "396");
        dunMap.put("立春", "852");
        dunMap.put("雨水", "963");
        dunMap.put("惊蛰", "174");
        dunMap.put("春分", "396");
        dunMap.put("清明", "417");
        dunMap.put("谷雨", "528");
        dunMap.put("立夏", "417");
        dunMap.put("小满", "528");
        dunMap.put("芒种", "639");
        dunMap.put("夏至", "936");
        dunMap.put("小暑", "825");
        dunMap.put("大暑", "714");
        dunMap.put("立秋", "258");
        dunMap.put("处暑", "147");
        dunMap.put("白露", "936");
        dunMap.put("秋分", "714");
        dunMap.put("寒露", "693");
        dunMap.put("霜降", "582");
        dunMap.put("立冬", "693");
        dunMap.put("小雪", "582");
        dunMap.put("大雪", "471");

        int jushu = 0;
        Log.e(TAG, "isYangdun: " + isYangdun);
        Log.e(TAG, "curJieqi: " + curJieqi);
        if (isYangdun) {
            String s = dunMap.get(curJieqi);
            if (yuan.equals("上元")) {
                jushu = Integer.parseInt(s.substring(0, 1));
            } else if (yuan.equals("中元")) {
                jushu = Integer.parseInt(s.substring(1, 2));
            } else {
                jushu = Integer.parseInt(s.substring(s.length() - 1));
            }
        } else {
            String s = dunMap.get(curJieqi);
            if (yuan.equals("上元")) {
                jushu = Integer.parseInt(s.substring(0, 1));
            } else if (yuan.equals("中元")) {
                jushu = Integer.parseInt(s.substring(1, 2));
            } else {
                jushu = Integer.parseInt(s.substring(s.length() - 1));
            }
        }
        List<Object> list = new ArrayList<>(5);
        list.add(0, isYangdun);
        list.add(1, yuan);
        list.add(2, curJieqi);
        list.add(3, jushu);
        Log.e("DHD", "isYangdun: " + isYangdun);
        Log.e("DHD", "yuan: " + yuan);
        Log.e("DHD", "curMonth: " + curMonth);
        Log.e("DHD", "curJieqi: " + curJieqi);
        Log.e("DHD", "jushu: " + jushu);
        return list;
    }

    public static String getDefaultGan(String s) {
        if (TextUtils.isEmpty(s)) {
            return "戊";
        } else {
            return s;
        }
    }

    public static String getXunShou(String sg) {
        int index = 0;
        for (int i = 0; i < sixty_jiazi.length; i++) {
            if (sixty_jiazi[i].equals(sg)) {
                index = i;
            }
        }
        int line = index / 10;
        String str = xs[line];
        String xunshou = "";
        for (int i = 0; i < liuxunshou.length; i++) {
            if (liuxunshou[i].contains(str)) {
                xunshou = liuxunshou[i];
            }
        }
        return xunshou;
    }

    public static String getXunshouGan(String sg) {
        String xunshou = getXunShou(sg);
        return xunshou.substring(xunshou.length() - 1);
    }


    /**
     * @param sg
     * @return 返回旬空对应的recycle索引位置
     */
    public static String getXunkong(String sg) {
        String xunshou = getXunShou(sg);
        String xk = "";
        for (int i = 0; i < liuxunshou.length; i++) {
            if (xunshou.equals(liuxunshou[i])) {
                xk = xunkong[i];
            }
        }
        Log.e(TAG, "getXunkong: " + xk);
        String position = "0";
        for (int i = 0; i < xunkong.length; i++) {
            if (xk.equals(xunkong[i])) {
                position = xunkongGongwei[i];
            }
        }
        return position;
    }

    private static String[] maxing = {"申子辰", "亥卯未", "寅午戌", "巳酉丑"};
    //    private static String[] maxingPosition = {"寅", "巳", "申", "亥"};
    private static int[] maxingPosition = {6, 0, 2, 8};

    public static int getStarMa(String sg) {
        String s = sg.substring(1);
        int index = 0;
        for (int i = 0; i < maxing.length; i++) {
            if (maxing[i].contains(s)) {
                index = maxingPosition[i];
            }
        }
        Log.e(TAG, "aaaa: " + s);
        Log.e(TAG, "index: " + index);

        return index;
    }


    /**
     * @param s 时干
     * @return 如果传进来的时干是包含在六旬首 需要重新计算
     */
    public static String getSpecialShigan(String s) {
        for (int i = 0; i < liuxunshou.length; i++) {
            if (liuxunshou[i].contains(s)) {
                return liuxunshou[i].substring(liuxunshou[i].length() - 1);
            }
        }
        return s.substring(0, 1);
    }

    /**
     * @param isYangdun 阴阳遁
     * @param jushu     局数
     * @return 返回数据
     */
    public static Map<Integer, String> getYinYangTianganMap(boolean isYangdun, int jushu) {
        Map<Integer, String> map = new HashMap<>(9);
        if (isYangdun) {
            for (int i = 0; i <= 9; i++) {
                if (jushu > 9) {
                    jushu = 1;
                }
                map.put(jushu++, tianpangan[i]);
            }
            for (Entry<Integer, String> entry : map.entrySet()) {
                int key = entry.getKey();
                String value = entry.getValue();
//                Log.e(TAG, key + "宫" + "天干为:" + value);
            }
        } else {
            for (int i = 0; i <= 9; i++) {
                if (jushu == 0) {
                    jushu = 9;
                }
                map.put(jushu--, tianpangan[i]);
            }
            for (Entry<Integer, String> entry : map.entrySet()) {
                int key = entry.getKey();
                String value = entry.getValue();
//                Log.e(TAG, key + "宫" + "天干为:" + value);
            }
        }
        return map;
    }

    public static String getMonthByRecyclerPosition(int position) {
        String monthWx = "";
        switch (position) {
            case 0:
            case 3:
                monthWx = "木";
                break;
            case 1:
                monthWx = "火";
                break;
            case 2:
            case 4:
            case 6:
                monthWx = "土";
                break;
            case 5:
            case 8:
                monthWx = "金";
                break;
            case 7:
                monthWx = "水";
                break;
        }
        return monthWx;
    }

    /**
     * @param position
     * @return 返回宫位对应的十二地支
     */
    public static String getDZByRecyclerPosition(int position) {
        String[] zhi_info = {"辰巳", "午", "未申", "卯", "", "酉", "丑寅", "子", "亥戌"};
        return zhi_info[position];
    }
}
