import static org.junit.Assert.*;

public class CodingBatTest {

    @org.junit.Test
    public void sumDouble() {
        // CodingBat cb = new CodingBat(); - instancję tworzymy, gdy metoda nie jest static
        // moje metody są static -> wywołujemy funkcję, jako metoda klasy CodingBat
        assertEquals(7, CodingBat.sumDouble(3,4));
        assertEquals(8, CodingBat.sumDouble(2,2));
        assertEquals(12, CodingBat.sumDouble(3,3));
        assertEquals(1, CodingBat.sumDouble(1,0));
    }

    @org.junit.Test
    public void monkeyTrouble() {
        assertTrue(CodingBat.monkeyTrouble(true, true));
        assertTrue(CodingBat.monkeyTrouble(false, false));
        assertFalse(CodingBat.monkeyTrouble(true, false));
        assertFalse(CodingBat.monkeyTrouble(false, true));
    }

    @org.junit.Test
    public void lucky13() {
        assertTrue(CodingBat.lucky13(new int[] {0, 2, 4}));
        assertFalse(CodingBat.lucky13(new int[] {1, 2, 3}));
        assertFalse(CodingBat.lucky13(new int[] {1, 2, 4}));
    }

    @org.junit.Test
    public void withoutEnd() {
        assertEquals("ell", CodingBat.withoutEnd("Hello"));
        assertEquals("av", CodingBat.withoutEnd("java"));
        assertEquals("odin", CodingBat.withoutEnd("coding"));
        assertEquals("G", CodingBat.withoutEnd("AGH"));
    }
}