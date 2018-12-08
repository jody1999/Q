package gcsenxmk.q.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import gcsenxmk.q.AppCompatPreferenceActivity;
import gcsenxmk.q.R;
import gcsenxmk.q.database.MerchantInformation_forSearch;

public class Cust_Search_Merchant extends AppCompatPreferenceActivity {

    EditText mSearchField;
    RecyclerView merchantlistResult;
    DatabaseReference mMerchantData;
    Button mSearchBtn;
    private static final String TAG = "Merchant_Search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_merchant_searchresult);

        mMerchantData = FirebaseDatabase.getInstance().getReference("Merchants");


        mSearchField = findViewById(R.id.testcme_editTxt_SearchBar);
        mSearchBtn =  findViewById(R.id.testBtnSearchResult);

        merchantlistResult= (RecyclerView) findViewById(R.id.testmerchant_search_list);
        merchantlistResult.setHasFixedSize(true);
        merchantlistResult.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(Cust_Search_Merchant.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mMerchantData.orderByChild("Merchants").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<MerchantInformation_forSearch, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MerchantInformation_forSearch, UsersViewHolder>(

                MerchantInformation_forSearch.class,
                R.layout.cust_list_item,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, MerchantInformation_forSearch model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), Integer.toString(model.getAvewaiting()), model.getImageUrl(),Integer.toString(model.getNumPeople()));

            }
        };

        merchantlistResult.setAdapter(firebaseRecyclerAdapter);

    }


    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(Context ctx, String userName, String avewaiting, String userImage, String numPeople){

            TextView user_name = (TextView) mView.findViewById(R.id.queueName);
            TextView user_status = (TextView) mView.findViewById(R.id.minutes);
            ImageView user_image = (ImageView) mView.findViewById(R.id.queueImage);
            TextView user_time= mView.findViewById(R.id.queueNumPeople);


            user_name.setText(userName);
            user_status.setText(avewaiting);

            Glide.with(ctx).load(userImage).into(user_image);


        }




    }

}
