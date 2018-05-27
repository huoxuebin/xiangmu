package com.example.jingdong.erji;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by huoxuebin on 2018/4/24.
 */

public class ErMyliebiao extends ExpandableListView {


    public ErMyliebiao(Context context) {
        super(context);
    }

    public ErMyliebiao(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ErMyliebiao(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, i);
    }
}
