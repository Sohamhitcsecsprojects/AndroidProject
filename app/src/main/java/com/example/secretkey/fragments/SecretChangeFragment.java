package com.example.secretkey.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secretkey.Database.RoomDB;
import com.example.secretkey.Model.Key;
import com.example.secretkey.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SecretChangeFragment extends Fragment {

    private Button btnSaveKey, btnEnableDisableMessage, btnDeleteAllMessage, btnEnableLock;
    private EditText txtKey;
    private TextView txtWarning;
    RoomDB database;

    public SecretChangeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_secret_change, container, false);
        database = RoomDB.getInstance(getActivity());
        txtKey = view.findViewById(R.id.txtKey);
        btnSaveKey = view.findViewById(R.id.btnSaveKey);
        txtWarning = view.findViewById(R.id.txtWarning);
        btnEnableDisableMessage = view.findViewById(R.id.btnEnableDisableMessage);
        btnDeleteAllMessage = view.findViewById(R.id.btnDeleteAllMessage);
        btnEnableLock = view.findViewById(R.id.btnEnableLock);

        btnSaveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyText = txtKey.getText().toString();
                if (isValidPassword(keyText)) {
                    saveKey(keyText);
                    txtWarning.setVisibility(View.GONE);
                    txtKey.getText().clear();
                    Toast.makeText(getContext(), "Encryption key changed", Toast.LENGTH_SHORT).show();
                } else {
                    txtWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        btnEnableDisableMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                Boolean status;
                if (key.getMessagebackup()) {
                    status = false;
                    message = "Message backup disabled";
                    dialogMessage = "Are you sure you want to disable(Message history creation) ?\n No message history will be made further";
                    dialogTitle = "Disable ";
                } else {
                    status = true;
                    message = "Message backup enabled";
                    dialogMessage = "Are you sure you want to enable (Message history creation) ?\n Message history will be created";
                    dialogTitle = "Enable ";
                }
                new AlertDialog.Builder(inflater.getContext()).setTitle(dialogTitle + "Confirmation ||")
                        .setMessage(dialogMessage)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.keyDao().enableDisable(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(status ? R.drawable.ic_enable : R.drawable.ic_disable).show();
            }
        });

        btnDeleteAllMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(inflater.getContext()).setTitle("Delete !!")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.mainDao().deleteAllMessages();
                                Toast.makeText(getContext(), "All messages deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(R.drawable.ic_delete).show();
            }
        });

        btnEnableLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                Boolean status;
                if (key.getSecurity()) {
                    status = false;
                    message = "Screen lock disabled";
                    dialogMessage = "Are you sure you want to disable screen lock?";
                    dialogTitle = "Disable ";
                } else {
                    status = true;
                    message = "Screen lock enabled";
                    dialogMessage = "Are you sure you want to enable screen lock";
                    dialogTitle = "Enable ";
                }

                new AlertDialog.Builder(inflater.getContext()).setTitle(dialogTitle + "Lock !!")
                        .setMessage(dialogMessage)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.keyDao().enableDisableSecurity(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(status ? R.drawable.ic_enable : R.drawable.ic_disable).show();
            }
        });
        return view;
    }

    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+=])(?=\\S+$).{4,}$";
        ;
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void saveKey(String keyToSave) {
        List<Key> keyList = database.keyDao().getAllKey();
        Key myKey = keyList.get(0);
        database.keyDao().changeKey(myKey.getID(), keyToSave);
    }
}