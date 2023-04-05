/*
 * Copyright (c) 1997-2023 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.encoder.OutputFormat;

import javax.imageio.ImageWriteParam;
import java.util.Locale;

/**
 * A class that provides Image Write parameters for the Image writer
 * This class extends ImageWriteParam
 */
@SuppressWarnings("WeakerAccess")
public class JDeliImageWriteParam extends ImageWriteParam {

    private final OutputFormat format;
    private final String[] formats;

    /**
     * Constructor to set the Image Write Parameters
     *
     * @param locale Locale
     * @param format OutputFormat
     */
    public JDeliImageWriteParam(final Locale locale, final OutputFormat format) {
        this.locale = locale;
        this.format = format;
        compressionTypes = new String[]{"none", "QUANTISED8BIT", "ZLIB_BETTER_COMPRESSION", "ZLIB_BETTER_SPEED", "DEFLATE", "DEFLATE_BETTER_COMPRESSION", "DEFLATE_BETTER_SPEED", "JPEG", "LOSSLESS", "LOSSY"};
        formats = new String[]{"JPEG", "JPG", "JPX", "JP2", "JPEG2000", "PNG", "TIFF", "WEBP"};// This is what we have currently compressible through ImageIO
    }

    /**
     * Check if the format can be compressed
     *
     * @return boolean
     */
    @Override
    public boolean canWriteCompressed() {
        for (final String type : formats) {
            if (format.name().equalsIgnoreCase(type)) {
                canWriteCompressed = true;
                break;
            }
        }
        return canWriteCompressed;
    }
}
