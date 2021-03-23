package GameMechanics;

import Classes.Player;
import Classes.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest(GameObjects.class)
class GameObjectsTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    void initializeGameObjects() {
        //given
        //when
        GameObjects.initializeGameObjects();

        //then
        ArrayList<Player> classes = GameObjects.CLASSES;
        assertEquals(4, classes.size());
        assertEquals(classes.get(0).getClass(), Warrior.class);
    }
}