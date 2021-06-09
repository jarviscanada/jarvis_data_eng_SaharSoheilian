package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc {

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    Stream<String> stringStream = createStrStream(strings);
    return stringStream.map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(string -> !string.contains(pattern));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    // boxed() is used to turn IntStream to Stream<Integer>
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.mapToDouble(Math::sqrt);
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i -> i % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return message -> System.out.println(prefix + message + suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).mapToObj(String::valueOf).forEach(printer);
  }

  /**
   *  1. flatMap(): flatten the nested Stream structure and
   *  eventually collect all elements to a particular collection
   *  2. forEach(): iterate over all elements and add them to a list
   *  then convert the list to stream
   *
   * @param ints
   * @return
   */
  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(Collection::stream).map(i -> i * i);
    /*
    List<Integer> intList = new ArrayList<>();
    ints.forEach(intList::addAll);
    return intList.stream().map(i -> i * i);*/
  }

  public static void main(String[] args) {
    LambdaStreamExc lambdaStream = new LambdaStreamImp();

    String[] string = {"String", "array", "for", "test."};
    int[] intArray = {1, 2, 3, 4, 5, 6, 7};
    List<Integer> listInt = new ArrayList<Integer>(){{add(1); add(2); add(3);}};

    Stream<List<Integer>> nestedStream = Stream.of(listInt);
    Stream<String> strStream, filteredStrStream;
    IntStream intStream, intRangeStream;
    DoubleStream doubleStream;

    strStream = lambdaStream.createStrStream("This", "is", "for", "test.");
    filteredStrStream = lambdaStream.filter(strStream, "t");
    intStream = lambdaStream.createIntStream(intArray);
    intRangeStream = lambdaStream.createIntStream(2, 5);
    doubleStream = lambdaStream.squareRootIntStream(intRangeStream);

    System.out.println(lambdaStream.toList(filteredStrStream));
    lambdaStream.toUpperCase(string).forEach(System.out::println);
    lambdaStream.getOdd(intStream).forEach(System.out::println);
    doubleStream.forEach(System.out::println);

    Consumer<String> printer = lambdaStream.getLambdaPrinter("start>", "<end");
    printer.accept("Message Body");

    lambdaStream.printMessages(string, printer);
    lambdaStream.printMessages(string,
        lambdaStream.getLambdaPrinter("msg:", "!"));

    lambdaStream.printOdd(lambdaStream.createIntStream(0, 5),
                          lambdaStream.getLambdaPrinter("odd num:", "!"));

    lambdaStream.flatNestedInt(nestedStream).forEach(System.out::println);
  }
}


