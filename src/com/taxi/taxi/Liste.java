package com.taxi.taxi;

import gmaps.DirectionException;
import gmaps.DirectionInvalidRequestException;
import gmaps.DirectionNotFoundException;
import gmaps.DirectionZeroResultsException;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import core.course.Course;
import core.course.CourseTaxi;

public class Liste extends Activity implements OnItemClickListener {
	private CourseAdapter courseAdapter;
	private ListView ls;
	private SharedData data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		TaxiRequest req = new TaxiRequest("http://88.184.190.42:8080");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste);
		ls = (ListView) findViewById(R.id.list);
		courseAdapter = new CourseAdapter(this, R.layout.listview);
		courseAdapter.setNotifyOnChange(true);
		data = (SharedData) getApplication();
		ls.setAdapter(courseAdapter);
		ls.setOnItemClickListener(this);
		if(data.position == null) {
			Toast.makeText(this,
					"Votre position n'a pas encore �t� determin�e",
					Toast.LENGTH_SHORT).show();
			return;
		}
		try {
			List<Course> listCourses = req.getCourses(data.idTaxi,
					data.password);
			for(Course c : listCourses) {
				CourseTaxi cTaxi = TaxiDirections.getCourseInfos(data.position,
						c);
				courseAdapter.add(cTaxi);
				courseAdapter.notifyDataSetChanged();
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
		} catch(DirectionNotFoundException e) {
			e.printStackTrace();
		} catch(DirectionInvalidRequestException e) {
			e.printStackTrace();
		} catch(DirectionException e) {
			e.printStackTrace();
		} catch(DirectionZeroResultsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		CourseTaxi c = courseAdapter.getItem(position);
		ConfirmCourseDialog dialbox = new ConfirmCourseDialog(this, c);
		dialbox.show();
	}
}
