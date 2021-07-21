/*
 * Copyright (c) 1997-2021 IDRsolutions (https://www.idrsolutions.com)
 */
package com.idrsolutions;


import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.image.JDeli;
import com.idrsolutions.image.encoder.options.Metadata;
import javax.imageio.metadata.IIOMetadata;
import org.w3c.dom.Node;

@SuppressWarnings("WeakerAccess")
public class JDeliMetadata extends IIOMetadata {

    private int height;
    private int width;
    protected ImageFormat type;
    private Metadata meta;

    public Metadata getMetadata() {
        return meta;
    }

    public void setMetadata(final byte[] bytes) throws Exception {
        meta = JDeli.getImageInfo(bytes);
        height = meta.getHeight();
        width = meta.getWidth();
        type = meta.getImageMetadataType();
    }

     public int getWidth() {
         return width;
     }

     public int getHeight() {
         return height;
     }

    @Override
    public boolean isReadOnly() {
        boolean isReadOnly = false;
        if (!JDeli.isImageSupportedForOutput(nativeMetadataFormatName)) {
            isReadOnly = true;
        }

        return isReadOnly;
    }

    @Override
    public Node getAsTree(final String formatName) {
        return getStandardTree();
    }

    @Override
    public void mergeTree(final String formatName, final Node root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
