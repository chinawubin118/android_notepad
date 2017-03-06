package com.lotte.android_notepad.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lotte.android_notepad.R;
import com.lotte.android_notepad.model.Note;

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
        Note note = noteList.get(position);
        holder.tvTitle.setText((position + 1 + ".") + note.getTitle());
        holder.tvTime.setText(note.getTime());
    }

    @Override
    public int getItemCount() {
        return null == noteList ? 0 : noteList.size();
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvTime;

        public NoteListViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}
