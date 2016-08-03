package com.example.mypc.listlocalrecycleview.Aapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypc.listlocalrecycleview.EditContactActivity;
import com.example.mypc.listlocalrecycleview.MainActivity;
import com.example.mypc.listlocalrecycleview.Model.Item;
import com.example.mypc.listlocalrecycleview.Model.Snippet;
import com.example.mypc.listlocalrecycleview.Model.Thumbnails;
import com.example.mypc.listlocalrecycleview.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MyPC on 30/06/2016.
 */
public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.MyViewHolder> {
    private List<Item> mList;
    private Context mContext;
    public ICallAdaterToAtv mICallAdaterToAtv;

    public DanhSachAdapter(Context mContext, List<Item> mList, ICallAdaterToAtv iCallAdaterToAtv) {
        this.mContext = mContext;
        this.mList = mList;
        this.mICallAdaterToAtv = iCallAdaterToAtv;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Item item = mList.get(position);
        final String title = item.getSnippet().getTitle();
        final String descripton = item.getSnippet().getDescription();
        holder.tvTitle.setText(title);
        holder.tvDescription.setText(descripton);
        Picasso.with(mContext)
                .load(item.getSnippet().getThumbnails().getDefault().getUrl())
                .error(R.drawable.ic_launcher)
                .placeholder(R.drawable.ic_launcher)
                .into(holder.imgAnh);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ICallAdaterToAtv) {
                    ((ICallAdaterToAtv) mContext).callAdapterToAtv(position, title, descripton);
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                delete.setTitle("Cofirm delete");
                delete.setMessage("Are you sure delete contact");
                delete.setIcon(R.drawable.ic_launcher);
                delete.setCancelable(false);
                delete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
                        mList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                delete.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                delete.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Item getItem(Integer position) {
        return mList.get(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgAnh;
        public TextView tvTitle, tvDescription;
        public Button btnEdit, btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgAnh = (ImageView) itemView.findViewById(R.id.imgAnh);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mICallAdaterToAtv.selectItem(v, getAdapterPosition());
        }
    }

    public interface ICallAdaterToAtv {
        void callAdapterToAtv(int pos, String name, String des);
        void selectItem(View view, int pos);
    }
}
