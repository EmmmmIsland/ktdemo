/*
 * Copyright (c) 2010, Snowball Finance and/or its affiliates. All rights reserved.
 * SNOWBALLFINANCE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.demo.toolkit.json;


import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author quan.zhou
 * @since 1.0.0 Sep 04, 2014 21:04
 */
public class Gsons {

    private static final String TAG = "Gsons";

    private static Gson gson = null;

    public static Gson defaultGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();

            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            builder.registerTypeAdapter(double.class, DoubleTypeAdapter);
            builder.registerTypeAdapter(Double.class, DoubleTypeAdapter);
            builder.registerTypeAdapter(int.class, IntegerTypeAdapter);
            builder.registerTypeAdapter(Integer.class, IntegerTypeAdapter);
            builder.registerTypeAdapter(float.class, FloatTypeAdapter);
            builder.registerTypeAdapter(Float.class, FloatTypeAdapter);

            builder.serializeSpecialFloatingPointValues();

            gson = builder.create();
        }
        return gson;
    }

    private static TypeAdapter<Number> DoubleTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result) || "null".equalsIgnoreCase(result)) {
                    return null;
                }
                return Double.parseDouble(result);
            } catch (Exception e) {
                Log.e(TAG, "parse Double fail", e);
                return null;
            }
        }
    };

    private static TypeAdapter<Number> IntegerTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result) || "null".equalsIgnoreCase(result) || "0.0".equalsIgnoreCase(result)) {
                    return null;
                }
                return Integer.parseInt(result);
            }catch (Exception e) {
                Log.e(TAG, "parse Integer fail", e);
                return null;
            }
        }
    };

    private static TypeAdapter<Number> FloatTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result) || "null".equalsIgnoreCase(result)) {
                    return null;
                }
                return Float.parseFloat(result);
            } catch (Exception e) {
                Log.e(TAG, "parse Float fail", e);
                return null;
            }
        }
    };
}
