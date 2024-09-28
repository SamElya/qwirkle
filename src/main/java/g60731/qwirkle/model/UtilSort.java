package g60731.qwirkle.model;

import java.util.List;

/**
 * Set of useful methods
 */
public class UtilSort {

    /**
     * Bubble-sort of several List and
     * place the numbers by decreasing value
     * @param list          A List of Integer
     * @param otherList     Optional List of
     *                      String to sort
     */
    @SafeVarargs
    public static void bubbleSort(List<Integer> list,
                                  List<String>... otherList) {

        for (int burstPos = 0;
                burstPos < list.size() - 1;
                burstPos++) {
            for (int bubblePos = list.size() - 1;
                    bubblePos > burstPos;
                    bubblePos--) {
                if (list.get(bubblePos) > list.get(bubblePos - 1)) {
                    swap(list, bubblePos,
                            bubblePos - 1, otherList);
                }
            }
        }
    }

    /**
     * Swap two values of several list
     * @param list      A List of Integer
     * @param pos1      An integer of the first
     *                  position to swap
     * @param pos2      An integer of the second
     *                  position to swap
     * @param otherList Optional List of
     *                  String to swap
     */
    @SafeVarargs
    private static void swap(List<Integer> list,
                             int pos1, int pos2,
                             List<String >... otherList) {
        int value = list.get(pos1);
        list.set(pos1, list.get(pos2));
        list.set(pos2, value);

        for (List<String> lString : otherList) {

            String valueString = lString.get(pos1);
            lString.set(pos1, lString.get(pos2));
            lString.set(pos2, valueString);
        }

    }
}
