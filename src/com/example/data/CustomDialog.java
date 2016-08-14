package com.example.data;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressWarnings("unused")
public class CustomDialog extends Dialog {

	private EditText editText, editText2;
	private Button positiveButton, negativeButton;
	private TextView title;

	public CustomDialog(Context context) {
		super(context, R.style.Dialog);
		setCustomDialog();
	}

	private void setCustomDialog() {
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
		title = (TextView) mView.findViewById(R.id.title);
		editText = (EditText) mView.findViewById(R.id.userName);
		editText2 = (EditText) mView.findViewById(R.id.Myemail);
		positiveButton = (Button) mView.findViewById(R.id.positiveButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		super.setContentView(mView);
	}

	public View getEditText() {
		return editText;
	}
	
	public View getEditText2() {
		return editText2;
	}

	@Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
	}

	@Override
	public void setContentView(View view) {
	}

	/**
	 * È·¶¨¼ü¼àÌýÆ÷
	 * 
	 * @param listener
	 */
	public void setOnPositiveListener(View.OnClickListener listener) {
		positiveButton.setOnClickListener(listener);
	}

	/**
	 * È¡Ïû¼ü¼àÌýÆ÷
	 * 
	 * @param listener
	 */
	public void setOnNegativeListener(View.OnClickListener listener) {
		negativeButton.setOnClickListener(listener);
	}
}