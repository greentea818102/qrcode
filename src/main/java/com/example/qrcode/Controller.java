package com.example.qrcode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.List;

@RestController
public class Controller {

@GetMapping("api/health")
public ResponseEntity < String > health () {
    return ResponseEntity.ok ( "OK" );
}



@GetMapping("api/qrcode")
public ResponseEntity < ? > qrCode (@RequestParam (name = "contents") String contents,
                                    @RequestParam(name = "size", defaultValue = "250") int size ,
                                    @RequestParam(name = "type", defaultValue = "png") String type,
                                    @RequestParam (name = "correction", defaultValue = "L") String correction ) {
    // Validate size
    if (contents == null || contents.trim ().isEmpty ()) {
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( Map.of("error", "Contents cannot be null or blank") );
    }
    if (size < 150 || size > 350) {
        //ResponseData responseMessage = new ResponseData ( "Image size must be between 150 and 350 pixels" );
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST )
                .body ( Map.of("error", "Image size must be between 150 and 350 pixels"  ) );
    }
    if(!List.of("L","M","Q","H").contains(correction)){
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( Map.of("error", "Permitted error correction levels are L, M, Q, H" ) );
    }


    // Validate type
    if (!(type.equals ( "png" ) || type.equals ( "jpeg" ) || type.equals ( "gif" ))) {
        //ResponseData responseMessage = new ResponseData ( "Only png, jpeg and gif image types are supported" );
        return ResponseEntity.status ( HttpStatus.BAD_REQUEST )
                .body ( Map.of("error","Only png, jpeg and gif image types are supported" ) );
    }

    // Create the image if both parameters are valid
    BufferedImage image = writeQRCode.generateQRCode(contents, size, size, correction);

    try (var baos = new ByteArrayOutputStream ()) {
        // Set the correct media type for the response
        String mediaType = "image/" + type.toLowerCase ();

        // Write image to the output stream
        assert image != null;
        ImageIO.write ( image , type , baos );
        byte[] bytes = baos.toByteArray ();

        // Return the image with correct content type
        return ResponseEntity.ok ()
                .contentType ( MediaType.parseMediaType ( mediaType ) )
                .body ( bytes );

    } catch (IOException e) {
        return ResponseEntity.status ( HttpStatus.INTERNAL_SERVER_ERROR )
                .body ( null );
    }
}
}
