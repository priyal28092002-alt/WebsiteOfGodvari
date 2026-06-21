package Programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//6,3,3,6,6,7,9,5
public class Pesticideplantsalive {

	
	public static void main(String [] args) {
		
		System.out.println(NumberofDaysPlantNotDie(Arrays.asList(6,5,8,4,7,10,9)));
	}
	
	public static int NumberofDaysPlantNotDie(List<Integer> list) {
		List<Integer> survive=new ArrayList<>();
		int days=0;
		boolean stop=true;
	int	previoussize=list.size();
		while(stop) {
			survive.add(list.get(0));
			for(int i=1;i<list.size();i++) {
			     if (list.get(i) <= list.get(i - 1)) {
		                survive.add(list.get(i));
		            }
			}
			if(previoussize==survive.size()) {
				stop=false;
			}else {
			previoussize=survive.size();
			list=survive;
			survive=new ArrayList<>();
			days++;
			}
		}
		
		return days;
		
	}
}
