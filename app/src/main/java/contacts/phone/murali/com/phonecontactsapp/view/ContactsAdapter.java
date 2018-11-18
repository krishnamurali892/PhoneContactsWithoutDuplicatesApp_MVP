package contacts.phone.murali.com.phonecontactsapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import contacts.phone.murali.com.phonecontactsapp.R;
import contacts.phone.murali.com.phonecontactsapp.model.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> {

    private List<Contact> itemList;

    public ContactsAdapter(List<Contact> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Contact item = itemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvNumber.setText(item.getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvNumber;

        public ItemViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvNumber = view.findViewById(R.id.tvNumber);
        }
    }
}
