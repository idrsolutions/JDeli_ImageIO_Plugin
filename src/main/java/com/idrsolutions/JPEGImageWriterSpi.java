package com.idrsolutions;

import com.idrsolutions.imageio.ImageIOSupport;
import com.idrsolutions.image.encoder.OutputFormat;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;

public class JPEGImageWriterSpi extends JDeliImageWriterSpi {

    private static final String[] names = {"JPEG"};
    private static final String[] suffixes = {"jpg", "jpeg"};
    private static final String[] MIMETypes = {"image/jpeg"};

    public JPEGImageWriterSpi() {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canEncodeImage(final ImageTypeSpecifier imageType) {
        final int bands = imageType.getNumBands();
        return bands == 1 || bands == 2 || bands == 3 || bands == 4;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "JPEG Image Writer";
    }

    @Override
    public ImageWriter createWriterInstance(final Object extension) throws IOException {
        return super.createWriterInstance(OutputFormat.JPEG);
    }

    @Override
    protected boolean isRegistered() {
        return ImageIOSupport.isregisteredWriter(OutputFormat.valueOf(names[0]));

    }
}
