package com.github.thejunkjon.utilities.app.filebrowser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.thejunkjon.utilities.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;
import static java.util.Collections.addAll;
import static java.util.Collections.sort;

public class FileBrowserActivity extends Activity
        implements FileListAdapter.OnFileListItemClickedListener {

    private static final String DEFAULT_ROOT_DIRECTORY = "/";
    private String mCurrentPath = DEFAULT_ROOT_DIRECTORY;
    private FileListAdapter mFileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        setupFileListView();
    }

    private void setupFileListView() {
        RecyclerView fileListView = (RecyclerView) findViewById(R.id.file_browser_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fileListView.setLayoutManager(layoutManager);

        mFileListAdapter = new FileListAdapter(this);
        fileListView.setAdapter(mFileListAdapter);
        updateFileList(DEFAULT_ROOT_DIRECTORY);
    }

    @Override
    public void onFileListItemClicked(final String selectedFilePath) {
        final String absoluteFilePath = mCurrentPath.endsWith(File.separator) ?
                mCurrentPath + selectedFilePath : mCurrentPath + File.separator + selectedFilePath;
        final boolean isDirectory = new File(mCurrentPath).isDirectory();
        if (isDirectory) {
            mCurrentPath = absoluteFilePath;
            updateFileList(absoluteFilePath);
        }
    }

    @Override
    public void onBackPressed() {
        if (DEFAULT_ROOT_DIRECTORY.equals(mCurrentPath)) {
            super.onBackPressed();
        } else {
            final int indexOfLastFileSeparator = mCurrentPath.lastIndexOf(File.separator);
            mCurrentPath = mCurrentPath.substring(0, indexOfLastFileSeparator);
            if (isEmpty(mCurrentPath)) {
                mCurrentPath = DEFAULT_ROOT_DIRECTORY;
            }
            updateFileList(mCurrentPath);
        }
    }

    private void updateFileList(final String path) {
        final File file = new File(path);
        if (!file.canRead()) {
            setTitle(path + " (inaccessible)");
        } else {
            setTitle(path);
        }

        String[] contents = file.list();
        if (contents != null) {
            final List<String> sortedContents = new ArrayList<>(contents.length);
            addAll(sortedContents, contents);
            sort(sortedContents);
            contents = sortedContents.toArray(new String[sortedContents.size()]);
        } else {
            contents = new String[0];
        }

        mFileListAdapter.updateContents(contents);
        mFileListAdapter.notifyDataSetChanged();
    }
}