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

	private Button TelephoneButton;
	private Button NavClientButton;
	private Button NavDestButton;
	private Button AnnulerButton;
	private Button TerminerButton;
	private SharedData data;
	private TextView nomclientTxtbox;
	private TextView prenomclientTxtBox;
	private TextView telclientTxtBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course);
		data = (SharedData) getApplication();
		TelephoneButton = (Button) findViewById(R.id.dialogInfoBtnTelephoner);
		TelephoneButton.setOnClickListener(this);
		NavClientButton = (Button) findViewById(R.id.dialogInfoBtnNavClient);
		NavClientButton.setOnClickListener(this);
		NavDestButton = (Button) findViewById(R.id.dialogInfoBtnNavDest);
		NavDestButton.setOnClickListener(this);
		AnnulerButton = (Button) findViewById(R.id.dialogInfoBtnAnnuler);
		AnnulerButton.setOnClickListener(this);
		TerminerButton = (Button) findViewById(R.id.dialogInfoBtnTerminer);
		TerminerButton.setOnClickListener(this);
		nomclientTxtbox = (TextView) findViewById(R.id.nomclient);
		nomclientTxtbox.setText("Nom : " + data.activCourse.cl.nom);
		prenomclientTxtBox = (TextView) findViewById(R.id.prenomclient);
		prenomclientTxtBox.setText("Prénom : " + data.activCourse.cl.prenom);
		telclientTxtBox = (TextView) findViewById(R.id.telclient);
		telclientTxtBox.setText("Téléphone : " + data.activCourse.cl.phone);
	}

	public void onClick(View v) {

		TaxiRequest req = new TaxiRequest("http://88.184.190.42:8080");
		Intent intent = new Intent(AfficheCourse.this, Menu.class);
		switch(v.getId()) {
		case R.id.dialogInfoBtnTelephoner:
			Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+data.activCourse.cl.phone));
			startActivity(intent2);
			break;

		case R.id.dialogInfoBtnNavClient:
			String uri = "google.navigation:q="+data.activCourse.cl.position.lat+","+data.activCourse.cl.position.lon;
			Intent intent3 = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
			intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent3);
			break;
		case R.id.dialogInfoBtnNavDest:
			String uri1 = "google.navigation:q="+data.activCourse.target;
			Intent intent4 = new Intent(Intent.ACTION_VIEW,Uri.parse(uri1));
			startActivity(intent4);

			break;
		case R.id.dialogInfoBtnAnnuler:
			try {
				req.cancelCourse(data.idTaxi, data.password, data.activCourse.id);
				data.activCourse = null;
			} catch(ParamsException e1) {
				e1.printStackTrace();
			} catch(CourseNotFoundException e1) {
				e1.printStackTrace();
			} catch(CourseIdTaxiException e1) {
				e1.printStackTrace();
			} catch(ConnectionException e) {
				Toast.makeText(this, "La connexion au serveur a échoué",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			startActivity(intent);
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
			startActivity(intent);
			break;
		}
	}

}
