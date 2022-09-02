package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class PSDImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"PSD"};
    private static final String[] suffixes = {"psd"};
    private static final String[] MIMETypes = {"image/psd"};

    public PSDImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "PSD JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.PSD_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("psd");
    }
}
