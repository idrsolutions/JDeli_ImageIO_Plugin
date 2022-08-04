package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class WMFImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"WMF"};
    private static final String[] suffixes = {"wmf"};
    private static final String[] MIMETypes = {"image/wmf"};

    public WMFImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "WMF JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {

        final ImageInputStream input = (ImageInputStream) source;
        final byte[] b = new byte[140];
        input.read(b);

        return ImageTypeFinder.getImageType(b).equals(ImageFormat.WMF_IMAGE);

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("wmf");
    }
}
