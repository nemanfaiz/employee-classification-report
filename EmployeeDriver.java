import java.text.*;
import java.util.*;

class EmployeeDriver
{   
    static Employee [] array = new Employee[1];
    static String selection;
    static Scanner reader = new Scanner(System.in);
    static NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    public static void main(String [] args)
    {
        do
        {
            //selection = employeeMenu();
            employeeMenu();
            selectOption(selection);
            
        }while(selection != "q");
    }
    
    public static void employeeMenu()
    {
        System.out.printf("\nN: New Employee\nP: Compute Paychecks\nR: Raise Wages\nL: List All Employees\nQ: Quit\n");
        System.out.println("\nEnter Command: ");
        selection = reader.next();
    }
    
    public static void selectOption(String selection)
    {
        selection = selection.toUpperCase();
        switch(selection)
        {
            case "N": newEmployee();
                break;
            case "P": computePaycheck();
                break;
            case "R": raiseWages();
                break;
            case "L": listEmployees();
                break;
            case "Q":
                System.out.println("You are done");
                System.exit(0);
            default:
                System.out.println("Invalid Command.");
                break;
        }       
    }
    
    public static void newEmployee()
    {
        String name ="";
        String wageType = "";
        double hourlyWage = 0.0;
        int salary = 0;
        
        System.out.print("Enter name of new employee: ");
        name = reader.next();        

        do
        {
           System.out.print("Enter Wage type( H for Hourly or S for Salary):");
           wageType = reader.next();
                   
           if(wageType.equalsIgnoreCase("H"))
           {
               System.out.print("Enter Hourly Wage: ");
               hourlyWage = reader.nextDouble();
               
               if(Employee.empCount < array.length)
               {
                   array[Employee.empCount] = new HourlyEmployee(name,hourlyWage);
                   (Employee.empCount)++;
               }
               else
               {
                   array = Arrays.copyOf(array,(array.length * 2));
                   array[Employee.empCount] = new HourlyEmployee(name,hourlyWage);
                   (Employee.empCount)++;
               }
           }
           else if(wageType.equalsIgnoreCase("S"))
           {
               System.out.print("Enter Annual Salary: ");
               salary = reader.nextInt();
               
               if(Employee.empCount < array.length)
               {
                   array[Employee.empCount] = new SalariedEmployee(name,salary);
                   (Employee.empCount)++;
               }
               else
               {
                   array = Arrays.copyOf(array,(array.length * 2));
                   array[Employee.empCount] = new SalariedEmployee(name,salary);
                   (Employee.empCount)++;
               }
           }
           else
           {
                  System.out.println("Invalid command for wage type.");
           }
           
        }while(!(wageType.equalsIgnoreCase("H")) & !((wageType.equalsIgnoreCase("S"))));
    }
    
    public static void computePaycheck()
    {
        for(int i = 0 ; i < Employee.empCount ; ++i)
        {
            if(array[i] instanceof HourlyEmployee)
            {
                System.out.println("Enter number of hours worked per week by: " + ((HourlyEmployee) array[i]).empName + ":");
                ((HourlyEmployee) array[i]).numHr = reader.nextInt();
                System.out.println("Pay: " + array[i].computePay() + "\n");
            }
            else if(array[i] instanceof SalariedEmployee)
            {
                System.out.println("Enter number of hours worked per week by: " + ((SalariedEmployee) array[i]).empName + ":");
                ((SalariedEmployee) array[i]).numHr = reader.nextInt();
                System.out.println("Pay: " + array[i].computePay() + "\n");
            }
        }
    }
    
    public static void raiseWages()
    {
        System.out.println("Enter Percentage Increase: ");
        double percentage = reader.nextDouble();
        
        for(int i = 0 ; i < Employee.empCount ; ++i)
        {
            array[i].increasePay(percentage);
        }
        
        System.out.println("\nName" + "    " + "    Wage" );
        System.out.println("_____" + "   " + "____________");
        
        for(int i = 0 ; i < Employee.empCount ; ++i)
        {
            System.out.println(array[i].getString());
        }
    }
    
    public static void listEmployees()
    {
        if(Employee.empCount == 0)
        {
            System.out.println("You have no employees to display.");
        }
        System.out.println("\nName" + "    " + "    Wage" );
        System.out.println("_____" + "   " + "____________");
              
        for(int i = 0 ; i < Employee.empCount ; ++i)
        {  
            if( array[i] instanceof SalariedEmployee)
            {
                System.out.println(((SalariedEmployee) array[i]).empName + "   " + ((SalariedEmployee) array[i]).empWage + "/hour");
            }
            else
            {
                System.out.println(array[i].getString());
            }
        }
    }       
}

abstract class Employee
{
    String empName;
    double empWage;
    static int empCount;
    
    int numHr;
    int empSalary;
    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
    
    void setName(String empName)
    {
        this.empName = empName;
    }
    
    String getName()
    {
        return empName;
    }
    
    void setWage(double empWage)
    {
        this.empWage = empWage;
    }
    
    double getWage()
    {
        return empWage;
    }
    
    void increasePay(double percentage)
    {
        empWage += ((percentage / 100) * empWage);
        empSalary += ((percentage / 100) * empSalary);
    }
    
    abstract double computePay();
    abstract String getString();
}

class HourlyEmployee extends Employee
{   
    double hours;
    HourlyEmployee(String empName, double empWage)
    {
        super.empName = empName;
        super.empWage = empWage;
    }
    
    double computePay()
    {
        if(numHr <= 40)
        {
            hours = empWage * numHr;
        }
        else
        {
            hours = (40 * empWage) + ((numHr - 40) * 1.5 * empWage);
        }
        return hours;
    }
    
    String getString()
    {
        return(empName + "   " + String.valueOf(nf.format(empWage)) + "/hour");
    }
}

class SalariedEmployee extends Employee
{
    SalariedEmployee(String empName, int empSalary)
    {
        super.empName = empName;
        super.empSalary = empSalary;
        empWage = (empSalary / 52) / 40;
    }
    
    void setSalary(int salary)
    {
        super.empSalary = empSalary;
    }
    
    int getSalary()
    {
        return empSalary;
    }
    
    double computePay()
    {
        return (empSalary / 52); // weekly
    }
    
    String getString()
    {
        return(empName + "   " + String.valueOf(nf.format(empSalary)) + "/year");
    }
}
