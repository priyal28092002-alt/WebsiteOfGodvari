package Programs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class Stream {
	// 1)Filter even numbers from a list
 List<Integer> list=Arrays.asList(1,2,3,4,5,6);
 List<String> str=Arrays.asList("abc,mm,HDSG,Priyal");
 List<Integer> even=list.stream().filter(n->n%2==0).collect(Collectors.toList());
 // Using the  Map
 Map<Boolean,List<Integer>> map=list.stream().collect(Collectors.partitioningBy(n->n%2==0));
 List<Integer> evenm=map.get(true);
 
   int gg=3;
   //2)Find all numbers greater than a given value
   List<Integer> gretaer=list.stream().filter(n->n>gg).collect(Collectors.toList());
 
   //3)Convert a list of strings to uppercase
   List<String> uppercase=str.stream().map(n->n.toUpperCase()).collect(Collectors.toList());
   
  //4)Sort a list of integers using streams
   List<Integer> listsorted=list.stream().sorted().collect(Collectors.toList());
   List<Integer> reverseOrder=list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
   
  //5)Count elements in a list
   Map<Integer,Long> mapofCountElements=list.stream().collect(Collectors.groupingBy(n->n,Collectors.counting()));
   
   //6)Find minimum and maximum element
   Optional<Integer> max=list.stream().sorted(Comparator.reverseOrder()).findFirst();
   Optional<Integer>min=list.stream().sorted().findFirst();
   
   //7)Remove duplicates from a list
   List<Integer>removingD=list.stream().distinct().collect(Collectors.toList());
   
   //8)Find first element in a stream
   Optional<Integer> firstElement=list.stream().findFirst();
   
   //9)Convert List → Set using streams
   Set<Integer> ss=list.stream().collect(Collectors.toSet());
  
   //10)Join list of strings into a single string
   String s1=str.stream().collect(Collectors.joining(" "));
   
   //11)Filter employees by salary > X and collect names
  // Map<String,Employees> m1=list.stream().filter(e->e.getsalry()>x).collect(Collectors.toMap(e::getName,e->e));
   
   //12)Get list of employee names from employee objects
  // List<String> stlist=list.stream().map(e->e.getName()).collect(Collectors.toList());
  // List<String> stList=list.stream().map(Employee::getName).collect(Collectors.toList());
   
  // 13)Convert List → Map<String, Integer> (string → length)
   Map<String,Integer> mapofConvertList=str.stream().collect(Collectors.toMap(e->e, String::length));
   Map<String,Integer> mapofConvertListm=str.stream().collect(Collectors.toMap(e->e,e->e.length()));
   Map<String,Integer> mapofConvertListmd=str.stream().collect(Collectors.toMap(e->e,e->e.length(),(o,n)->o)); // to handle duplicates
   
   //14)Filter null values from a list
   List<Integer> listnonull=list.stream().filter(Objects::nonNull).collect(Collectors.toList());
   
   //15)Find names starting with a specific letter
   List<String> strtawit=str.stream().filter(e->e.toLowerCase().startsWith("s")).collect(Collectors.toList());
   
   //16)Sort employees by salary
  // List<Employee> emp=list.stream().sorted(Comparator.comparing(ُEmployee::getSalary)).collect(Collectors.toList());
   
   //17)Sort employees by name then salary
   //List<Employee> emp=list.stream().sorted(Comparator.comparing(e->e.getName()).thenComparing(e->e.getSalary())).collect(Collectors.toList());
   // List<Employee> emp = list.stream().sorted(Comparator.comparing(Employee::getName).thenComparing(Employee::getSalary)).collect(Collectors.toList());
   
   //18)Reverse sort a list
   List<Integer> desc=list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
   
   //19)Find second highest number in a list
   List<Integer> sortedlis=list.stream().distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
   Optional<Integer> secondH=sortedlis.stream().skip(1).findFirst();
   
   //20)Find kth largest element
   int n=2;
   List<Integer>sortedForKth=list.stream().distinct().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
   Optional<Integer> kthLarge=sortedForKth.stream().skip(n-1).findFirst();
   
   //21)Find sum of all elements
   Integer sumofall=list.stream().filter(Objects::nonNull).mapToInt(e->e.intValue()).reduce(0,Integer::sum);
   Integer ssss=list.stream().mapToInt(Integer::intValue).sum();
   //22) Find average of numbers
   OptionalDouble smm=list.stream().filter(Objects::nonNull).mapToInt(e->e.intValue()).average();
 
   //23)Find total salary of all employees
  // long totalsal=list.stream().mapToLong(Employee::getSalary).sum();
   
  //24)Count occurrences of each element
   Map<Integer,Long> countOccurrence=list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(e->e,Collectors.counting()));
   
   //25)Find max occurring element
 Map<Integer,Long>counttoformaxocc=list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(e->e,Collectors.counting()));
  Long maxvalue=Collections.max(counttoformaxocc.values());
  List<Integer>allmaxocc=counttoformaxocc.entrySet().stream().filter(x->x.getValue()==maxvalue).map(x->x.getKey()).collect(Collectors.toList());
  
  //26)/32)Group employees by department
