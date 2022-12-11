package com.cajablanca.editor;

import com.cajanegra.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cyclomatic Complexity: 3
 * We add 1 TestClass in order to test both of the OR conditions of l43
 */
class GetLineaTest {

    private Editor  editor;

    @BeforeEach
    public void setUp() {
        editor = new Editor();
    }
    @Test
    void getLineaEmptyKO() {
        assertThrows(EmptyCollectionException.class, () -> editor.getLinea(1));
    }
    @Test
    void getLineaLTEZeroKO() {
        editor.cargarEditor("README.md");
        assertThrows(IllegalArgumentException.class, () -> editor.getLinea(-1));
    }
    @Test
    void getLineaGTSizeKO() {
        editor.cargarEditor("README.md");
        assertThrows(IllegalArgumentException.class, () -> editor.getLinea(1000000));
    }
    @Test
    void getLineaOK() throws EmptyCollectionException {
        editor.cargarEditor("README.md");
        assertEquals("[mvn, test]", editor.getLinea(2).toString());
    }
}