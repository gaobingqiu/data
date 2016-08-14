package com.example.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText userName, email;
	String tag = "记录id";
	DBAdapter db;
	CustomDialog dialog;
	String personid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DBAdapter(this);
		db.open();
	}

	public void add(View view) {
		dialog();

	}

	public void delete(long id) {
		db = new DBAdapter(this);
		db.open();
		if (db.deleteContact(id))
			Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
		db.close();

	}

	public void update(View view) {
		db = new DBAdapter(this);
		db.open();
		if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
			Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
		db.close();

	}

	public void fetch(View view) {
		ListView listView = (ListView) findViewById(R.id.list);
		LinearLayout layout = (LinearLayout)findViewById(R.id.header);
		layout.setVisibility(View.VISIBLE);//显示header
		db = new DBAdapter(this);
		db.open();
		Cursor c = db.getAllContacts();
		// 创建SimpleCursorAdapter适配器将数据绑定到item显示控件上
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, c,
				new String[] { "_id", "name", "email" }, new int[] { R.id.userId, R.id.user, R.id.email });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				ListView listView = (ListView) adapterView;
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);
				personid = String.valueOf(cursor.getInt(cursor.getColumnIndex("_id")));
				new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")// 设置对话框标题
						.setMessage("请选择操作类型！")// 设置显示的内容
						.setPositiveButton("更新", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
								// TODO Auto-generated method stub
								dialogUpdate(Long.valueOf(personid));
							}
						}).setNegativeButton("删除", new DialogInterface.OnClickListener() {// 添加返回按钮
							@Override
							public void onClick(DialogInterface dialog, int which) {// 响应事件
								// TODO Auto-generated method stub
								delete(Long.valueOf(personid));
							}
						}).show();// 在按键响应事件中显示此对话框
			}
		});
		db.close();

	}

	public void DisplayContact(Cursor c) {
		Toast.makeText(this,
				"id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) + "\n" + "Email:  " + c.getString(2),
				Toast.LENGTH_LONG).show();
	}

	// 弹窗
	private void dialog() {
		dialog = new CustomDialog(MainActivity.this);
		userName = (EditText) dialog.getEditText();// 方法在CustomDialog中实现
		email = (EditText) dialog.getEditText2();// 方法在CustomDialog中实现
		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DBAdapter(MainActivity.this);
				db.open();
				long id = db.insertContact(userName.getText().toString(), email.getText().toString());
				// id = db.insertContact("smj", "99@qq.com");
				Log.d(tag, "" + id);
				db.close();
				dialog.dismiss();
			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	// 弹窗
	private void dialogUpdate(long id) {
		db.open();
		Cursor cursor = db.getContact(id);
		final long userId = id;
		dialog = new CustomDialog(MainActivity.this);
		userName = (EditText) dialog.getEditText();// 方法在CustomDialog中实现
		email = (EditText) dialog.getEditText2();// 方法在CustomDialog中实现
		if (cursor.moveToFirst()) {
			userName.setText(cursor.getString(1).toCharArray(), 0,cursor.getString(1).length());
			email.setText(cursor.getString(2).toCharArray(), 0,cursor.getString(2).length());
		}
		db.close();
		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DBAdapter(MainActivity.this);
				db.open();
				if (db.updateContact(userId, userName.getText().toString(), email.getText().toString()))
					Toast.makeText(MainActivity.this, "Update successful.", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MainActivity.this, "Update failed.", Toast.LENGTH_LONG).show();
				db.close();
				dialog.dismiss();
			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

}
