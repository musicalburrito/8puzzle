import java.util.Comparator;

public class nodeComparator implements Comparator<node>{
	@Override
	public int compare(node x, node y){
		if(x.f < y.f){
			return -1;
		}
		else if(x.f > y.f){
			return 1;
		}
		return 0;
	}
}
