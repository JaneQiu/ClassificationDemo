package jane.mall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.Subscriber;

import jane.mall.base.EventBusTag;
import jane.mall.classification.ClassificationContentFragment;
import jane.mall.classification.ClassificationMenuFragment;


public class ClassificationFragment extends Fragment {

    private int mCurrentCategoryId;

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

    @Subscriber(tag = EventBusTag.CATEGORY_ON_CHECKED_CHANGED_TAG)
    public void onCategoryIdChecked(Integer id) {
        mCurrentCategoryId = id;
    }


}
