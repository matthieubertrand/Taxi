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
	public static final String SERVER_ADDR = "http://88.184.190.42:8080";
	private static final String FILE_INFO_TAXI = "MyPrefsFile";
	private Button connexionButton;
	private CheckBox rememberCheckBox;
	private EditText loginEditText;
	private EditText passwordEditText;
	private SharedData data;
	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		data = (SharedData) getApplication();
		connexionButton = (Button) findViewById(R.id.ConnexionButton);
		connexionButton.setOnClickListener(this);
		rememberCheckBox = (CheckBox) findViewById(R.id.RememberCheckBox);
		rememberCheckBox.setOnClickListener(this);
		loginEditText = (EditText) findViewById(R.id.LoginEditText);
		loginEditText.setOnClickListener(this);
		passwordEditText = (EditText) findViewById(R.id.PasswordEditText);
		passwordEditText.setOnClickListener(this);
		sp = getSharedPreferences(FILE_INFO_TAXI, 0);
		data.login = sp.getString("login", "");
		loginEditText.setText(data.login);
		data.password = sp.getString("password", "");
		passwordEditText.setText(data.password);
		if(loginEditText.length() > 0 && passwordEditText.length() > 0)
			rememberCheckBox.setChecked(true);
	}

	@Override
	public void onClick(View v) {
		data.login = loginEditText.getText().toString();
		data.password = passwordEditText.getText().toString();
		Pattern loginPattern = Pattern.compile("[a-z]{6}[0-9]{3}");
		Matcher loginMatcher = loginPattern.matcher(data.login);
		// FIXME trouver le pattern pour le mot de passe
		Pattern passwordPattern = Pattern.compile("[a-z0-9]{6,}");
		Matcher passwordMatcher = passwordPattern.matcher(data.password);
		if(!loginMatcher.matches() || !passwordMatcher.matches()) {
			if(!loginMatcher.matches() && !passwordMatcher.matches())
				Toast.makeText(this, "Login et pasword incorrects",
						Toast.LENGTH_SHORT).show();
			else if(!loginMatcher.matches())
				Toast.makeText(this, "Le login est incorrect",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this, "Le password est incorrect",
						Toast.LENGTH_SHORT).show();
			return;
		}
		switch(v.getId()) {
		case R.id.ConnexionButton:
			Intent intent = new Intent(Main.this, Menu.class);
			TaxiRequest req = new TaxiRequest(SERVER_ADDR);
			try {
				data.idTaxi = req.connexion(data.login, data.password);
				startActivity(intent);
			} catch(ParamsException e) {
				e.printStackTrace();
			} catch(BadLoginException e) {
				Toast.makeText(this, "Identifiants incorrects",
						Toast.LENGTH_SHORT).show();
			} catch(ConnectionException e) {
				Toast.makeText(this, "La connexion au serveur a échouée",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.RememberCheckBox:
			SharedPreferences.Editor editor = sp.edit();
			if(rememberCheckBox.isChecked()) {
				editor.putString("password", data.password);
				editor.putString("login", data.login);
				editor.commit();
			} else {
				editor.putString("password", "");
				editor.putString("login", "");
				editor.commit();
				loginEditText.setText("");
				passwordEditText.setText("");
				rememberCheckBox.setChecked(false);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onPause() {
		if(rememberCheckBox.isChecked()) {
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("password", data.password);
			editor.putString("login", data.login);
			editor.commit();
		}
		super.onStop();
	}
}
