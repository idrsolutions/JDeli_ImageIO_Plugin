/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.ImageTypeFinder;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class TIFFImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"tif", "TIF", "tiff", "TIFF"};
    private static final String[] suffixes = {"tif", "tiff"};
    private static final String[] MIMETypes = {"image/tiff"};

    public TIFFImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "TIFF JDeli Image Reader";
    }

    public static boolean isFormatSupported(final String extension) {
        return "TIFF".equalsIgnoreCase(extension);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.TIFF_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("tiff");
    }
}
