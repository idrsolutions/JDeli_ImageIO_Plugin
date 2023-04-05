/*
 * Copyright (c) 1997-2023 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.encoder.OutputFormat;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;

public class PNGImageWriterSpi extends JDeliImageWriterSpi {

    private static final String[] names = {"PNG"};
    private static final String[] suffixes = {"png"};
    private static final String[] MIMETypes = {"image/png"};

    public PNGImageWriterSpi() {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canEncodeImage(final ImageTypeSpecifier imageType) {
        final int bands = imageType.getNumBands();
        return bands == 1 || bands == 2 || bands == 3 || bands == 4;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "PNG Image Writer";
    }

    @Override
    public ImageWriter createWriterInstance(final Object extension) throws IOException {
        return super.createWriterInstance(OutputFormat.PNG);
    }

}
