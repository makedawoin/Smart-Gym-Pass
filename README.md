We built a complete gym management system called Smart Gym Pass System. The system allows members to join, buy passes, check in, and track their gym visits. It keeps track of everything, including members, passes, check-ins, and payments.

Team Members and Responsibilities

We are a team of 5 people:

#Feven built the database connection so the program can communicate with MySQL and also handles all payment saving and tracking.

#Hasset created the pass system with 3 types of passes (Trial, Monthly, Yearly), where each pass type has different prices and durations.

#Huda built the attendance tracking system that records when members check in and check out.

#Makeda created the member profiles and member management system, handling everything related to gym members.

#Meron built the exception handling system with custom exceptions and the reporting system so admins can view reports.

   System Features

When the program runs, users can:

*Add new members to the gym

*View member details

*Update member information

*Issue passes to members (Trial, Monthly, or Yearly)

*Record check-in and check-out times

*Process payments

*View detailed reports about members, passes, check-ins, and finances


Technologies Used

We used:

*Java as the programming language

*MySQL as the database to store all data

*JDBC as the bridge between Java and MySQL


Setup Instructions

To set up the system:

1.Download and install MySQL Server on your computer (you can leave the password empty or set one).

2.The program automatically creates the database when it runs, so no manual setup is required.

3.In IntelliJ, go to Project Structure and add the MySQL JDBC driver.

4.If you set a password during MySQL installation, open DatabaseConnection.java and set it in:
private static final String DB_PASSWORD = "";

5.Right-click SmartGymPassSystem.java and click Run.


Program Menu

When you open the program, you will see 6 options:

1.Member Operations – add, view, or update members

2.Pass Operations – issue or renew passes

3.Check-in/Attendance – record gym visits

4.Payment Operations – process payments

5.Reports & Analytics – view reports

6.Exit – close the program


File Structure

Our project contains:

*Core classes: DatabaseConnection.java, SmartGymPassSystem.java

*Member classes: Member.java, MemberManager.java

*Pass classes: Pass.java, MonthlyPass.java, YearlyPass.java, TrialPass.java, PassManager.java

*Check-in classes: CheckIn.java, AttendanceTracker.java

*Payment classes: Payment.java, PaymentProcessor.java

*Exception classes: InvalidPassException.java, ExpiredPassException.java

*Report class: GymReport.java


Test Data

When you run the program for the first time, two test members are automatically created:

*Abebe (M001) with a monthly pass

*Almaz (M002) with a yearly pass

These are included for testing, or you can add your own members.


OOP Concepts Used

We learned and applied all 7 chapters of OOP:

1.Encapsulation – private fields with getters and setters

2.Inheritance – Pass classes extend the abstract Pass class

3.Polymorphism – different pass types calculate renewal cost differently

4.Abstraction – abstract Pass class with abstract methods

5.Exception Handling – custom exceptions for error handling

6.Files & Streams – report generation ready for saving

7.JDBC – database operations


Common Issues and Fixes
*“Cannot resolve symbol Pass” → ensure all class files are in the same folder and Pass classes exist

*“Connection refused” → make sure MySQL Server is running and the password in DatabaseConnection.java is correct

*“Table already exists” → this is normal; the program checks before creating tables, so it can be ignored


Future Improvements

We could add more features in the future, such as:

*Member login system

*Email notifications

*Membership renewal reminders

*Trainer scheduling

*Workout history tracking

If something does not work, check that:

*MySQL is running

*All Java files are in the same project

*JDBC driver is added to the project

*Password in DatabaseConnection is correct

Made by: Feven, Hasset, Huda, Makeda, and Meron.
