package com.trinoeg8.trino.oneloginexampleapp.Clases;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.trinoeg8.trino.oneloginexampleapp.R;
import java.util.List;

/**
 * Created by trino on 24/01/15.
 */
public class Adapter extends SelectableAdapter<Adapter.ViewHolder>{
    private List<Company> items;
    private ViewHolder.ClickListener clickListener;
    private ImageLoader imageLoader;
    private String tempCategory;
    private static final int DIFERENT_CATEGORY = 0;
    private static final int SAME_CATEGORY = 1;
    public Adapter(ViewHolder.ClickListener clickListener, List<Company> list,Context context,String tempCategory){
        super();
        this.clickListener = clickListener;
        this.items = list;
        this.imageLoader = new ImageLoader(context);
        this.tempCategory = tempCategory;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.i("ViewType",Integer.toString(viewType));
        final int layout = viewType == SAME_CATEGORY ? R.layout.company_row_category: R.layout.company_row;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ViewHolder(v,clickListener);
    }
    @Override
    public int getItemViewType(int position){
        final Company item = items.get(position);
        if(!tempCategory.equalsIgnoreCase(item.getCategory())){
            tempCategory = "";
            return DIFERENT_CATEGORY;
        }else{
            return SAME_CATEGORY;
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final Company company = items.get(position);
        holder.name.setText(company.getName());
        if (company.getIcon_url()!= null) {
            imageLoader.DisplayImage(company.getIcon_url(),holder.logo);
        }
        holder.category.setText(company.getCategory().toString());
        if(position == 0){
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,35);
            holder.category.setLayoutParams(p);
        }
        tempCategory = company.getCategory().toString();
    }
    public Company getSelectedItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView name;
        TextView category;
        ImageView logo;
        View selectedOverlay;
        private ClickListener listener;
        public ViewHolder(View itemView,ClickListener listener){
            super(itemView);
            category = (TextView)itemView.findViewById(R.id.category);
            name = (TextView)itemView.findViewById(R.id.company_name);
            logo = (ImageView)itemView.findViewById(R.id.logo);
            //selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(listener!=null)
                listener.onItemClicked(getPosition());
        }
        @Override
        public boolean onLongClick(View v){
            if(listener!=null)
                return listener.onItemLongClicked(getPosition());
            return false;
        }
        public interface ClickListener {
            public void onItemClicked(int position);
            public boolean onItemLongClicked(int position);
        }
    }
}
