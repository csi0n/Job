package com.csi0n.searchjob.ui;

import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by chqss on 2016/5/3 0003.
 */
@Target(METHOD)
@Retention(CLASS)
@ListenerClass(
        targetType = "android.widget.RadioGroup",
        setter = "setOnCheckedChangeListener",
        type = "android.widget.RadioGroup.OnCheckedChangeListener",
        method = @ListenerMethod(
                name = "onCheckedChanged",
                parameters = {
                        "android.widget.RadioGroup",
                        "int",
                }
        )
)
public @interface OnCheckedChanged {
    int[] value() default { View.NO_ID };
}
