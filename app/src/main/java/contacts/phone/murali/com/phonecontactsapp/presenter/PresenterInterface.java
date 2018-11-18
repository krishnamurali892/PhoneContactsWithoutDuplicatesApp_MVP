package contacts.phone.murali.com.phonecontactsapp.presenter;

import java.util.ArrayList;

import contacts.phone.murali.com.phonecontactsapp.model.Contact;

public interface PresenterInterface {

    void onContactsLoadingFinished(ArrayList<Contact> contactsList);

    void showProgress();

    void cancelProgress();
}
