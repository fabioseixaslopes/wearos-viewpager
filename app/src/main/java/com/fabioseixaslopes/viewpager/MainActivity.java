package com.fabioseixaslopes.viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private static final int NUM_PAGES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        @NonNull
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new Fragment_1();
                case 1:
                    return new Fragment_2();
                case 2:
                    return new Fragment_3();
                default:
                    return new Fragment_4();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
    private static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE=0.85f;
        private static final float MIN_ALPHA=0.5f;

        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            if (position < -1) {
                page.setAlpha(0f);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
                float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    page.setTranslationX(horizontalMargin - verticalMargin / 2);
                } else {
                    page.setTranslationX(-horizontalMargin + verticalMargin / 2);
                }
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else {
                page.setAlpha(0f);
            }
        }
    }
}