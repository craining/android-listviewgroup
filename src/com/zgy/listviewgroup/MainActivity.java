package com.zgy.listviewgroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zgy.listviewgroup.adapter.MailAdapter;
import com.zgy.listviewgroup.bean.Message;
import com.zgy.listviewgroup.interfaces.ItemClickedListener;
import com.zgy.listviewgroup.interfaces.ItemHeaderClickedListener;
import com.zgy.listviewgroup.view.MailListView;


/**
 * 欢迎加入QQ开发群讨论：88130145
 * 
 * @Description:
 * @author: zhuanggy
 * @see:   
 * @since:      
 * @copyright © 35.com
 * @Date:2013-10-14
 */
public class MainActivity extends Activity {

	private MailListView mListView;
	private MailAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (MailListView) findViewById(R.id.listview_main);

		List<Message> messages = new ArrayList<Message>();

		for (int i = 0; i < 20; i++) {
			Message msg = new Message();
			msg.setGroupName("Group" + i);
			for (int j = 0; j < 5; j++) {
				msg.setInfo("info");
				messages.add(msg);
			}
		}

		mAdapter = new MailAdapter(MainActivity.this, messages, false);//第三个参数是：第一次填充listview时，分组是否展开

		mListView.setAdapter(mAdapter);

		mListView.setOnItemHeaderClickedListener(new ItemHeaderClickedListener() {

			@Override
			public void onItemHeaderClick(View header, int itemPosition, long headerId) {
				// 展开或收起分组
				if (mAdapter != null) {
					mAdapter.onListHeaderClicked(itemPosition);
				}
			}
		});

		mListView.setOnItemClickedListener(new ItemClickedListener() {

			@Override
			public void onItemClick(View item, int itemPosition) {
				// TODO Auto-generated method stub

			}
		});

	}

}
