package com.ssafy.s12p21d206.achu.chat.domain.support;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

  private CollectionUtils() {}

  public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyMapper) {
    return collection.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
  }

  public static <T> Set<T> toSet(Collection<T> collection) {
    return new HashSet<>(collection);
  }

  public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
    return collection.stream().map(mapper).collect(Collectors.toSet());
  }

  public static <T, R> List<R> mapToList(Collection<T> collection, Function<T, R> mapper) {
    return collection.stream().map(mapper).toList();
  }

  public static <T, R> Set<R> flatMapToSet(
      Collection<T> collection, Function<T, Collection<R>> flatMapper) {
    return collection.stream()
        .flatMap(item -> flatMapper.apply(item).stream())
        .collect(Collectors.toSet());
  }
}
