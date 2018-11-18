package contacts.phone.murali.com.phonecontactsapp.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import contacts.phone.murali.com.phonecontactsapp.R;
import contacts.phone.murali.com.phonecontactsapp.model.Contact;
import contacts.phone.murali.com.phonecontactsapp.model.TaskLoadContacts;
import contacts.phone.murali.com.phonecontactsapp.view.ViewInterface;

public class Presenter implements PresenterInterface {
    private Context context;
    private ViewInterface viewInterface;

    public Presenter(Context context) {
        this.context = context;
        viewInterface = (ViewInterface) context;
    }

    public void loadContacts() {
        new TaskLoadContacts(context, this).execute();
    }

    @Override
    public void onContactsLoadingFinished(ArrayList<Contact> contactsList) {
        if (contactsList != null && contactsList.size() > 0) {
            viewInterface.displayContacts(contactsList);
        } else {
            viewInterface.showErrorMessage(context.getString(R.string.no_contacts_alert));
        }
    }

    @Override
    public void showProgress() {
        viewInterface.showProgressDialog();
    }

    @Override
    public void cancelProgress() {
        viewInterface.dismissProgressDialog();
    }

    public boolean checkContactPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
