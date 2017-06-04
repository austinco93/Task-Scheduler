import java.util.*;

/* Task.java 
 * Author: Austin Corotan
 * Date: June 4, 2017
 * Description: This is a task class which stores the data of a unit task (penalty and deadline) */

public class Task {
  private int id;
  private int  penalty;
  private int  deadline;

  public Task(int id, int penalty, int deadline) {
    this.id = id;
    this.penalty = penalty;
    this.deadline = deadline;
  }

  public int getID(){
    return this.id;
  }

  public int getPenalty(){
    return this.penalty;
  }

  public int getDeadline(){
    return this.deadline;
  }


  public static Comparator<Task> compByPenalty() {   
    Comparator<Task> comp = new Comparator<Task>(){
     @Override
      public int compare(Task s1, Task s2){
         int retInt = 0;
         if(s1.penalty < s2.penalty){
            retInt = 1;
          } else if (s1.penalty > s2.penalty){
            retInt = -1;
          } else {
            retInt = 0;
          }
          return retInt;
      }        
    };
    return comp;
  } 

  public static Comparator<Task> compByDeadline() {   
    Comparator<Task> comp = new Comparator<Task>(){
     @Override
      public int compare(Task s1, Task s2){
         int retInt = 0;
         if(s1.deadline > s2.deadline){
            retInt = 1;
          } else if (s1.deadline < s2.deadline){
            retInt = -1;
          } else {
            retInt = 0;
          }
          return retInt;
      }        
    };
    return comp;
  } 

}