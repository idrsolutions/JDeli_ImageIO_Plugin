package com.idrsolutions;

import com.idrsolutions.image.encoder.OutputFormat;
import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;

public class BMPImageWriterSpi extends JDeliImageWriterSpi {

    private static final String[] names = {"BMP"};
    private static final String[] suffixes = {"bmp"};
    private static final String[] MIMETypes = {"image/bmp"};

    public BMPImageWriterSpi() {
        super(names, suffixes, MIMETypes);
    }

    @Override
    public boolean canEncodeImage(final ImageTypeSpecifier imageType) {
        final int bands = imageType.getNumBands();
        return bands == 1 || bands == 2 || bands == 3 || bands == 4;
    }

    @Override
    public String getDescription(final Locale locale) {
        return "BMP Image Writer";
    }

    @Override
    public ImageWriter createWriterInstance(final Object extension) throws IOException {
        return super.createWriterInstance(OutputFormat.BMP);
    }

}
