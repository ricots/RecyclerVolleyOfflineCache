package com.example.big.RecyclerVolleyOfflineCache.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.big.RecyclerVolleyOfflineCache.R;
import com.example.big.RecyclerVolleyOfflineCache.model.Point;
import com.example.big.RecyclerVolleyOfflineCache.utils.network.GsonRequest;
import com.example.big.RecyclerVolleyOfflineCache.utils.network.MySingleton;
import com.example.big.RecyclerVolleyOfflineCache.utils.network.URL;
import com.example.big.RecyclerVolleyOfflineCache.view.adapters.CardsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardsFragment extends Fragment {
	private RecyclerView mRootView;
    RequestQueue queue;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (RecyclerView) inflater.inflate(R.layout.fragment_cards, container, false);


        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.meet_cards_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // maybe getActivity() is more suitable to get context
        final CardsAdapter cardsAdapter = new CardsAdapter(getActivity());

        Point temp = new Point();
        List<Point> tempList = new ArrayList<>();
        tempList.add(new Point());
        //final CardsAdapter cardsAdapter = new CardsAdapter(tempList);

        recyclerView.setAdapter(cardsAdapter);

        if(savedInstanceState != null){

        }

        //TODO: Make the request for the Points!


        // Get a RequestQueue
		queue = MySingleton.getInstance(getActivity()).getRequestQueue();
		@SuppressWarnings("unchecked")

		GsonRequest gsonRequest = new GsonRequest(URL.URL, Point[].class, null, new Response.Listener<Point[]>() {
			@Override
			public void onResponse(Point[] points) {
				try {
					List<Point> pointList = Arrays.asList(points);
					for (Point point : pointList){
						Toast.makeText(getActivity(),point.getDimos(),Toast.LENGTH_LONG).show();
						Log.e("A POINT ", point.getId() + "");
						Log.e("A POINT DIMOS ", point.getDimos());
						Log.e("A POINT image ", point.getHighResImageUrl1());

                        //notify the adapter that the data has been changed
					}
                    cardsAdapter.setPoints(pointList);

				}
				catch (Exception e) {
					Log.e("error", String.valueOf(e));
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				if(volleyError != null) Log.e("MainActivity", volleyError.getMessage());
			}
		});

		queue.add(gsonRequest);

        makeTheRequest();
        return mRootView;
	}

    private void makeTheRequest() {

    }
    //I Dont think that is needed
//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//	}
//
//	private void initRecyclerView(Context context) {
//
//
//		mRootView.setAdapter(new CardsAdapter(context));
//	}

	public static Fragment newInstance() {
		return new CardsFragment();
	}
}





	/* Autogenerated by studio

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	//**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment CardsFragment.
	 /

	// TODO: Rename and change types and number of parameters
	public static CardsFragment newInstance(String param1, String param2) {
		CardsFragment fragment = new CardsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public CardsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_cards, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 *
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}
	*/
