package com.bsyun.aaai;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bsyun.aaai.utils.FileUtil;
import com.bsyun.aaai.utils.SpannableStringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends ZBaseActivity {

    private static final String TAG = "ItemDetailActivity";
    List<String> mSG = new ArrayList<>();
    List<String> mKY = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String tpg = getIntent().getStringExtra("tpg");
        String dpg = getIntent().getStringExtra("dpg");
        if (dpg.isEmpty()) {
            dpg = "戊";
        }
        if (tpg.isEmpty()) {
            tpg = "戊";
        }

        TextView desc = findViewById(R.id.describe);
        String[] textContent = FileUtil.getTextContent();
        assert textContent != null;
        for (int i = 0; i < textContent.length; i++) {
            if (i % 2 == 1) {
                mSG.add(textContent[i]);
            } else {
                mKY.add(textContent[i]);
            }
        }
        String ganzhi = tpg + dpg;
        String content = "";
        for (int i = 0; i < mSG.size(); i++) {
            if (mSG.get(i).equals(ganzhi)){
                content = mKY.get(i);
            }
        }
        desc.setText(SpannableStringUtils.getBuilder(tpg + "加" + dpg + ": ")
                .setForegroundColor(ContextCompat.getColor(this, R.color.red))
                .append(content)
                .setForegroundColor(ContextCompat.getColor(this, R.color.black))
                .create());
    }
}
