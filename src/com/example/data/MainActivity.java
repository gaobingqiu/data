package com.example.data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	String tag = "���ݿ����";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(tag, "kaise��");
		// �򿪻򴴽�test.db���ݿ�
		SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
		db.execSQL("DROP TABLE IF EXISTS person");
		// ����person��
		db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
		Person person = new Person();
		person.name = "john";
		person.age = 30;
		// ��������
		db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[] { person.name, person.age });

		person.name = "david";
		person.age = 33;
		// ContentValues�Լ�ֵ�Ե���ʽ�������
		ContentValues cv = new ContentValues();
		cv.put("name", person.name);
		cv.put("age", person.age);
		// ����ContentValues�е�����
		db.insert("person", null, cv);
		Log.d(tag, "���������ݣ�");
		cv = new ContentValues();
		cv.put("age", 35);
		// ��������
		db.update("person", cv, "name = ?", new String[] { "john" });
		Log.d(tag, "���������ݣ�");
		Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[] { "33" });
		while (c.moveToNext()) {
			int _id = c.getInt(c.getColumnIndex("_id"));
			String name = c.getString(c.getColumnIndex("name"));
			int age = c.getInt(c.getColumnIndex("age"));
			Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
		}
		c.close();
		Log.d(tag, "ɾ�������ݣ�");
		// ɾ������
		db.delete("person", "age < ?", new String[] { "35" });

		// �رյ�ǰ���ݿ�
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
