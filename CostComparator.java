import java.util.Comparator;


class CostComparator implements Comparator<MyState>
{
	public int compare(MyState a, MyState b)
	{
		if(a.cost + a.h < b.cost + b.h)
    {
      return -1;
    }
    else if(a.cost + a.h > b.cost + b.h)
    {
      return 1;
    }
    return 0;

	}
}
