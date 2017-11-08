package co.com.etn.arquitecturamvpbase.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.com.etn.arquitecturamvpbase.R;

/**
 * Created by Erika on 28/10/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context context;
    private List<String> arrayFiles;
    private int widthLayout, heightLayout;

    public PhotoAdapter(Context context){
        this.context = context;
    }

    public void setFiles(ArrayList<String> list){
        this.arrayFiles = list;
    }

    public void setSize(int witdh, int height){
        this.widthLayout = witdh;
        this.heightLayout = height;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoAdapter.ViewHolder holder, final int position) {
        String fileName = arrayFiles.get(position);
        Glide.with(context).load(fileName).override(widthLayout,heightLayout).into(holder.photo_item_image);
    }

    @Override
    public int getItemCount() {
        return arrayFiles.size();
    }

    public void removeAt(int position) {
        arrayFiles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayFiles.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView photo_item_image;
        public ImageView photo_item_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            photo_item_image = (ImageView) itemView.findViewById(R.id.photo_item_image);
            photo_item_delete =  (ImageView) itemView.findViewById(R.id.photo_item_delete);
            photo_item_delete.setOnClickListener(this);
            photo_item_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == photo_item_delete) {
                removeAt(getAdapterPosition());
            }else if (v == photo_item_image){
                Toast.makeText(context, "Click en la imagen: "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
