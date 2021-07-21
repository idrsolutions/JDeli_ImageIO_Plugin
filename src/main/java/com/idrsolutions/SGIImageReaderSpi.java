package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.utility.ImageTypeFinder;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class SGIImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"SGI"};
    private static final String[] suffixes = {"sgi", "rgb"};
    private static final String[] MIMETypes = {"image/sgi"};

    public SGIImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "SGI JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {

        final ImageInputStream input = (ImageInputStream) source;
        final byte[] b = new byte[140];
        input.read(b);

        return ImageTypeFinder.getImageType(b).equals(ImageFormat.SGI_IMAGE);

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("sgi");
    }
}
