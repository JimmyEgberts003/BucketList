package com.egberts.jimmy.bucketlist;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BucketListAdapter.BucketListItemClickListener {

    private List<BucketListItem> bucketListItems;

    private BucketListAdapter bucketListAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton addBucketListItemButton = findViewById(R.id.addBucketListItemButton);
        addBucketListItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddBucketListItemClick();
            }
        });

        mainViewModel = new MainViewModel(getApplicationContext());
        mainViewModel.getAllBucketListItems().observe(this, new Observer<List<BucketListItem>>() {
            @Override
            public void onChanged(@Nullable List<BucketListItem> newBucketListItems) {
                bucketListItems = newBucketListItems;
                bucketListAdapter.swapList(newBucketListItems);
            }
        });

        bucketListItems = new ArrayList<>();
        bucketListAdapter = new BucketListAdapter(bucketListItems, this);

        RecyclerView bucketListItemsView = findViewById(R.id.bucketListItemsView);
        bucketListItemsView.setLayoutManager(new GridLayoutManager(this, LinearLayoutManager.VERTICAL));
        bucketListItemsView.setAdapter(bucketListAdapter);

        ItemTouchHelper.SimpleCallback callback = getTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(bucketListItemsView);
    }

    @NonNull
    private ItemTouchHelper.SimpleCallback getTouchHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                System.out.println(viewHolder.getAdapterPosition());
                System.out.println(bucketListItems.size());
                mainViewModel.delete(bucketListItems.remove(viewHolder.getAdapterPosition()));
            }
        };
    }

    private void onAddBucketListItemClick() {
        final View layoutInflater = getLayoutInflater().inflate(R.layout.add_bucket_list_item_content, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(layoutInflater);
        builder.setTitle(R.string.add_bucket_list_item_title);
        builder.setPositiveButton(R.string.confirm_button_content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText) layoutInflater.findViewById(R.id.titleInput)).getText().toString();
                String description = ((EditText) layoutInflater.findViewById(R.id.descriptionInput)).getText().toString();
                mainViewModel.insert(new BucketListItem(title, description, false));
            }
        });
        builder.setNegativeButton(R.string.cancel_button_content, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alert = builder.create();

        Window window = alert.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnBucketListItemClick(int i, boolean finished) {
        System.out.println("1 selected, state: "+ i + finished);
        BucketListItem item = bucketListItems.get(i);
        item.setFinished(finished);
        mainViewModel.update(item);
        System.out.println("2 state: "+ mainViewModel.getAllBucketListItems().getValue().get(i).isFinished() );
    }
}
