package com.taxi.taxi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ConfirmCourseDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Button ValiderButton;
	private Button AnnulerButton;
	private Liste parent;

	public ConfirmCourseDialog(Context context) {
		super(context);
		setContentView(R.layout.dialoguebox);
		AnnulerButton = (Button) findViewById(R.id.AnnulerButton);
		AnnulerButton.setOnClickListener(this);
		ValiderButton = (Button) findViewById(R.id.ValiderButton);
		ValiderButton.setOnClickListener(this);
		parent = (Liste) context;
	}

	public void onClick(View v) {
		Intent intent = new Intent(parent, AfficheCourse.class);
		switch(v.getId()) {
		case R.id.AnnulerButton:
			dismiss();

			break;
		case R.id.ValiderButton:
			parent.startActivity(intent);
			break;

		}
	}
}
