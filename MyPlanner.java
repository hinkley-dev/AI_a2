import java.util.*;

class MyPlanner
{
  Model model;
  Stack<MyState> path = new Stack();
  PriorityQueue<MyState> frontier;
  TreeSet<MyState> visited;
  static double maxSpeed;

  MyPlanner(Model m)
  {
      model = m;
      maxSpeed = findMaxSpeed();
  }



  public MyState UCS(MyState start, MyState goal)
  {

    start.parent = null;
    start.cost = 0;

    StateComparator comp = new StateComparator();
    CostComparator costComp = new CostComparator();
    frontier = new PriorityQueue(costComp);
    visited = new TreeSet(comp);

    visited.add(start);
    frontier.add(start);

    while(frontier.size() > 0)
    {

      MyState parent = frontier.poll();

      if(Math.abs(parent.state[0] - goal.state[0]) < 10 && Math.abs(parent.state[1] - goal.state[1]) < 10 )
      {
        MyState end = new MyState(0,parent);
        end.state = goal.state.clone();
        double endCost =  getDistanceToDestination(parent, goal)/((double)model.getTravelSpeed(goal.state[0], goal.state[1]));
        end.cost = endCost + parent.cost;
        copyPath(end);
        return end;
      }
      for(int i = 0; i < 8; ++i)
      {
        double childCost = 0;
        MyState child = addPossibleNextSteps(parent,   i);
        if(child.isValid())
        {
          childCost = getDistanceToDestination(parent, child)/((double)model.getTravelSpeed(child.state[0], child.state[1]));
          if(visited.contains(child))
          {
            MyState oldChild = visited.floor(child);
            if(parent.cost + childCost < oldChild.cost)
            {
              oldChild.cost = parent.cost + childCost;
              oldChild.parent = parent;
            }
          }
          else
          {
            child.cost = childCost + parent.cost;
            child.parent = parent;
            frontier.add(child);
            visited.add(child);
          }
        }

        //check for replacing existing path

      }
    }
    System.out.println("No path");
    return null;
  }

  public MyState aStar(MyState start, MyState goal)
  {
    start.parent = null;
    start.cost = 0;


    StateComparator comp = new StateComparator();
    CostComparator costComp = new CostComparator();
    frontier = new PriorityQueue(costComp);
    visited = new TreeSet(comp);

    visited.add(start);
    frontier.add(start);

    while(frontier.size() > 0)
    {
      MyState parent = frontier.poll();

      if(Math.abs(parent.state[0] - goal.state[0]) < 10 && Math.abs(parent.state[1] - goal.state[1]) < 10 )
      {
        MyState end = new MyState(0,parent);
        end.state = goal.state.clone();
        double endCost =  getDistanceToDestination(parent, goal)/((double)model.getTravelSpeed(goal.state[0], goal.state[1]));
        end.cost = endCost + parent.cost;
        copyPath(end);
        return end;
      }
      for(int i = 0; i < 8; ++i)
      {
        double childCost = 0;
        MyState child = addPossibleNextSteps(parent, i);
        if(child.isValid())
        {
          childCost = getDistanceToDestination(parent, child)/((double)model.getTravelSpeed(child.state[0], child.state[1]));
          if(visited.contains(child))
          {
            MyState oldChild = visited.floor(child);
            if(parent.cost + childCost +  getDistanceToDestination(child, goal)/this.maxSpeed < oldChild.cost)
            {
              oldChild.cost = parent.cost + childCost;
              oldChild.parent = parent;
            }
          }
          else
          {
            child.cost = childCost + parent.cost;
            child.h =  getDistanceToDestination(child, goal)/(this.maxSpeed);
            child.parent = parent;
            frontier.add(child);
            visited.add(child);
          }

        }

      }
    }
    System.out.println("No path");
    return null;


  }

  double findMaxSpeed()
  {
      double max = 0;
      for(int i = 0; i < 1200; ++i)
      {
        for(int j = 0; j < 600; ++j)
        {
          if((double)model.getTravelSpeed(i, j) > max)
            max = (double)model.getTravelSpeed(i, j);
        }
      }
      return max;
  }


  MyState addPossibleNextSteps(MyState parent, int i)
  {
    int destX = 0;
    int destY = 0;

    switch(i)
    {
      case 0:
        destX = parent.state[0] + 10;
        destY = parent.state[1] - 10;
        break;
      case 1:
        destX = parent.state[0] + 10;
        destY = parent.state[1];
        break;
      case 2:
        destX = parent.state[0] + 10;
        destY = parent.state[1] + 10;
        break;
      case 3:
        destX = parent.state[0];
        destY = parent.state[1] + 10;
        break;
      case 4:
        destX = parent.state[0];
        destY = parent.state[1] - 10;
        break;
      case 5:
        destX = parent.state[0] - 10;
        destY = parent.state[1] - 10;
        break;
      case 6:
        destX = parent.state[0] - 10;
        destY = parent.state[1];
        break;
      case 7:
        destX = parent.state[0] - 10;
        destY = parent.state[1] + 10;
        break;
      default:
        System.out.println("Error in for loop of UCS");
        break;
    }

    MyState child = new MyState(0, parent);
    child.state[0] = destX;
    child.state[1] = destY;

    return child;

  }



  double getDistanceToDestination(MyState current, MyState goal) {

    return Math.sqrt((current.state[0] - goal.state[0]) * (current.state[0] - goal.state[0]) + (current.state[1] - goal.state[1]) * (current.state[1] - goal.state[1]));
  }




  void copyPath(MyState f){
    MyState current = f;
    while(current != null)
    {
      path.push(current);
      current = current.parent;
    }
  }
}
