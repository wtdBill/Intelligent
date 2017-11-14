package com.muxin.asus.arg;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.SensorBean;
import com.muxin.asus.arg.common.impl.SensorImpl;
import com.muxin.asus.arg.main.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testShowSensor() throws Exception {
        final SensorBean[][] s = {new SensorBean[4]};
        new SensorImpl(new MainActivity()).showSensor(new ModelAfter() {
            @Override
            public void success(Object o) {
                s[0] = (SensorBean[]) o;
            }

            @Override
            public void error() {

            }
        });
//        assertEquals("aaaa",  "", s[0][0]);

    }




    public void testGet() throws Exception {
    }
}