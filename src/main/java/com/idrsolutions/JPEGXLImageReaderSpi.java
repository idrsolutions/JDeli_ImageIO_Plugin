package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class JPEGXLImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"JPEGXL"};
    private static final String[] suffixes = {"jxl"};
    private static final String[] MIMETypes = {"image/jxl"};

    public JPEGXLImageReaderSpi () {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.JPEGXL_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public String getDescription(final Locale locale) {
        return "JPEG XL JDeli Image Reader";
    }

    public static boolean isFormatSupported(final String extension) {
        return "JPEGXL".equalsIgnoreCase(extension);
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("jxl");
    }
}
