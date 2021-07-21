
/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.utility.ImageTypeFinder;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class PNGImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"PNG"};
    private static final String[] suffixes = {"png"};
    private static final String[] MIMETypes = {"image/png"};

    public PNGImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "PNG JDeli Image Reader";
    }

    public static boolean isFormatSupported(final String extension) {
        return "PNG".equalsIgnoreCase(extension);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {

        final ImageInputStream input = (ImageInputStream) source;
        final byte[] b = new byte[140];
        input.read(b);

        return ImageTypeFinder.getImageType(b).equals(ImageFormat.PNG_IMAGE);

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("png");
    }
}
