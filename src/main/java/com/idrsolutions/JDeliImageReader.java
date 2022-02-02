/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;

import com.idrsolutions.image.JDeli;
import com.idrsolutions.image.encoder.OutputFormat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import com.idrsolutions.image.heic.options.HeicMetadata;
import com.idrsolutions.image.metadata.Exif;
import org.jpedal.utils.FastByteArrayOutputStream;
import org.jpedal.utils.LogWriter;

/**
 * This is the image reader for the JDeli ImageIO plug-in and utilises JDeli to
 * read images This class extends ImageReader
 */
@SuppressWarnings("WeakerAccess")
public class JDeliImageReader extends ImageReader {

    private ImageInputStream stream;
    private BufferedImage image;
    private final String format;

    private int currentImageIndex = -1;
    private int currentThumbnailIndex = -1;
    final JDeliMetadata metadata;
    private byte[] bytes;
    private BufferedImage tn;
    private final ImageReader delegate;

    @SuppressWarnings("WeakerAccess")
    public JDeliImageReader(final ImageReaderSpi jdeliSpi, final String format, final ImageReader delegate) {
        super(jdeliSpi);
        metadata = new JDeliMetadata();
        this.format = format;
        this.delegate = delegate;
    }

    /**
     * Sets variable stream to the input passed in
     *
     * @param input Object
     * @param seekForwardOnly boolean
     * @param ignoreMetaData boolean
     */
    @Override
    public void setInput(final Object input, final boolean seekForwardOnly, final boolean ignoreMetaData) {
        if (File.class.equals(input.getClass())) {
            try {
                stream = new FileImageInputStream((File) input);
                if (delegate != null) {
                    delegate.setInput(new FileImageInputStream((File) input), seekForwardOnly, ignoreMetaData);
                }
            } catch (final IOException e) {
                LogWriter.writeLog("Failed to create stream from file : " + e.getMessage());
            }
        } else if (URL.class.equals(input.getClass())) {
            try {
                stream = ImageIO.createImageInputStream(((URL) input).openStream());
                if (delegate != null) {
                    delegate.setInput(ImageIO.createImageInputStream(((URL) input).openStream()), seekForwardOnly, ignoreMetaData);
                }
            } catch (final IOException e) {
                LogWriter.writeLog("Failed to create stream from url : " + e.getMessage());
            }
        } else {
            stream = (ImageInputStream) input;
            if (delegate != null) {
                delegate.setInput(input, seekForwardOnly, ignoreMetaData);
            }
        }
        getByteArray();
        this.seekForwardOnly = seekForwardOnly;
    }

    /**
     * Return the number of images in the stream
     *
     * @param allowSearch boolean
     * @return int
     */
    @Override
    public int getNumImages(final boolean allowSearch) {
        if (!allowSearch) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Return the width of the image
     *
     * @param imageIndex the image index
     * @return width of the image
     * @throws java.io.IOException if an input output error occurs
     */
    @Override
    public int getWidth(final int imageIndex) throws IOException {
        int w;
        if (delegate == null) {
            getByteArray();
            try {
                Rectangle rectangle = JDeli.readDimension(bytes);
                if (rectangle != null) {
                    w = rectangle.width;
                } else {
                    w = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                w = 0;
            }
        } else {
            w = delegate.getWidth(imageIndex);
        }
        return w;
    }

    /**
     * returns the height of the image
     *
     * @param imageIndex the image index
     * @return height of the image
     * @throws java.io.IOException if an input output error occurs
     */
    @Override
    public int getHeight(final int imageIndex) throws IOException {
        int h;
        if (delegate == null) {
            getByteArray();
            try {
                Rectangle rectangle = JDeli.readDimension(bytes);
                if (rectangle != null) {
                    h = rectangle.height;
                } else {
                    h = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                h = 0;
            }
        } else {
            h = delegate.getHeight(imageIndex);
        }
        return h;
    }

    /**
     * Returns the possible imageTypes of the image
     *
     * @param imageIndex int
     * @return Iterator
     */
    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(final int imageIndex) {
        final List<ImageTypeSpecifier> l = new ArrayList<>();
        final ImageTypeSpecifier imageType = new ImageTypeSpecifier(image);
        l.add(imageType);
        currentImageIndex = imageIndex;
        return l.iterator();
    }

    @Override
    public String getFormatName() {
        return format;
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        return delegate.getStreamMetadata();
    }

    @Override
    public IIOMetadata getImageMetadata(final int imageIndex) throws IOException {
        return delegate.getImageMetadata(imageIndex);
    }

    @Override
    public int getNumThumbnails(final int imageIndex) {
        getByteArray();
        try {
            Exif exif = ((HeicMetadata)JDeli.getImageInfo(bytes)).getExif();
            if (exif != null) {
                return exif.getIfdImages().size();
            }
        } catch (final Exception ex) {
            ex.getStackTrace();
        }
        return 0;
    }

    public void readMetadata() throws Exception {
        metadata.setMetadata(bytes);
    }

    /**
     * Read the image from the stream
     *
     * @param imageIndex int
     * @param param ImageReadParam
     * @return BufferedImage
     * @throws IOException if an error occurs in reading image
     */
    @Override
    public BufferedImage read(final int imageIndex, final ImageReadParam param) throws IOException {
        try {
            if (seekForwardOnly && currentImageIndex == 0) {
                minIndex++;
            }
            if (currentImageIndex != imageIndex) {
                currentImageIndex = imageIndex;
                image = JDeli.read(bytes);
            } else {
                if (image != null) {
                    return image;
                }
            }
        } catch (final Exception ex) {
            throw new IOException(ex);
        }
        return image;
    }

    private void getByteArray() {
        if (bytes == null) {
            try {
                final FastByteArrayOutputStream ba = new FastByteArrayOutputStream(8192);
                int nRead;
                final byte[] data = new byte[2048];
                while ((nRead = stream.read(data, 0, data.length)) != -1) {
                    ba.write(data, 0, nRead);
                }
                bytes = ba.toByteArray();
                stream.seek(0);
            } catch (final IOException ex) {
                ex.getStackTrace();
            }
        }
    }

    /**
     * Read the thumbnail
     *
     * @param imageIndex int
     * @param thumbnailIndex int
     * @return BufferedImage
     * @throws IOException if an error occurs in reading image
     */
    @Override
    public BufferedImage readThumbnail(final int imageIndex, final int thumbnailIndex) throws IOException {
        if (!format.equalsIgnoreCase(OutputFormat.HEIC.name()) && !format.equalsIgnoreCase(OutputFormat.JPEG.name())) {
            tn = delegate.readThumbnail(imageIndex, thumbnailIndex);
        } else if (currentThumbnailIndex != thumbnailIndex && currentImageIndex != imageIndex && tn == null) {
            currentThumbnailIndex = thumbnailIndex;
            getByteArray();
            try {
                tn = JDeli.readEmbeddedThumbnail(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tn;
    }

    @Override
    public boolean readerSupportsThumbnails() {
        return format.equals("heic") || format.equals("jpeg");
    }
}
