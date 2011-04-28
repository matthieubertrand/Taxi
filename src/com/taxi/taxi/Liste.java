package com.taxi.taxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import core.course.Client;
import core.course.CourseTaxi;
import core.localisation.GeoPoint;

public class Liste extends Activity implements OnItemClickListener {
	int textViewResourceId;
	CourseTaxi courseTaxi;
	int idCourse;
	CourseAdapter courseAdapter;
	ListView ls;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste);
		ls = (ListView) findViewById(R.id.list);
		courseAdapter = new CourseAdapter(this, R.layout.listview);
		courseAdapter.setNotifyOnChange(true);
		CourseTaxi t = new CourseTaxi(1, "Paris", new Client("bertrand",
				"matthieu", new GeoPoint(0.214587, 0.745898), "0675659896"),
				"12.35kms", "30 secondes");
		courseAdapter.add(t);
		CourseTaxi r = new CourseTaxi(1, "New york", new Client("Bizeau",
				"Clement", new GeoPoint(0.214587, 0.745898), "0675659896"),
				"12.35kms", "30 secondes");
		courseAdapter.add(r);
		courseAdapter.notifyDataSetChanged();
		ls.setAdapter(courseAdapter);
		ls.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		CourseTaxi c = courseAdapter.getItem(position);
		ConfirmCourseDialog dialbox = new ConfirmCourseDialog(this);
		dialbox.show();

	}

}
