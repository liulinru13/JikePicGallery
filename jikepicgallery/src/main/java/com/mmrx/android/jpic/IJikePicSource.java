package com.mmrx.android.jpic;

import android.widget.ImageView;

/**
 * Created by mmrx on 2021/2/23
 */
public interface IJikePicSource {
	void jikePicFill(ImageView imageView, int position);

	int getDateSize();

	void notifyCurrentIndex(int position, int total);
}
