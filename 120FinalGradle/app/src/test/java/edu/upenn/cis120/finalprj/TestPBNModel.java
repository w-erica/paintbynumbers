package edu.upenn.cis120.finalprj;

import edu.upenn.cis120.finalprj.support.PBNModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static javax.imageio.ImageIO.read;
import static org.junit.jupiter.api.Assertions.*;

// Class for tests of the model - Done for now
public class TestPBNModel {
    // things to test in constructor:
    // canvas ends up with the right size, there are the right amount of pixels
    // palette ends up the right size with the right colors
    // each color gets the right pixels/ the right amount of pixels
    // pixels end up with the right palette colortags

    // test constructor with image (dont test all 100 pixels, test a few)
    @Test
    public void testConstructorImage() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("testImage.png");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }
        final File filepath = new File(resource.toURI());

//        File filepath = new File("testImage.png");
        BufferedImage bufferedImage;
        try {
            bufferedImage = read(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("whoops lol!",e);
        }
        PBNModel actual = new PBNModel(bufferedImage);
        assertEquals(10, actual.getNumRows(), "canvas rows");
        assertEquals(10, actual.getNumCols(), "canvas columns");
        assertEquals(100, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(4, actual.getNumColors(), "palette length");
        assertEquals(Color.red, actual.getCurrentColor(), "starter color");
        assertEquals(Color.green, actual.getPBNColor(1).getColor(),
                "correct color tag 1");
        assertEquals(36, actual.getPBNColor(0).getPixels().size(),
                "number of pixels stored by color 0");
        assertEquals(4, actual.getPBNColor(3).getPixels().size(),
                "number of pixels stored by color 3");
        assertEquals(0, actual.getPixel(9, 9).getColorTag(),
                "get correct pixel color 9, 9");
        assertEquals(3, actual.getPixel(5, 5).getColorTag(),
                "get correct pixel color 5,5");
        assertEquals(2, actual.getPixel(1, 2).getColorTag(),
                "get correct pixel color 1,2");
    }

    // test constructor with 1 by 1 array
    @Test
    public void testConstructor1by1() {
        Color[][] array = {{new Color(83, 115, 49)}};
        PBNModel actual = new PBNModel(array);
        assertEquals(1, actual.getNumRows(), "canvas rows");
        assertEquals(1, actual.getNumCols(), "canvas columns");
        assertEquals(1, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(1, actual.getNumColors(), "palette length");
        assertEquals(new Color(83, 115, 49), actual.getCurrentColor(), "current color");
        assertEquals(1, actual.getPBNColor(0).getPixels().size(), "number of pixels stored");
    }

    // test constructor with 3 by 3 array with 2 colors
    @Test
    public void testConstructor3by3color2() {
        Color[][] array = {{new Color(83, 115, 49), new Color(83, 115, 48),
                new Color(83, 115, 48)},
                {new Color(83, 115, 48), new Color(83, 115, 49),
                        new Color(83, 115, 49)},
                {new Color(83, 115, 48), new Color(83, 115, 49),
                        new Color(83, 115, 49)}};
        PBNModel actual = new PBNModel(array);
        assertEquals(3, actual.getNumRows(), "canvas rows");
        assertEquals(3, actual.getNumCols(), "canvas columns");
        assertEquals(9, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(2, actual.getNumColors(), "palette length");
        assertEquals(new Color(83, 115, 49), actual.getCurrentColor(), "starter color");
        assertEquals(new Color(83, 115, 48), actual.getPBNColor(1).getColor(),
                "correct color tag 1");
        assertEquals(5, actual.getPBNColor(0).getPixels().size(),
                "number of pixels stored by color 0");
        assertEquals(4, actual.getPBNColor(1).getPixels().size(),
                "number of pixels stored by color 1");
        assertEquals(0, actual.getPixel(0,0).getColorTag(),
                "get correct pixel color 0, 0");
        assertEquals(1, actual.getPixel(0,1).getColorTag(),
                "get correct pixel color 0, 1");
        assertEquals(1, actual.getPixel(0,2).getColorTag(),
                "get correct pixel color 0, 2");
        assertEquals(1, actual.getPixel(1,0).getColorTag(),
                "get correct pixel color 1, 0");
        assertEquals(0, actual.getPixel(1,1).getColorTag(),
                "get correct pixel color 1, 1");
        assertEquals(0, actual.getPixel(1,2).getColorTag(),
                "get correct pixel color 1, 2");
        assertEquals(1, actual.getPixel(2,0).getColorTag(),
                "get correct pixel color 2, 0");
        assertEquals(0, actual.getPixel(2,1).getColorTag(),
                "get correct pixel color 2, 1");
        assertEquals(0, actual.getPixel(2,2).getColorTag(),
                "get correct pixel color 2, 2");
    }

    // test constructor with 3 by 3 array with 9 colors
    // welcome to hell
    @Test
    public void testConstructor3by3color9() {
        Color[][] array = {{new Color(83, 115, 49), new Color(83, 115, 48),
                new Color(83, 115, 47)},
                {new Color(83, 115, 46), new Color(83, 115, 45),
                        new Color(83, 115, 44)},
                {new Color(83, 115, 43), new Color(83, 115, 42),
                        new Color(83, 115, 41)}};
        PBNModel actual = new PBNModel(array);
        assertEquals(3, actual.getNumRows(), "canvas rows");
        assertEquals(3, actual.getNumCols(), "canvas columns");
        assertEquals(9, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(9, actual.getNumColors(), "palette length");
        assertEquals(new Color(83, 115, 49), actual.getCurrentColor(),
                "starter color");
        assertEquals(new Color(83, 115, 48), actual.getPBNColor(1).getColor(),
                "correct color tag 1");
        assertEquals(new Color(83, 115, 47), actual.getPBNColor(2).getColor(),
                "correct color tag 2");
        assertEquals(new Color(83, 115, 46), actual.getPBNColor(3).getColor(),
                "correct color tag 3");
        assertEquals(new Color(83, 115, 45), actual.getPBNColor(4).getColor(),
                "correct color tag 4");
        assertEquals(new Color(83, 115, 44), actual.getPBNColor(5).getColor(),
                "correct color tag 5");
        assertEquals(new Color(83, 115, 43), actual.getPBNColor(6).getColor(),
                "correct color tag 6");
        assertEquals(new Color(83, 115, 42), actual.getPBNColor(7).getColor(),
                "correct color tag 7");
        assertEquals(new Color(83, 115, 41), actual.getPBNColor(8).getColor(),
                "correct color tag 8");
        assertEquals(1, actual.getPBNColor(0).getPixels().size(),
                "number of pixels stored by color 0");
        assertEquals(1, actual.getPBNColor(1).getPixels().size(),
                "number of pixels stored by color 1");
        assertEquals(1, actual.getPBNColor(2).getPixels().size(),
                "number of pixels stored by color 2");
        assertEquals(1, actual.getPBNColor(3).getPixels().size(),
                "number of pixels stored by color 3");
        assertEquals(1, actual.getPBNColor(4).getPixels().size(),
                "number of pixels stored by color 4");
        assertEquals(1, actual.getPBNColor(5).getPixels().size(),
                "number of pixels stored by color 5");
        assertEquals(1, actual.getPBNColor(6).getPixels().size(),
                "number of pixels stored by color 6");
        assertEquals(1, actual.getPBNColor(7).getPixels().size(),
                "number of pixels stored by color 7");
        assertEquals(1, actual.getPBNColor(8).getPixels().size(),
                "number of pixels stored by color 8");
        assertEquals(0, actual.getPixel(0,0).getColorTag(),
                "get correct pixel color 0, 0");
        assertEquals(1, actual.getPixel(0,1).getColorTag(),
                "get correct pixel color 0, 1");
        assertEquals(2, actual.getPixel(0,2).getColorTag(),
                "get correct pixel color 0, 2");
        assertEquals(3, actual.getPixel(1,0).getColorTag(),
                "get correct pixel color 1, 0");
        assertEquals(4, actual.getPixel(1,1).getColorTag(),
                "get correct pixel color 1, 1");
        assertEquals(5, actual.getPixel(1,2).getColorTag(),
                "get correct pixel color 1, 2");
        assertEquals(6, actual.getPixel(2,0).getColorTag(),
                "get correct pixel color 2, 0");
        assertEquals(7, actual.getPixel(2,1).getColorTag(),
                "get correct pixel color 2, 1");
        assertEquals(8, actual.getPixel(2,2).getColorTag(),
                "get correct pixel color 2, 2");
    }
    // test constructor with 2 by 2 array with 1 color
    @Test
    public void testConstructor2by2color1() {
        Color[][] array = {{new Color(83, 115, 49), new Color(83, 115, 49)},
                {new Color(83, 115, 49), new Color(83, 115, 49)}};
        PBNModel actual = new PBNModel(array);
        assertEquals(2, actual.getNumRows(), "canvas rows");
        assertEquals(2, actual.getNumCols(), "canvas columns");
        assertEquals(4, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(1, actual.getNumColors(), "palette length");
        assertEquals(new Color(83, 115, 49), actual.getCurrentColor(),
                "starter color");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> actual.getPBNColor(1),
                "only has one color");
        assertEquals(4, actual.getPBNColor(0).getPixels().size(),
                "number of pixels stored by color 0");
        assertEquals(0, actual.getPixel(0,0).getColorTag(),
                "get correct pixel color 0, 0");
        assertEquals(0, actual.getPixel(0,1).getColorTag(),
                "get correct pixel color 0, 1");
        assertEquals(0, actual.getPixel(1,0).getColorTag(),
                "get correct pixel color 1, 0");
        assertEquals(0, actual.getPixel(1,1).getColorTag(),
                "get correct pixel color 1, 1");
    }
    // test constructor with 3 by 2 array with 3 colors
    @Test
    public void testConstructor3by2color3() {
        Color[][] array = {{new Color(83, 115, 49), new Color(83, 115, 48)},
                {new Color(83, 115, 49), new Color(83, 115, 49)},
                {new Color(83, 115, 43), new Color(83, 115, 43)}};
        PBNModel actual = new PBNModel(array);
        assertEquals(3, actual.getNumRows(), "canvas rows");
        assertEquals(2, actual.getNumCols(), "canvas columns");
        assertEquals(6, actual.getPixelsTotal(), "total pixels");
        assertEquals(0, actual.getPixelsColored(), "none colored");
        assertEquals(0, actual.getCurrentColorTag(), "start color tag");
        assertEquals(3, actual.getNumColors(), "palette length");
        assertEquals(new Color(83, 115, 49), actual.getCurrentColor(),
                "starter color");
        assertEquals(new Color(83, 115, 48), actual.getPBNColor(1).getColor(),
                "correct color tag 1");
        assertEquals(new Color(83, 115, 43), actual.getPBNColor(2).getColor(),
                "correct color tag 2");
        assertEquals(3, actual.getPBNColor(0).getPixels().size(),
                "number of pixels stored by color 0");
        assertEquals(1, actual.getPBNColor(1).getPixels().size(),
                "number of pixels stored by color 1");
        assertEquals(2, actual.getPBNColor(2).getPixels().size(),
                "number of pixels stored by color 2");
        assertEquals(0, actual.getPixel(0,0).getColorTag(),
                "get correct pixel color 0, 0");
        assertEquals(1, actual.getPixel(0,1).getColorTag(),
                "get correct pixel color 0, 1");
        assertEquals(0, actual.getPixel(1,0).getColorTag(),
                "get correct pixel color 1, 0");
        assertEquals(0, actual.getPixel(1,1).getColorTag(),
                "get correct pixel color 1, 1");
        assertEquals(2, actual.getPixel(2,0).getColorTag(),
                "get correct pixel color 2, 0");
        assertEquals(2, actual.getPixel(2,1).getColorTag(),
                "get correct pixel color 2, 1");
    }

    // once constructor is tested right initialize the 3 by 3 test picture for the rest
    // of the tests
    PBNModel testModel;
    @BeforeEach
    public void setup3by3color2() {
        Color[][] array = {{new Color(83, 115, 49), new Color(83, 115, 48),
                new Color(83, 115, 48)},
                {new Color(83, 115, 48), new Color(83, 115, 49),
                        new Color(83, 115, 49)},
                {new Color(83, 115, 48), new Color(83, 115, 49),
                        new Color(83, 115, 49)}};
        testModel = new PBNModel(array);
    }
    // here's what that guy looks like:
    // 0 1 1
    // 1 0 0
    // 1 0 0

    // test switching colors - get correct colortag and the correct color
    @Test
    public void testSwitchColorTagInBounds() {
        assertEquals(0, testModel.getCurrentColorTag(), "current tag is 0");
        assertEquals(1, testModel.switchColorTag(1), "switch once");
        assertEquals(0, testModel.switchColorTag(0), "switch second");
    }

    @Test
    public void testSwitchColorOutofBounds() {
        assertThrows(IllegalArgumentException.class, () -> testModel.switchColorTag(2),
                "color tag to switch to is too large");
        assertThrows(IllegalArgumentException.class, () -> testModel.switchColorTag(-1),
                "color tag to switch to is too small");
    }

    // test toggling highlights - make sure all the right pixels are highlighted and
    // none of the wrong ones are, and also be able to toggle back and forth
    @Test
    public void testToggleHighlightOnce() {
        testModel.toggleHighlight(0);
        // check that the right pixels are highlighted
        assertTrue(testModel.getPixel(0, 0).isHighlighted(), "0,0");
        assertTrue(testModel.getPixel(1, 1).isHighlighted(), "1,1");
        assertTrue(testModel.getPixel(1, 2).isHighlighted(), "1,2");
        assertTrue(testModel.getPixel(2, 1).isHighlighted(), "2,1");
        assertTrue(testModel.getPixel(2, 2).isHighlighted(), "2,2");
        // check that the right pixels are not highlighted
        assertFalse(testModel.getPixel(0, 1).isHighlighted(), "0, 1");
        assertFalse(testModel.getPixel(0, 2).isHighlighted(), "0, 2");
        assertFalse(testModel.getPixel(1, 0).isHighlighted(), "1, 0");
        assertFalse(testModel.getPixel(2, 0).isHighlighted(), "2, 0");
    }

    @Test
    public void testToggleHighlightTwice() {
        testModel.toggleHighlight(0);
        testModel.toggleHighlight(0);
        testModel.toggleHighlight(1);
        // check that the right pixels are not highlighted
        assertFalse(testModel.getPixel(0, 0).isHighlighted(), "0,0");
        assertFalse(testModel.getPixel(1, 1).isHighlighted(), "1,1");
        assertFalse(testModel.getPixel(1, 2).isHighlighted(), "1,2");
        assertFalse(testModel.getPixel(2, 1).isHighlighted(), "2,1");
        assertFalse(testModel.getPixel(2, 2).isHighlighted(), "2,2");
        // check that the right pixels are not highlighted
        assertTrue(testModel.getPixel(0, 1).isHighlighted(), "0, 1");
        assertTrue(testModel.getPixel(0, 2).isHighlighted(), "0, 2");
        assertTrue(testModel.getPixel(1, 0).isHighlighted(), "1, 0");
        assertTrue(testModel.getPixel(2, 0).isHighlighted(), "2, 0");
    }

    // test color function:
    // test bounds, if the currrent color is set to the right color, if it's set
    // to the wrong color, if coloring that pixel makes the difference between done
    // and not done
    @Test
    public void testColorCorrect() {
        testModel.color(0, 0);
        assertFalse(testModel.color(1, 1), "not done");
        assertTrue(testModel.getPixel(0, 0).isColored(), "colored 0,0");
        assertTrue(testModel.getPixel(1, 1).isColored(), "colored 1, 1");
        assertEquals(2, testModel.getPixelsColored(), "2 pixels colored");
    }

    @Test
    public void testColorCorrect2() {
        testModel.switchColorTag(1);
        testModel.color(1, 0);
        assertTrue(testModel.getPixel(1, 0).isColored(), "colored 1,0");
        assertEquals(1, testModel.getPixelsColored(), "1 pixel colored");
    }

    @Test
    public void testWrongColor() {
        testModel.color(1,0);
        assertFalse(testModel.getPixel(1,0).isColored(), "not colored 1,0");
        assertEquals(0, testModel.getPixelsColored(), "no pixels colored");
    }

    @Test
    public void testRightWrongColor() {
        testModel.color(0, 0);
        testModel.color(1,0);
        assertTrue(testModel.getPixel(0,0).isColored(), "0, 0 colored");
        assertFalse(testModel.getPixel(1,0).isColored(), "1, 0 not colored");
        assertEquals(1, testModel.getPixelsColored(), "1 pixel colored");
    }

    @Test
    public void testDoneColor() {
        testModel.color(0, 0);
        testModel.color(1,1);
        testModel.color(1,2);
        testModel.color(2,1);
        testModel.color(2,2);
        testModel.switchColorTag(1);
        testModel.color(0, 1);
        testModel.color(0, 2);
        testModel.color(1, 0);
        assertTrue(testModel.color(2,0), "returns done");
        assertTrue(testModel.isDone(), "status returns done");
    }
}
