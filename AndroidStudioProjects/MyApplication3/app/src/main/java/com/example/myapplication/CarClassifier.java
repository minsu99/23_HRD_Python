package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class CarClassifier {
    private static final String MODEL_NAME = "car_model2.tflite";

    private Context context;
    private Interpreter interpreter;
    private int modelInputWidth;
    private int modelInputHeight;
    private int modelInputChannel;
    private int modelOutputClasses;
    private Map<Integer, String> classMap;

    public CarClassifier(Context context) {
        this.context = context;
        classMap = createClassMap();
    }

    public void loadModel() {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd(MODEL_NAME);

            FileInputStream inputStream = afd.createInputStream();
            long startOffset = afd.getStartOffset();
            long declaredLength = afd.getDeclaredLength();

            ByteBuffer modelBuffer = ByteBuffer.allocateDirect((int) declaredLength);
            FileChannel fileChannel = inputStream.getChannel();
            fileChannel.position(startOffset);
            fileChannel.read(modelBuffer);
            modelBuffer.rewind();

            Interpreter.Options options = new Interpreter.Options();
            interpreter = new Interpreter(modelBuffer, options);

            initModelShape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initModelShape() {
        int numInputs = interpreter.getInputTensorCount();
        int numOutputs = interpreter.getOutputTensorCount();

        if (numInputs != 1 || numOutputs != 1) {
            throw new RuntimeException("This example supports models with only 1 input and 1 output");
        }

        int[] inputShape = interpreter.getInputTensor(0).shape();
        modelInputChannel = inputShape[0];
        modelInputWidth = inputShape[1];
        modelInputHeight = inputShape[2];

        int[] outputShape = interpreter.getOutputTensor(0).shape();
        modelOutputClasses = outputShape[1];
    }

    public Pair<String, Float> classifyImage(Bitmap image) {
        ByteBuffer inputBuffer = preprocessImage(image);
        float[][] outputArray = new float[1][modelOutputClasses];

        interpreter.run(inputBuffer, outputArray);

        // 디버깅을 위해 출력 배열을 로그로 출력
        for (float val : outputArray[0]) {
            Log.d("Output", "Class Probability: " + val);
        }

        Pair<Integer, Float> result = argmax(outputArray[0]);
        int classIndex = result.first;
        String className = classMap.get(classIndex);;


        return new Pair<>(className, result.second);
    }


    private ByteBuffer preprocessImage(Bitmap image) {
        int inputSize = modelInputWidth * modelInputHeight * 3;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(inputSize * 4);

        image = Bitmap.createScaledBitmap(image, modelInputWidth, modelInputHeight, true);
        int[] intValues = new int[modelInputWidth * modelInputHeight];
        image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        int pixel = 0;
        for (int i = 0; i < modelInputWidth; ++i) {
            for (int j = 0; j < modelInputHeight; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                byteBuffer.putFloat((val & 0xFF) / 255.0f);
            }
        }

        return byteBuffer;
    }

    private Pair<Integer, Float> argmax(float[] array) {
        int argmax = 0;
        float max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                argmax = i;
                max = array[i];
            }
        }
        return new Pair<>(argmax, max);
    }

    public void finish() {
        if (interpreter != null) {
            interpreter.close();
        }
    }

    private Map<Integer, String> createClassMap() {
        Map<Integer, String> map = new HashMap<>();

        map.put(0, "Audi");
        map.put(1, "Hyundai Creta");
        map.put(2, "Mahindra Scorpio");
        map.put(3, "Rolls Royce");
        map.put(4, "Swift");
        map.put(5, "Tata Safari");
        map.put(6, "Toyota Innova");
        //
        // Add other class mappings here...
        return map;
    }
}
