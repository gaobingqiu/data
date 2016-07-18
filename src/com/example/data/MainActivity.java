package com.example.data;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DBAdapter db = new DBAdapter(this);
		db.open();
		/**-------------------新增---------------------------*/
		// long id = db.insertContact("gbq", "990924291@qq.com");
		// id = db.insertContact("smj", "99@qq.com");
		/**-------------------新增---------------------------
		 * 
		 * --------------------查询----------------------------*/
//		Cursor c = db.getAllContacts();
//		if (c.moveToFirst()) {
//			do {
//				DisplayContact(c);
//			} while (c.moveToNext());
//		}
		/**----------------------查询-----------------
		 * 
		 *----------------------更新-------------------*/
//		 if (db.updateContact(1, "Wei-Meng Lee", "weimenglee@gmail.com"))
//	            Toast.makeText(this, "Update successful.", Toast.LENGTH_LONG).show();
//	        else
//	            Toast.makeText(this, "Update failed.", Toast.LENGTH_LONG).show();
		 /**----------------------更新-------------------
		  * 
		  *-----------------------删除-----------------*/
		 if (db.deleteContact(1))
	            Toast.makeText(this, "Delete successful.", Toast.LENGTH_LONG).show();
	        else
	            Toast.makeText(this, "Delete failed.", Toast.LENGTH_LONG).show();
		db.close();
	}

	public void DisplayContact(Cursor c) {
		Toast.makeText(this,
				"id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) + "\n" + "Email:  " + c.getString(2),
				Toast.LENGTH_LONG).show();
	}
}
