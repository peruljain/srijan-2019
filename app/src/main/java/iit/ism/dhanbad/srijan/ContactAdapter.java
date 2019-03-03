package iit.ism.dhanbad.srijan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import iit.ism.dhanbad.srijan.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ContactAdapter extends ArrayAdapter<Contact> {

    List<Contact> contactList;
    Context context;
    private LayoutInflater mInflater;
    public static String getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utcZone);

        String dt;
        Date t;
        try {
            t = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        sdf = new SimpleDateFormat("MMMM' 'dd', 'yyyy'\n'HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());
        dt = sdf.format(t);

        return dt;
    }

    // Constructors
    public ContactAdapter(Context context, List<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Contact item = getItem(position);

        vh.textViewName.setText(item.getMessage());
        String d1 = getDate(item.getCreated_time());
        vh.textViewEmail.setText(d1);

        Picasso.get().load(item.getFull_picture()).into(vh.imageViewn);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageViewn;
        public final TextView textViewName;
        public final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageViewn, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageViewn = imageViewn;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageViewn);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewEmail = (TextView) rootView.findViewById(R.id.textViewEmail);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail);
        }
    }
}

