package jane.mall;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mMainFragmentTabhost;
    private TabWidget mTabWidget;

    private String[] mTabNames = {
            "首页",
            "分类",
            "购物车",
            "我的"
    };

    private int[] mTabSrc = {
            R.drawable.tab_home_tv,
            R.drawable.tab_classification_tv,
            R.drawable.tab_shopping_cart_tv,
            R.drawable.tab_my_tv
    };

    private Class mClass[] = {HomeFragment.class, ClassificationFragment.class, ShoppingCartFragment.class, MineFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mMainFragmentTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);

        initTabHost();
    }

    private void initTabHost() {
        mMainFragmentTabhost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < mClass.length; i++) {
            TabHost.TabSpec tabSpec = mMainFragmentTabhost.newTabSpec(mTabNames[i]).setIndicator(createTabView(i));
            mMainFragmentTabhost.addTab(tabSpec, mClass[i], null);
            mMainFragmentTabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mMainFragmentTabhost.getTabWidget().setDividerDrawable(null);
        mMainFragmentTabhost.getTabWidget().setStripEnabled(false);
    }

    private View createTabView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_iv);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_tv);

        imageView.setImageResource(mTabSrc[i]);
        textView.setText(mTabNames[i]);

        return view;
    }

}
