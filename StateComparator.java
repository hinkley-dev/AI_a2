import java.util.Comparator;
import java.util.TreeSet;

class StateComparator implements Comparator<MyState>
{
	public int compare(MyState a, MyState b)
	{
		for(int i = 0; i < 2; i++)
		{
			if(a.state[i] < b.state[i])
				return -1;
			else if(a.state[i] > b.state[i])
				return 1;
		}
		return 0;
	}
}
