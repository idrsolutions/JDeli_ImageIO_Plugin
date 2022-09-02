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

public class HEICImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"HEIC"};
    private static final String[] suffixes = {"heic", "heif"};
    private static final String[] MIMETypes = {"image/heic"};

    public HEICImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "HEIC JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        if(isRegistered()) {
            final ImageInputStream input = (ImageInputStream) source;
            final byte[] b = new byte[140];
            input.read(b);

            return ImageTypeFinder.getImageType(b).equals(ImageFormat.HEIC_IMAGE);
        } else {
            return false;
        }
    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("heic");
    }
}
