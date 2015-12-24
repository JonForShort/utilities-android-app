package com.github.thejunkjon.utilities.app.filebrowser;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.thejunkjon.utilities.app.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.thejunkjon.utilities.app.filebrowser.FileUtils.convertByteSizeToHumanReadableForm;
import static java.lang.String.format;
import static java.util.Locale.US;

final class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private final OnFileListItemClickedListener mOnFileListItemClickedListener;
    private String mPath = File.separator;
    private String[] mContents = new String[0];

    interface OnFileListItemClickedListener {
        void onFileListItemClicked(final String fileListItem);
    }

    FileListAdapter(final OnFileListItemClickedListener onFileListItemClickedListener) {
        mOnFileListItemClickedListener = onFileListItemClickedListener;
    }

    void updateContents(final String path, String[] contents) {
        mPath = path;
        mContents = contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_file_list_item, parent, false);
        final FileListAdapter.ViewHolder viewHolder = new ViewHolder(inflatedView);
        viewHolder.text = (TextView) inflatedView.findViewById(R.id.file_browser_list_text);
        viewHolder.details = (TextView) inflatedView.findViewById(R.id.file_browser_list_details);
        viewHolder.image = (ImageView) inflatedView.findViewById(R.id.file_browser_list_icon);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final String absolutePath = mPath + File.separator + mContents[position];
        final File file = new File(absolutePath);
        if (file.isDirectory()) {
            final String[] contents = file.list();
            viewHolder.details.setText(format(US, "%s %d files",
                    convertDateToString(file.lastModified()),
                    contents == null ? 0 : contents.length));
        } else {
            viewHolder.details.setText(format(US, "%s %s",
                    convertDateToString(file.lastModified()),
                    convertByteSizeToHumanReadableForm(file.length(), false)));
        }

        viewHolder.text.setText(mContents[position]);

        final Drawable drawable = viewHolder.image.getContext()
                .getResources().getDrawable(R.drawable.ic_launcher);
        viewHolder.image.setImageDrawable(drawable);
    }

    private static String convertDateToString(final long date) {
        return new SimpleDateFormat("EEE, MMM d, ''yy", US).format(new Date(date));
    }

    @Override
    public int getItemCount() {
        return mContents.length;
    }

    final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text;
        TextView details;
        ImageView image;

        ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            mOnFileListItemClickedListener.onFileListItemClicked(text.getText().toString());
        }
    }
}
