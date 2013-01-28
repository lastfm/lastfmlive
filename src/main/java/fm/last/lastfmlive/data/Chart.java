package fm.last.lastfmlive.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class Chart<T> {
  public static final class ChartEntry<E> {
    private final E item;
    private final int weight;

    public E getItem() {
      return item;
    }

    public int getWeight() {
      return weight;
    }

    public ChartEntry(E item, int weight) {
      this.item = item;
      this.weight = weight;
    }

  }

  private final HashMap<T, Integer> artistCountMap;

  public Chart() {
    artistCountMap = new HashMap<T, Integer>();
  }

  public void putAll(List<ChartEntry<T>> entries) {
    for (ChartEntry<T> entry : entries) {
      put(entry.item, entry.weight);
    }
  }

  public void put(T item, int weight) {
    if (artistCountMap.containsKey(item)) {
      artistCountMap.put(item, artistCountMap.get(item) + weight);
    } else {
      artistCountMap.put(item, weight);
    }
  }

  public List<T> sortedView() {
    Ordering<Map.Entry<T, Integer>> ordering = new Ordering<Map.Entry<T, Integer>>() {
      @Override
      public int compare(java.util.Map.Entry<T, Integer> arg0, java.util.Map.Entry<T, Integer> arg1) {
        return arg0.getValue().compareTo(arg1.getValue());
      }
    };
    return Lists.transform(ordering.reverse().sortedCopy(artistCountMap.entrySet()),
        new Function<Map.Entry<T, Integer>, T>() {
          public T apply(Entry<T, Integer> arg0) {
            return arg0.getKey();
          }
        });
  }

  public static <E> List<ChartEntry<E>> autoWeight(List<E> items) {
    List<ChartEntry<E>> entries = new ArrayList<ChartEntry<E>>();
    int i = 0;
    int len = items.size();
    for (E a : items) {
      entries.add(new ChartEntry<E>(a, len - i));
      i++;
    }
    return entries;
  }

}
