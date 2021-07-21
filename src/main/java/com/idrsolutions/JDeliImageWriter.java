/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.JDeli;
import com.idrsolutions.image.encoder.OutputFormat;
import com.idrsolutions.image.utility.SupportedFormats;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;

/**
 * This class is part of the JDeli ImageIO plug-in and utilises JDeli to write out images
 * This class extends ImageWriter
 */
@SuppressWarnings("WeakerAccess")
public class JDeliImageWriter extends ImageWriter {

    private ImageOutputStream stream;
    private final OutputFormat output;
    private JDeliImageWriteParam iwp;

    public JDeliImageWriter(final ImageWriterSpi originatingProvider, final OutputFormat format) {
        super(originatingProvider);
        output = format;
    }

    /**
     * Set the output stream in which to write to
     *
     * @param output Object
     */
    @Override
    public void setOutput(final Object output) {
        super.setOutput(output);
        if (output != null) {
            stream = (ImageOutputStream) output;
        } else {
            stream = null;
        }
    }

    @Override
    public IIOMetadata getDefaultStreamMetadata(final ImageWriteParam param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IIOMetadata getDefaultImageMetadata(final ImageTypeSpecifier imageType, final ImageWriteParam param) {
        return new JDeliMetadata();
    }

    @Override
    public IIOMetadata convertStreamMetadata(final IIOMetadata inData, final ImageWriteParam param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IIOMetadata convertImageMetadata(final IIOMetadata inData, final ImageTypeSpecifier imageType, final ImageWriteParam param) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Writes out the IIOImage using JDeli and the parameters given
     *
     * @param streamMetadata IIOMetadata
     * @param image IIOImage
     * @param param ImageWriteParam
     */
    @Override
    public void write(final IIOMetadata streamMetadata, final IIOImage image, final ImageWriteParam param) {
        final BufferedImage im = (BufferedImage) image.getRenderedImage();
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            if (param != null) {
                final JDeliImageWriteParam params = (JDeliImageWriteParam) param;
                JDeli.write(im, SupportedFormats.getEncoderOptions(params, output), b);
            } else {
                JDeli.write(im, output, b);
            }
            if (stream != null) {
                stream.write(b.toByteArray());
            }
        } catch (final Exception ex) {
            System.err.println("Using" + JDeliImageWriter.class.getName() + " Error:" + ex.getMessage());
        }
    }

    /**
     * Retrieve the JDeliImageWriteParam associated with this writer
     *
     * @return ImageWriteParam
     */
    @Override
    public ImageWriteParam getDefaultWriteParam() {
        if (iwp == null) {
            iwp = new JDeliImageWriteParam(Locale.getDefault(), output);
        }
        return iwp;
    }
}
