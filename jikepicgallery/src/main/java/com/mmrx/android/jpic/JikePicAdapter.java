package com.mmrx.android.jpic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mmrx on 2021/2/23
 */
class JikePicAdapter extends RecyclerView.Adapter<JikePicAdapter.JikePicHolder> {

	private IJikePicSource mSource;
	private HashMap<Integer, SoftReference<JikePicView>> mPicCache = new HashMap<>();
	@NonNull
	@Override
	public JikePicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new JikePicHolder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.jike_pic_content, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull JikePicHolder holder, int position) {
		// TODO
		if(mSource != null) {
			mSource.jikePicFill(holder.image, position);
		}
		mPicCache.remove(position);
		mPicCache.put(position, new SoftReference<JikePicView>(holder.image));
	}

	public void setPicSource(IJikePicSource mSource) {
		this.mSource = mSource;
	}

	public ImageView getPicViewByPosition(int position){
		if(!mPicCache.isEmpty() && mPicCache.containsKey(position)){
			SoftReference<JikePicView> sr = mPicCache.get(position);
			if(sr != null && sr.get() != null){
				return sr.get();
			}
		}
		return null;
	}

	@Override
	public int getItemCount() {
		return mSource != null ? mSource.getDateSize() : 0;
	}

	static class JikePicHolder extends RecyclerView.ViewHolder{
		JikePicView image;

		public JikePicHolder(@NonNull View itemView) {
			super(itemView);
			image = itemView.findViewById(R.id.j_image);
		}
	}

//	static class JikePicData {
//		boolean isFromNet;
//		String url;
//		String filePath;
//	}
}

