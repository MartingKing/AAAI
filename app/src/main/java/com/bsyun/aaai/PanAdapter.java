package com.bsyun.aaai;

import android.widget.TextView;

import com.bsyun.aaai.utils.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


public class PanAdapter extends BaseQuickAdapter<PanEntity, BaseViewHolder> {

    private String zhishi;
    public PanAdapter(String s) {
        super(R.layout.item_paipan, null);
        this.zhishi = s;
    }

    @Override
    protected void convert(BaseViewHolder helper, PanEntity item) {
        if (helper.getAdapterPosition() == 4) {
            helper.setBackgroundResource(R.id.ll_container, R.drawable.bg_border_gray_0);
            helper.setBackgroundResource(R.id.tv_gongwei, R.mipmap.bg_center);
            helper.setBackgroundResource(R.id.tv_makong, R.mipmap.bg_center);
            helper.setText(R.id.tv_bamen, "");
            helper.setText(R.id.tv_bashen, "");
            helper.setText(R.id.tv_tiangan1, "");
            helper.setText(R.id.tv_tianpangan, "");
            helper.setText(R.id.tv_dipangan, item.getDipangan());
            helper.setText(R.id.tv_jiuxing1, "");
            helper.setText(R.id.tv_jiuxing2, "");
            helper.setText(R.id.tv_menke, "");
            helper.setText(R.id.tv_xingke, "");
            helper.setText(R.id.tv_changsheng, "");
        } else {
            helper.setBackgroundResource(R.id.tv_gongwei, item.getGongwei());
            helper.setText(R.id.tv_bamen, item.getBamen());
            if (item.getBamen().equals(zhishi)) {
                helper.getView(R.id.ll_container).setBackgroundColor(AppUtils.getApp().getResources().getColor(R.color.halforig));
            }
            if (item.getBashen().equals("值符")){
                TextView view = helper.getView(R.id.tv_bashen);
                view.setBackgroundColor(AppUtils.getApp().getResources().getColor(R.color.colorPrimaryDark));
            }
            helper.setText(R.id.tv_bashen, item.getBashen());
            helper.setText(R.id.tv_tiangan1, item.getTiangan1());
            helper.setText(R.id.tv_tianpangan, item.getTianpangan());
            helper.setText(R.id.tv_dipangan, item.getDipangan());
            helper.setText(R.id.tv_jiuxing1, item.getJiuxing1());
            helper.setText(R.id.tv_jiuxing2, item.getJiuxing2());
            helper.setText(R.id.tv_menke, item.getMenke());
            helper.setText(R.id.tv_xingke, item.getXingke());
            helper.setText(R.id.tv_changsheng, item.getChangsheng());
            helper.setBackgroundResource(R.id.tv_makong, item.getMakong());
        }

    }
}
