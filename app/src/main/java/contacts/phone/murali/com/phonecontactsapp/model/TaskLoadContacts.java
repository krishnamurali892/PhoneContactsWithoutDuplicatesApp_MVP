package contacts.phone.murali.com.phonecontactsapp.model;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.ArrayList;
import java.util.HashSet;

import contacts.phone.murali.com.phonecontactsapp.presenter.Presenter;
import contacts.phone.murali.com.phonecontactsapp.presenter.PresenterInterface;

public class TaskLoadContacts extends AsyncTask<Void, Void, ArrayList<Contact>> {

    private Context context;
    private PresenterInterface presenterInterface;

    public TaskLoadContacts(Context context, Presenter presenter) {
        this.context = context;
        this.presenterInterface = presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        presenterInterface.showProgress();
    }

    @Override
    protected ArrayList<Contact> doInBackground(Void... voids) {
        HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
        ArrayList<Contact> alContacts = new ArrayList<>();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String phoneNumber = null;
        Contact contact = null;
        Phonenumber.PhoneNumber normalizedNumber = null;
        Cursor cursor = null;
        String[] projection = null;
        int indexOfName;
        int indexOfNumber;
        try {
            projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
            };
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            indexOfName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            indexOfNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            while (cursor.moveToNext()) {
                phoneNumber = cursor.getString(indexOfNumber);
                normalizedNumber = phoneUtil.parse(phoneNumber, "IN");
                String number = Long.toString(normalizedNumber.getNationalNumber());
                if (normalizedNumbersAlreadyFound.add(number)) {
                    contact = new Contact();
                    contact.setName(cursor.getString(indexOfName));
                    contact.setMobileNumber(number);
                    alContacts.add(contact);
                }
            }
        } catch (Exception e) {

        } finally {
            normalizedNumbersAlreadyFound = null;
            phoneUtil = null;
            phoneNumber = null;
            normalizedNumber = null;
            cursor = null;
            projection = null;
        }
        return alContacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> arrayList) {
        super.onPostExecute(arrayList);
        presenterInterface.cancelProgress();
        presenterInterface.onContactsLoadingFinished(arrayList);
    }
}
