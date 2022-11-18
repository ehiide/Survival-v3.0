package mc.server.survival.libraries.java;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class WrappedList<E> extends ArrayList<E>
{
    @Override
    public boolean add(final @NotNull E element)
    {
        if (contains(element))
            return false;

        return super.add(element);
    }

    @Override
    public boolean addAll(final @NotNull Collection<? extends E> collection)
    {
        final Collection<E> copy = new ArrayList<E>(collection);
        copy.removeAll(this);

        return super.addAll(copy);
    }

    @Override
    public boolean addAll(final int index, final @NotNull Collection<? extends E> collection)
    {
        final Collection<E> copy = new ArrayList<E>(collection);
        copy.removeAll(this);

        return super.addAll(index, copy);
    }

    @Override
    public void add(final int index, final @NotNull E element)
    {
        if (!contains(element))
            super.add(index, element);
    }

    @Override
    public boolean contains(final @NotNull Object object)
    {
        return super.contains(object);
    }
}