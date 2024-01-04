package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class JPEG2000ImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"JPEG2000"};
    private static final String[] suffixes = {"jpx", "jp2"};
    private static final String[] MIMETypes = {"image/jpeg2000"};

    public JPEG2000ImageReaderSpi() {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.JPEG2000_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public String getDescription(final Locale locale) {
        return "JPEG2000 JDeli Image Reader";
    }

    public static boolean isFormatSupported(final String extension) {
        return "JPEG2000".equalsIgnoreCase(extension);
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("jpx");
    }
}
