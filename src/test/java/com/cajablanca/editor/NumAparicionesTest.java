package com.cajablanca.editor;

import com.cajanegra.EmptyCollectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Cyclomatic Complexity: 7
 */
class NumAparicionesTest {

    private Editor  editor;

    @BeforeEach
    public void setUp() {
        editor = new Editor();
    }
    @Test
    void numAparicionesInicioLTEZeroKO() {
        assertThrows(IllegalArgumentException.class, () -> editor.numApariciones(-1, 2, "maven"));
    }
    @Test
    void numAparicionesFinGTSizeKO() {
        assertThrows(IllegalArgumentException.class, () -> editor.numApariciones(1, 1000000, "maven"));
    }
    @Test
    void numAparicionesNumLineasLTEZeroOK() {
        assertEquals(0, editor.numApariciones(1, -1, "maven"));
    }
    @Test
    void numAparicionesNumInicioGTEFinOK() {
        editor.cargarEditor("README.md");
        assertEquals(0, editor.numApariciones(1, 0, "mvn"));
    }
    @Test
    void numAparicionesPosGTSizeOK() throws EmptyCollectionException {
        editor.cargarEditor("README.md");
        editor.getLinea(7).removeLast();
        assertEquals(0, editor.numApariciones(7, 7, "mvn"));
    }
    @Test
    void numAparicionesPosLTESizeOK() {
        editor.cargarEditor("README.md");
        assertEquals(1, editor.numApariciones(6, 6, "mvn"));
    }
    @Test
    void numAparicionesWholeOK() {
        editor.cargarEditor("README.md");
        assertEquals(3, editor.numApariciones(1, 7, "mvn"));
    }
}