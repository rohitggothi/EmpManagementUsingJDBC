/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rohit.dao;

import com.rohit.bean.empBean;
/**
 *
 * @author rokie
 */
import com.rohit.utility.connectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class empDao {

    private static Connection conn;
    private Scanner sk = new Scanner(System.in);

    public int addEmployee(empBean ub) {
        int r = 0;
        try {
            conn = connectionPool.connectDB();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO emp VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            System.out.println("Enter EmpID: ");
            ub.setEmpId(sk.nextInt());
            sk.nextLine(); // consume newline

            System.out.println("Enter EName: ");
            ub.setEname(sk.nextLine());

            System.out.println("Enter Job: ");
            ub.setJob(sk.nextLine());

            System.out.println("Enter Manager no: ");
            ub.setMgr(sk.nextInt());
            sk.nextLine(); // consume newline

            System.out.println("Enter HireDate (yyyy-mm-dd): ");
            String hireDateStr = sk.nextLine();
            LocalDate hireDate = LocalDate.parse(hireDateStr, DateTimeFormatter.ISO_DATE);
            ub.setHireDate(hireDate);
            System.out.println("Enter Salary: ");
            ub.setSal(sk.nextFloat());

            System.out.println("Enter Commission: ");
            ub.setComm(sk.nextFloat());

            System.out.println("Enter Deptno: ");
            ub.setDeptNo(sk.nextInt());

            pstmt.setInt(1, ub.getEmpId());
            pstmt.setString(2, ub.getEname());
            pstmt.setString(3, ub.getJob());
            pstmt.setInt(4, ub.getMgr());
            pstmt.setString(5, ub.getHireDate().toString());
            pstmt.setFloat(6, ub.getSal());
            pstmt.setFloat(7, ub.getComm());
            pstmt.setInt(8, ub.getDeptNo());

            r = pstmt.executeUpdate();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public int updateEmployee(empBean ub) {
        int r = 0;
        conn = connectionPool.connectDB();
        String sql = "";
        System.out.println("What do you want to update : ");
        System.out.println("1. Salary \n2.Commision ");
        int option = sk.nextInt();
        System.out.println("Enter Employee ID : ");
        ub.setEmpId(sk.nextInt());

        switch (option) {
            case 1:
                System.out.println("Enter salary : ");
                ub.setSal(sk.nextFloat());
                sql = "update emp set sal='" + ub.getSal() + "'where empno='" + ub.getEmpId() + "'";
                break;
            case 2:
                System.out.println("Enter Commission : ");
                ub.setComm(sk.nextFloat());
                sql = "update emp set comm='" + ub.getComm() + "'where empno='" + ub.getEmpId() + "'";
                break;
            default:
                System.out.println("Enter valid Choice");
        }
        Statement stmt;
        try {
            stmt = conn.createStatement();
            r = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public int deleteEmployee(empBean ub) {
        int r = 0;
        conn = connectionPool.connectDB();
        System.out.println("Enter Emp ID to Delete : ");
        ub.setEmpId(sk.nextInt());
        String sql = "delete from emp where empno='" + ub.getEmpId() + "'";
        Statement stmt;
        try {
            stmt = conn.createStatement();
            r = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public void findALl() {
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        String sql = "select * from emp";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void findById(empBean ub) {
        System.out.println("Enter Id To find Employee");
        ub.setEmpId(sk.nextInt());
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        String sql = "Select * from emp where empno='" + ub.getEmpId() + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void findByDeptno(empBean ub) {
        System.out.println("Enter Deptno To find Employee ");
        ub.setDeptNo(sk.nextInt());
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        String sql = "Select * from emp where deptno='" + ub.getDeptNo() + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void findByJob(empBean ub) {
        System.out.println("Enter Job to Find Employee");
        ub.setJob(sk.nextLine());
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        String sql = "select * from emp where job='" + ub.getJob() + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void findByHiredate(empBean ub) {
        System.out.println("Enter Hiredate of Employee to find (yyyy-mm-dd)");
        String HireDate = sk.nextLine();
        LocalDate hireDate = LocalDate.parse(HireDate, DateTimeFormatter.ISO_DATE);
        ub.setHireDate(hireDate);
        String sql = "Select * from emp where hiredate='" + ub.getHireDate() + "'";
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateSumSalaryAllEmployee() {
        String sql = "SELECT SUM(sal) AS Total FROM emp";
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                float salary = rs.getFloat("Total");
                System.out.println("Total Sum Salary of Employee : " + salary);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateSumSalaryOfDeptNo(empBean ub) {
        System.out.println("Enter Deptno To calculate total salary : ");
        ub.setDeptNo(sk.nextInt());
        String sql = "SELECT SUM(sal) AS Total FROM emp where deptno ='" + ub.getDeptNo() + "'";
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                float salary = rs.getFloat("Total");
                System.out.println("Total Sum Salary of Employee from deptno " + ub.getDeptNo() + " : " + salary);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateSumSalaryOfJob(empBean ub) {
        System.out.println("Enter Job To calculate total salary : ");
        ub.setJob(sk.nextLine());
        String sql = "SELECT SUM(sal) AS Total FROM emp where job ='" + ub.getJob() + "'";
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                float salary = rs.getFloat("Total");
                System.out.println("Total Sum Salary of Employee from Job " + ub.getJob() + " : " + salary);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void calculateAvgSalaryAllEmployee() {
        String sql = "SELECT AVG(sal) AS AvgSalary FROM emp";
        conn = connectionPool.connectDB();
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                float avgSalary = rs.getFloat("AvgSalary");
                System.out.println("Average Salary of Employees: " + avgSalary);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateAvgSalaryOfDeptNo(empBean ub) {
        System.out.println("Enter Deptno to calculate avg salary ");
        ub.setDeptNo(sk.nextInt());
        String sql = "SELECT AVG(sal) AS AvgSalary FROM emp WHERE deptno = '" + ub.getDeptNo() + "'";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                float avgSalary = rs.getFloat("AvgSalary");
                System.out.println("Average Salary of Employees in Department " + ub.getDeptNo() + ": " + avgSalary);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void maxSalaryEmployeeData() {
        String sql = "SELECT * FROM emp WHERE sal = (SELECT MAX(sal) FROM emp)";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int empId = rs.getInt("empno");
                String ename = rs.getString("ename");
                String job = rs.getString("job");
                int mgr = rs.getInt("mgr");
                String hireDate = rs.getString("hireDate");
                float sal = rs.getFloat("sal");
                float comm = rs.getFloat("comm");
                int deptNo = rs.getInt("deptNo");

                System.out.println("Employee with Maximum Salary:");
                System.out.println("EmpID: " + empId);
                System.out.println("EName: " + ename);
                System.out.println("Job: " + job);
                System.out.println("Manager no: " + mgr);
                System.out.println("Hire Date: " + hireDate);
                System.out.println("Salary: " + sal);
                System.out.println("Commission: " + comm);
                System.out.println("DeptNo: " + deptNo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void minSalaryEmployeeData() {
        String sql = "SELECT * FROM emp WHERE sal = (SELECT min(sal) FROM emp)";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int empId = rs.getInt("empno");
                String ename = rs.getString("ename");
                String job = rs.getString("job");
                int mgr = rs.getInt("mgr");
                String hireDate = rs.getString("hireDate");
                float sal = rs.getFloat("sal");
                float comm = rs.getFloat("comm");
                int deptNo = rs.getInt("deptNo");

                System.out.println("Employee with Minimum Salary:");
                System.out.println("EmpID: " + empId);
                System.out.println("EName: " + ename);
                System.out.println("Job: " + job);
                System.out.println("Manager no: " + mgr);
                System.out.println("Hire Date: " + hireDate);
                System.out.println("Salary: " + sal);
                System.out.println("Commission: " + comm);
                System.out.println("DeptNo: " + deptNo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void countNoOfEmployee() {
        String sql = "SELECT COUNT(*) AS EmployeeCount FROM emp";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int employeeCount = rs.getInt("EmployeeCount");
                System.out.println("Total Number of Employees: " + employeeCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void countNumberOfEmployeeInDept(empBean ub) {
        System.out.println("Enter deptno to count it's employee ");
        ub.setDeptNo(sk.nextInt());
        String sql = "SELECT COUNT(*) AS DeptEmployeeCount FROM emp WHERE deptNo = '" + ub.getDeptNo() + "'";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int deptEmployeeCount = rs.getInt("DeptEmployeeCount");
                System.out.println("Number of Employees in Department " + ub.getDeptNo() + ": " + deptEmployeeCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sortDataBasisOfSalary() {
        String sql = "SELECT * FROM emp ORDER BY sal";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sortDataBasisOfName(){
          String sql = "SELECT * FROM emp ORDER BY ename";
        conn = connectionPool.connectDB();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("EmpID \t\t EmpName \t\t Job \t\t Manager \t\t HireDate \t\t Salary \t\t Commission \t\t DeptNo");
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + " \t\t " + rs.getString("ename") + " \t\t " + rs.getString("job") + " \t\t " + rs.getInt("mgr") + " \t\t " + rs.getDate("hiredate") + " \t\t " + rs.getFloat("sal") + " \t\t " + rs.getFloat("comm") + " \t\t " + rs.getInt("deptno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(empDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Scanner sk = new Scanner(System.in);
        empDao ud = new empDao();
        empBean ub = new empBean();
        System.out.println("1.Add Employee\n2.Update Employee\n3.Delete Employee\n4.FindAll\n5.Find By ID\n6.Find By DeptNo\n7.Find By Job\n8.Find By HireDate\n9.Calculate Sum Salary Of Employee\n10.Calculate sum salary of Department\n11.Calculate sum salary of Job\n12.Calculate Avg Salary of employee\n13.Calculate avg salary by deptno\n14.Highest Salary Employee\n15.Lowest salary employee\n16.Count No. of Employee\n17.Count No. of Employee in Deptno\n18.Sort Employee on basis of Salary\n19.Sort Employee on basis of Name");
        int option = sk.nextInt();
        switch (option) {
            case 1:
                int x = ud.addEmployee(ub);
                if (x > 0) {
                    System.out.println("Data sent Successfully");
                } else {
                    System.out.println("Data Not Sent");
                }
                break;
            case 2:
                x = ud.updateEmployee(ub);
                if (x > 0) {
                    System.out.println("Data updated Successfully");
                } else {
                    System.out.println("Data not updated");
                }
                break;
            case 3:
                x = ud.deleteEmployee(ub);
                if (x > 0) {
                    System.out.println("Data Deleted Successfully");
                } else {
                    System.out.println("Data Not Deleted");
                }
                break;
            case 4:
                ud.findALl();
                break;
            case 5:
                ud.findById(ub);
                break;
            case 6:
                ud.findByDeptno(ub);
                break;
            case 7:
                ud.findByJob(ub);
                break;
            case 8:
                ud.findByHiredate(ub);
                break;
            case 9:
                ud.calculateSumSalaryAllEmployee();
                break;
            case 10:
                ud.calculateSumSalaryOfDeptNo(ub);
                break;
            case 11:
                ud.calculateSumSalaryOfJob(ub);
                break;
            case 12:
                ud.calculateAvgSalaryAllEmployee();
                break;
            case 13:
                ud.calculateAvgSalaryOfDeptNo(ub);
                break;
            case 14:
                ud.maxSalaryEmployeeData();
                break;
            case 15:
                ud.minSalaryEmployeeData();
                break;
            case 16:
                ud.countNoOfEmployee();
                break;
            case 17:
                ud.countNumberOfEmployeeInDept(ub);
                break;
            case 18:
                ud.sortDataBasisOfSalary();
                break;
            case 19:
                ud.sortDataBasisOfName();
                break;
            default:
                System.out.println("Enter Valid Choice");

        }

    }
}
