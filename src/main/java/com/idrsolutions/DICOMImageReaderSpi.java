package com.idrsolutions;

import com.idrsolutions.image.ImageFormat;
import com.idrsolutions.imageio.ImageIOSupport;
import com.idrsolutions.image.ImageTypeFinder;


import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class DICOMImageReaderSpi extends JDeliImageReaderSpi {

    private static final String[] names = {"DICOM"};
    private static final String[] suffixes = {"dcm"};
    private static final String[] MIMETypes = {"image/dicom"};

    public DICOMImageReaderSpi() {

        super(names, suffixes, MIMETypes);
    }

    @Override
    public String getDescription(final Locale locale) {
        return "DICOM JDeli Image Reader";
    }

    @Override
    public boolean canDecodeInput(final Object source) throws IOException {

        final ImageInputStream input = (ImageInputStream) source;
        final byte[] b = new byte[140];
        input.read(b);

        return ImageTypeFinder.getImageType(b).equals(ImageFormat.DICOM_IMAGE);

    }

    @Override
    public ImageReader createReaderInstance() throws IOException {
        return createReaderInstance("dcm");
    }

    @Override
    protected boolean isRegistered() {
        return ImageIOSupport.isregisteredReader(ImageIOSupport.InputFormat.valueOf(suffixes[0].toUpperCase()));

    }
}
