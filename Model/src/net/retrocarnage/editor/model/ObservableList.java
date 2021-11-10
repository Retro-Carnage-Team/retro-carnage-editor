package net.retrocarnage.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A List of things that can inform listener
 *
 * @author Thomas Werner
 * @param <T> some kind of thing
 */
public class ObservableList<T> implements List<T> {

    private static final Logger logger = Logger.getLogger(ObservableList.class.getName());

    private final List<T> delegate;
    private final List<ChangeListener> changeListeners;

    public ObservableList() {
        delegate = new ArrayList<>();
        changeListeners = new LinkedList<>();
    }

    public void addChangeListener(final ChangeListener listener) {
        changeListeners.add(listener);
    }

    public void removeChangeListener(final ChangeListener listener) {
        changeListeners.remove(listener);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] ts) {
        return delegate.toArray(ts);
    }

    @Override
    public boolean add(final T e) {
        final boolean result = delegate.add(e);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean remove(final Object o) {
        final boolean result = delegate.remove(o);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean containsAll(final Collection<?> clctn) {
        final boolean result = delegate.containsAll(clctn);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean addAll(final Collection<? extends T> clctn) {
        final boolean result = delegate.addAll(clctn);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean addAll(final int i, final Collection<? extends T> clctn) {
        final boolean result = delegate.addAll(i, clctn);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean removeAll(final Collection<?> clctn) {
        final boolean result = delegate.removeAll(clctn);
        callChangeListeners();
        return result;
    }

    @Override
    public boolean retainAll(final Collection<?> clctn) {
        final boolean result = delegate.retainAll(clctn);
        callChangeListeners();
        return result;
    }

    @Override
    public void clear() {
        delegate.clear();
        callChangeListeners();
    }

    @Override
    public T get(final int i) {
        return delegate.get(i);
    }

    @Override
    public T set(final int i, final T e) {
        final T result = delegate.set(i, e);
        callChangeListeners();
        return result;
    }

    @Override
    public void add(final int i, final T e) {
        delegate.add(i, e);
        callChangeListeners();
    }

    @Override
    public T remove(final int i) {
        final T result = delegate.remove(i);
        callChangeListeners();
        return result;
    }

    @Override
    public int indexOf(final Object o) {
        return delegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return delegate.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return delegate.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(final int i) {
        return delegate.listIterator(i);
    }

    @Override
    public List<T> subList(final int i, final int i1) {
        return delegate.subList(i, i1);
    }

    private void callChangeListeners() {
        changeListeners.forEach(listener -> {
            try {
                listener.stateChanged(new ChangeEvent(this));
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to inform listener about list update", ex);
            }
        });
    }

}
