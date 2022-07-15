package com.hungduy.honghunghospitalapp.Utility;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    private FragmentUtils(){}
    public static void replaceFragment(int content, FragmentManager frgmanager, Fragment fragment, String tag){
        FragmentTransaction transaction = frgmanager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
      //  transaction.remove(findFragmentByTag(frgmanager,tag));

        transaction.replace(content, fragment,tag);
        // Commit the transaction
        transaction.commit();
    }

    public static void replaceFragment(int content, FragmentManager frgmanager, Fragment fragment){
        replaceFragment(content,frgmanager,fragment,null);
    }

    public static void addFragmentToLayout(final int containerId, final FragmentManager fragmentManager,
                                           final Fragment fragment, final String tag) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment previousFragment = fragmentManager
                .findFragmentByTag(tag);
        if (previousFragment != null) {
            ft.remove(previousFragment);
        }
        ft.add(containerId, fragment, tag);
        ft.commit();
    }


    public static <T> T findFragmentById(FragmentManager fragmentManager, int fragmentId) {
        return (T) fragmentManager.findFragmentById(fragmentId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findFragmentByTag(FragmentManager fragmentManager, String fragmentTag) {
        return (T) fragmentManager.findFragmentByTag(fragmentTag);
    }
}
