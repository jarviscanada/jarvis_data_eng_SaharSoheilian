package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Employee {
  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee() {
  }

  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Employee)) {
      return false;
    }
    Employee employee = (Employee) o;
    return getId() == employee.getId() && getAge() == employee.getAge() && getSalary() == employee
        .getSalary() && Objects.equals(getName(), employee.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAge(), getSalary());
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getSalary() {
    return salary;
  }

  public void setSalary(long salary) {
    this.salary = salary;
  }

  public static void main(String[] args) {
    Map<Employee, List<String>> empStrMap = new HashMap<>();

    Employee amy = new Employee(1, "Amy", 25, 45000);
    List<String> amyPreviousEmployers = Arrays.asList("TD", "CIBC", "RBC");
    empStrMap.put(amy, amyPreviousEmployers);

    Employee bob = new Employee(2, "Bob", 30, 50000);
    List<String> bobPreviousEmployers = Arrays.asList("Walmart", "Costco");
    empStrMap.put(bob, bobPreviousEmployers);

    System.out.println("Bob hashcode: " + bob.hashCode());
    System.out.println("Bob value: " + empStrMap.get(bob).toString());
    System.out.println("Amy value: " + empStrMap.get(amy).toString());

  }
}
