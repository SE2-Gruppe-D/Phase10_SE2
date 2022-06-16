package com.example.phase10_se2;

import static org.mockito.Mockito.mock;

import com.example.phase10_se2.ENUM.PlayerColor;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayfieldTest {
    Playfield mockedPlayfield;
    FirebaseFirestore mockedDB;
    Player testPlayerCurr;
    Player testPlayerBlue;
    Player testPlayerGreen;
    Player testPlayerYellow;
    Player testPlayerRed;
    Player player = new Player();



    @BeforeEach
    public void setUp(){
        mockedPlayfield = mock(Playfield.class);
        mockedDB = mock(FirebaseFirestore.class);
        //String name, PlayerColor color, String room, int phaseNumber, int minusPoints, ArrayList<Cards> cards, ArrayList<Cards> cardField
        testPlayerCurr = new Player("NoName", PlayerColor.BLUE, "TestRoom",1, 0, new ArrayList<>(),new ArrayList<>());
        testPlayerBlue = new Player("NoName",PlayerColor.BLUE, "TestRoom",1, 0,new ArrayList<>(),new ArrayList<>());
        testPlayerGreen = new Player("NoName",PlayerColor.GREEN, "TestRoom",1, 0,new ArrayList<>(),new ArrayList<>());
        testPlayerYellow = new Player("NoName",PlayerColor.YELLOW, "TestRoom",1, 0,new ArrayList<>(),new ArrayList<>());
        testPlayerRed = new Player("NoName",PlayerColor.RED, "TestRoom",1, 0,new ArrayList<>(),new ArrayList<>());

        mockedDB = FirebaseFirestore.getInstance();
        Map<String, Object> testGameInfo = new HashMap<>();
        testGameInfo.put("RoomName", "TestRoom");
        testGameInfo.put("Round", "1");
        testGameInfo.put("CurrentPlayer", testPlayerCurr);

            testGameInfo.put("PlayerYellow", testPlayerYellow);
            testGameInfo.put("PlayerBlue",testPlayerBlue);

            testGameInfo.put("PlayerRed", testPlayerRed);

            testGameInfo.put("PlayerGreen", testPlayerGreen);
            testGameInfo.put("StartOrder", new ArrayList<>());


        testGameInfo.put("DiceRoll", 2);
        testGameInfo.put("Cheated", false);
        testGameInfo.put("Cardlist", new ArrayList<>());
        testGameInfo.put("DiscardpileList", new ArrayList<>());

        mockedDB.collection("testing")
                .add(testGameInfo)
                .addOnSuccessListener(documentReference -> {

                });

    }

//    @Test
//    public void testGetPhasenumberDB() {
//        try {
//            Assertions.assertEquals(1, mockedPlayfield.getPhasenumberDB());
//        }catch (NullPointerException e){
//
//        }
//    }
}
