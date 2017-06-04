import java.util.*;
import java.io.*;

/* TaskScheduler.java 
 * Author: Austin Corotan
 * Date: June 4, 2017
 * Description: This program is able to to perform optimal unit task scheduling given a set of unit tasks
   with a penalty and a deadline. */

public class TaskScheduler {
	public static ArrayList<Task> tasks = new ArrayList<Task>(); //all tasks
	public static ArrayList<Task> earlyTasks = new ArrayList<Task>();  // early tasks
  public static ArrayList<Integer> n = new ArrayList<Integer>(); // N_t
	public static int numTasks = 0;
	public static int totalPenalty = 0;

	public static void main(String[] args) {
      if(args.length != 1){
        System.err.println("Usage: java TaskScheduler <input.txt>");
        System.exit(1);
      }

      /* load tasks in from file */
      String fileName = args[0];
      loadTasks(fileName);

      printTasks(tasks);
      Collections.sort(tasks, Task.compByPenalty());

       /* initialize N_t */
      for (int i = 0; i < numTasks; i++){
        n.add(0);
      }
      
      Task currentTask;
      for(int i = 0; i < tasks.size(); i++){
        currentTask = tasks.get(i);
        if(check(currentTask)){
          for(int j = n.size()-1; j >= currentTask.getDeadline()-1; j--){
            n.set(j, n.get(j) + 1);
          }
          earlyTasks.add(currentTask);
          tasks.remove(i);
          i--;
        }
      }

      for(int k = 0; k < tasks.size(); k++){
        totalPenalty = totalPenalty + tasks.get(k).getPenalty();
      }
      Collections.sort(earlyTasks, Task.compByDeadline());
      printSchedule();
    }

    public static boolean check(Task currentTask){
      boolean retVal = true;
      for(int i = n.size()-1; i >= currentTask.getDeadline()-1; i--){
        if(n.get(i) + 1 > i+1){
          retVal = false;
        }
      }

      return retVal;
    }

    public static void loadTasks(String fileName){
      Scanner fileScanner = null;
        try{
          File file = new File(fileName);
          fileScanner = new Scanner(file);
        } catch(FileNotFoundException e){
          System.exit(2);
        }
        /* Store verticies */
        int taskID = 1;
        while(fileScanner.hasNextLine()){
          String taskLine = fileScanner.nextLine();
          Scanner taskInfo = new Scanner(taskLine);
          int taskPenalty = taskInfo.nextInt();
          int taskDeadline = taskInfo.nextInt();
          Task temp = new Task(taskID, taskPenalty, taskDeadline);
          tasks.add(temp);
          taskID++;
          numTasks++;
        }
    }

    public static void printSchedule(){
      System.out.format("Number of penalty-free tasks scheduled: %d\n", earlyTasks.size());
      System.out.format("Total penalty incurred: %d\n", totalPenalty);
      System.out.format("%10s %10s %10s %20s\n", "Task ID", "Penalty", "Deadline", "Scheduled Position");
      int position = 1;
      for(int i = 0; i < earlyTasks.size(); i++){
        System.out.format("%10d %10d %10d %12d\n", earlyTasks.get(i).getID(), earlyTasks.get(i).getPenalty(), earlyTasks.get(i).getDeadline(), position);
        position++;
      }
      System.out.println("Penalized...");
       for(int i = 0; i < tasks.size(); i++){
        System.out.format("%10d %10d %10d %12d\n", tasks.get(i).getID(), tasks.get(i).getPenalty(), tasks.get(i).getDeadline(), position);
        position++;
      }
      System.out.println();
    }

    public static void printTasks(ArrayList<Task> printTasks){
      System.out.println("Input:");
      System.out.format("%5s %5s %5s\n", "Task ID", "Penalty", "Deadline");
      for(int i = 0; i < printTasks.size(); i++){
        System.out.format("%5d %5d %5d\n", printTasks.get(i).getID(), printTasks.get(i).getPenalty(), printTasks.get(i).getDeadline());
      }
      System.out.println();
    }
  }