package com.taxi.taxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AfficheCourse extends Activity implements OnClickListener {

	private Button TelephoneButton;
	private Button NavClientButton;
	private Button NavDestButton;
	private Button AnnulerButton;
	private Button TerminerButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course);
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
	}

	public void onClick(View v) {

		switch(v.getId()) {
		case R.id.dialogInfoBtnTelephoner:

			break;

		case R.id.dialogInfoBtnNavClient:

			break;
		case R.id.dialogInfoBtnNavDest:

			break;
		case R.id.dialogInfoBtnAnnuler:

			break;
		case R.id.dialogInfoBtnTerminer:

			break;
		}
	}

}
