package com.taxi.taxi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rest_client.ConnectionException;
import rest_client.ParamsException;
import taxi_request.BadLoginException;
import taxi_request.TaxiRequest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	private static final String Info_Taxi = "MyPrefsFile"; // création d'un
															// fichier pour
															// récupérer les
															// infos taxi
	Button ConnexionButton;
	CheckBox RememberCheckBox;
	EditText LoginEditText;
	EditText PasswordEditText;
	private SharedData data;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		data = (SharedData) getApplication();
		ConnexionButton = (Button) findViewById(R.id.ConnexionButton);
		ConnexionButton.setOnClickListener(this);
		RememberCheckBox = (CheckBox) findViewById(R.id.RememberCheckBox);
		RememberCheckBox.setOnClickListener(this);
		LoginEditText = (EditText) findViewById(R.id.LoginEditText);
		LoginEditText.setOnClickListener(this);
		PasswordEditText = (EditText) findViewById(R.id.PasswordEditText);
		PasswordEditText.setOnClickListener(this);
		SharedPreferences sp = getSharedPreferences(Info_Taxi, 0);// Méthode
																	// pour
																	// récupérer
																	// les infos
																	// taxi en
																	// mode
																	// private
		data.login = sp.getString("login", "");
		LoginEditText.setText(data.login);
		data.password = sp.getString("password", "");
		PasswordEditText.setText(data.password);
		if (LoginEditText.length() > 0 && PasswordEditText.length() > 0) {
			RememberCheckBox.setChecked(true);
		}
	}

	@Override
	public void onClick(View v) {

		data.login = LoginEditText.getText().toString();
		data.password = PasswordEditText.getText().toString();
		Pattern p = Pattern.compile("[a-z]{6}[0-9]{3}");
		Matcher m = p.matcher(data.login);
		Pattern p2 = Pattern.compile("[a-z0-9]{6,}");
		Matcher m2 = p2.matcher(data.password);
		SharedPreferences sp = getSharedPreferences(Info_Taxi, 0);
		SharedPreferences.Editor editor = sp.edit();
		switch (v.getId()) {
		case R.id.ConnexionButton:

			if (m.matches()) {

				if (m2.matches()) {
					Intent intent = new Intent(Main.this, Menu.class);
					TaxiRequest req = new TaxiRequest(
							"http://88.184.190.42:8080");
					try {
						data.idTaxi = req.connexion(data.login, data.password);
						startActivity(intent);
					} catch (ParamsException e) {
						e.printStackTrace();
					} catch (BadLoginException e) {
						Toast.makeText(this, "Identifiants incorrects",
								Toast.LENGTH_SHORT).show();
					} catch (ConnectionException e) {
						Toast.makeText(this,
								"La connexion au serveur a �chou�",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					PasswordEditText.setText("");
				}
			} else {
				LoginEditText.setText("");
			}
			break;
		case R.id.RememberCheckBox:
			if (RememberCheckBox.isChecked()) {
				if (m.matches() && m2.matches()) {
					editor.putString("password", data.password);
					editor.putString("login", data.login);
					editor.commit();
				} else {
					PasswordEditText.setText("");
					LoginEditText.setText("");
				}
			} else {
				editor.putString("password", "");
				editor.putString("login", "");
				editor.commit();
				LoginEditText.setText("");
				PasswordEditText.setText("");
			}
			break;
		default:

			break;
		}
	}
}
