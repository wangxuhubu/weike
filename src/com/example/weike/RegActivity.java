package com.example.weike;

import com.example.weike.DbHelper.SqliteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author 王旭
 *  注册页面
 */
public class RegActivity extends Activity {
	private EditText userName, userPassword1, userPassword2;
	private SqliteOpenHelper mySqlHelper;
	private SQLiteDatabase db;
	private Button btn_reg, btn_clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mySqlHelper = new SqliteOpenHelper(RegActivity.this);
		db = mySqlHelper.getWritableDatabase();

		// 通过id找到每一个控件
		userName = (EditText) findViewById(R.id.editName);
		userPassword1 = (EditText) findViewById(R.id.editPassword);
		userPassword2 = (EditText) findViewById(R.id.editPassword2);
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_clear = (Button) findViewById(R.id.btn_reset);
		// 对button监听
		btn_reg.setOnClickListener(new View.OnClickListener() {
			// 先对账号进行判断，判断数据库中是否有重复账号
			// 再对两次输入密码进行判断，看两次密码输入是否一致
			// 注册成功点击“注册”将数据写入数据库，并跳转回登录界面
			@Override
			public void onClick(View v) {
				if (userName.getText().toString().equals("")
						|| userName.getText().toString().equalsIgnoreCase(null)
						|| userPassword1.getText().toString().equals("")
						|| userPassword1.getText().toString()
								.equalsIgnoreCase(null)
						|| userPassword2.getText().toString().equals("")
						|| userPassword2.getText().toString()
								.equalsIgnoreCase(null))
					Toast.makeText(RegActivity.this, "信息要补充完整哦！",
							Toast.LENGTH_SHORT).show();
				else {	
					if (userPassword1.getText().toString()
							.equals(userPassword2.getText().toString())) {
						Cursor cursor = db.rawQuery(
								"select count(*) from normalusers where username = '"
										+ userName.getText().toString() + "'",
								null);
						cursor.moveToNext();
						int count = cursor.getInt(0);
						if (count == 0) {
							ContentValues values = new ContentValues();
							values.put("username", userName.getText()
									.toString());
							values.put("password", userPassword1.getText()
									.toString());
							db.insertOrThrow("normalusers", null, values);
							Toast.makeText(RegActivity.this, "注册成功,请登录！",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(RegActivity.this,
									LoginActivity.class);
							RegActivity.this.finish();
							startActivity(intent);
						} else {
							Toast.makeText(RegActivity.this, "您注册的用户名已存在！",
									Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						Toast.makeText(RegActivity.this, "您两次输入的密码不一样！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		btn_clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				userName.setText("");
				userPassword1.setText("");
				userPassword2.setText("");
			}
		});
	}
}
