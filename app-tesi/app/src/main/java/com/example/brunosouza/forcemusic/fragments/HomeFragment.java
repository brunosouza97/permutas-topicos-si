/*
 * Espaço para JAVADOC
 * */

package com.example.brunosouza.forcemusic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosouza.forcemusic.R;

/**
 * @author Bruno Souza
 * Fragment da home - exibe as informações da página inicial
 *
 * @see Fragment
 * @link https://developer.android.com/reference/android/app/Fragment
 */
public class HomeFragment extends Fragment {

    /**
     *
     * @param view = passa o objeto view como parâmetro para apresentar o fragment
     * @param savedInstanceState = permite restaurar o estado do fragment
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.home_frag);
    }

    /**
     *
     * @param inflater = infla o layout fragment_home
     * @param container = organiza e controla o layout
     * @param savedInstanceState = permite restaurar o estado do fragment
     * @return = retorna o layout do fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}
