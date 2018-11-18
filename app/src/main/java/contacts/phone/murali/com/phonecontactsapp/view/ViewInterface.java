package contacts.phone.murali.com.phonecontactsapp.view;

import java.util.ArrayList;

import contacts.phone.murali.com.phonecontactsapp.model.Contact;

public interface ViewInterface {
    void displayContacts(ArrayList<Contact> alContacts);

    void showErrorMessage(String msg);

    void showProgressDialog();

    void dismissProgressDialog();
}
