package utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Hashtable;

public class QRCodeGenerator {

    public static BufferedImage generateQRCodeImage(String data) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 300, 300, hints);

        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        return image;
    }

    public static void displayQRCode(String data) {
        try {
            // Tạo mã QR và lấy hình ảnh
            BufferedImage qrCodeImage = generateQRCodeImage(data);

            // Tạo một JFrame để hiển thị mã QR
            JFrame frame = new JFrame("QR Code");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 350);
            frame.setLayout(new BorderLayout());

            // Đặt hình ảnh vào JLabel
            JLabel label = new JLabel(new ImageIcon(qrCodeImage));
            frame.add(label, BorderLayout.CENTER);

            // Hiển thị JFrame
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WritableImage getImage(BufferedImage img){
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        newImg.createGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

        int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
        IntBuffer buffer = IntBuffer.wrap(type_int_agrb);

        PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer(newImg.getWidth(), newImg.getHeight(), buffer, pixelFormat);
        return (new WritableImage(pixelBuffer));
    }

    public static WritableImage getQRCodeImage(String data) {
        try {
            BufferedImage bi = generateQRCodeImage(data);

            WritableImage wr = getImage(bi);

            return wr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void main(String[] args) {
        String data = "https://www.examplex.com";
        displayQRCode(data);
    }
}
