package com.example.redditClone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redditClone.R;

import com.example.redditClone.mongo_api.MongoAPIListener;
import com.example.redditClone.mongo_api.MongoAPITask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        // Encontre o botão na view inflada
        Button sendButton = view.findViewById(R.id.send_btn);
        TextView data_output = view.findViewById(R.id.textInput);
        // Defina um listener para o botão
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Enviando", Toast.LENGTH_SHORT).show();
                String body = "{\"dataSource\": \"Cluster0\", \"database\": \"Server_DB\", \"collection\": \"base0\"}";

                new MongoAPITask("/action/find", body, new MongoAPIListener() {
                    @Override
                    public void onResult(String result) {
                        if (result != null) {
                            Toast.makeText(getContext(), "Recebido", Toast.LENGTH_SHORT).show();
                            data_output.setText(result);
                        } else {
                            // Exibir uma mensagem de erro se o resultado for nulo
                            Toast.makeText(getContext(), "Erro na requisição", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });

        return view;
    }

}