package com.cajablanca.editor;

import com.cajanegra.EmptyCollectionException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumPalabrasTest {

    private Editor  editor;
//    @Mock Editor mock;
    @BeforeEach
    public void setUp() {
        editor = new Editor();
    }
    @Test
    void numPalabrasEmptyKO() {
        assertThrows(EmptyCollectionException.class, () -> editor.numPalabras());
    }
    @Test
    void numPalabrasSizeVarZeroOK() throws EmptyCollectionException {
        Editor mock = mock(editor.getClass());
        when(mock.editIsEmpty()).thenReturn(false);
        ed.cargarEditor("README.md");
        System.out.println(editor.numPalabras());
    }
}