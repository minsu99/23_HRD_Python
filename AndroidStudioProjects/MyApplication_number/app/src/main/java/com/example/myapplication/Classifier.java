package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Pair;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Classifier {
    Context context;
    private static final String MODEL_NAME = "cnn_model.tflite";

    Interpreter interpreter = null;

    int modelInputWidth = 0;
    int modelInputHeight = 0;
    int modelInputChannel = 0;

    int modelOutputClasses; // 출력할 분류의 수

    public Classifier(Context context) {
        this.context = context;
    }

    private ByteBuffer loadModelFile(String modelName) throws IOException{
        AssetManager am = context.getAssets();
        AssetFileDescriptor afd = am.openFd(modelName);
        FileInputStream fis = new FileInputStream((afd.getFileDescriptor()));
        FileChannel fc = fis.getChannel();
        long startOffset = afd.getStartOffset();
        long declearedLength = afd.getDeclaredLength();

        return fc.map(FileChannel.MapMode.READ_ONLY, startOffset, declearedLength);
    }

    public void init() throws IOException {
        ByteBuffer model = loadModelFile(MODEL_NAME);
        model.order(ByteOrder.nativeOrder());
        interpreter = new Interpreter(model);

        initModelShape();
    }

    private void initModelShape() {
        Tensor inputTensor = interpreter.getInputTensor(0);
        int[] inputShape = inputTensor.shape();
        modelInputChannel = inputShape[0];
        modelInputWidth = inputShape[1];
        modelInputHeight = inputShape[2];

        Tensor outputTensor = interpreter.getOutputTensor(0);
        int[] outputShape = outputTensor.shape();
        modelOutputClasses = outputShape[1];
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap,modelInputWidth,modelInputHeight,false);
    }

    private ByteBuffer convertBitmapToGrayByteBuffer(Bitmap bitmap) {
        // 출력을 내보낼 원본과 동일한 크기의 새로운 비트맵 메모리 공간 할당
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bitmap.getByteCount());
        // 멀티바이트 저장 순서는 네이티브 기준으로 설정
        byteBuffer.order(ByteOrder.nativeOrder());

        // 원본 비트맵에서 모든 픽셀을 int 형으로 변환하기 위한 공간 할당
        int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
        // 원본 비트맵에서 모든 픽셀을 32bit int 형으로 가져오기
        bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,
                bitmap.getWidth(), bitmap.getHeight());

        // 모든 픽셀에 대하여 RGB컬러를 Gray로 변환하여 저장
        for(int pixel : pixels) {
            // 모든 픽셀에 대해서 R,G,B 값을 각각 가져오기
            int r = pixel >> 16 & 0xFF; // 가장 높은 자리에 있는 Red
            int g = pixel >> 8 & 0xFF; // 두번째 높은 자리에 있는 Green
            int b = pixel & 0xFF;   // 가장 낮은 자리에 있는 Blue

            // 3가지 색상을 평균내기
            float avgPixelValue = (r+g+b)/3.0f;
            // 1바이트 값 최대인 255로 나누어 0~1사이의 값으로 정규화 하기
            float normalizedPixelValue = avgPixelValue/255.0f;
            // 정규화된 픽셀을 새로운 공간에 저장하기
            byteBuffer.putFloat(normalizedPixelValue);
        }
        return byteBuffer; // 결과값 돌려주고 끝내기
    }

    public Pair<Integer, Float> classify(Bitmap image) {
        // 크기를 줄인 다음, 컬러 영상을 흑백으로 바꾼다.
        ByteBuffer buffer = convertBitmapToGrayByteBuffer(resizeBitmap(image));

        // 예측 결과를 저장할 2차원 공간(여러 개 처리를 위한 2차원 배열 형식)
        float[][] result = new float[1][modelOutputClasses];

        interpreter.run(buffer, result);

        return argmax(result[0]);
    }

    private Pair<Integer, Float> argmax(float[] array){
        int argmax = 0;
        float max = array[0];
        for(int i = 1; i< array.length; i++) {
            float f= array[i];
            if(f>max) {
                argmax = i;
                max = f;
            }
        }
        return new Pair<>(argmax,max);
    }

    public void finish() {
        if(interpreter != null)
            interpreter.close();
    }
}