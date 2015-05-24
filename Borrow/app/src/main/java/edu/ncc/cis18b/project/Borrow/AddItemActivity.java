package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddItemActivity extends ActionBarActivity
{
    private ImageButton buttonUploadPicture;
    private ImageButton buttonCancel;
    private ImageButton buttonAddItem;
    private EditText editTextName;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private ImageView imageViewThumb;
    private StringBuilder errMsg;
    private Uri picUri;
    private Bitmap picThumb;
    private Intent i;
    private String uid; //picture name

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_add_new_item);

        initializeActionBar();

        initializeWidgets();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Add Item");

        // TODO: set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeWidgets()
    {
        imageViewThumb = (ImageView)findViewById(R.id.addItemImageViewThumb);

        buttonAddItem = (ImageButton)findViewById(R.id.addItemButtonAddItem);
        buttonCancel = (ImageButton)findViewById(R.id.addItemButtonCancel);
        buttonUploadPicture = (ImageButton)findViewById(R.id.addItemButtonUploadPicture);

        buttonAddItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity();
            }
        });

        buttonUploadPicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCamera()) {
                    getPic();
                }
            }
        });
    }

    private void addItem()
    {
        BorrowObject bo;

        if (readUserInput()) {
            toast(errMsg.toString());
            return;
        } else {
            bo = getItem();
            bo.save();
            HomeActivity.borrowObjects.add(bo); // TODO: temporary, replace later
            toHomeActivity();
        }
    }

    private boolean readUserInput()
    {
        errMsg = new StringBuilder("Issue(s) with item\n");
        boolean err = false;
        editTextName = (EditText)findViewById(R.id.addItemEditTextName);
        editTextDesc = (EditText)findViewById(R.id.addItemEditTextDesc);
        editTextPrice = (EditText)findViewById(R.id.addItemEditTextPrice);

        if (editTextName.length() == 0){
            errMsg.append("Item must have a name\n");
            err = true;
        }
        if (editTextDesc.length() == 0){
            errMsg.append("Item must have a description\n");
            err = true;
        }
        if (editTextPrice.length() == 0){
            errMsg.append("Item must have a price\n");
            err = true;
        }
        // TODO: if statement for picture

        return err;
    }

    private BorrowObject getItem()
    {
        Log.d("Sagev", "create obj");
        BorrowObject bo = new BorrowObject();

        Log.d("Sagev", "set name");
        bo.setName(editTextName.getText().toString());
        Log.d("Sagev", "set desc");
        bo.setDesc(editTextDesc.getText().toString());
        Log.d("Sagev", "set price");
        bo.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
        Log.d("Sagev", "set user");
        bo.setUser(ParseUser.getCurrentUser());
        Log.d("Sagev", "set pic");
        if (picThumb != null)
            bo.setPic(picThumb, uid);

        Log.d("Sagev", "return obj");
        return bo;
    }

    private void toHomeActivity()
    {
        finish();
    }

    private void toast(String msg)
    {
        Toast.makeText(AddItemActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_new_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id)
        {
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home : {
                Log.d("Sagev", "Back");
                toHomeActivity();
                return true;
            }
            case R.id.action_logout : {
                Log.d("Sagev", "ActionMenu logout");
                ParseUser.getCurrentUser().logOut();
                i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // camera methods
    private void getPic()
    {
        i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Borrow");

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                toast("Could not create storage directory");
                return;
            }
        }

        uid = new SimpleDateFormat("HHmmss-ddMMyyyy").format(new Date());
        File picFile = new File(storageDir.getPath() + File.separator +
            "BorrowImg_" + uid + ".jpg");

        picUri = Uri.fromFile(picFile);
        i.putExtra(MediaStore.EXTRA_OUTPUT, picUri);

        startActivityForResult(i, 1);
    }

    private boolean hasCamera()
    {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            picThumb = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picUri.getPath()), 320, 180);
            imageViewThumb.setImageBitmap(picThumb);
        } else if (resultCode == RESULT_CANCELED) {
            toast("Canceled operation");
        } else {
            toast("Error");
        }
    }
}
