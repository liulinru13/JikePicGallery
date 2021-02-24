# JikePicGallery
仿即刻图片浏览的卷轴效果


![demo示例](https://github.com/liulinru13/JikePicGallery/blob/main/image/demo.gif)

## 使用说明

### 布局

```xml
 <com.mmrx.android.jpic.JikePicGallery
        android:id="@+id/gallery"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

```

### 使用

```java
		JikePicGallery gallery = findViewById(R.id.gallery);
		
		// 设置数据源
		gallery.setPicSource(new IJikePicSource() {
			@Override
			public void jikePicFill(ImageView imageView, int position) {
			// 图片加载交给业务方处理
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
				// 当前展示到第几张图片的位置回调
				return;
			}
		});
		// 设置展示第几张图片，默认第一张
		gallery.setCurrentItem(3);
```

完整代码看 module app 中示例代码


