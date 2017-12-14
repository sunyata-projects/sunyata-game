package org.sunyata.game.majiang.core.models.majiang;

import com.google.common.collect.ArrayListMultimap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajiangAI {
    // Function name: whichCard
    // Input: Map ---- all the cards the player has
    // Output: String -- representing the card the player wants to play
    // Description: This is the core method for the program, this function should calculate the importence of
    //              every card the player has and determine which one has the least importence so that the
    //              player should throw this card away

    public static Pai whichCard(ArrayListMultimap<PaiType, Pai> myCards, List<Pai> availMa,
                                int numAvailma) {
        //Map<String, Integer> currentCards = new HashMap<String, Integer>(myCards);
        ArrayListMultimap<PaiType, Pai> currentCards = ArrayListMultimap.create(myCards);
        Map<Pai, Double> importentRank = new HashMap<Pai, Double>();
        // Find special cases
        // Find if there are 1 or 9, if there are no 1 or 9, keep 2,3and 7,8
        int numOf19 = 0;
        for (Pai k : currentCards.values()) {
            if ((k.getDian() == 1 || k.getDian() == 9) && (k.getType() == PaiType.TIAO || k.getType() == PaiType.TONG
                    || k.getType() == PaiType.WAN)) {
                numOf19++;
            }
        }

        // if there are no 1 or 9, keep 2, 3, 8, and 9
        if (numOf19 == 0) {
            for (Pai k : myCards.values()) {
                if (k.getDian() == 2 || k.getDian() == 3 || k.getDian() == 7 || k.getDian() == 8 || k.getType() ==
                        PaiType.SANYUAN) {
                    importentRank.put(k, 1.00);
                }
            }
        } else {
            // check if there is only one 1 or 9
            if (numOf19 == 1) {
                for (Pai k : myCards.values()) {
                    if (k.getDian() == 1 || k.getDian() == 9) {
                        importentRank.put(k, 1.00);
                    }
                }
            }
        }

        // remove all suit 5
        for (Pai k : myCards.values()) {
            if (k.getType() == PaiType.SANYUAN) {
                currentCards.remove(PaiType.SANYUAN, k);
            }
        }

        // Caculate the possibility of this card getting tripple
        for (Pai k : currentCards.values()) {

            if (k.getDian() == 3) {
                importentRank.put(k, 1.00);
            } else if (k.getDian() == 2) {
                // times 3 because we can call triple for every card players play
                long count = availMa.stream().filter(p -> p.getIndex() == k.getIndex()).count();
                importentRank.put(k, 1.5 * (double) count / (double) numAvailma);
            } else {
                long count = availMa.stream().filter(p -> p.getIndex() == k.getIndex()).count();
                double thisP = (double) count / (double) numAvailma;
                importentRank.put(k, thisP * thisP);
            }
        }

        // remove all suit 4 because there is no row for suit 4
        for (Pai k : myCards.values()) {
            if (k.getType() == PaiType.FENG) {
                currentCards.remove(PaiType.FENG, k);
            }
        }

        // Calculate the possibility of this card getting row
        for (Pai k : currentCards.values()) {
            // check for the the left two straight
            int thisMag = k.getDian();
            if (thisMag - 2 > 0) {
                //String thisMagleft1 = Integer.toString(k.getDian() - 1);
                Pai prevPai = k.prevPaiType();
                Pai prevPrevPai = prevPai.prevPaiType();
//                String thisMagLeft1 = k.prevPaiType();
//                String thisMagleft2 = Integer.toString(k.getDian() - 2);
//                String thisMagleft1 = Integer.toString(k.getDian() - 1);
                double thisRowP = possC(String.valueOf(prevPrevPai.getIndex()), availMa, numAvailma, currentCards)
                        * possC(String.valueOf(prevPai.getIndex()), availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
            // check for the thisCard in the middle
            if (thisMag - 1 > 0 && thisMag + 1 < 10) {
                String thisMagright1 = Integer.toString(k.getIndex() + 1);
                String thisMagleft1 = Integer.toString(k.getIndex() - 1);
                double thisRowP = possC(thisMagright1, availMa, numAvailma, currentCards)
                        * possC(thisMagleft1, availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
            // check for the right two row
            if (thisMag + 2 < 10) {
                String thisMagright1 = Integer.toString(k.getIndex() + 1);
                String thisMagright2 = Integer.toString(k.getIndex() + 2);
                double thisRowP = possC(thisMagright1, availMa, numAvailma, currentCards)
                        * possC(thisMagright2, availMa, numAvailma, currentCards);
                importentRank.put(k, importentRank.get(k) + thisRowP);
            }
        }

        // check for special cases, if 2 or 8 are close to other cards, then play 2 or 8
        Pai minCard = null;
        double minCardP = 1;
        for (Pai k : importentRank.keySet()) {
            System.out.println(k + "with possibility" + importentRank.get(k));
            if (importentRank.get(k) < minCardP) {
                minCard = k;
                minCardP = importentRank.get(k);
            }
        }

        return minCard;
    }

    // Function name: getSuit
    // Input: String
    // Output: int
    // Description: Given the card, check which suit it is and turn the corresponding suit
    public static int getSuit(String card) {
        return Integer.parseInt(card.substring(0, 1));
    }

    // Function name: getMag
    // Input: String
    // Output: int
    // Description: Given the card, return the magnitude of the card
    public static int getMag(String card) {
        return Integer.parseInt(card.substring(1, 2));
    }

    public static double possC(String thisCard, List<Pai> availMa, int numAvailma,
                               ArrayListMultimap<PaiType, Pai> myCards) {
        Pai pai = Pai.fromIndex(Integer.parseInt(thisCard));
        Pai availMaPai = availMa.stream().filter(p -> p.getIndex() == pai.getIndex()).findFirst().orElse(null);
        Pai myCardsExistsPai = myCards.values().stream().filter(p -> p.getIndex() == pai.getIndex()).findFirst().orElse
                (null);
        if (availMaPai != null) {
            if (myCardsExistsPai != null) {
                return 1.00;
            } else {
                long count = availMa.stream().filter(p -> p.getIndex() == pai.getIndex()).count();
                return (double) count / (double) numAvailma;
            }
        }
        return 0;
    }
}
