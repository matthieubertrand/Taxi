package com.taxi.taxi;

import bing.DirectionNotFoundException;
import rest_client.ConnectionException;
import rest_client.CourseNotFoundException;
import rest_client.ParamsException;
import taxi_directions.TaxiDirections;
import taxi_request.TaxiRequest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import core.course.CourseTaxi;

public class ConfirmCourseDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Button validerButton;
	private Button annulerButton;
	private Liste parent;
	private CourseTaxi course;
	private TextView distDestTxtBox;
	private TextView distClientTxtBox;
	private TextView tempsclientTxtBox;
	private SharedData data;
	private TextView tempsdistTxtBox;

	public ConfirmCourseDialog(Context context, CourseTaxi c) {
		super(context);
		setContentView(R.layout.dialoguebox);
		data = (SharedData) ((Liste) context).getApplication();
		annulerButton = (Button) findViewById(R.id.AnnulerButton);
		annulerButton.setOnClickListener(this);
		validerButton = (Button) findViewById(R.id.ValiderButton);
		validerButton.setOnClickListener(this);
		parent = (Liste) context;
		course = c;
		distDestTxtBox = (TextView) findViewById(R.id.distdest);
		distClientTxtBox = (TextView) findViewById(R.id.distclient);
		tempsclientTxtBox = (TextView) findViewById(R.id.tempsclient);
		tempsdistTxtBox = (TextView) findViewById(R.id.tempsdest);
		try {
			course = TaxiDirections.getCourseDestInfos(course);
		} catch(DirectionNotFoundException e) {
			e.printStackTrace();
			course.distanceDestination = "inconnue";
			course.tempsDestination = "inconnu";
		}
		distDestTxtBox.setText("Distance de la course : "
				+ course.distanceDestination);
		distClientTxtBox.setText("Distance jusqu'au client : "
				+ course.distanceClient);
		tempsclientTxtBox.setText("Temps jusqu'au client : "
				+ course.tempsClient);
		tempsdistTxtBox.setText("Temps de la course : "
				+ course.tempsDestination);
	}

	@Override
	public void onClick(View v) {
		Intent afficherCourseIntent = new Intent(parent, AfficheCourse.class);
		switch(v.getId()) {
		case R.id.AnnulerButton:
			dismiss();
			break;
		case R.id.ValiderButton:
			TaxiRequest req = new TaxiRequest(Main.SERVER_ADDR);
			try {
				req.choisirCourse(data.idTaxi, data.password, course.id);
				data.activCourse = course;
				parent.startActivity(afficherCourseIntent);
				dismiss();
				parent.finish();
			} catch(ParamsException e) {
				e.printStackTrace();
			} catch(CourseNotFoundException e) {
				e.printStackTrace();
			} catch(ConnectionException e) {
				e.printStackTrace();
			}
			break;
		}
	}
}
