package com.bsyun.aaai;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bsyun.aaai.utils.CommmonUtil;
import com.bsyun.aaai.utils.DateUtil;
import com.bsyun.aaai.utils.MatrixUtil;
import com.bsyun.aaai.utils.XuanxueUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaipanActivity extends ZBaseActivity implements View.OnClickListener {

    private static final String TAG = "PaipanActivity";
    private static final String DPG = "dipangan";
    private static final String TPG = "tianpangan";
    private static final String JX = "jiuxing";
    private static final String BS = "bashen";
    private static final String BM = "bamen";

    private TextView shigan, shizhi, yuegan, yuezhi, rigan, rizhi, niangan, nianzhi, tvdate, tv_ju, tv_xunshou, tv_zhifu, tv_zhishi, tvXJ, tvRM;
    private TextView tvback, tvlast, tvnext;
    private RecyclerView recyclerView;
    private PanAdapter mAdapter;
    private List<Object> panInfo;
    private int[] gongweishu = {4, 9, 2, 3, 5, 7, 8, 1, 6};//正常宫位数
    private int[] gongweishu2 = {4, 9, 2, 7, 6, 1, 8, 3, 5};//使用算法排天盘干的时候对应数组的宫位数
    private Map<Integer, String> dipanganMap;
    private Map<Integer, String> tianpanMap = new HashMap<>(9);
    private Map<Integer, String> jiuxingMap = new HashMap<>(9);
    private Map<Integer, String> bashenMap = new HashMap<>(9);
    private Map<Integer, String> bamenMap = new HashMap<>(9);
    private String startshigan, xunshouGan, recalculateshigan, zhifuxing, zhishimen;
    //值使门
    private String zhishi = "";
    //值符星
    private String zhifu = "";
    private long time;
    private Lunar lunar;
    //是阴盾还是阳盾
    private boolean isYangdun;
    //旬首干位置
    private int pXunshougan;
    private List<String> dipanganData = new ArrayList<>();
    private List<String> tianpanganData = new ArrayList<>();
    private List<String> bamenData = new ArrayList<>();
    private List<String> bashenData = new ArrayList<>();
    private ArrayList<String> jiuxingData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paipan);
        initView();
    }

    private void initView() {
        niangan = findViewById(R.id.niangan);
        nianzhi = findViewById(R.id.nianzhi);
        rizhi = findViewById(R.id.rizhi);
        rigan = findViewById(R.id.rigan);
        yuezhi = findViewById(R.id.yuezhi);
        yuegan = findViewById(R.id.yuegan);
        shizhi = findViewById(R.id.shizhi);
        shigan = findViewById(R.id.shigan);
        tvdate = findViewById(R.id.tvdate);
        tv_ju = findViewById(R.id.tv_ju);
        tv_xunshou = findViewById(R.id.tv_xunshou);
        tv_zhifu = findViewById(R.id.tv_zhifu);
        tv_zhishi = findViewById(R.id.tv_zhishi);
        tvXJ = findViewById(R.id.tvXJ);
        tvRM = findViewById(R.id.tvRM);
        recyclerView = findViewById(R.id.recyclerview);

        tvback = findViewById(R.id.tvback);
        tvlast = findViewById(R.id.tvlast);
        tvnext = findViewById(R.id.tvnext);

        tvback.setOnClickListener(this);
        tvlast.setOnClickListener(this);
        tvnext.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        time = getIntent().getLongExtra(CommmonUtil.TIME, 0);
        initData(time);
    }

    private void initData(long time) {
        Solar solar = Solar.fromDate(new Date(time));
        Log.e(TAG, "time: " + time);
        lunar = solar.getLunar();
        String rigz = lunar.getDayInGanZhi();
        panInfo = CommmonUtil.getPanInfo(time, rigz);
        startshigan = XuanxueUtil.getDizhiHour(time);
        xunshouGan = CommmonUtil.getXunshouGan(startshigan);
        recalculateshigan = CommmonUtil.getSpecialShigan(startshigan);
        Log.e(TAG, "时辰: " + XuanxueUtil.getGanZhi() + " " + startshigan + "时");
        Log.e(TAG, "时干: " + recalculateshigan);
        isYangdun = (boolean) panInfo.get(0);
        int jushu = (int) panInfo.get(3);
        String j = isYangdun ? "阳盾" : "阴盾";
        tv_ju.setText("盾局：" + j + jushu + "局");
        dipanganMap = CommmonUtil.getYinYangTianganMap(isYangdun, jushu);
        //排地盘干
        sortMapDataToList(dipanganMap, DPG);
        for (int i = 0; i < 9; i++) {
            if (dipanganData.get(i).equals(xunshouGan)) {
                pXunshougan = i;
            }
        }
        sortListData();
        setData();
    }

    private void setData() {
        mAdapter = new PanAdapter(zhishimen);
        recyclerView.setAdapter(mAdapter);
        niangan.setText(lunar.getYearGan());
        nianzhi.setText(lunar.getYearZhi());
        yuegan.setText(lunar.getMonthGan());
        yuezhi.setText(lunar.getMonthZhi());
        rigan.setText(lunar.getDayGan());
        rizhi.setText(lunar.getDayZhi());
        shigan.setText(lunar.getTimeGan());
        shizhi.setText(lunar.getTimeZhi());
        tvdate.setText("日期：" + DateUtil.getDateToString(time));
        tv_xunshou.setText("旬首：" + xunshouGan);
        List<PanEntity> mdatas = new ArrayList<>(9);
        String pXunkong = CommmonUtil.getXunkong(startshigan);
        int pMaxing = CommmonUtil.getStarMa(startshigan);
        String kong1 = pXunkong.substring(0, 1);
        String kong2 = TextUtils.isEmpty(pXunkong.substring(1)) ? kong1 : pXunkong.substring(1);

        for (int i = 0; i < 9; i++) {
            PanEntity entity = new PanEntity();
            entity.setDipangan(CommmonUtil.getDefaultGan(dipanganData.get(i)));
            entity.setTianpangan(CommmonUtil.getDefaultGan(tianpanganData.get(i)));
            entity.setGongwei(CommmonUtil.iconGongwei[i]);
            if (CommmonUtil.getDefaultGan(dipanganData.get(i)).equals(xunshouGan)) {
                tv_zhishi.setText("值使：" + CommmonUtil.yuanBamen[i]);
                Log.e(TAG, "值使: " + i + ":" + CommmonUtil.yuanBamen[i]);
            }
            entity.setBamen(bamenData.get(i));
            entity.setJiuxing2(jiuxingData.get(i));
            if (jiuxingData.get(i).equals("天芮")) {
                entity.setJiuxing1("天禽");
                entity.setTiangan1(CommmonUtil.getDefaultGan(dipanganData.get(4)));
            }
            entity.setBashen(bashenData.get(i));
            if ((i == Integer.parseInt(kong1) || i == Integer.parseInt(kong2)) && i == pMaxing) {
                entity.setMakong(R.mipmap.empty_horse);
            }
            if (kong1.equals(kong2)) {
                equalCondition(pMaxing, kong1, i, entity);
            } else {
                equalCondition(pMaxing, kong1, i, entity);
                if (i == Integer.parseInt(kong2) && i == pMaxing) {
                    entity.setMakong(R.mipmap.empty_horse);
                }
                if (i == Integer.parseInt(kong2) && i != pMaxing) {
                    entity.setMakong(R.mipmap.icon_empty);
                }
                if (i == pMaxing && i != Integer.parseInt(kong2)) {
                    entity.setMakong(R.mipmap.icon_horse);
                }
            }
            mdatas.add(i, entity);
        }
        mAdapter.setNewData(mdatas);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(PaipanActivity.this, ItemDetailActivity.class)
                        .putExtra("tpg", tianpanganData.get(position)).putExtra("dpg", dipanganData.get(position)));
            }
        });
        showJxMk();
    }

    private void showJxMk() {
        /**
         * 天干庚落在8宫，戊落在3宫，壬癸落在4宫，辛落在9宫，己落在2宫就显示内容：击刑；
         * 休门落在3宫，开门惊门落在8宫，景门落在6宫，杜伤门落在2宫显示：门入墓
         */
        if (tianpanganData.get(6).equals("庚")) {
            tvXJ.setText("8宫击刑");
        }
        if (tianpanganData.get(3).equals("")) {
            tvXJ.setText("3宫击刑");
        }
        if (tianpanganData.get(0).equals("壬")) {
            tvXJ.setText("4宫击刑");
        }
        if (tianpanganData.get(0).equals("癸")) {
            tvXJ.setText("4宫击刑");
        }
        if (tianpanganData.get(1).equals("辛")) {
            tvXJ.setText("9宫击刑");
        }
        if (tianpanganData.get(2).equals("己")) {
            tvXJ.setText("2宫击刑");
        }
        if (bamenData.get(3).equals("休门")) {
            tvRM.setText("3宫门入墓");
        }
        if (bamenData.get(6).equals("开门")) {
            tvRM.setText("8宫门入墓");
        }
        if (bamenData.get(6).equals("惊门")
        ) {
            tvRM.setText("8宫门入墓");
        }
        if (bamenData.get(8).equals("景门")) {
            tvRM.setText("6宫门入墓");
        }
        if (bamenData.get(2).equals("杜门")) {
            tvRM.setText("2宫门入墓");
        }
        if (bamenData.get(2).equals("伤门")) {
            tvRM.setText("2宫门入墓");
        }
    }

    private void equalCondition(int pMaxing, String kong1, int i, PanEntity entity) {
        if (i == Integer.parseInt(kong1) && i == pMaxing) {
            entity.setMakong(R.mipmap.empty_horse);
        }
        if (i == Integer.parseInt(kong1) && i != pMaxing) {
            entity.setMakong(R.mipmap.icon_empty);
        }
        if (i == pMaxing && i != Integer.parseInt(kong1)) {
            entity.setMakong(R.mipmap.icon_horse);
        }
    }

    /**
     * 根据排好的地盘干
     * 通过时干找到旬首
     * 获取时干对应的天盘干  定在哪呢，找时干，比如时干是丙申时，就定在丙上
     * 如果旬首天干在中宫，那么排天盘干的时候使用中宫寄存所在的二宫的天干定在相应的时干上
     */
    private void sortListData() {
        int pAroundShigan = 0;
        int pAroundXunshou = 0;
        ArrayList<String> aroundDPG = MatrixUtil.clockwiseTheNum(MatrixUtil.listToMatrix(dipanganData));
        //aroundDPG为地盘干顺时针方向从4共开始到3宫，最后一个为中宫
        //只需要找到aroundDPG中对应时干的index，和旬首干对应的index，此index对应的天盘干就是旬首干，那么当前index的位置就是旬首干在天盘干list中的位置，旬首的位置减去 旬首和时干的index差就是旬首在数组中位置
        for (int i = 0; i < 9; i++) {
            if (recalculateshigan.equals(aroundDPG.get(i))) {
                pAroundShigan = i;
            }
            if (xunshouGan.equals(aroundDPG.get(i)) && i != 4) {
                pAroundXunshou = i;
            }
            if (xunshouGan.equals(aroundDPG.get(i))) {
                pAroundXunshou = i;
            }
        }
        //如果旬首天干在中宫，那么排天盘干的时候使用中宫寄存所在的二宫的天干转就行了
        //如果时干在中宫，同理
        if (pAroundXunshou == 8) {
            xunshouGan = aroundDPG.get(2);
        }
        if (pAroundShigan == 8) {
            recalculateshigan = aroundDPG.get(2);
        }
        for (int i = 0; i < 9; i++) {
            if (xunshouGan.equals(aroundDPG.get(i))) {
                pAroundXunshou = i;
            }
            if (recalculateshigan.equals(aroundDPG.get(i))) {
                pAroundShigan = i;
            }
        }
        Log.e(TAG, "旬首: " + xunshouGan);
        sorteBamen();
        regetZhifu();
        sortedBashen();
        sortedTPGan();
    }

    private void sorteBamen() {
        int index = 0;//当前时干在60甲子中的index
        int index1 = 0;//当前时干在二维数组的某个一位数组中的index
        int correspondingDpgGw = 0;//旬首在地盘干中对应的宫位
        for (int i = 0; i < 9; i++) {
            if (CommmonUtil.getDefaultGan(dipanganData.get(i)).equals(xunshouGan)) {
                zhishimen = CommmonUtil.yuanBamen[i];
            }
        }
        String[][] jiazi = {
                {"甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳", "庚午", "辛未", "壬申", "癸酉"},
                {"甲戌", "乙亥", "丙子", "丁丑", "戊寅", "己卯", "庚辰", "辛巳", "壬午", "癸未"},
                {"甲申", "乙酉", "丙戌", "丁亥", "戊子", "己丑", "庚寅", "辛卯", "壬辰", "癸巳"},
                {"甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥", "庚子", "辛丑", "壬寅", "癸卯"},
                {"甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥", "壬子", "癸丑"},
                {"甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥"}};
        for (int i = 0; i < CommmonUtil.sixty_jiazi.length; i++) {
            if (CommmonUtil.sixty_jiazi[i].equals(startshigan)) {
                index = i;
            }
        }
        Log.e(TAG, "index: " + index);
        int theJiazi = index > 10 ? Integer.parseInt(String.valueOf(index).substring(0, 1)) : 0;
        String[] singlejiazi = jiazi[theJiazi];
        for (int i = 0; i < singlejiazi.length; i++) {
            if (startshigan.equals(singlejiazi[i])) {
                index1 = i;
                Log.e(TAG, "时干在60甲子的位置:" + index1);
            }
        }
        for (Map.Entry<Integer, String> entry : dipanganMap.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue().isEmpty() ? "戊" : entry.getValue();
            if (value.equals(xunshouGan)) {
                correspondingDpgGw = key;
                Log.e(TAG, "旬首对应地支所在宫位: " + correspondingDpgGw);
            }
        }
        String[] matrixBamen = {"杜门", "景门", "死门", "惊门", "开门", "休门", "生门", "伤门"};
        int[] matrixGongwei = {4, 9, 2, 7, 6, 1, 8, 3};
        List<String> sortedData = new ArrayList<>(9);
        int correctBmGw = 0;
        int matrixMenIndex = 0;//宫位在matrixBamen数组的位置
        int sortedMenIndex = 0;//经过计算后，宫位在数组的位置
        if (isYangdun) {

            int sum = index1 + correspondingDpgGw;
            if (sum > 9) {
                correctBmGw = sum - 9;
            }
            if (sum <= 9) {
                correctBmGw = sum;
            }

            Log.e(TAG, "correctBmGw: " + correctBmGw);
            correctBmGw = correctBmGw == 5 ? 2 : correctBmGw;
            //根据原来的matrixMenIndex和sortedMenIndex计算转动后的数据
            for (int i = 0; i < matrixGongwei.length; i++) {
                if (correctBmGw == matrixGongwei[i]) {
                    sortedMenIndex = i;
                }
                if (zhishimen.equals(matrixBamen[i])) {
                    matrixMenIndex = i;
                }
            }
            Log.e(TAG, "经过计算后，宫位在数组的位置:" + sortedMenIndex);
            Log.e(TAG, "宫位在matrixBamen数组的位置:" + matrixMenIndex);
            int resize = matrixMenIndex - sortedMenIndex;
            if (resize >= 0) {
                sortedData.addAll(Arrays.asList(matrixBamen).subList(resize, matrixBamen.length));
                sortedData.addAll(Arrays.asList(matrixBamen).subList(0, resize));
            } else {
                sortedData.addAll(Arrays.asList(matrixBamen).subList(matrixBamen.length + resize, matrixBamen.length));
                sortedData.addAll(Arrays.asList(matrixBamen).subList(0, matrixBamen.length + resize));
            }

            for (int i = 0; i < sortedData.size(); i++) {
                bamenMap.put(matrixGongwei[i], sortedData.get(i));
            }
        } else {
            //zhishimen对应宫位为 correctBmGw
            int countHead = correspondingDpgGw - index1;
            if (countHead < 0) {
                correctBmGw = 9 - Math.abs(countHead);
            }
            if (countHead > 0) {
                correctBmGw = countHead;
            }
            if (countHead == 0) {
                correctBmGw = 9;
            }
            correctBmGw = correctBmGw == 5 ? 2 : correctBmGw;
            Log.e(TAG, "countHead: " + countHead);
            Log.e(TAG, "correctBmGw: " + correctBmGw);
            //{"杜门", "景门", "死门", "惊门", "开门", "休门", "生门", "伤门"};
            //{4, 9, 2, 7, 6, 1, 8, 3};
            //根据原来的matrixMenIndex和sortedMenIndex计算转动后的数据
            for (int i = 0; i < matrixGongwei.length; i++) {
                if (correctBmGw == matrixGongwei[i]) {
                    sortedMenIndex = i;
                }
                if (zhishimen.equals(matrixBamen[i])) {
                    matrixMenIndex = i;
                }
            }
            Log.e(TAG, "经过计算后，宫位在数组的位置:" + sortedMenIndex);
            Log.e(TAG, "宫位在matrixBamen数组的位置:" + matrixMenIndex);
            int resize = matrixMenIndex - sortedMenIndex;
            if (resize >= 0) {
                sortedData.addAll(Arrays.asList(matrixBamen).subList(resize, matrixBamen.length));
                sortedData.addAll(Arrays.asList(matrixBamen).subList(0, resize));
            } else {
                sortedData.addAll(Arrays.asList(matrixBamen).subList(matrixBamen.length + resize, matrixBamen.length));
                sortedData.addAll(Arrays.asList(matrixBamen).subList(0, matrixBamen.length + resize));
            }

            for (int i = 0; i < sortedData.size(); i++) {
                bamenMap.put(matrixGongwei[i], sortedData.get(i));
            }
        }
        bamenMap.put(5, "");
        sortMapDataToList(bamenMap, BM);
    }

    private void sortedTPGan() {
        int[] matrixGWS = {4, 9, 2, 7, 6, 1, 8, 3, 5};
        Map<String, Integer> sourceJXMap = new HashMap<>();
        for (int i = 0; i < matrixGWS.length; i++) {
            sourceJXMap.put(CommmonUtil.aroundJiuXing[i], matrixGWS[i]);
            tianpanMap.put(i, CommmonUtil.aroundJiuXing[i]);
        }
        for (int i = 0; i < 9; i++) {
            Integer sourceGongweiJX = sourceJXMap.get(jiuxingData.get(i));//值符星在原始顺序的宫位
            tianpanMap.put(gongweishu[i], dipanganMap.get(sourceGongweiJX));
        }
        sortMapDataToList(tianpanMap, TPG);
    }

    /**
     * 重新定义时干，根据新时干对应的地盘干所在宫位就是值符星所在宫位
     * 新时干对应宫位为newSGIndex,那么当天值符星所在宫位也是newSGIndex
     * 如果值符星所落对应的天干所在的宫位在九宫的5宫，则将值符星寄存到九宫的坤二宫。
     */
    private void regetZhifu() {
        String[] sourceshigan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] correctshigan = {"戊", "乙", "丙", "丁", "癸", "戊", "己", "庚", "辛", "壬"};
        String[] matrixJiuxin = {"天辅", "天英", "天芮", "天柱", "天心", "天蓬", "天任", "天冲"};
        int[] matrixGWS = {4, 9, 2, 7, 6, 1, 8, 3};
        int newSGIndex = 0;
        int sourceSGIndex = 0;
        String oldShigan = startshigan.substring(0, 1);
        Log.e(TAG, "oldshigan+++++++++++++++++++" + oldShigan);
        for (int i = 0; i < sourceshigan.length; i++) {
            if (oldShigan.equals(sourceshigan[i])) {
                sourceSGIndex = i;
            }
        }
        String newshigan = correctshigan[sourceSGIndex];
        Log.e(TAG, "newshigan+++++++++++++++++++" + newshigan);
        for (int i = 0; i < 9; i++) {
            if (CommmonUtil.getDefaultGan(dipanganData.get(i)).equals(xunshouGan)) {
                zhifuxing = CommmonUtil.yuanJiuXing[i];
                //如果值符星为天禽，那么寄存到二宫的天芮星
                zhifuxing = zhifuxing.equals("天禽") ? "天芮" : zhifuxing;
                tv_zhifu.setText("值符：" + zhifuxing);
                Log.e(TAG, "值符: " + i + ":" + zhifuxing);
            }
        }
        for (Map.Entry<Integer, String> entry : dipanganMap.entrySet()) {
            String ganzhi = entry.getValue().isEmpty() ? "戊" : entry.getValue();
            int gongwei = entry.getKey();
            if (ganzhi.equals(newshigan)) {
                newSGIndex = gongwei;
                Log.e(TAG, "newSGIndex所在宫位: " + newSGIndex);
            }
        }

        //newSGIndex==5那么这种情况就应该是天芮在2宫了，其他九星都是原来的位置了，这就是星伏吟了
        newSGIndex = newSGIndex == 5 ? 2 : newSGIndex;
        //根据宫位数逆推对应宫位在数组中的位置，然后在确定九星全体位置
        int sourceJXIndex = 0;//当天值符星在原九星数组中matrixJiuxin对应的宫位数
        int newJXIndex = 0;//newSGIndex对应gongweishu2中数组索引
        for (int i = 0; i < matrixJiuxin.length; i++) {
            if (matrixJiuxin[i].equals(zhifuxing)) {
                sourceJXIndex = i;
            }
            if (newSGIndex == matrixGWS[i]) {
                newJXIndex = i;
            }
        }
        Log.e(TAG, "值符星原来所在宫位index: " + sourceJXIndex);
        Log.e(TAG, "值符星对应新干支所在宫位index: " + newJXIndex);
        int gongweiChange = newJXIndex - sourceJXIndex;
        List<String> sortedData = new ArrayList<>(9);
        Log.e(TAG, "gongweiChange: " + gongweiChange);
        //{"天辅", "天英", "天芮", "天柱", "天心", "天蓬", "天任", "天冲"};
        //{  4,     9,      2,     7,      6,     1,     8,      3   };
        /**
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: oldshigan+++++++++++++++++++甲
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: newshigan+++++++++++++++++++戊
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: 值符: 5:天柱
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: newSGIndex所在宫位: 3
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: 值符星原来所在宫位index: 3
         * 2021-12-01 15:07:03.535 17583-17583/com.bsyun.aaai E/PaipanActivity: 值符星对应新干支所在宫位index: 7
         * 2021-12-01 15:10:23.929 17583-17583/com.bsyun.aaai E/PaipanActivity: gongweiChange: 4
         * 天柱星的index为3，他新的宫位为七宫index为7，在最后一个，因此只需要从他原来位置的后一个开始截取也就是3+1=4处截取
         * 天英星的index为1，他新的宫位为八宫index为6，倒数第二个，因此只需要从他原来位置的后二个开始截取也就是1+2=3处截取
         */
        if (gongweiChange > 0) {
            int diff = sourceJXIndex + 8 - newJXIndex;
            sortedData.addAll(Arrays.asList(matrixJiuxin).subList(diff, 8));
            sortedData.addAll(Arrays.asList(matrixJiuxin).subList(0, diff));
        } else if (gongweiChange == 0) {
            sortedData.addAll(Arrays.asList(matrixJiuxin));
        } else {
            int resize = Math.abs(gongweiChange);
            sortedData.addAll(Arrays.asList(matrixJiuxin).subList(resize, matrixJiuxin.length));
            sortedData.addAll(Arrays.asList(matrixJiuxin).subList(0, resize));
        }
        for (int i = 0; i < sortedData.size(); i++) {
            jiuxingMap.put(gongweishu2[i], sortedData.get(i));
        }
        jiuxingMap.put(5, "天禽");
        sortMapDataToList(jiuxingMap, JX);
    }

    private void sortedBashen() {
        String[] yangdunBashen = {"值符", "腾蛇", "太阴", "六合", "白虎", "玄武", "九地", "九天"};
        String[] yindunBashen = {"九天", "九地", "玄武", "白虎", "六合", "太阴", "腾蛇", "值符"};
        ArrayList<String> aroundJx = MatrixUtil.clockwiseTheNum(MatrixUtil.listToMatrix(jiuxingData));
        int pJx = 0;
        //中宫天禽星永远寄存在天芮星
        List<String> sbData = new ArrayList<>(8);
        if (isYangdun) {
            for (int i = 0; i < 8; i++) {
                if (aroundJx.get(i).equals(zhifuxing)) {
                    pJx = i;
                }
            }
            Log.e(TAG, "pJx: " + pJx);
            Log.e(TAG, "length: " + yangdunBashen.length);
            if (pJx == 0) {
                sbData.addAll(Arrays.asList(yangdunBashen));
            } else {
                sbData.addAll(Arrays.asList(yangdunBashen).subList(8 - pJx, 8));
                sbData.addAll(Arrays.asList(yangdunBashen).subList(0, 8 - pJx));
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (aroundJx.get(i).equals(zhifuxing)) {
                    pJx = i;
                }
            }
            int differ = 7 - pJx;
            Log.e(TAG, "pZf: " + pJx);
            Log.e(TAG, "differ: " + differ);
            if (differ == 0) {
                sbData.addAll(Arrays.asList(yindunBashen));
            } else {
                sbData.addAll(Arrays.asList(yindunBashen).subList(differ, yindunBashen.length));
                sbData.addAll(Arrays.asList(yindunBashen).subList(0, differ));
            }

        }
        for (int i = 0; i < sbData.size(); i++) {
            if (i < 9)
                bashenMap.put(gongweishu2[i], sbData.get(i));
        }
        bashenMap.put(5, "");
        sortMapDataToList(bashenMap, BS);
    }


    /**
     * @param map 把九宫位置按照recycler的index顺序排序
     */
    private void sortMapDataToList(Map<Integer, String> map, String flag) {
        for (int i = 0; i < 9; i++) {
            switch (flag) {
                case DPG:
                    dipanganData.add(i, map.get(gongweishu[i]));
                    break;
                case TPG:
                    tianpanganData.add(i, map.get(gongweishu[i]));
                    break;
                case JX:
                    jiuxingData.add(i, map.get(gongweishu[i]));
                    break;
                case BM:
                    bamenData.add(i, map.get(gongweishu[i]));
                    break;
                case BS:
                    bashenData.add(i, map.get(gongweishu[i]));
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvback:
                finish();
                break;
            case R.id.tvlast:
                time = time - 3600000 * 2;
                initData(time);
                break;
            case R.id.tvnext:
                time = time + 3600000 * 2;
                initData(time);
                break;
        }
    }
}
