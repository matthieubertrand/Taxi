package com.taxi.taxi;

import gmaps.DirectionException;
import gmaps.DirectionInvalidRequestException;
import gmaps.DirectionNotFoundException;
import gmaps.DirectionZeroResultsException;
import gmaps.OverQueryLimitException;
import java.util.ArrayList;
import java.util.List;
import rest_client.ConnectionException;
import rest_client.CourseEmptyException;
import rest_client.CourseErrorException;
import rest_client.ParamsException;
import taxi_directions.TaxiDirections;
import taxi_request.BadLoginException;
import taxi_request.TaxiRequest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import core.course.Course;
import core.course.CourseTaxi;

public class Liste extends Activity implements OnItemClickListener {
	private static final int UPDATE_UI = 0;
	private static final int UPDATE_TIME = 120000;
	private CourseAdapter courseAdapter;
	private ListView listCoursesDispo;
	private SharedData data;
	private Handler handlerTimer = new Handler();
	private List<CourseTaxi> lCourseTaxi = new ArrayList<CourseTaxi>();
	private List<Course> listCourses;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste);
		listCoursesDispo = (ListView) findViewById(R.id.list);
		courseAdapter = new CourseAdapter(this, R.layout.listview);
		courseAdapter.setNotifyOnChange(true);
		data = (SharedData) getApplication();
		listCoursesDispo.setAdapter(courseAdapter);
		listCoursesDispo.setOnItemClickListener(this);
		if(data.position == null) {
			Toast.makeText(this,
					"Votre position n'a pas encore été determinée",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Log.i("taxi", "start request list");
		handlerTimer.removeCallbacks(updateCourseList);
		handlerTimer.postDelayed(updateCourseList, 500);
	}

	private Runnable updateCourseList = new Runnable() {
		@Override
		public void run() {
			try {
				Log.i("taxi", "go timer");
				TaxiRequest req = new TaxiRequest(Main.SERVER_ADDR);
				listCourses = req.getCourses(data.idTaxi, data.password);
				if(listCourses.size() <= 0)
					lCourseTaxi.clear();
				else {
					for(CourseTaxi c : lCourseTaxi)
						if(!isIn(c)) {
							Log.i("taxi", "remove taxi course");
							lCourseTaxi.remove(c);
						}
					for(Course c : listCourses)
						if(!isIn(c)) {
							Log.i("taxi", "get maps infos");
							CourseTaxi cTaxi;
							try {
								cTaxi = TaxiDirections.getCourseInfos(
										data.position, c);
								lCourseTaxi.add(cTaxi);
							} catch(DirectionNotFoundException e) {
								e.printStackTrace();
							} catch(DirectionInvalidRequestException e) {
								e.printStackTrace();
							} catch(DirectionException e) {
								e.printStackTrace();
							} catch(DirectionZeroResultsException e) {
								e.printStackTrace();
							} catch(OverQueryLimitException e) {
								e.printStackTrace();
								cTaxi = new CourseTaxi(c, "inconnue",
										"inconnue");
								lCourseTaxi.add(cTaxi);
							}
						}
				}
			} catch(CourseEmptyException e) {
				e.printStackTrace();
			} catch(ConnectionException e) {
				e.printStackTrace();
			} catch(ParamsException e) {
				e.printStackTrace();
			} catch(BadLoginException e) {
				e.printStackTrace();
			} catch(CourseErrorException e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.what = UPDATE_UI;
			updateUiEvent.sendMessage(msg);
			handlerTimer.postDelayed(this, UPDATE_TIME);
		}
	};
	private Handler updateUiEvent = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case UPDATE_UI:
				courseAdapter.clear();
				for(CourseTaxi c : lCourseTaxi)
					courseAdapter.add(c);
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

	private boolean isIn(Course c) {
		for(CourseTaxi cTaxi : lCourseTaxi)
			if(c.id == cTaxi.id)
				return true;
		return false;
	}

	private boolean isIn(CourseTaxi cTaxi) {
		for(Course c : listCourses)
			if(c.id == cTaxi.id)
				return true;
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		CourseTaxi c = courseAdapter.getItem(position);
		ConfirmCourseDialog dialbox = new ConfirmCourseDialog(this, c);
		dialbox.show();
	}
}
