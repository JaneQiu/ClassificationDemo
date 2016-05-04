package jane.mall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ClassificationFragment extends Fragment {


    public ClassificationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classification, container, false);
        replaceFragment(R.id.frag_classification_menu_fl, ClassificationMenuFragment.class);
        replaceFragment(R.id.frag_classification_content_fl, ClassificationContentFragment.class);
        return view;
    }

    public void replaceFragment(int resId, Class clazz) {
        getChildFragmentManager().beginTransaction()
                .replace(resId, Fragment.instantiate(getActivity(), clazz.getSimpleName()))
                .commit();
    }


}
