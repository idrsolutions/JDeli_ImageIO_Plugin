package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class BMPImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"BMP"};
    private static final String[] suffixes = {"bmp"};
    private static final String[] MIMETypes = {"image/bmp"};

    public BMPImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "BMP JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.BMP_IMAGE);
        } else {
            return false;
        }

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("bmp");
    }
}
