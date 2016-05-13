package jane.mall.classification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jane.mall.R;
import jane.mall.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassificationContentFragment extends BaseFragment {

    private static final String EXTRA_TITLE = "title";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classification_menu, container, false);
    }

}
