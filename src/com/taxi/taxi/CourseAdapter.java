package com.taxi.taxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import core.course.CourseTaxi;

public class CourseAdapter extends ArrayAdapter<CourseTaxi> {
	private LayoutInflater inf;
	private int textViewResourceId;

	public CourseAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inf = LayoutInflater.from(context);
		this.textViewResourceId = textViewResourceId;
	}

	public CourseAdapter(Context context, int textViewResourceId,
			CourseTaxi[] items) {
		super(context, textViewResourceId, items);
		inf = LayoutInflater.from(context);
		this.textViewResourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View ConvertView, ViewGroup parent) {
		LinearLayout layout;
		if (ConvertView == null)
			layout = (LinearLayout) inf.inflate(textViewResourceId, parent,
					false);
		else
			layout = (LinearLayout) ConvertView;
		TextView destination = (TextView) layout
				.findViewById(R.id.DestinationText);
		TextView tempsClient = (TextView) layout
				.findViewById(R.id.TempsClientText);
		TextView distanceClient = (TextView) layout
				.findViewById(R.id.DistanceClientText);
		destination.setText("Destination : " + getItem(position).target);
		tempsClient.setText("Temps jusqu'au client : "
				+ getItem(position).tempsClient);
		distanceClient.setText("Distance jusqu'au client : "
				+ getItem(position).distanceClient);
		return layout;
	}
}
