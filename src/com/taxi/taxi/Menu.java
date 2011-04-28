package com.taxi.taxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity implements OnClickListener {

	Button ConsulterListeButton;
	Button StatistiquesButton;
	Button QuitterButton;
	Button AboutButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		ConsulterListeButton = (Button) findViewById(R.id.ConsulterListeButton);
		ConsulterListeButton.setOnClickListener(this);
		StatistiquesButton = (Button) findViewById(R.id.StatistiquesButton);
		StatistiquesButton.setOnClickListener(this);
		QuitterButton = (Button) findViewById(R.id.QuitterButton);
		QuitterButton.setOnClickListener(this);
		AboutButton = (Button) findViewById(R.id.AboutButton);
		AboutButton.setOnClickListener(this);

	}

	public void onClick(View v) {

		Intent intent = new Intent(Menu.this, Liste.class);
		Intent intent2 = new Intent(Menu.this, Statistiques.class);
		Intent intent3 = new Intent(Menu.this, Main.class);
		Intent intent4 = new Intent(Menu.this, About.class);

		switch(v.getId()) {

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

}
