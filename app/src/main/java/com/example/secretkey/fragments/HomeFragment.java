package com.example.secretkey.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.secretkey.Database.RoomDB;
import com.example.secretkey.EncryptDecrypt;
import com.example.secretkey.Model.Key;
import com.example.secretkey.Model.Message;
import com.example.secretkey.R;
import com.example.secretkey.Utility.BDUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private MaterialButton btnEncrypt, btnDecrypt;

    private TextInputEditText txtEncrypt, txtDecrypt;

    RoomDB database;
    String key;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        database = RoomDB.getInstance(getActivity());
        txtEncrypt = view.findViewById(R.id.txtEncrypt);
        txtDecrypt = view.findViewById(R.id.txtDecrypt);
        btnEncrypt = view.findViewById(R.id.btnEncrypt);
        btnDecrypt = view.findViewById(R.id.btnDecrypt);
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = txtEncrypt.getText().toString();
                if (value != null && !value.isEmpty()) {
                    key = database.keyDao().getAllKey().get(0).getKey();
                    String encryptedText = EncryptDecrypt.encrypt(txtEncrypt.getText().toString(), key);
                    txtDecrypt.setText(encryptedText);

                    List<Key> keyList = database.keyDao().getAllKey();
                    Boolean saveMessage = keyList.get(0).getMessagebackup();
                    if (saveMessage)
                        saveEncryptedMessages(encryptedText, txtEncrypt.getText().toString());
                    BDUtility.setClipboard(getContext(), txtDecrypt.getText().toString());

                    txtEncrypt.getText().clear();
                    Toast.makeText(getContext(), "Encrypted text coppied to clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Field empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String value = txtDecrypt.getText().toString();
                    if (value != null && !value.isEmpty()) {
                        key = database.keyDao().getAllKey().get(0).getKey();
                        String decryptedText = EncryptDecrypt.decrypt(value, key);
                        txtEncrypt.setText(decryptedText);
                        txtDecrypt.getText().clear();
                        BDUtility.setClipboard(getContext(), txtEncrypt.getText().toString());
                        Toast.makeText(getContext(), "Decrypted text copied to clipboard", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Field empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "Key changed or invalid encrypted data", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "Invalid encrypted data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void saveEncryptedMessages(String eText, String pText) {
        Message message = new Message();
        message.setEtext(eText);
        message.setPtext(pText);
        message.setCreationtime(new Date());
        database.mainDao().saveItem(message);
    }
}