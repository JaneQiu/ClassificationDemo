package jane.mall.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 19:16
 *         description
 *         vsersion
 */
public class BaseFragment extends Fragment {


    protected void startActivitys(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        getActivity().startActivity(intent);
    }

    protected void startActivitys(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    protected <T extends View> T findView(@IdRes int viewId) {
        if (getView() != null) {
            return (T) getView().findViewById(viewId);
        }
        return null;
    }


}
