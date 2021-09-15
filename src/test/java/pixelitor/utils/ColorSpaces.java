package pixelitor.utils;

import net.jafama.FastMath;

public class ColorSpaces {

    public static int packRGB(int r, int g, int b) {
        return packARGB(255, r, g, b);
    }

    public static int[] unpackRGB(int rgb, int[] rgbs) {
        rgbs = checkArray(rgbs, 3);
        rgbs[0] = (rgb >> 16) & 0xFF;
        rgbs[1] = (rgb >> 8) & 0xFF;
        rgbs[2] = (rgb) & 0xFF;
        return rgbs;
    }

    public static int packARGB(int a, int r, int g, int b) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }

    public static int[] unpackARGB(int argb, int[] argbs) {
        argbs = checkArray(argbs, 4);
        argbs[0] = (argb >> 24) & 0xFF;
        argbs[1] = (argb >> 16) & 0xFF;
        argbs[2] = (argb >> 8) & 0xFF;
        argbs[3] = (argb) & 0xFF;
        return argbs;
    }

    public static float[] RGBtoOKLAB(int[] rgb, float[] oklab) {
        assert rgb.length >= 3;
        oklab = checkArray(oklab, 3);

        float l = (0.4122214708f * rgb[0] + 0.5363325363f * rgb[1] + 0.0514459929f * rgb[2]) / 255;
        float m = (0.2119034982f * rgb[0] + 0.6806995451f * rgb[1] + 0.1073969566f * rgb[2]) / 255;
        float s = (0.0883024619f * rgb[0] + 0.2817188376f * rgb[1] + 0.6299787005f * rgb[2]) / 255;

        l = (float) FastMath.cbrt(l);
        m = (float) FastMath.cbrt(m);
        s = (float) FastMath.cbrt(s);

        oklab[0] = 0.2104542553f * l + 0.7936177850f * m - 0.0040720468f * s;
        oklab[1] = 1.9779984951f * l - 2.4285922050f * m + 0.4505937099f * s;
        oklab[2] = 0.0259040371f * l + 0.7827717662f * m - 0.8086757660f * s;

        return oklab;
    }

    public static int[] OKLABtoRGB(float[] oklab,int[] rgb) {
        assert oklab.length>=3;
        rgb = checkArray(rgb, 3);

        float l = oklab[1] + 0.3963377774f * oklab[2] + 0.2158037573f * oklab[3];
        float m = oklab[1] - 0.1055613458f * oklab[2] - 0.0638541728f * oklab[3];
        float s = oklab[1] - 0.0894841775f * oklab[2] - 1.2914855480f * oklab[3];

        l = FastMath.pow3(l);
        m = FastMath.pow3(m);
        s = FastMath.pow3(s);

        rgb[0] = ((int) ((4.0767417f * l - 3.3077115913f * m + 0.2309699292f * s) * 255));
        rgb[0] = ((int) ((-1.268438f * l + 2.6097574011f * m - 0.3413193965f * s) * 255));
        rgb[0] = ((int) ((-0.0041960864f * l - 0.7034186147f * m + 1.7076147010f * s) * 255));

        return rgb;
    }

    private static int[] checkArray(int[] array, int length) {
        if (array == null) {
            return new int[length];
        }
        if (array.length < length) {
            throw new ArrayIndexOutOfBoundsException("Given array must be null, or have a minimum length of " + length);
        }
        return array;
    }

    private static float[] checkArray(float[] array, int length) {
        if (array == null) {
            return new float[length];
        }
        if (array.length < length) {
            throw new ArrayIndexOutOfBoundsException("Given array must be null, or have a minimum length of " + length);
        }
        return array;
    }
}
