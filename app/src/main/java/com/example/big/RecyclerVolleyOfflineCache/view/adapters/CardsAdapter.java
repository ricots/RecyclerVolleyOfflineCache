package com.example.big.RecyclerVolleyOfflineCache.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.toolbox.ImageLoader;
import com.example.big.RecyclerVolleyOfflineCache.utils.network.MySingleton;
import com.example.big.RecyclerVolleyOfflineCache.R;
import com.example.big.RecyclerVolleyOfflineCache.model.Point;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardViewHolder> {

	//Contains the list of Points
	private List<Point> mPointList;
	private LayoutInflater mLayoutInflater;
	private MySingleton mMySingleton;
	private ImageLoader mImageLoader;

    CardsAdapter (List<Point> pointList){
        mPointList  = pointList;
    }
	public CardsAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		mMySingleton = MySingleton.getInstance(context);
		mImageLoader = mMySingleton.getImageLoader();
	}

	public void setPoints(List<Point> pointList){
		mPointList = pointList;
		//update the adapter to reflect the new set of Points
		notifyDataSetChanged();
	}

	@Override
	public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		//TODO: Check if this layout is the correct!!
		View view = mLayoutInflater.inflate(R.layout.view_holder_card, viewGroup, false);

		CardViewHolder cardViewHolder = new CardViewHolder(view);
		return cardViewHolder;
	}

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if (mPointList.isEmpty()){
            holder.card_textview_name.setText("Name");
            holder.card_textview_intro.setText("intro");
            holder.card_networkimageview.setImageResource(holder.imageNotAvailableID);
        }else {
            //Get the whole point this view is about
            Point currentPoint = mPointList.get(position);

            //set the data to the appropriate views (the +"" is for some weird bug i dont get, dont remove it)
            holder.card_textview_name.setText(currentPoint.getName() + "");
            holder.card_textview_intro.setText(currentPoint.getIntro() + "");


            String highresImageUrl = currentPoint.getHighResImageUrl1();
            if (!highresImageUrl.isEmpty()) {
                System.out.println("Trying to get image for position " + position);

                holder.card_networkimageview.setImageUrl(currentPoint.getHighResImageUrl1(), mImageLoader);
            } else {
                System.out.println("No HighResImage for position " + position);
                holder.card_networkimageview.setImageResource(holder.imageNotAvailableID);
            }
        }
    }

	@Override
	public int getItemCount() {
        if (mPointList==null){
            return 0;
        }else{
            return mPointList.size();
        }

	}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
