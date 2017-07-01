package com.sourcey.intelligentmedicalsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sourcey.intelligentmedicalsystem.R;
import com.sourcey.intelligentmedicalsystem.bean.ListViewItemData;
import com.sourcey.intelligentmedicalsystem.bean.Msg;
import com.sourcey.intelligentmedicalsystem.bean.PopupItem;

import java.util.List;
/*
问答界面的适配器
 */

public class MsgAdapter extends BaseAdapter {
	private static final int TYPE_MSG = 0;//编辑框
	private static final int TYPE_POPUP = 1;//按钮

	private Context mContext;

	private int resourceId;

	private int type=0;

	private LayoutInflater mInflater;

	private List<ListViewItemData> myList;
	
	public MsgAdapter(Context context, List<ListViewItemData> objects) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myList=objects;
		mContext=context;
		
	}

	public void setType(int type){
		this.type=type;
	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem(int i) {
		return myList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListViewItemData itemData = (ListViewItemData) getItem(position);
		if(itemData.getDataType()==0){
			Msg msg= (Msg) itemData.getT();
			View view;
			ViewHolder viewHolder;

				view = mInflater.inflate(R.layout.msg_item,null);
				viewHolder = new ViewHolder();
				viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
				viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
				viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
				viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
				view.setTag(viewHolder);

			if (msg.getType() == Msg.TYPE_RECEIVED||msg.getType()==Msg.TYPE_ELSE||msg.getType()==Msg.TYPE_MEDICINE) {
				viewHolder.leftLayout.setVisibility(View.VISIBLE);
				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.leftMsg.setText(msg.getContent());
			} else if(msg.getType() == Msg.TYPE_SENT) {
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.rightMsg.setText(msg.getContent());

			}else if(msg.getType()==Msg.TYPE_PIC){
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				//viewHolder.rightMsg.setText(msg.getContent());
				viewHolder.rightMsg.setText(msg.getPicture());
			}
			return view;
		}
		if(itemData.getDataType()==1){
			PopupItem popupItem= (PopupItem) itemData.getT();
			PopupHolder popupHolder;
			View view;

				view = mInflater.inflate(R.layout.popup_item,null);
				popupHolder=new PopupHolder();
				view.setTag(popupHolder);


			return view;

		}
		return null;

	}

	class ViewHolder {
		
		LinearLayout leftLayout;
		
		LinearLayout rightLayout;
		
		TextView leftMsg;
		
		TextView rightMsg;
		
	}
	class PopupHolder  {


	}

}
