package Displays;

import GameMechanics.GameMechanics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class DisplayTest {

    GameMechanics gameMechanics = mock(GameMechanics.class);
    Display display = new Display(gameMechanics);
    @BeforeEach
    void setUp() {
    }

    @Test
    void setUpMainFrame() {
        //Given

        //When
        //Then
    }
}