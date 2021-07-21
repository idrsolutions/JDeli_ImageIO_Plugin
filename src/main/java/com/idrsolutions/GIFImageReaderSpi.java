package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.utility.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class GIFImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"GIF"};
    private static final String[] suffixes = {"gif"};
    private static final String[] MIMETypes = {"image/gif"};

    public GIFImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "GIF JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {

        final ImageInputStream input = (ImageInputStream) source;
        final byte[] b = new byte[140];
        input.read(b);

        return ImageTypeFinder.getImageType(b).equals(ImageFormat.GIF_IMAGE);

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("gif");
    }
}
