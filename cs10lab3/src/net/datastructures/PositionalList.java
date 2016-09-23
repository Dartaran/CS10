package net.datastructures;

import java.util.Iterator;

public abstract interface PositionalList<E>
  extends Iterable<E>
{
  public abstract int size();
  
  public abstract boolean isEmpty();
  
  public abstract Position<E> first();
  
  public abstract Position<E> last();
  
  public abstract Position<E> before(Position<E> paramPosition)
    throws IllegalArgumentException;
  
  public abstract Position<E> after(Position<E> paramPosition)
    throws IllegalArgumentException;
  
  public abstract Position<E> addFirst(E paramE);
  
  public abstract Position<E> addLast(E paramE);
  
  public abstract Position<E> addBefore(Position<E> paramPosition, E paramE)
    throws IllegalArgumentException;
  
  public abstract Position<E> addAfter(Position<E> paramPosition, E paramE)
    throws IllegalArgumentException;
  
  public abstract E set(Position<E> paramPosition, E paramE)
    throws IllegalArgumentException;
  
  public abstract E remove(Position<E> paramPosition)
    throws IllegalArgumentException;
  
  public abstract Iterator<E> iterator();
  
  public abstract Iterable<Position<E>> positions();
}
