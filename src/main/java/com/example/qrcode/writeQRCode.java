package com.example.qrcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.Map;


class writeQRCode {
    public static BufferedImage generateQRCode ( String contents , int width , int height, String correction ) {
        try {
            QRCodeWriter writer = new QRCodeWriter ();
            Map< EncodeHintType, Object > hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf ( correction ));
            BitMatrix bitMatrix = writer.encode ( contents , BarcodeFormat.QR_CODE , width , height, hints );
            return MatrixToImageWriter.toBufferedImage ( bitMatrix );
        } catch (WriterException e) {
            e.printStackTrace ();
            return null;
            // handle the WriterException
        }
    }
}
