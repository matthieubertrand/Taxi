package com.taxi.taxi;


import rest_client.ConnectionException;
import taxi_request.BadLoginException;
import taxi_request.TaxiRequest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class Statistiques extends Activity  {
	private SharedData data;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistiques);
		data=(SharedData) getApplication();
		TaxiRequest req = new TaxiRequest(Main.SERVER_ADDR);
		TextView nbcourse = (TextView) findViewById(R.id.NbCOurseAccept);
		try {
			nbcourse.setText(String.valueOf(req.getNbCourse(data.idTaxi, data.password)));
		} catch(ConnectionException e) {
			e.printStackTrace();
		} catch(BadLoginException e) {
			e.printStackTrace();
		}
	}

}