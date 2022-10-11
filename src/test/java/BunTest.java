import org.junit.Test;
import praktikum.Bun;

import static org.junit.Assert.assertEquals;

public class BunTest {
    private final String name = "Вкусная булка";
    private final float price = 100f;

    @Test
    public void createBun(){
        Bun bun = new Bun(name, price);
        assertEquals(name, bun.getName());
        assertEquals(price, bun.getPrice(), 0.0);
    }
}