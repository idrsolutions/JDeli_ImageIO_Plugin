/*
 * Copyright (c) 1997-2023 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class WEBPImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"WEBP"};
    private static final String[] suffixes = {"webp"};
    private static final String[] MIMETypes = {"image/webp"};

    public WEBPImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "WEBP JDeli Image Reader";
    }

    public static boolean isFormatSupported(final String extension) {
        return "WEBP".equalsIgnoreCase(extension);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.WEBP_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("webp");
    }
}
