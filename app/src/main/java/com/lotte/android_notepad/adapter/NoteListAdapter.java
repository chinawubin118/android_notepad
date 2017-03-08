package com.lotte.android_notepad.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lotte.android_notepad.R;
import com.lotte.android_notepad.model.Note;
import com.lotte.android_notepad.ui.activity.NoteDetailActivity;

import java.io.File;
import java.util.List;

/**
 * Created by wubin on 2017/3/6.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder> {

    private Activity activity;
    private List<Note> noteList;

    public NoteListAdapter(Activity activity, List<Note> noteList) {
        this.activity = activity;
        this.noteList = noteList;
    }

    @Override
    public NoteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteListViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_note_list, null));
    }

    @Override
    public void onBindViewHolder(NoteListViewHolder holder, int position) {
        final Note note = noteList.get(position);
        holder.tvTitle.setText((position + 1 + ".") + note.getTitle());
        holder.tvTime.setText(note.getTime());
        if (!TextUtils.isEmpty(note.getImagePath())) {
            Glide.with(activity).load(new File(note.getImagePath())).into(holder.ivPic);
        } else {
            Glide.with(activity).load(R.mipmap.default_image).into(holder.ivPic);
        }
        if (!TextUtils.isEmpty(note.getVideoPath())){
            holder.ivPic.setImageBitmap(getVideoImage(note.getVideoPath(),100,100, MediaStore.Images.Thumbnails.MICRO_KIND));
        }

        //点击Item的时候执行
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NoteDetailActivity.class);
                intent.putExtra("note", note);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == noteList ? 0 : noteList.size();
    }

    private Bitmap getVideoImage(String path, int width, int height, int kind) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvTime;
        ImageView ivPic;
        View itemView;

        public NoteListViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
        }
    }
}
