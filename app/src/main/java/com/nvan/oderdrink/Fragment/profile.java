package com.nvan.oderdrink.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nvan.oderdrink.Model.User;
import com.nvan.oderdrink.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class profile extends Fragment {
    private View mview;
    private ImageView pravatar;
    private TextView premail;
    private EditText prphone,praddress,prname;
    private Button prsave;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_profile,container,false);
        prname = mview.findViewById(R.id.prName);
        praddress = mview.findViewById(R.id.prAddress);
        premail = mview.findViewById(R.id.prEmail);
        prphone = mview.findViewById(R.id.prPhone);
        prsave = mview.findViewById(R.id.prSave);
        //setUserimg();
        readdatabase();
        prsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        return mview;


        //innitListener();
        //setUserimg();

    }


    private void onClickSave() {
        String name = prname.getText().toString().trim();
        String address = praddress.getText().toString().trim();
        String phone = prphone.getText().toString().trim();

        updateUserInfo(name, address, phone);
    }



    private void innitListener() {
        pravatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  onClickRequest();
            }
        });
    }

    //    private void onClickRequest() {
//        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
//            openGallery();
//            return;
//        }
//        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE == packageManager.PERMISSION_GRANTED){
//            openGallery();
//        }else{
//            string
//        }
//    }
//
    private void setUserimg() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            return;
        }
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.imgdd).into(pravatar);
    }
    private void readdatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String userId = user.getUid();

        myRef.orderByChild("id").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        premail.setText(user.getEmail());
                        prname.setText(user.getName());
                        praddress.setText(user.getAddress());
                        prphone.setText(user.getPhone());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    private void updateUserInfo(String name, String address, String phone) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String userId = user.getUid();

        DatabaseReference userRef = myRef.child(userId);
        HashMap<String, Object> updateData = new HashMap<>();
        updateData.put("name", name);
        updateData.put("address", address);
        updateData.put("phone", phone);

        userRef.updateChildren(updateData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    // Xử lý lỗi nếu cần
                } else {
                    Toast.makeText(getContext(), "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
