module com.idrsolutions.jdeli_imageio_plugin {
    requires java.desktop;

    exports com.idrsolutions;

    exports com.idrsolutions.image;
    exports com.idrsolutions.image.bmp;
    exports com.idrsolutions.image.bmp.options;
    exports com.idrsolutions.image.dicom;
    exports com.idrsolutions.image.dicom.options;
    exports com.idrsolutions.image.emf;
    exports com.idrsolutions.image.emf.options;
    exports com.idrsolutions.image.encoder;
    exports com.idrsolutions.image.encoder.options;
    exports com.idrsolutions.image.gif;
    exports com.idrsolutions.image.gif.options;
    exports com.idrsolutions.image.heic;
    exports com.idrsolutions.image.heic.options;
    exports com.idrsolutions.image.ico;
    exports com.idrsolutions.image.jpeg;
    exports com.idrsolutions.image.jpeg.options;
    exports com.idrsolutions.image.jpeg2000;
    exports com.idrsolutions.image.jpeg2000.options;
    exports com.idrsolutions.image.jpeglossless;
    exports com.idrsolutions.image.metadata;
    exports com.idrsolutions.image.metadata.ifd;
    exports com.idrsolutions.image.pdf;
    exports com.idrsolutions.image.pdf.options;
    exports com.idrsolutions.image.png;
    exports com.idrsolutions.image.png.options;
    exports com.idrsolutions.image.process;
    exports com.idrsolutions.image.psd;
    exports com.idrsolutions.image.psd.options;
    exports com.idrsolutions.image.scale;
    exports com.idrsolutions.image.sgi;
    exports com.idrsolutions.image.sgi.options;
    exports com.idrsolutions.image.tiff;
    exports com.idrsolutions.image.tiff.options;
    exports com.idrsolutions.image.utility;
    exports com.idrsolutions.image.webp;
    exports com.idrsolutions.image.webp.options;
    exports com.idrsolutions.image.wmf;
    exports com.idrsolutions.image.wmf.options;

    exports com.idrsolutions.image.tika;

    exports com.idrsolutions.imageio;

    exports org.jpedal.utils;

    provides javax.imageio.spi.ImageReaderSpi with
            com.idrsolutions.BMPImageReaderSpi,
            com.idrsolutions.DICOMImageReaderSpi,
            com.idrsolutions.EMFImageReaderSpi,
            com.idrsolutions.GIFImageReaderSpi,
            com.idrsolutions.HEICImageReaderSpi,
            com.idrsolutions.JDeliImageReaderSpi,
            com.idrsolutions.JPEG2000ImageReaderSpi,
            com.idrsolutions.JPEGImageReaderSpi,
            com.idrsolutions.JPEGXLImageReaderSpi,
            com.idrsolutions.PNGImageReaderSpi,
            com.idrsolutions.PSDImageReaderSpi,
            com.idrsolutions.SGIImageReaderSpi,
            com.idrsolutions.TIFFImageReaderSpi,
            com.idrsolutions.WEBPImageReaderSpi,
            com.idrsolutions.WMFImageReaderSpi;

    provides javax.imageio.spi.ImageWriterSpi with
            com.idrsolutions.BMPImageWriterSpi,
            com.idrsolutions.HEICImageWriterSpi,
            com.idrsolutions.JDeliImageWriterSpi,
            com.idrsolutions.JPEG2000ImageWriterSpi,
            com.idrsolutions.JPEGImageWriterSpi,
            com.idrsolutions.PDFImageWriterSpi,
            com.idrsolutions.PNGImageWriterSpi,
            com.idrsolutions.TIFFImageWriterSpi,
            com.idrsolutions.WEBPImageWriterSpi;
}