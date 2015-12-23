package com.github.thejunkjon.utilities.app.filebrowser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.thejunkjon.utilities.app.R;

final class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private final OnFileListItemClickedListener mOnFileListItemClickedListener;

    private String[] mContents = new String[0];

    interface OnFileListItemClickedListener {
        void onFileListItemClicked(final String fileListItem);
    }

    FileListAdapter(final OnFileListItemClickedListener onFileListItemClickedListener) {
        mOnFileListItemClickedListener = onFileListItemClickedListener;
    }

    void updateContents(final String[] contents) {
        mContents = contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_file_list_item, parent, false);
        final FileListAdapter.ViewHolder viewHolder = new ViewHolder(inflatedView);
        viewHolder.textView = (TextView) inflatedView.findViewById(R.id.file_browser_list_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.textView.setText(mContents[position]);
    }

    @Override
    public int getItemCount() {
        return mContents.length;
    }

    final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            mOnFileListItemClickedListener.onFileListItemClicked(textView.getText().toString());
        }
    }
}
