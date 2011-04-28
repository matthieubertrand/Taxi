package com.taxi.taxi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Main extends Activity implements OnClickListener {
	private static final String Info_Taxi = "MyPrefsFile"; // création d'un
															// fichier pour
															// récupérer les
															// infos taxi
	Button ConnexionButton;
	CheckBox RememberCheckBox;
	EditText LoginEditText;
	EditText PasswordEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
		String login = sp.getString("login", "");
		LoginEditText.setText(login);
		String password = sp.getString("password", "");
		PasswordEditText.setText(password);
		if(LoginEditText.length()>0&&PasswordEditText.length()>0) {
			RememberCheckBox.setChecked(true);
		}
	}

	public void onClick(View v) {

		String login = LoginEditText.getText().toString();
		String password = PasswordEditText.getText().toString();
		Pattern p = Pattern.compile("[a-z]{6}[0-9]{3}");
		Matcher m = p.matcher(login);
		Pattern p2 = Pattern.compile("[a-z0-9]{6,}");
		Matcher m2 = p2.matcher(password);
		SharedPreferences sp = getSharedPreferences(Info_Taxi, 0);
		SharedPreferences.Editor editor = sp.edit();
		switch(v.getId()) {
		case R.id.ConnexionButton:

			if(m.matches()) {

				if(m2.matches()) {
					Intent intent = new Intent(Main.this, Menu.class);
					startActivity(intent);
				} else {
					PasswordEditText.setText("");
				}
			} else {
				Log.i("taxi", "login not match");
				LoginEditText.setText("");
			}
			break;
		case R.id.RememberCheckBox:
			if(RememberCheckBox.isChecked()) {
				if(m.matches() && m2.matches()) {
					editor.putString("password", password);
					editor.putString("login", login);
					editor.commit();
				} else {
					PasswordEditText.setText("");
					LoginEditText.setText("");
				}
			}
			else {
				editor.putString("password", "");
				editor.putString("login", "");
				editor.commit();
				LoginEditText.setText("");
				PasswordEditText.setText("");
			}
			break;
		default:

			Log.i("taxi", "WrongIdEvent");

			break;
		}
	}
}
