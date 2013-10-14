package com.zgy.listviewgroup.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgy.listviewgroup.R;
import com.zgy.listviewgroup.bean.Group;
import com.zgy.listviewgroup.bean.Message;
import com.zgy.listviewgroup.interfaces.ItemClickedListener;
import com.zgy.listviewgroup.interfaces.ItemHeaderClickedListener;

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
public class MailAdapter extends BaseAdapter implements StickyListHeadersAdapter {

	private List<Message> mMessageList;
	private List<Group> mMessageListGroup;
	
	private ItemClickedListener mItemClickedListener;
	private ItemHeaderClickedListener mItemHeaderClickedListener;

	private Context context;
	
	private boolean mIsOpen;//初始化View时分组是否展开

	public MailAdapter(Context context, List<Message> message_list, boolean isOpen) {
		this.context = context;
		this.mMessageList = message_list;
		this.mIsOpen = isOpen;
		initMessageList(message_list);
	}

	private void initMessageList(List<Message> message_list) {
		this.mMessageList = message_list;
		if (message_list != null && message_list.size() > 0) {
			getSectionIndicesAndGroupNames();
		}
	}

	@Override
	public int getCount() {
		return mMessageList == null ? 0 : mMessageList.size();
	}

	public int getRealCount() {
		return mMessageList == null ? 0 : mMessageList.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ItemViewHolder holder;

		if (convertView == null) {
			holder = new ItemViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.view_item, null);
			holder.textViewInfo = (TextView) convertView.findViewById(R.id.text_item_content_info);
			convertView.setTag(holder);
		} else {
			holder = (ItemViewHolder) convertView.getTag();
		}
		holder.textViewInfo.setText(mMessageList.get(position).getInfo());
		
		//若合起分组，则里面的view不显示
		holder.textViewInfo.setVisibility(mMessageListGroup.get(mMessageList.get(position).getGroupId()).isShown() ? View.VISIBLE : View.GONE);

		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mItemClickedListener != null) {
					mItemClickedListener.onItemClick(v, position);
				}
				
			}
		});
		
		return convertView;
	}

	/**
	 * 分组
	 */
	private void getSectionIndicesAndGroupNames() {
		mMessageListGroup = new ArrayList<Group>();
		Group gp;
		int countGp = 0;
		String groupName = "";
		for (int i = 0; i < mMessageList.size(); i++) {
			String groupName2 = mMessageList.get(i).getGroupName();
			if (!groupName2.equals(groupName)) {
				
				if(mMessageListGroup.size()>0) {
					mMessageListGroup.get(mMessageListGroup.size()-1).setCount(countGp);
				}
				groupName = groupName2;
				countGp = 1;
				mMessageList.get(i).setGroupId(mMessageListGroup.size());
				gp = new Group();
				gp.setName(groupName);
				gp.setFirstPositionInList(i);
				gp.setShown(mIsOpen);
				mMessageListGroup.add(gp);
			} else {
				countGp ++;
				mMessageList.get(i).setGroupId(mMessageListGroup.size() - 1);
			}

		}

	}

	@Override
	public View getHeaderView(final int position, View convertView, ViewGroup parent) {

		ItemHeaderViewHolder holder = null;
		if (convertView == null) {
			holder = new ItemHeaderViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.view_item_header, parent, false);
			holder.textViewGroupName = (TextView) convertView.findViewById(R.id.text_item_header_name);
			holder.textViewGroupCount = (TextView) convertView.findViewById(R.id.text_item_header_count);
			holder.imgArrow = (ImageView) convertView.findViewById(R.id.img_item_header);
			convertView.setTag(holder);
		} else {
			holder = (ItemHeaderViewHolder) convertView.getTag();
		}

		
		Group gp = mMessageListGroup.get(mMessageList.get(position).getGroupId());
		holder.textViewGroupName.setText(gp.getName());
		
		holder.textViewGroupCount.setText("( " + gp.getCount() +" )");
		
		 if(gp.isShown()) {
			 holder.imgArrow.setImageResource(R.drawable.ic_group_arrow_open);
		 } else {
			 holder.imgArrow.setImageResource(R.drawable.ic_group_arrow_close);
		 }
		
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return mMessageList.get(position).getGroupId();
	}

	/**
	 * 当点击header时，即GroupName，可以展开合起
	 * @Description:
	 * @param headerId
	 * @see: 
	 * @since: 
	 * @author: zhuanggy
	 * @date:2013-10-14
	 */
	public void onListHeaderClicked(int position) {
		Group gp = mMessageListGroup.get(mMessageList.get(position).getGroupId());
		gp.setShown(!gp.isShown());

		MailAdapter.this.notifyDataSetChanged();
	}
	
	/////////////////////////////////////////
	public void setOnItemClickedListener(ItemClickedListener listener) {
		this.mItemClickedListener = listener;
	}
	
	public void setOnItemHeaderClickedListener(ItemHeaderClickedListener listener) {
		this.mItemHeaderClickedListener = listener;
	}
	
	public ItemHeaderClickedListener getmItemHeaderClickedListener() {
		return mItemHeaderClickedListener;
	}


	// /////////////////////////////////////
	public class ItemHeaderViewHolder {

		TextView textViewGroupName;
		ImageView imgArrow;
		TextView textViewGroupCount;
	}

	public class ItemViewHolder {

		TextView textViewInfo;
	}

}
