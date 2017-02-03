class MyState
{
  public double cost;
  MyState parent;
  int[] state;
  double h; //heuristic
  //because epsilon valuse makes edge of screen under 600 and 1200
  public static final int xMax  = (int)Model.XMAX;
  public static final int yMax = (int)Model.YMAX;
  public static final int xMin = 0;
  public static final int yMin = 0;

  MyState(double cost, MyState parent)
  {
    state = new int[2];
    this.parent = parent;
    this.cost = cost;
    this.h = 0;
  }

  public void print()
  {
    System.out.println("(" + state[0] + ", " + state[1] + ")  " + "cost: " + cost);
  }

  public boolean isValid()
  {
    if(state[0] <= xMin || state[0] >= xMax || state[1] <= yMin || state[1] >= yMax)
      return false;
    return true;
  }
}
