package io.ciera.runtime.types;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class AbstractStream<T> implements Stream<T> {
    
    private final Stream<T> innerStream;
    
    public AbstractStream() {
        innerStream = Stream.of();
    }
    
    public AbstractStream(Stream<T> stream) {
        innerStream = stream;
    }

    @Override
    public Iterator<T> iterator() {
        return innerStream.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return innerStream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return innerStream.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return innerStream.sequential();
    }

    @Override
    public Stream<T> parallel() {
        return innerStream.parallel();
    }

    @Override
    public Stream<T> unordered() {
        return innerStream.unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return innerStream.onClose(closeHandler);
    }

    @Override
    public void close() {
        innerStream.close();
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return innerStream.filter(predicate);
    }

    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        return innerStream.map(mapper);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return innerStream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return innerStream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return innerStream.mapToDouble(mapper);
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return innerStream.flatMap(mapper);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return innerStream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return innerStream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return innerStream.flatMapToDouble(mapper);
    }

    @Override
    public Stream<T> distinct() {
        return innerStream.distinct();
    }

    @Override
    public Stream<T> sorted() {
        return innerStream.sorted();
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return innerStream.sorted(comparator);
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return innerStream.peek(action);
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return innerStream.limit(maxSize);
    }

    @Override
    public Stream<T> skip(long n) {
        return innerStream.skip(n);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        innerStream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        innerStream.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return innerStream.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return innerStream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return innerStream.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return innerStream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return innerStream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return innerStream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return innerStream.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return innerStream.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return innerStream.max(comparator);
    }

    @Override
    public long count() {
        return innerStream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return innerStream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return innerStream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return innerStream.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return innerStream.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return innerStream.findAny();
    }

}
