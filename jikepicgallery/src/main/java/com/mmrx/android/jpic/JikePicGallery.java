package com.mmrx.android.jpic;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;

/**
 * Created by mmrx on 2021/2/23
 */
public class JikePicGallery extends FrameLayout {

	private static final String TAG = "JikePicGallery";

	private View mRootView;
	private ViewPager2 mViewPager;
	private JikePicAdapter mAdapter;
	private IJikePicSource mPicSource;
	private View mDivider;

	private int mCurrentPosition = -1;
	private int mScrollState = SCROLL_STATE_IDLE;
	private float lastPositionOffset;
	private int mDividerOriginWidth;// 原始宽度
	private int mScreenWidth; // 屏幕宽度
	private float mDividerSpeed; // 分割线移动速度
	private float mPicScrollDis; // 图片位移
	private boolean mStartScroll = false;

	public JikePicGallery(@NonNull Context context) {
		this(context, null);
	}

	public JikePicGallery(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public JikePicGallery(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init() {
		WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay = windowManager.getDefaultDisplay();
		Point point = new Point();
		defaultDisplay.getSize(point);
		mDividerOriginWidth = point.x / 15;
		mScreenWidth = point.x;
		mDividerSpeed = (float) (mDividerOriginWidth * 2 + mScreenWidth) / mScreenWidth;
		mPicScrollDis = mScreenWidth/4f;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mRootView = LayoutInflater.from(getContext()).inflate(R.layout.jike_pic_gallery_layout, this, true);
		mViewPager = mRootView.findViewById(R.id.jike_pic_viewpager);
		mAdapter = new JikePicAdapter();
		mViewPager.setAdapter(mAdapter);
		mDivider = findViewById(R.id.jike_divider);
		init();

		mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				super.onPageScrolled(position, positionOffset, positionOffsetPixels);
				Log.d(TAG, "onPageScrolled mCurrentPosition " + mCurrentPosition + " position "
						+ position + " positionOffset: " + positionOffset + " positionOffsetPixels " + positionOffsetPixels);

				resetDivider(false);
				move(-positionOffsetPixels, true, positionOffset);

//				if(position < mCurrentPosition){
//					// 上一张
//
//				}else {
//					// 下一张
//					ImageView next = mAdapter.getPicViewByPosition(position+1);
//					if(!mStartScroll){
//						if(next != null){
//							next.setX(mPicScrollDis);
//						}
//						mStartScroll = true;
//					}
//					if(next != null){
//						if(positionOffset <=0.5f){
//							next.setX(-mPicScrollDis + positionOffset*2*mPicScrollDis);
//							Log.d(TAG, "next  " + (position+1) + " currx " + next.getX() );
//						}
//					}
//				}
			}

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				Log.d(TAG, "onPageSelected " + position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
				super.onPageScrollStateChanged(state);
				mScrollState = state;
				if (state == SCROLL_STATE_IDLE) {
					mStartScroll = false;
					resetDivider(false);
					mCurrentPosition = mViewPager.getCurrentItem();
					if (mPicSource != null) {
						mPicSource.notifyCurrentIndex(mCurrentPosition, mAdapter.getItemCount());
					}
//					ImageView next = mAdapter.getPicViewByPosition(mCurrentPosition + 1);
//					if(next != null){
////						next.setX(mPicScrollDis);
//					}
//					ImageView last = mAdapter.getPicViewByPosition(mCurrentPosition - 1);
//					if(last != null){
////						last.setX(mPicScrollDis);
//					}
				}
				Log.d(TAG, "onPageScrollStateChanged " + state);
			}
		});
	}

	public void setCurrentItem(int index) {
		mViewPager.setCurrentItem(index, false);
		mCurrentPosition = mViewPager.getCurrentItem();
	}

	public void setPicSource(IJikePicSource mPicSource) {
		this.mPicSource = mPicSource;
		if (mAdapter != null) {
			mAdapter.setPicSource(this.mPicSource);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void resetDivider(boolean left) {
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mDivider.getLayoutParams();
		layoutParams.width = mDividerOriginWidth;
		mDivider.setLayoutParams(layoutParams);
		mDivider.setX(left ? -layoutParams.width : mScreenWidth);
	}

	/**
	 * @param distance 图片移动距离
	 * @param next     是否是下一张图片
	 * @param percent  移动百分比
	 */
	private void move(float distance, boolean next, float percent) {
		float currX = mDivider.getX();
		float deltX = mDividerSpeed * distance + currX;
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mDivider.getLayoutParams();
		layoutParams.width = mDividerOriginWidth + (int) ((next && percent > 0.5f) ? mDividerOriginWidth * percent : mDividerOriginWidth * (1 - percent));

		mDivider.setLayoutParams(layoutParams);
		mDivider.setX(deltX);

	}
}