//Map<String,List<Employee>> listofem=list.stream().collect(Collectors.groupingBy(e->e.getdep()));
//only map name with depaRTMENT
 // Map<String,List<String>>listorm=list.stream().collect(Collectors.groupingBy(e->e.getdept(),Collectors.mapping(e->e.getName(),Collectors.toList())));
  
  //27),44)Group words by frequency,Group strings by their length
  Map<Integer,List<String>> mapl=str.stream().collect(Collectors.groupingBy(String::length,Collectors.mapping(s->s, Collectors.toList())));
  //another simple method 
  Map<Integer,List<String>> mm=str.stream().collect(Collectors.groupingBy(String::length));
  
 // 28)Partition numbers into even and odd
  Map<Boolean,List<Integer>> boo=list.stream().filter(Objects::nonNull).collect(Collectors.partitioningBy(n->n%2==0));
  List<Integer> ee=boo.get(true);
  List<Integer>mmm=boo.get(false);
  
  //29)	Count employees in each department
  //Map<String,Long> employecoount=list.stream().collect(Collectors.groupingBy(d->d.getname(),Collectors.counting()));
  
  //30)Find highest salary in each department
//  Map<String,Long>macda=list.stream().collect(Collectors.groupingBy(d->d.getName(),Collectors.maxBy(Comparator.comparing(e->e.getSalary(),e->e.get().getsalary()))));
  
  //31)Convert list into Map (handling duplicate keys)
  Map<Integer,Integer> ccm=list.stream().filter(Objects::nonNull).collect(Collectors.toMap(e->e,e->e,(old,newval)->old));
  
   //33)Create Map<Dept, AvgSalary>
 // Map<String,Double> mapva=list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(e->e.getDeptName(),Collectors.averagingInt(e->e.getSalary())));
  
  //34)Join employee names with delimiter
  String empployye=str.stream().collect(Collectors.joining(" ,"));
 //	 String moo=str.stream().map(e->e.getname()).collect(Collectors.joining(" ,"));
  	
  //35)Collect only unique elements into a specific collection
  Set<Integer> setofElements=list.stream().filter(Objects::nonNull).collect(Collectors.toSet());
  
  //36)Find product of all numbers using reduce()
  Integer product=list.stream().filter(Objects::nonNull).reduce(1, (a,b)->a*b);
  
  //37)Find longest string in a list
 Optional<String> dd= str.stream().filter(Objects::nonNull).max(Comparator.comparing(S->S.length()));
  
 //38)Find sum of digits of all numbers
 Integer so=list.stream().filter(Objects::nonNull).flatMapToInt(n->String.valueOf(n).chars().map(c->c-'0')).sum();
 
 //39)Flatten nested lists using flatMap()
// List<Integer> flatt=list.stream().filter(Objects::nonNull).flatMap(innerlist->innerlist.stream).collect(Collectors.toList());
 
 //40)Find intersection of two lists
 //List<Integer> intersectionoftwolist=list.stream().filter(list1::contains).collect(Collectors.toList());
 
 //41)Find duplicate elements in a list
 Set<Integer> dup=new HashSet<>();
List<Integer> mmmo=list.stream().filter(e->!dup.add(e)).collect(Collectors.toList());
  //43)Find all anagrams in a list of strings
Map<String,List<String>> anagram=str.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(s->{char[] ch=s.toString().toCharArray();Arrays.sort(ch);return new String(ch);}));

}