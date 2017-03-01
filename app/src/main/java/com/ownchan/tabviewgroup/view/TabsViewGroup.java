package com.ownchan.tabviewgroup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ownchan.tabviewgroup.R;

/**
 * Author: Owen Chan
 * DATE: 2017-02-27.
 */

public class TabsViewGroup extends ViewGroup {

    private static String TAG = TabsViewGroup.class.getSimpleName();

    private int childHorizontalSpace;
    private int childVerticalSpace;

    public TabsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TabsViewGroup);
        if (attrArray != null) {
            childHorizontalSpace = attrArray.getDimensionPixelSize(R.styleable.TabsViewGroup_tabHorizontalSpace, 0);
            childVerticalSpace = attrArray.getDimensionPixelSize(R.styleable.TabsViewGroup_tabHorizontalSpace, 0);
            attrArray.recycle();
        }
    }

    //测量控件的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        //输出宽度高度以及测量模式
        Log.i(TAG, "sizeWidth :" + widthSize);
        switch (widthModel) {
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "Width model AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "Width model EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "Width model UNSPECIFIED");
                break;
        }

        Log.i(TAG, "sizeHeight :" + heightSize);
        switch (heightModel) {
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "Height model AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "Height model EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "Height model UNSPECIFIED");
                break;
        }
        //左右边距
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //记录每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        // 如果是warp_content情况下，记录宽和高
        int containerWidth = 0;
        int containerHeight = 0;
        // 遍历每个子元素
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin + childHorizontalSpace;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin + childVerticalSpace;

            if (lineWidth + childWidth > widthSize - getPaddingRight() - getPaddingLeft()) {
                containerWidth = Math.max(lineWidth, childWidth);
                lineWidth = childWidth;
                containerHeight += lineHeight;
                lineHeight = childHeight;
                Rect rect = new Rect(left,
                        containerHeight + top,
                        left + childWidth - childHorizontalSpace,
                        containerHeight + top + child.getMeasuredHeight());
                child.setTag(rect);
            } else {
                Rect rect = new Rect(lineWidth + left,
                        containerHeight + top,
                        lineWidth + left + childWidth - childHorizontalSpace,
                        containerHeight + top + child.getMeasuredHeight());
                child.setTag(rect);
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }

        containerWidth = Math.max(containerWidth, lineWidth) + getPaddingLeft() + getPaddingRight();
        containerHeight += lineHeight + getPaddingTop() + getPaddingBottom();
        containerHeight -= childVerticalSpace;
        setMeasuredDimension(widthModel == MeasureSpec.EXACTLY ? widthSize : containerWidth,
                heightModel == MeasureSpec.EXACTLY ? heightSize : containerHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            Rect location = (Rect) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }
}
