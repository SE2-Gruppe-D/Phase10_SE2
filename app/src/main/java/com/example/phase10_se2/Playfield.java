package com.example.phase10_se2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class Playfield extends AppCompatActivity {
    ImageView card1, card2,card3, card4, card5, card6, card7, card8 ,card9 ,card10 ,card11, card12,card13, card14, card15, card16, card17, card18, card19, card20, card21,card22, card23, card24, card25, card26, card27, card28, card29, card30, card31, card32, card33, card34,card35,card36, card37, card38, card39, card40;

    ArrayList<Integer> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);

        //entfernt die label Leiste (Actionbar) auf dem Playfield
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        card1= findViewById(R.id.card1);
        card2= findViewById(R.id.card2);
        card3= findViewById(R.id.card3);
        card4= findViewById(R.id.card4);
        card5= findViewById(R.id.card5);
        card6= findViewById(R.id.card6);
        card7= findViewById(R.id.card7);
        card8= findViewById(R.id.card8);
        card9= findViewById(R.id.card9);
        card10= findViewById(R.id.card10);
        card11= findViewById(R.id.card11);
        card12= findViewById(R.id.card12);
        card13= findViewById(R.id.card13);
        card14= findViewById(R.id.card14);
        card15= findViewById(R.id.card15);
        card16= findViewById(R.id.card16);
        card17= findViewById(R.id.card17);
        card18= findViewById(R.id.card18);
        card19= findViewById(R.id.card19);
        card20= findViewById(R.id.card20);
        card21= findViewById(R.id.card21);
        card22= findViewById(R.id.card22);
        card23= findViewById(R.id.card23);
        card24= findViewById(R.id.card24);
        card25= findViewById(R.id.card25);
        card26= findViewById(R.id.card26);
        card27= findViewById(R.id.card27);
        card28= findViewById(R.id.card28);
        card29= findViewById(R.id.card29);
        card30= findViewById(R.id.card30);
        card31= findViewById(R.id.card31);
        card32= findViewById(R.id.card32);
        card33= findViewById(R.id.card33);
        card34= findViewById(R.id.card34);
        card35= findViewById(R.id.card35);
        card36= findViewById(R.id.card36);
        card37= findViewById(R.id.card37);
        card38= findViewById(R.id.card38);
        card39= findViewById(R.id.card39);
        card40= findViewById(R.id.card40);


        //alle 96 Karten werden in eine Array Liste gespeichter
        cards=new ArrayList<>();

        //ID
        cards.add(101);     //blau 1
        cards.add(102);     //blau 2
        cards.add(103);     //blau 3
        cards.add(104);     //blau 4
        cards.add(105);     //blau 5
        cards.add(106);     //blau 6
        cards.add(107);     //blau 7
        cards.add(108);     //blau 8
        cards.add(109);     //blau 9
        cards.add(110);     //blau 10
        cards.add(111);     //blau 11
        cards.add(112);     //blau 12

        cards.add(121);     //blau 1
        cards.add(122);     //blau 2
        cards.add(123);     //blau 3
        cards.add(124);     //blau 4
        cards.add(125);     //blau 5
        cards.add(126);     //blau 6
        cards.add(127);     //blau 7
        cards.add(128);     //blau 8
        cards.add(129);     //blau 9
        cards.add(130);     //blau 10
        cards.add(131);     //blau 11
        cards.add(132);     //blau 12

        cards.add(201);     //gelb 1
        cards.add(202);     //gelb 2
        cards.add(203);     //gelb 3
        cards.add(204);     //gelb 4
        cards.add(205);     //gelb 5
        cards.add(206);     //gelb 6
        cards.add(207);     //gelb 7
        cards.add(208);     //gelb 8
        cards.add(209);     //gelb 9
        cards.add(210);     //gelb 10
        cards.add(211);     //gelb 11
        cards.add(212);     //gelb 12

        cards.add(221);     //gelb 1
        cards.add(222);     //gelb 2
        cards.add(223);     //gelb 3
        cards.add(224);     //gelb 4
        cards.add(225);     //gelb 5
        cards.add(226);     //gelb 6
        cards.add(227);     //gelb 7
        cards.add(228);     //gelb 8
        cards.add(229);     //gelb 9
        cards.add(230);     //gelb 10
        cards.add(231);     //gelb 11
        cards.add(232);     //gelb 12

        cards.add(301);     //gruen 1
        cards.add(302);     //gruen 2
        cards.add(303);     //gruen 3
        cards.add(304);     //gruen 4
        cards.add(305);     //gruen 5
        cards.add(306);     //gruen 6
        cards.add(307);     //gruen 7
        cards.add(308);     //gruen 8
        cards.add(309);     //gruen 9
        cards.add(310);     //gruen 10
        cards.add(311);     //gruen 11
        cards.add(312);     //gruen 12

        cards.add(321);     //gruen 1
        cards.add(322);     //gruen 2
        cards.add(323);     //gruen 3
        cards.add(324);     //gruen 4
        cards.add(325);     //gruen 5
        cards.add(326);     //gruen 6
        cards.add(327);     //gruen 7
        cards.add(328);     //gruen 8
        cards.add(329);     //gruen 9
        cards.add(330);     //gruen 10
        cards.add(331);     //gruen 11
        cards.add(332);     //gruen 12

        cards.add(401);     //rot 1
        cards.add(402);     //rot 2
        cards.add(403);     //rot 3
        cards.add(404);     //rot 4
        cards.add(405);     //rot 5
        cards.add(406);     //rot 6
        cards.add(407);     //rot 7
        cards.add(408);     //rot 8
        cards.add(409);     //rot 9
        cards.add(410);     //rot 10
        cards.add(411);     //rot 11
        cards.add(412);     //rot 12

        cards.add(421);     //rot 1
        cards.add(422);     //rot 2
        cards.add(423);     //rot 3
        cards.add(424);     //rot 4
        cards.add(425);     //rot 5
        cards.add(426);     //rot 6
        cards.add(427);     //rot 7
        cards.add(428);     //rot 8
        cards.add(429);     //rot 9
        cards.add(430);     //rot 10
        cards.add(431);     //rot 11
        cards.add(432);     //rot 12

        //Karten werden gemischt
        Collections.shuffle(cards);

        //jeder Spieler bekommt 10 Karten
        cardID(cards.get(0), card1);
        cardID(cards.get(1), card2);
        cardID(cards.get(2), card3);
        cardID(cards.get(3), card4);
        cardID(cards.get(4), card5);
        cardID(cards.get(5), card6);
        cardID(cards.get(6), card7);
        cardID(cards.get(7), card8);
        cardID(cards.get(8), card9);
        cardID(cards.get(9), card10);
        cardID(cards.get(10), card11);
        cardID(cards.get(11), card12);
        cardID(cards.get(12), card13);
        cardID(cards.get(13), card14);
        cardID(cards.get(14), card15);
        cardID(cards.get(15), card16);
        cardID(cards.get(16), card17);
        cardID(cards.get(17), card18);
        cardID(cards.get(18), card19);
        cardID(cards.get(19), card20);
        cardID(cards.get(20), card21);
        cardID(cards.get(21), card22);
        cardID(cards.get(22), card23);
        cardID(cards.get(23), card24);
        cardID(cards.get(24), card25);
        cardID(cards.get(25), card26);
        cardID(cards.get(26), card27);
        cardID(cards.get(27), card28);
        cardID(cards.get(28), card29);
        cardID(cards.get(29), card30);
        cardID(cards.get(30), card31);
        cardID(cards.get(31), card32);
        cardID(cards.get(32), card33);
        cardID(cards.get(33), card34);
        cardID(cards.get(34), card35);
        cardID(cards.get(35), card36);
        cardID(cards.get(36), card37);
        cardID(cards.get(37), card38);
        cardID(cards.get(38), card39);
        cardID(cards.get(39), card40);

    }
    //ID zuweisung Karten
    public void cardID(int card, ImageView image) {
        switch (card) {
            case (101):
                image.setImageResource(R.drawable.blau1);
                break;

            case (102):
                image.setImageResource(R.drawable.blau2);
                break;

            case (103):
                image.setImageResource(R.drawable.blau3);
                break;

            case (104):
                image.setImageResource(R.drawable.blau4);
                break;

            case (105):
                image.setImageResource(R.drawable.blau5);
                break;

            case (106):
                image.setImageResource(R.drawable.blau6);
                break;

            case (107):
                image.setImageResource(R.drawable.blau7);
                break;

            case (108):
                image.setImageResource(R.drawable.blau8);
                break;

            case (109):
                image.setImageResource(R.drawable.blau9);
                break;

            case (110):
                image.setImageResource(R.drawable.blau10);
                break;

            case (111):
                image.setImageResource(R.drawable.blau11);
                break;

            case (112):
                image.setImageResource(R.drawable.blau12);
                break;

            case (121):
                image.setImageResource(R.drawable.blau1);
                break;

            case (122):
                image.setImageResource(R.drawable.blau2);
                break;

            case (123):
                image.setImageResource(R.drawable.blau3);
                break;

            case (124):
                image.setImageResource(R.drawable.blau4);
                break;

            case (125):
                image.setImageResource(R.drawable.blau5);
                break;

            case (126):
                image.setImageResource(R.drawable.blau6);
                break;

            case (127):
                image.setImageResource(R.drawable.blau7);
                break;

            case (128):
                image.setImageResource(R.drawable.blau8);
                break;

            case (129):
                image.setImageResource(R.drawable.blau9);
                break;

            case (130):
                image.setImageResource(R.drawable.blau10);
                break;

            case (131):
                image.setImageResource(R.drawable.blau11);
                break;

            case (132):
                image.setImageResource(R.drawable.blau12);
                break;

            case (201):
                image.setImageResource(R.drawable.gelb1);
                break;

            case (202):
                image.setImageResource(R.drawable.gelb2);
                break;

            case (203):
                image.setImageResource(R.drawable.gelb3);
                break;

            case (204):
                image.setImageResource(R.drawable.gelb4);
                break;

            case (205):
                image.setImageResource(R.drawable.gelb5);
                break;

            case (206):
                image.setImageResource(R.drawable.gelb6);
                break;

            case (207):
                image.setImageResource(R.drawable.gelb7);
                break;

            case (208):
                image.setImageResource(R.drawable.gelb8);
                break;

            case (209):
                image.setImageResource(R.drawable.gelb9);
                break;

            case (210):
                image.setImageResource(R.drawable.gelb10);
                break;

            case (211):
                image.setImageResource(R.drawable.gelb11);
                break;

            case (212):
                image.setImageResource(R.drawable.gelb12);
                break;

            case (221):
                image.setImageResource(R.drawable.gelb1);
                break;

            case (222):
                image.setImageResource(R.drawable.gelb2);
                break;

            case (223):
                image.setImageResource(R.drawable.gelb3);
                break;

            case (224):
                image.setImageResource(R.drawable.gelb4);
                break;

            case (225):
                image.setImageResource(R.drawable.gelb5);
                break;

            case (226):
                image.setImageResource(R.drawable.gelb6);
                break;

            case (227):
                image.setImageResource(R.drawable.gelb7);
                break;

            case (228):
                image.setImageResource(R.drawable.gelb8);
                break;

            case (229):
                image.setImageResource(R.drawable.gelb9);
                break;

            case (230):
                image.setImageResource(R.drawable.gelb10);
                break;

            case (231):
                image.setImageResource(R.drawable.gelb11);
                break;

            case (232):
                image.setImageResource(R.drawable.gelb12);
                break;

            case (301):
                image.setImageResource(R.drawable.gruen1);
                break;

            case (302):
                image.setImageResource(R.drawable.gruen2);
                break;

            case (303):
                image.setImageResource(R.drawable.gruen3);
                break;

            case (304):
                image.setImageResource(R.drawable.gruen4);
                break;

            case (305):
                image.setImageResource(R.drawable.gruen5);
                break;

            case (306):
                image.setImageResource(R.drawable.gruen6);
                break;

            case (307):
                image.setImageResource(R.drawable.gruen7);
                break;

            case (308):
                image.setImageResource(R.drawable.gruen8);
                break;

            case (309):
                image.setImageResource(R.drawable.gruen9);
                break;

            case (310):
                image.setImageResource(R.drawable.gruen10);
                break;

            case (311):
                image.setImageResource(R.drawable.gruen11);
                break;

            case (312):
                image.setImageResource(R.drawable.gruen12);
                break;

            case (321):
                image.setImageResource(R.drawable.gruen1);
                break;

            case (322):
                image.setImageResource(R.drawable.gruen2);
                break;

            case (323):
                image.setImageResource(R.drawable.gruen3);
                break;

            case (324):
                image.setImageResource(R.drawable.gruen4);
                break;

            case (325):
                image.setImageResource(R.drawable.gruen5);
                break;

            case (326):
                image.setImageResource(R.drawable.gruen6);
                break;

            case (327):
                image.setImageResource(R.drawable.gruen7);
                break;

            case (328):
                image.setImageResource(R.drawable.gruen8);
                break;

            case (329):
                image.setImageResource(R.drawable.gruen9);
                break;

            case (330):
                image.setImageResource(R.drawable.gruen10);
                break;

            case (331):
                image.setImageResource(R.drawable.gruen11);
                break;

            case (332):
                image.setImageResource(R.drawable.gruen12);
                break;

            case (401):
                image.setImageResource(R.drawable.rot1);
                break;

            case (402):
                image.setImageResource(R.drawable.rot2);
                break;

            case (403):
                image.setImageResource(R.drawable.rot3);
                break;

            case (404):
                image.setImageResource(R.drawable.rot4);
                break;

            case (405):
                image.setImageResource(R.drawable.rot5);
                break;

            case (406):
                image.setImageResource(R.drawable.rot6);
                break;

            case (407):
                image.setImageResource(R.drawable.rot7);
                break;

            case (408):
                image.setImageResource(R.drawable.rot8);
                break;

            case (409):
                image.setImageResource(R.drawable.rot9);
                break;

            case (410):
                image.setImageResource(R.drawable.rot10);
                break;

            case (411):
                image.setImageResource(R.drawable.rot11);
                break;

            case (412):
                image.setImageResource(R.drawable.rot12);
                break;

            case (421):
                image.setImageResource(R.drawable.rot1);
                break;

            case (422):
                image.setImageResource(R.drawable.rot2);
                break;

            case (423):
                image.setImageResource(R.drawable.rot3);
                break;

            case (424):
                image.setImageResource(R.drawable.rot4);
                break;

            case (425):
                image.setImageResource(R.drawable.rot5);
                break;

            case (426):
                image.setImageResource(R.drawable.rot6);
                break;

            case (427):
                image.setImageResource(R.drawable.rot7);
                break;

            case (428):
                image.setImageResource(R.drawable.rot8);
                break;

            case (429):
                image.setImageResource(R.drawable.rot9);
                break;

            case (430):
                image.setImageResource(R.drawable.rot10);
                break;

            case (431):
                image.setImageResource(R.drawable.rot11);
                break;

            case (432):
                image.setImageResource(R.drawable.rot12);
                break;
        }
    }
}