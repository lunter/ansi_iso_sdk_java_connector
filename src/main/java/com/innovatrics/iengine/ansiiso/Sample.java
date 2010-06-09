package com.innovatrics.iengine.ansiiso;

/**
 * A sample application which shows the API usage.
 * @author Martin Vysny
 */
public class Sample {

    public static void main(String[] args) throws Exception {
        final AnsiIso i = new AnsiIso();
        i.init();
        try {
            System.out.println("Using ANSI/ISO Generator & Matcher SDK, version " + i.getVersion());
            final RawImage img = i.loadBMP("fingerprint.bmp");
            System.out.println("Fingerprint.bmp - " + img);
            int quality = i.getImageQuality(img.width, img.height, img.raw);
            System.out.println("First image quality: " + quality);
            final RawImage img2 = i.loadBMP("fingerprint2.bmp");
            System.out.println("Fingerprint2.bmp - " + img2);
            quality = i.getImageQuality(img2.width, img2.height, img2.raw);
            System.out.println("Second image quality: " + quality);
            final byte[] isoTemplate = i.isoCreateTemplate(img.width, img.height, img.raw);
            final byte[] ansiTemplate = i.isoConvertToANSI(isoTemplate);
            final byte[] isoTemplate2 = i.isoCreateTemplate(img2.width, img2.height, img2.raw);
            final byte[] ansiTemplate2 = i.isoConvertToANSI(isoTemplate2);
            i.isoSetTemplateParameter(isoTemplate, TemplateParameter.PARAM_FINGER_POSITION, AnsiIso.FingerPosition.LEFT_THUMB.ordinal());
            final int value = i.isoGetTemplateParameter(isoTemplate, TemplateParameter.PARAM_TEMPLATE_SIZE);
            System.out.println("Template size of ISO template in bytes: " + value);
            i.isoSaveTemplate("fingerprint.iso", isoTemplate);
            final int score = i.isoVerifyMatch(isoTemplate, isoTemplate2, 180);
            final int scoreAnsi = i.ansiVerifyMatch(ansiTemplate, ansiTemplate2, 180);
            System.out.println("Similarity score (calculated from ISO templates): " + score);
            System.out.println("Similarity score (calculated from ANSI templates): " + scoreAnsi);
        } finally {
            i.terminate();
        }
    }
}
