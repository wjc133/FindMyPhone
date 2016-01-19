package com.elite.findmyphone.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elite.findmyphone.R;
import com.elite.findmyphone.api.weather.City;

import java.util.List;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 20:41
 */
public class CityListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<City> data;

    public CityListAdapter(Context context, List<City> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_city_info, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cityname.setText(data.get(position).getName_cn());
        viewHolder.province.setText(data.get(position).getProvince_cn() + "省" + data.get(position).getDistrict_cn() + "市");
        viewHolder.number.setText("城市代码:" + data.get(position).getArea_id());
        return convertView;
    }

    private class ViewHolder {
        TextView cityname;
        TextView province;
        TextView number;

        public ViewHolder(View view) {
            cityname = (TextView) view.findViewById(R.id.cityname);
            province = (TextView) view.findViewById(R.id.province);
            number = (TextView) view.findViewById(R.id.number);
        }
    }
}
