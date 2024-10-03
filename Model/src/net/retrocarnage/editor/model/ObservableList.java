package net.retrocarnage.editor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A List of things that can inform listener
 *
 * @author Thomas Werner
 * @param <T> some kind of thing
 */
public class ObservableList<T> implements List<T> {

    public static final String PROPERTY_ELEMENT = "element";
    public static final String PROPERTY_SIZE = "size";        

    private final PropertyChangeSupport propertyChangeSupport;
    private final List<T> delegate;    

    public ObservableList() {
        delegate = new ArrayList<>();        
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
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
        propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, delegate.size() -1, delegate.size());        
        return result;
    }

    @Override
    public boolean remove(final Object o) {
        final int oldSize = delegate.size();
        final boolean result = delegate.remove(o);
        if(oldSize != delegate.size()) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, delegate.size());        
        }
        return result;
    }

    @Override
    public boolean containsAll(final Collection<?> clctn) {
        return delegate.containsAll(clctn);        
    }

    @Override
    public boolean addAll(final Collection<? extends T> clctn) {
        final int oldSize = delegate.size();
        final boolean result = delegate.addAll(clctn);
        if(oldSize != delegate.size()) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, delegate.size());        
        }
        return result;
    }

    @Override
    public boolean addAll(final int i, final Collection<? extends T> clctn) {
        final int oldSize = delegate.size();
        final boolean result = delegate.addAll(i, clctn);
        if(oldSize != delegate.size()) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, delegate.size());        
        }
        return result;
    }

    @Override
    public boolean removeAll(final Collection<?> clctn) {
        final int oldSize = delegate.size();
        final boolean result = delegate.removeAll(clctn);
        if(oldSize != delegate.size()) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, delegate.size());        
        }
        return result;
    }

    @Override
    public boolean retainAll(final Collection<?> clctn) {
        final int oldSize = delegate.size();
        final boolean result = delegate.retainAll(clctn);
        if(oldSize != delegate.size()) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, delegate.size());        
        }
        return result;
    }

    @Override
    public void clear() {
        final int oldSize = delegate.size();
        delegate.clear();
        if(oldSize > 0) {
            propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, oldSize, 0);        
        }
    }

    @Override
    public T get(final int i) {
        return delegate.get(i);
    }

    @Override
    public T set(final int i, final T e) {
        final T result = delegate.set(i, e);
        propertyChangeSupport.firePropertyChange(PROPERTY_ELEMENT, null, null);
        return result;
    }

    @Override
    public void add(final int i, final T e) {
        delegate.add(i, e);
        propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, delegate.size() -1, delegate.size());        
    }

    @Override
    public T remove(final int i) {
        final T result = delegate.remove(i);
        propertyChangeSupport.firePropertyChange(PROPERTY_SIZE, delegate.size() +1, delegate.size());
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

}
