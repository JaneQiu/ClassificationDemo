package jane.mall.classification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jane.mall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassificationContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classification_content, container, false);
    }

}
