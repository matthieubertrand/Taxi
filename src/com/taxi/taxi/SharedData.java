package com.taxi.taxi;

import android.app.Application;
import core.course.CourseTaxi;
import core.localisation.GeoPoint;

public class SharedData extends Application {
	public String password;
	public String login;
	public int idTaxi;
	public GeoPoint position;
	public CourseTaxi activCourse;
}
