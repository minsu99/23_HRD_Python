package com.example.app1;


import android.graphics.Bitmap;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Classifier {
    private Interpreter interpreter;
    private int inputImageWidth;
    private int inputImageHeight;
    private int numChannels;

    public Classifier(Interpreter interpreter) {
        this.interpreter = interpreter;
        int[] inputShape = interpreter.getInputTensor(0).shape();
        inputImageHeight = inputShape[1];
        inputImageWidth = inputShape[2];
        numChannels = inputShape[3];
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    // 이미지 전처리 및 모델 실행 메서드
    public String classifyImage(Bitmap image) {
        // 이미지를 모델 입력 크기에 맞게 조정
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, inputImageWidth, inputImageHeight, true);

        // 이미지를 모델에 입력하기 위해 ByteBuffer로 변환
        ByteBuffer inputBuffer = preprocessImage(resizedImage);

        // 모델 실행
        TensorBuffer outputBuffer = runInference(inputBuffer);

        // 결과 분류
        String result = postprocessOutput(outputBuffer);

        return result;
    }

    // 이미지를 모델 입력에 맞게 전처리
    private ByteBuffer preprocessImage(Bitmap image) {
        // 이미지를 ByteBuffer로 변환하고 정규화
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(inputImageWidth * inputImageHeight * numChannels * 4);
        inputBuffer.order(ByteOrder.nativeOrder());

        int[] pixels = new int[inputImageWidth * inputImageHeight];
        image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        for (int pixelValue : pixels) {
            // 픽셀 값을 정규화하여 ByteBuffer에 추가
            float normalizedPixelValue = (pixelValue & 0xFF) / 255.0f;
            inputBuffer.putFloat(normalizedPixelValue);
        }

        return inputBuffer;
    }

    // 모델 실행
    // 모델 실행
    private TensorBuffer runInference(ByteBuffer inputBuffer) {
        // 입력 데이터를 모델에 설정
        ByteBuffer outputBuffer = ByteBuffer.allocateDirect(interpreter.getOutputTensor(0).shape()[0] * 4);
        outputBuffer.order(ByteOrder.nativeOrder());

        interpreter.run(inputBuffer, outputBuffer);

        // 결과 텐서를 TensorBuffer로 변환
        int[] outputShape = interpreter.getOutputTensor(0).shape();
        DataType outputDataType = interpreter.getOutputTensor(0).dataType();

        TensorBuffer tensorBuffer = TensorBuffer.createFixedSize(outputShape, outputDataType);
        tensorBuffer.loadBuffer(outputBuffer);

        return tensorBuffer;
    }


    // 모델 결과 후처리
    private String postprocessOutput(TensorBuffer outputBuffer) {
        // 결과를 분류하고 클래스 레이블 및 임계값을 반환
        float threshold = 0.001f;; // 임계값 설정
        float[] outputData = outputBuffer.getFloatArray();
        String label;

        if (outputData[0] >= threshold) {
            label = "This is not a car"; // 칼 클래스
        } else {
            label = "THIS IS A CAR"; // 총 클래스
        }

        return label + "\n임계값: " + outputData[0];
    }


}
