package com.taxi.taxi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import core.localisation.GeoPoint;

public class Menu extends Activity implements OnClickListener, LocationListener {
	private Button ConsulterListeButton;
	private Button StatistiquesButton;
	private Button QuitterButton;
	private Button AboutButton;
	private SharedData data;
	private LocationManager locManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		data = (SharedData) getApplication();
		ConsulterListeButton = (Button) findViewById(R.id.ConsulterListeButton);
		ConsulterListeButton.setOnClickListener(this);
		StatistiquesButton = (Button) findViewById(R.id.StatistiquesButton);
		StatistiquesButton.setOnClickListener(this);
		QuitterButton = (Button) findViewById(R.id.QuitterButton);
		QuitterButton.setOnClickListener(this);
		AboutButton = (Button) findViewById(R.id.AboutButton);
		AboutButton.setOnClickListener(this);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Menu.this, Liste.class);
		Intent intent2 = new Intent(Menu.this, Statistiques.class);
		Intent intent3 = new Intent(Menu.this, Main.class);
		Intent intent4 = new Intent(Menu.this, About.class);
		switch (v.getId()) {
		case R.id.ConsulterListeButton:
			startActivity(intent);
			break;
		case R.id.StatistiquesButton:
			startActivity(intent2);
			break;
		case R.id.QuitterButton:
			startActivity(intent3);
			break;
		case R.id.AboutButton:
			startActivity(intent4);
			break;
		default:
			Log.i("taxi", "WrongIdEvent");
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (data.position == null)
			data.position = new GeoPoint(location.getLatitude(),
					location.getLongitude());
		else {
			data.position.lat = location.getLatitude();
			data.position.lon = location.getLongitude();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
