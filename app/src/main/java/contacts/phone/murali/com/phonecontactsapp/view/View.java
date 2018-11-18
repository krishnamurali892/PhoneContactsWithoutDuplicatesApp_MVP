package contacts.phone.murali.com.phonecontactsapp.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import contacts.phone.murali.com.phonecontactsapp.R;
import contacts.phone.murali.com.phonecontactsapp.model.Contact;
import contacts.phone.murali.com.phonecontactsapp.presenter.Presenter;

public class View extends AppCompatActivity implements ViewInterface {

    private final static int PERMISSION_REQUEST_CONTACT = 123;
    private Presenter presenter;
    private RecyclerView list_contacts;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_contacts = findViewById(R.id.list_Contacts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list_contacts.setLayoutManager(mLayoutManager);

        presenter = new Presenter(this);

        if (presenter.checkContactPermission()) {
            presenter.loadContacts();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CONTACT && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.loadContacts();
        } else {
            Toast.makeText(this, R.string.plz_try_again, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayContacts(ArrayList<Contact> alContacts) {
        if (list_contacts != null && alContacts != null && alContacts.size() > 0) {
            list_contacts.setAdapter(new ContactsAdapter(alContacts));
        }
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(View.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
