/*
 * Copyright (c) 1997-2023 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.imageio.ImageIOSupport;
import com.idrsolutions.image.encoder.OutputFormat;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageOutputStream;

@SuppressWarnings("WeakerAccess")
public class JDeliImageWriterSpi extends ImageWriterSpi {

    private static final String vendorName = "IDRSolutions";
    private static final String version = "1.0";
    private static final String writerClassName = JDeliImageWriter.class.getName();

    private static final String[] readerSpiNames = {
        "com.idrsolutions.JDeliImageReaderSpi"};

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

    @SuppressWarnings("WeakerAccess")
    public JDeliImageWriterSpi(final String[] name, final String[] suffix, final String[] MIMEType) {
        super(vendorName, version,
                name, suffix, MIMEType,
                writerClassName,
                new Class[]{ImageOutputStream.class}, // Write to ImageOutputStreams
                readerSpiNames,
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
    public boolean canEncodeImage(final ImageTypeSpecifier imageType) {
        if (isRegistered()) {
            final int bands = imageType.getNumBands();
            return bands == 1 || bands == 2 || bands == 3 || bands == 4;
        } else {
            return false;
        }
    }

    @Override
    public String[] getMIMETypes() {
        return isRegistered() ? MIMETypes : new String[0];
    }

    @Override
    public String getDescription(final Locale locale) {
        return "JDeli Image Writer";
    }

    @Override
    public ImageWriter createWriterInstance(final Object extension) throws IOException {
        final OutputFormat format = (OutputFormat) extension;
        if (format == null) {
            throw new IOException("This format is not supported");
        }
        return new JDeliImageWriter(this, format);
    }

    @Override
    public String[] getFormatNames() {
        return isRegistered() ? names : new String[0];
    }

    @Override
    public String[] getFileSuffixes() {
        return isRegistered() ? suffixes : new String[0];
    }

    @SuppressWarnings("all")
    @Override
    public void onRegistration(final ServiceRegistry registry, final Class<?> category) {
        if (isRegistered()) {
            final Iterator i = registry.getServiceProviders(category, true);
            while (i.hasNext()) {
                final ImageWriterSpi provider = (ImageWriterSpi) i.next();
                if (provider != this) {
                    registry.setOrdering(ImageWriterSpi.class, this, provider);

                }
            }
        } else {
            registry.deregisterServiceProvider(registry.getServiceProviderByClass(this.getClass()));
        }
    }

    protected boolean isRegistered() {
        return ImageIOSupport.isregisteredWriter(OutputFormat.valueOf(suffixes[0].toUpperCase(Locale.ENGLISH)));
    }
}
