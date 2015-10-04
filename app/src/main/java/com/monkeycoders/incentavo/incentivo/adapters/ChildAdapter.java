package com.monkeycoders.incentavo.incentivo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.monkeycoders.incentavo.incentivo.Items.ChildCard;
import com.monkeycoders.incentavo.incentivo.R;
import com.monkeycoders.incentavo.incentivo.activities.ProductDetailActivity;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChildAdapter extends ArrayAdapter<ChildCard> {
    private static final String TAG = "ChildAdapter";
    private List<ChildCard> cardList = new ArrayList<ChildCard>();

    static class CardViewHolder {
        TextView name;
        CircleImageView image;
    }

    public ChildAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(ChildCard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public ChildCard getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.card_wishlist, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.name = (TextView) row.findViewById(R.id.info_text);
            viewHolder.image = (CircleImageView) row.findViewById(R.id.image);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        final ChildCard card = getItem(position);
        viewHolder.name.setText(card.getName());
        viewHolder.image.setImageResource(R.mipmap.ic_launcher);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("child_id", card.getId());
                getContext().startActivity(intent);
            }
        });
        return row;
    }

}