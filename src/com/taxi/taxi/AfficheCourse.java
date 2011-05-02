package com.taxi.taxi;

import rest_client.ConnectionException;
import rest_client.CourseIdTaxiException;
import rest_client.CourseNotFoundException;
import rest_client.ParamsException;
import taxi_request.TaxiRequest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AfficheCourse extends Activity implements OnClickListener {
	private Button telephoneButton;
	private Button navClientButton;
	private Button navDestButton;
	private Button annulerButton;
	private Button terminerButton;
	private SharedData data;
	private TextView nomclientTxtbox;
	private TextView prenomclientTxtBox;
	private TextView telclientTxtBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course);
		data = (SharedData) getApplication();
		telephoneButton = (Button) findViewById(R.id.dialogInfoBtnTelephoner);
		telephoneButton.setOnClickListener(this);
		navClientButton = (Button) findViewById(R.id.dialogInfoBtnNavClient);
		navClientButton.setOnClickListener(this);
		navDestButton = (Button) findViewById(R.id.dialogInfoBtnNavDest);
		navDestButton.setOnClickListener(this);
		annulerButton = (Button) findViewById(R.id.dialogInfoBtnAnnuler);
		annulerButton.setOnClickListener(this);
		terminerButton = (Button) findViewById(R.id.dialogInfoBtnTerminer);
		terminerButton.setOnClickListener(this);
		nomclientTxtbox = (TextView) findViewById(R.id.nomclient);
		nomclientTxtbox.setText("Nom : " + data.activCourse.cl.nom);
		prenomclientTxtBox = (TextView) findViewById(R.id.prenomclient);
		prenomclientTxtBox.setText("Pr�nom : " + data.activCourse.cl.prenom);
		telclientTxtBox = (TextView) findViewById(R.id.telclient);
		telclientTxtBox.setText("T�l�phone : " + data.activCourse.cl.phone);
	}

	@Override
	public void onClick(View v) {
		TaxiRequest req = new TaxiRequest("http://88.184.190.42:8080");
		switch(v.getId()) {
		case R.id.dialogInfoBtnTelephoner:
			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ data.activCourse.cl.phone));
			startActivity(callIntent);
			break;
		case R.id.dialogInfoBtnNavClient:
			String navClientUri = "google.navigation:q="
					+ data.activCourse.cl.position.lat + ","
					+ data.activCourse.cl.position.lon;
			Intent navClientIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(navClientUri));
			navClientIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(navClientIntent);
			break;
		case R.id.dialogInfoBtnNavDest:
			String navDestUri = "google.navigation:q="
					+ data.activCourse.target;
			Intent nacDestIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(navDestUri));
			startActivity(nacDestIntent);
			break;
		case R.id.dialogInfoBtnAnnuler:
			try {
				req.cancelCourse(data.idTaxi, data.password,
						data.activCourse.id);
				data.activCourse = null;
			} catch(ParamsException e1) {
				e1.printStackTrace();
			} catch(CourseNotFoundException e1) {
				e1.printStackTrace();
			} catch(CourseIdTaxiException e1) {
				e1.printStackTrace();
			} catch(ConnectionException e) {
				Toast.makeText(this, "La connexion au serveur a �chou�",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			finish();
			break;
		case R.id.dialogInfoBtnTerminer:
			try {
				req.endCourse(data.idTaxi, data.password, data.activCourse.id);
				data.activCourse = null;
			} catch(ParamsException e) {
				e.printStackTrace();
			} catch(CourseNotFoundException e) {
				e.printStackTrace();
			} catch(CourseIdTaxiException e) {
				e.printStackTrace();
			} catch(ConnectionException e) {
				e.printStackTrace();
			}
			finish();
			break;
		}
	}
}
