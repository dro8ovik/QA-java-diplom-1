import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PriceReceiptParametrizedTest {
    @Mock
    Bun bun;
    @Mock
    Ingredient firstIngredient;
    @Mock
    Ingredient secondIngredient;

    private final String bunName = "Вкусная булка";
    private final float bunPrice = 100f;
    private final IngredientType firstIngredientType = IngredientType.SAUCE;
    private final String firstIngredientName = "Горные травы";
    private final float firstIngredientPrice = 40f;
    private final IngredientType secondIngredientType = IngredientType.FILLING;
    private final String secondIngredientName = "Мясо муравьев";
    private final float secondIngredientPrice = 60f;
    private final int ingredientsQty;
    private Burger burger;

    public PriceReceiptParametrizedTest(int ingredientsQty) {
        this.ingredientsQty = ingredientsQty;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {0},
                {1},
                {2},
        };
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
        burger.setBuns(bun);
    }

    @Test
    public void getPriceTest() {
        float expectedPrice = bunPrice * 2;
        switch (ingredientsQty) {
            case 0:
                break;
            case 1:
                burger.addIngredient(firstIngredient);
                expectedPrice += firstIngredientPrice;
                break;
            case 2:
                burger.addIngredient(firstIngredient);
                burger.addIngredient(secondIngredient);
                expectedPrice = expectedPrice + firstIngredientPrice + secondIngredientPrice;
                break;
            default:
                throw new RuntimeException("Для юнит тестов используй максимум 2 ингредиента");
        }
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(firstIngredient.getPrice()).thenReturn(firstIngredientPrice);
        Mockito.when(secondIngredient.getPrice()).thenReturn(secondIngredientPrice);
        assertEquals(expectedPrice, burger.getPrice(), 0.0);
    }

    @Test
    public void getReceiptTest() {
        StringBuilder expectedReceipt = new StringBuilder(String.format("(==== %s ====)%n", bunName));
        float expectedPrice = bunPrice * 2;
        switch (ingredientsQty) {
            case 0:
                break;
            case 1:
                burger.addIngredient(firstIngredient);
                expectedReceipt.append(String.format("= %s %s =%n", firstIngredientType.toString().toLowerCase(), firstIngredientName));
                expectedPrice += firstIngredientPrice;
                break;
            case 2:
                burger.addIngredient(firstIngredient);
                burger.addIngredient(secondIngredient);
                expectedReceipt.append(String.format("= %s %s =%n", firstIngredientType.toString().toLowerCase(), firstIngredientName));
                expectedReceipt.append(String.format("= %s %s =%n", secondIngredientType.toString().toLowerCase(), secondIngredientName));
                expectedPrice = expectedPrice + firstIngredientPrice + secondIngredientPrice;
                break;
            default:
                throw new RuntimeException("Для юнит тестов используй максимум 2 ингредиента");
        }
        expectedReceipt.append(String.format("(==== %s ====)%n", bunName));
        expectedReceipt.append(String.format("%nPrice: %f%n", expectedPrice));
        Mockito.when(bun.getName()).thenReturn(bunName);
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(firstIngredient.getType()).thenReturn(firstIngredientType);
        Mockito.when(firstIngredient.getName()).thenReturn(firstIngredientName);
        Mockito.when(firstIngredient.getPrice()).thenReturn(firstIngredientPrice);
        Mockito.when(secondIngredient.getType()).thenReturn(secondIngredientType);
        Mockito.when(secondIngredient.getName()).thenReturn(secondIngredientName);
        Mockito.when(secondIngredient.getPrice()).thenReturn(secondIngredientPrice);
        assertEquals(expectedReceipt.toString(), burger.getReceipt());
    }
}