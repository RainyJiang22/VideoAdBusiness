package com.example.videobusiness;

import android.widget.Button;
import android.widget.TextView;

import com.example.videobusiness.activity.TestActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author jacky
 * @date 2021/8/23
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class TestActivityTest {

    @Test
    public void clickingButtonChangeTextView() throws Exception {
        TestActivity testActivity = Robolectric.setupActivity(TestActivity.class);
        Button button =  testActivity.findViewById(R.id.text_button);
        TextView results = testActivity.findViewById(R.id.txt_test);

        //模拟点击按钮
        button.performClick();
        Assert.assertEquals("Robolectric Rocks!", results.getText().toString());
    }
}
