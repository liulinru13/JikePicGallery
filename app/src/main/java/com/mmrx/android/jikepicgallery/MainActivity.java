package com.mmrx.android.jikepicgallery;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mmrx.android.jpic.IJikePicSource;
import com.mmrx.android.jpic.JikePicGallery;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		JikePicGallery gallery = findViewById(R.id.gallery);

		final String[] picArray = new String[]{
				"http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg",
				"http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg",
				"http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg",
				"http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg",
				"http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg",
				"http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg",
				"http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg",
				"http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg",
				"http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg",
				"http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg"
		};

		gallery.setPicSource(new IJikePicSource() {
			@Override
			public void jikePicFill(ImageView imageView, int position) {
				Glide.with(MainActivity.this)
						.load(picArray[position])
						.into(imageView);
			}

			@Override
			public int getDateSize() {
				return picArray.length;
			}

			@Override
			public void notifyCurrentIndex(int position, int total) {
				return;
			}
		});
		gallery.setCurrentItem(3);
	}


}