package com.example.big.RecyclerVolleyOfflineCache.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.example.big.RecyclerVolleyOfflineCache.R;


public class CardViewHolder extends RecyclerView.ViewHolder {
	protected TextView card_textview_name;
	protected NetworkImageView card_networkimageview;
	protected TextView card_textview_intro;
	//protected ImageLoader imageLoader;
	protected int imageNotAvailableID;

	public CardViewHolder(View itemView) {
		super(itemView);
		card_textview_name = (TextView) itemView.findViewById(R.id.card_textview_name);
		card_networkimageview = (NetworkImageView) itemView.findViewById(R.id.card_networkimageview);
		card_textview_intro = (TextView) itemView.findViewById(R.id.card_textview_intro);
		//imageLoader = MySingleton.getInstance(itemView.getContext()).getImageLoader();
		imageNotAvailableID = R.drawable.image_not_available;
	}
}
