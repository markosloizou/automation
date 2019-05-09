import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ShutterstockDetailsParametrizedTest {
    private ShutterstockDetails ss;
    private ImageFormat format;
    private Boolean expectedResult;

    @Before
    public void initialize()
    {
        ss = new ShutterstockDetails();
    }

    public ShutterstockDetailsParametrizedTest(ImageFormat f, Boolean bool)
    {
        this.format = f;
        this.expectedResult = bool;
    }

    @Parameterized.Parameters
    public static Collection formats(){
        return Arrays.asList(new Object[][]{
                    {ImageFormat.PNG, false},
                    {ImageFormat.JPEG, true},
                    {ImageFormat.TIFF, true},
                    {ImageFormat.EPS, true}
                });
    }

    @Test
    public void testAcceptedFormats(){
        assertEquals(expectedResult, ss.checkIfImageTypeIsAccepted(format));
    }
}