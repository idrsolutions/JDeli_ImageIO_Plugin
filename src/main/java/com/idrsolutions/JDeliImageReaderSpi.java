/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;

@SuppressWarnings("WeakerAccess")
public class JDeliImageReaderSpi extends ImageReaderSpi {

    private static final String vendorName = "IDRSolutions";
    private static final String version = "1.0";
    private static final String readerClassName = JDeliImageReader.class.getName();
    private static final String[] writerSpiNames = {"com.idrsolutions.JDeliImageWriterSpi"};

    // Metadata formats, more information below
    private static final boolean supportsStandardStreamMetadataFormat = false;
    private static final String nativeStreamMetadataFormatName = null;
    private static final String nativeStreamMetadataFormatClassName = null;
    private static final String[] extraStreamMetadataFormatNames = null;
    private static final String[] extraStreamMetadataFormatClassNames = null;
    private static final boolean supportsStandardImageMetadataFormat = false;
    private static final String nativeImageMetadataFormatName
            = "com.idrsolutions.JDeliMetadata_1.0";
    private static final String nativeImageMetadataFormatClassName
            = "com.idrsolutions.JDeliMetadata";
    private static final String[] extraImageMetadataFormatNames = null;
    private static final String[] extraImageMetadataFormatClassNames = null;
    private ImageReaderSpi delegate;

    public JDeliImageReaderSpi(final String[] names, final String[] suffixes, final String[] MIMETypes) {

        super(vendorName, version,
                names, suffixes, MIMETypes,
                readerClassName,
                new Class[]{File.class, URL.class, ImageInputStream.class}, // Accept ImageInputStreams
                writerSpiNames,
                supportsStandardStreamMetadataFormat,
                nativeStreamMetadataFormatName,
                nativeStreamMetadataFormatClassName,
                extraStreamMetadataFormatNames,
                extraStreamMetadataFormatClassNames,
                supportsStandardImageMetadataFormat,
                nativeImageMetadataFormatName,
                nativeImageMetadataFormatClassName,
                extraImageMetadataFormatNames,
                extraImageMetadataFormatClassNames);
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {
        return false;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "JDeli Image Reader";
    }

    @Override
    public ImageReader createReaderInstance(final Object extension) throws IOException {
        final String format = (String)extension;

        if (format == null) {
            throw new IOException("This format is not supported");
        }
        if (delegate == null) {
           return new JDeliImageReader(this, format, null);
        } else {
            return new JDeliImageReader(this, format, delegate.createReaderInstance());
        }
    }

    @SuppressWarnings("all")
    @Override
    public void onRegistration(final ServiceRegistry registry, final Class<?> category) {
       Iterator i = registry.getServiceProviders(category, true);
        while (i.hasNext()) {
            final ImageReaderSpi provider = (ImageReaderSpi) i.next();
            if (provider != this && provider.getFormatNames()[0].equalsIgnoreCase(this.getFormatNames()[0])) {
                delegate = provider;
                break;
            } else {
                delegate = null;
            }
        }
    }
}
