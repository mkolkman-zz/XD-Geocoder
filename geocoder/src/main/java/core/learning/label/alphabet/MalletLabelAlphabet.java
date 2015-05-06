package core.learning.label.alphabet;

import cc.mallet.types.LabelAlphabet;

public class MalletLabelAlphabet {

    private static LabelAlphabet labelAlphabet;

    public static LabelAlphabet getInstance() {
        if(labelAlphabet == null) {
            labelAlphabet = new LabelAlphabet();
        }
        return labelAlphabet;
    }

    public static cc.mallet.types.Label getMalletLabel(String label) {
        return getInstance().lookupLabel(label);
    }
}
