package brpcompare;

import net.automatalib.words.Alphabet;
import net.automatalib.words.abstractimpl.AbstractAlphabet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joshua on 14/07/2016.
 * I noticed that getSymbolIndex was called a lot. So I optimized for that, with a look up structure.
 * Too bad it wasn't in LearnLib already.
 */
public class HashAlphabet<I> extends AbstractAlphabet<I> {
    private final ArrayList<I> symbols;
    private final HashMap<I, Integer> indices;

    public HashAlphabet(Alphabet<I> in) {
        symbols = new ArrayList<>(in);
        indices = new HashMap<>(in.size());
        for (int i = 0; i < symbols.size(); i++) {
            indices.put(symbols.get(i), i);
        }
    }

    @Override
    public I getSymbol(int index) {
        return symbols.get(index);
    }

    @Override
    public int getSymbolIndex(I symbol) {
        return indices.get(symbol);
    }

    @Override
    public int size() {
        return symbols.size();
    }

    /* (non-Javadoc)
     * @see net.automatalib.words.abstractimpl.AbstractAlphabet#writeToArray(int, java.lang.Object[], int, int)
     */
    @Override
    public void writeToArray(int offset, Object[] array, int tgtOfs, int num) {
        System.arraycopy(symbols, offset, array, tgtOfs, num);
    }

    @Override
    public boolean containsSymbol(I symbol) {
        return indices.containsKey(symbol);
    }

}
