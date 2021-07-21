/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.encoder.OutputFormat;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;

public class WEBPImageWriterSpi extends JDeliImageWriterSpi {

    private static final String[] names = {"WEBP"};
    private static final String[] suffixes = {"webp"};
    private static final String[] MIMETypes = {"image/webp"};

    public WEBPImageWriterSpi() {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canEncodeImage(final ImageTypeSpecifier imageType) {
        final int bands = imageType.getNumBands();
        return bands == 1 || bands == 2 || bands == 3 || bands == 4;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "WEBP Image Writer";
    }

    @Override
    public String[] getFormatNames() {
        return suffixes;
    }

    @Override
    public ImageWriter createWriterInstance(final Object extension) throws IOException {
        return super.createWriterInstance(OutputFormat.WEBP);
    }
}
